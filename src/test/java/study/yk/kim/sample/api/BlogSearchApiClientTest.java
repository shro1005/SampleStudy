package study.yk.kim.sample.api;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import study.yk.kim.sample.dto.RequestBlogSearchDto;
import study.yk.kim.sample.dto.ResponseKakaoBlogSearchDto;
import study.yk.kim.sample.dto.ResponseNaverBlogSearchDto;
import study.yk.kim.sample.util.auth.ApiKey;
import study.yk.kim.sample.util.exception.KakaoApiException;
import study.yk.kim.sample.util.exception.NaverApiException;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogSearchApiClientTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ApiKey API_KEY;

    @Test
    public void 카카오API호출() throws KakaoApiException {
        RequestBlogSearchDto requestBlogSearchDto = RequestBlogSearchDto.builder()
                .query("테스트")
                .sort("accuracy")
                .page(1)
                .size(10)
                .build();

        API_KEY.setKakoApiKey("7a93e35e74be88ea2b3f78f76681559d");

        //given
        BlogSearchApiClient blogSearchApiClient = new BlogSearchApiClient(API_KEY, restTemplate);
        ResponseKakaoBlogSearchDto kakaoBlogSearchDto = blogSearchApiClient.requestKaKaoBlogSearchApi(requestBlogSearchDto);

        //then
        assertNotNull(kakaoBlogSearchDto);
    }

    @Test
    public void 네이버API호출() throws NaverApiException {
        RequestBlogSearchDto requestBlogSearchDto = RequestBlogSearchDto.builder()
                .query("테스트")
                .sort("accuracy")
                .page(1)
                .size(10)
                .build();

        API_KEY.setNaverClientId("0EZgjYRPIFtiSwVdJ6fx");
        API_KEY.setNaverClientSecret("xrkw6qNMfB");

        //given
        BlogSearchApiClient blogSearchApiClient = new BlogSearchApiClient(API_KEY, restTemplate);
        ResponseNaverBlogSearchDto naverBlogSeachDto = blogSearchApiClient.requestNaverBlogSearchApi(requestBlogSearchDto);

        //then
        assertNotNull(naverBlogSeachDto);
    }

}
