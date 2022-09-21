package study.yk.kim.sample.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class ResponseBlogSearchDto {
    private boolean end_yn;
    private int page;
    private int total_cnt;
    private int size;
    private List<DocumentDto> dataList;

    public ResponseBlogSearchDto(boolean end_yn, int page, int total_cnt, int size, List<DocumentDto> dataList) {
        this.end_yn = end_yn;
        this.page = page;
        this.total_cnt = total_cnt;
        this.size = size;
        this.dataList = dataList;
    }
}
