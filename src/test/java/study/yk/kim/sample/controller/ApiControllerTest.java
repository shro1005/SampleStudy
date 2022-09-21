package study.yk.kim.sample.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import study.yk.kim.sample.dto.DocumentDto;
import study.yk.kim.sample.dto.ResponseBlogSearchDto;
import study.yk.kim.sample.dto.ResponseTop10WordDto;
import study.yk.kim.sample.dto.Top10WordDto;
import study.yk.kim.sample.service.SearchBlogSerivceImpl;
import study.yk.kim.sample.util.exception.AllApiException;
import study.yk.kim.sample.util.exception.InvalidArgumentPage;
import study.yk.kim.sample.util.exception.InvalidArgumentSize;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class ApiControllerTest {
    @InjectMocks
    private ApiController apiController;
    @Mock
    private SearchBlogSerivceImpl searchBlogSerivce;
    @Mock
    private ExceptionController exceptionController;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiController)
                .setControllerAdvice(new ExceptionController())
                .build();
    }

    @Test
    void 블로그_API_조회() throws Exception {
        //given
        MultiValueMap<String, String> requestBlogSearchDto = new LinkedMultiValueMap<>();
        requestBlogSearchDto.add("query", "조회테스트");
        requestBlogSearchDto.add("sort", "accuracy");
        requestBlogSearchDto.add("page", "1");
        requestBlogSearchDto.add("size", "10");

        List<DocumentDto> dataList = new ArrayList<>();
        dataList.add(DocumentDto.builder().build());

        ResponseBlogSearchDto responseBlogSearchDto = ResponseBlogSearchDto.builder()
                .page(1)
                .end_yn(false)
                .size(10)
                .dataList(dataList)
                .build();

        given(searchBlogSerivce.findBlogByParam(any())).willReturn(responseBlogSearchDto);

        //when & then
        mockMvc.perform(get("/blog/search")
                .contentType(MediaType.APPLICATION_JSON)
                .params(requestBlogSearchDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.end_yn").value(false))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    void 상위_검색어_조회() throws Exception {
        //given
        ResponseTop10WordDto responseTop10WordDto = ResponseTop10WordDto.builder()
                .dataList(new ArrayList<Top10WordDto>())
                .build();

        given(searchBlogSerivce.findTop10SerachWord()).willReturn(responseTop10WordDto);

        mockMvc.perform(get("/blog/topword")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void Page_50_초과_요청시() throws Exception {
        //given
        MultiValueMap<String, String> requestBlogSearchDto = new LinkedMultiValueMap<>();
        requestBlogSearchDto.add("query", "조회테스트");
        requestBlogSearchDto.add("sort", "accuracy");
        requestBlogSearchDto.add("page", "100");
        requestBlogSearchDto.add("size", "10");

        //when
        when(apiController.searchBlog(any()))
                .thenThrow(new InvalidArgumentPage("page 값이 50을 초과했습니다."));

        // then
        mockMvc.perform(get("/blog/search")
                .contentType(MediaType.APPLICATION_JSON)
                .params(requestBlogSearchDto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("page 값이 50을 초과했습니다."))
                .andExpect(jsonPath("$.errorType").value("InvalidArgument"));

    }

    @Test
    void Size_50_초과_요청시() throws Exception {
        //given
        MultiValueMap<String, String> requestBlogSearchDto = new LinkedMultiValueMap<>();
        requestBlogSearchDto.add("query", "조회테스트");
        requestBlogSearchDto.add("sort", "accuracy");
        requestBlogSearchDto.add("page", "1");
        requestBlogSearchDto.add("size", "100");

        //when
        when(apiController.searchBlog(any()))
                .thenThrow(new InvalidArgumentSize("size 값이 50을 초과했습니다."));

        // then
        mockMvc.perform(get("/blog/search")
                .contentType(MediaType.APPLICATION_JSON)
                .params(requestBlogSearchDto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("size 값이 50을 초과했습니다."))
                .andExpect(jsonPath("$.errorType").value("InvalidArgument"));

    }

    @Test
    void 모든_API_호출실() throws Exception {
        //given
        MultiValueMap<String, String> requestBlogSearchDto = new LinkedMultiValueMap<>();
        requestBlogSearchDto.add("query", "조회테스트");
        requestBlogSearchDto.add("sort", "accuracy");
        requestBlogSearchDto.add("page", "1");
        requestBlogSearchDto.add("size", "10");

        //when
        when(apiController.searchBlog(any()))
                .thenThrow(new AllApiException("현재 모든 외부 오픈 API 오류로 서비스 사용이 불가합니다."));

        // then
        mockMvc.perform(get("/blog/search")
                .contentType(MediaType.APPLICATION_JSON)
                .params(requestBlogSearchDto))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("현재 모든 외부 오픈 API 오류로 서비스 사용이 불가합니다."))
                .andExpect(jsonPath("$.errorType").value("InternalServerError"));

    }
}
