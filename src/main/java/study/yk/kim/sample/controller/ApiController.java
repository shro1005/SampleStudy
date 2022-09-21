package study.yk.kim.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.yk.kim.sample.dto.RequestBlogSearchDto;
import study.yk.kim.sample.dto.ResponseBlogSearchDto;
import study.yk.kim.sample.dto.ResponseTop10WordDto;
import study.yk.kim.sample.service.SearchBlogService;

@Slf4j
@RestController
public class ApiController {
    @Autowired
    private SearchBlogService searchService;

    @GetMapping("/blog/search")
    public ResponseBlogSearchDto searchBlog(RequestBlogSearchDto requestBlogSearchDto) throws Exception {
        ResponseBlogSearchDto searchDto = searchService.findBlogByParam(requestBlogSearchDto);

        return searchDto;
    }

    @GetMapping("/blog/topword")
    public ResponseTop10WordDto searchTop10SearchWord() {
        ResponseTop10WordDto top10WordDtoList = searchService.findTop10SerachWord();

        return top10WordDtoList;
    }
}