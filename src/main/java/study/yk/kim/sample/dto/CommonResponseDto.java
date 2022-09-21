package study.yk.kim.sample.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
public class CommonResponseDto {
    private int status;
    private String message;

    public CommonResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
