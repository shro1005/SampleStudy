package study.yk.kim.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.yk.kim.sample.api.BlogSearchApiClient;
import study.yk.kim.sample.dao.SearchWordRepository;
import study.yk.kim.sample.dto.*;
import study.yk.kim.sample.entity.SearchWord;
import study.yk.kim.sample.util.exception.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("SearchService")
public class SearchBlogSerivceImpl implements SearchBlogService {
    @Autowired
    SearchWordRepository blogSearchRepository;
    @Autowired
    BlogSearchApiClient blogSearchApiClient;

    @Override
    @Transactional
    public ResponseBlogSearchDto findBlogByParam(RequestBlogSearchDto requestBlogSearchDto) throws Exception {
        ResponseBlogSearchDto responseSearchDto;
        if(requestBlogSearchDto.getPage() > 50) {
            throw new InvalidArgumentPage("page 값이 50을 초과했습니다.");
        }
        if(requestBlogSearchDto.getSize() > 50) {
            throw new InvalidArgumentSize("size 값이 50을 초과했습니다.");
        }

        /** 검색어 저장 (검색어 및 검색 횟수)*/
        SearchWord blogSearch = blogSearchRepository.findBySearchWord(requestBlogSearchDto.getQuery())
                .orElseGet(() -> SearchWord.builder()
                        .searchWord(requestBlogSearchDto.getQuery())
                        .searchCnt(0l)
                        .build());

        blogSearch.increaseSearchCnt();
        blogSearchRepository.save(blogSearch);

        try {
            /** 1. 카카오 API 호출 */
            ResponseKakaoBlogSearchDto kakaoBlogSearchDto = blogSearchApiClient.requestKaKaoBlogSearchApi(requestBlogSearchDto);
            responseSearchDto = parseToResponseSearchDto(kakaoBlogSearchDto);
            responseSearchDto.setPage(requestBlogSearchDto.getPage());
            responseSearchDto.setSize(requestBlogSearchDto.getSize());
        } catch (KakaoApiException kakaoErr) {
            /** 2. 네이버 API 호출*/
            try {
                ResponseNaverBlogSearchDto naverBlogSearchDto = blogSearchApiClient.requestNaverBlogSearchApi(requestBlogSearchDto);
                responseSearchDto = parseToResponseSearchDto(naverBlogSearchDto);
            } catch (NaverApiException naverErr) {
                log.info(naverErr.getMessage());
                throw new AllApiException("현재 모든 외부 오픈 API 오류로 서비스 사용이 불가합니다.");
            }
        }

        return responseSearchDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseTop10WordDto findTop10SerachWord() {
        List<Top10WordDto> top10WordDtoList = blogSearchRepository.findTop10ByOrderBySerachCntDesc();

        ResponseTop10WordDto responseTop10WordDto = ResponseTop10WordDto.builder()
                .dataList(top10WordDtoList)
                .build();

        return responseTop10WordDto;
    }

    /**
     * parseToResponseSearchDto : 카카오 및 네이버 API 호출 결과를 우리 API 명세에 맞게 파싱
     * */
    private<T> ResponseBlogSearchDto parseToResponseSearchDto(T dto) {
        ResponseBlogSearchDto responseSearchDto = null;
        String classNm = dto.getClass().getName();
        classNm = classNm.substring(classNm.lastIndexOf(".")+1);

        List<DocumentDto> dataList = new ArrayList<>();

        if ("ResponseKakaoBlogSearchDto".equals(classNm)) {
            ResponseKakaoBlogSearchDto kakaoBlogSeachDto = (ResponseKakaoBlogSearchDto) dto;

            kakaoBlogSeachDto.getDocuments().forEach(item -> {
                String date = item.getDatetime();

                DocumentDto data = DocumentDto.builder()
                        .title(item.getTitle())
                        .contents(item.getContents())
                        .url(item.getUrl())
                        .blogname(item.getBlogname())
                        .thumbnail(item.getThumbnail())
                        .datetime(date.substring(0, date.indexOf("T")))
                        .build();
                dataList.add(data);
            });

            responseSearchDto = ResponseBlogSearchDto.builder()
                    .total_cnt(kakaoBlogSeachDto.getMeta().getTotal_count())
                    .end_yn(kakaoBlogSeachDto.getMeta().is_end())
                    .dataList(dataList)
                    .build();

        } else if ("ResponseNaverBlogSearchDto".equals(classNm)) {
            ResponseNaverBlogSearchDto naverBlogSeachDto = (ResponseNaverBlogSearchDto) dto;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

            naverBlogSeachDto.getItems().forEach(item -> {
                String date = "";
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").format(dateFormat.parse(item.getPostdate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                DocumentDto data = DocumentDto.builder()
                        .title(item.getTitle())
                        .contents(item.getDescription())
                        .url(item.getLink())
                        .blogname(item.getBloggername())
                        .thumbnail("")
                        .datetime(date)
                        .build();
                dataList.add(data);
            });

            responseSearchDto = ResponseBlogSearchDto.builder()
                    .total_cnt(naverBlogSeachDto.getTotal())
                    /** total 또는 2500 <= start(page) * display(size) */
                    .end_yn(naverBlogSeachDto.getTotal() <= naverBlogSeachDto.getStart() * naverBlogSeachDto.getDisplay() ||
                            2500 <= naverBlogSeachDto.getStart() * naverBlogSeachDto.getDisplay())
                    .size(naverBlogSeachDto.getDisplay())
                    .page(naverBlogSeachDto.getStart())
                    .dataList(dataList)
                    .build();
        }
        return responseSearchDto;
    }
}
