package study.yk.kim.sample.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class DocumentDto {
    String blogname;
    String contents;
    String datetime;
    String thumbnail;
    String title;
    String url;

    public DocumentDto(String blogname, String contents, String datetime, String thumbnail, String title, String url) {
        this.blogname = blogname;
        this.contents = contents;
        this.datetime = datetime;
        this.thumbnail = thumbnail;
        this.title = title;
        this.url = url;
    }
}
