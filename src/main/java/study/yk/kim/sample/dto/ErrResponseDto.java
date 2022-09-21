package study.yk.kim.sample.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ErrResponseDto extends CommonResponseDto {
    private String errorType;

    public ErrResponseDto(String errorType, int status, String message) {
        super(status, message);
        this.errorType = errorType;
    }
}
