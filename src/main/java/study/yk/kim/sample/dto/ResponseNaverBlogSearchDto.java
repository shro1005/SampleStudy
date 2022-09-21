package study.yk.kim.sample.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ResponseNaverBlogSearchDto extends CommonResponseDto{
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    public ResponseNaverBlogSearchDto(int status, String message, String lastBuildDate, int total,
                                      int start, int display, List<Item> items) {
        super(status, message);
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Item {
        String title;
        String link;
        String description;
        String bloggername;
        String bloggerlink;
        String postdate;

        public Item(String title, String link, String description, String bloggername, String bloggerlink, String postdate) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.bloggername = bloggername;
            this.bloggerlink = bloggerlink;
            this.postdate = postdate;
        }
    }
}
