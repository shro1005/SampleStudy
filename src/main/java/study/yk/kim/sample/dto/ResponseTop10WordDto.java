package study.yk.kim.sample.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class ResponseTop10WordDto {
    private List<Top10WordDto> dataList;

    public ResponseTop10WordDto(List<Top10WordDto> dataList) {
        this.dataList = dataList;
    }

}
