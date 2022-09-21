package study.yk.kim.sample.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ResponseKakaoBlogSearchDto extends CommonResponseDto {
    private List<DocumentDto> documents;
    private Meta meta;

    public ResponseKakaoBlogSearchDto(int status, String message, List<DocumentDto> documents, Meta meta) {
        super(status, message);
        this.documents = documents;
        this.meta = meta;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Meta {
        int total_count;
        int pageable_count;
        boolean is_end;

        public Meta(int total_count, int pageable_count, boolean is_end) {
            this.total_count = total_count;
            this.pageable_count = pageable_count;
            this.is_end = is_end;
        }
    }
}
