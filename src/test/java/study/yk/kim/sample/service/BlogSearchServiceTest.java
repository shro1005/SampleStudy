package study.yk.kim.sample.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import study.yk.kim.sample.dao.SearchWordRepository;
import study.yk.kim.sample.dto.RequestBlogSearchDto;
import study.yk.kim.sample.dto.ResponseTop10WordDto;
import study.yk.kim.sample.dto.Top10WordDto;
import study.yk.kim.sample.entity.SearchWord;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BlogSearchServiceTest {
    @Autowired
    SearchWordRepository searchWordRepository;
    @Autowired
    SearchBlogService searchBlogService;

    @Test
    public void 검색시_검색횟수_증가() throws Exception {
        //then
        RequestBlogSearchDto reqeustDto = RequestBlogSearchDto.builder()
                .query("카카오")
                .sort("accuracy")
                .page(1)
                .size(10)
                .build();

        SearchWord searchWord = searchWordRepository.findBySearchWord(reqeustDto.getQuery())
                .orElseGet(() -> SearchWord.builder()
                        .searchWord(reqeustDto.getQuery())
                        .searchCnt(0l)
                        .build());

        Long nowCnt = searchWord.getSearchCnt();

        //when
        searchBlogService.findBlogByParam(reqeustDto);

        //then
        Long afterService = searchWordRepository.findBySearchWord(reqeustDto.getQuery()).get().getSearchCnt();
        assertEquals("검색 횟수 증가 확인 ", (long)(nowCnt + 1l), (long) afterService);
    }

    @Test
    public void 상위_검색어_조회() {
        //given
        for (int i = 1 ; i <= 10 ; i ++) {
            SearchWord searchWord = SearchWord.builder()
                    .searchWord(i+"_zkzkdh")
                    .searchCnt((long)i)
                    .build();
            searchWordRepository.save(searchWord);
        }

        //when
        ResponseTop10WordDto responseBlogSearchDto = searchBlogService.findTop10SerachWord();
        List<Top10WordDto> dataList = responseBlogSearchDto.getDataList();

        //then
        assertEquals("건수 비교", dataList.size(), 10);

    }
}
