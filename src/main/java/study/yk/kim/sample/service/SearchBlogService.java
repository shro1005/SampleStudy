package study.yk.kim.sample.service;

import study.yk.kim.sample.dto.RequestBlogSearchDto;
import study.yk.kim.sample.dto.ResponseBlogSearchDto;
import study.yk.kim.sample.dto.ResponseTop10WordDto;

public interface SearchBlogService {
    ResponseBlogSearchDto findBlogByParam(RequestBlogSearchDto requestBlogSearchDto) throws Exception;

    ResponseTop10WordDto findTop10SerachWord();
}
