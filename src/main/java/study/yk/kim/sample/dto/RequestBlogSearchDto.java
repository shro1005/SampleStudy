package study.yk.kim.sample.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class RequestBlogSearchDto {
    private String query;
    @Builder.Default
    private String sort = "accuracy";
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;

    public RequestBlogSearchDto(String query, String sort, Integer page, Integer size) {
        this.query = query;
        this.sort = sort;
        this.page = page;
        this.size = size;
    }
}
