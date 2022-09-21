package study.yk.kim.sample.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import study.yk.kim.sample.dto.*;
import study.yk.kim.sample.util.auth.ApiKey;
import study.yk.kim.sample.util.exception.KakaoApiException;
import study.yk.kim.sample.util.exception.NaverApiException;

@Slf4j
@Service
@AllArgsConstructor
public class BlogSearchApiClient {
    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/search/blog?query={query}&sort={sort}&page={page}&size={size}";
    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/search/blog.json?query={query}&sort={sort}&start={page}&display={size}";
    private final ApiKey API_KEY;
    private final RestTemplate restTemplate;

    public ResponseKakaoBlogSearchDto requestKaKaoBlogSearchApi(RequestBlogSearchDto requestDto) throws KakaoApiException {
        ResponseKakaoBlogSearchDto kakaoBlogSeachDto = null;
        String kakaoKey = API_KEY.getKakoApiKey();

        /** header 생성 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK " + kakaoKey);
        HttpEntity entity = new HttpEntity(headers);

        /**kakao API 요청*/
        try {
            kakaoBlogSeachDto = restTemplate.exchange(KAKAO_API_URL, HttpMethod.GET, entity, ResponseKakaoBlogSearchDto.class
                    , requestDto.getQuery(), requestDto.getSort(), requestDto.getPage(), requestDto.getSize())
                    .getBody();

            /** kakao 결과가 0건이면 다음 api호출을 위한 excepxtion 발생*/
            if(kakaoBlogSeachDto.getMeta().getTotal_count() == 0) {
                throw new KakaoApiException("Kakao api 응단 건수 0건");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new KakaoApiException("Kakao api 호출 오류 발생");
        }

        return kakaoBlogSeachDto;
    }

    public ResponseNaverBlogSearchDto requestNaverBlogSearchApi(RequestBlogSearchDto requestDto) throws NaverApiException {
        ResponseNaverBlogSearchDto naverBlogSeachDto = null;
        String naverClientId = API_KEY.getNaverClientId();
        String naverClientSecret = API_KEY.getNaverClientSecret();

        /** header 생성 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        HttpEntity entity = new HttpEntity(headers);

        /** naver API 요청*/
        try {
            naverBlogSeachDto = restTemplate.exchange(NAVER_API_URL, HttpMethod.GET, entity, ResponseNaverBlogSearchDto.class
                    , requestDto.getQuery()
                    , ("recency".equals(requestDto.getSort()) ? "date" : "sim")
                    , (requestDto.getPage() == null ? 1 : requestDto.getPage())
                    , (requestDto.getSize() == null ? 10 : requestDto.getSize()))
                    .getBody();

        } catch (Exception e) {
            throw new NaverApiException("Naver api 호출 오류 발생");
        }

        return naverBlogSeachDto;
    }

}
