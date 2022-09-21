package study.yk.kim.sample.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Top10WordDto {
    private String searchWord;
    private Long searchCnt;
    private int rank;

    public Top10WordDto(String searchWord, Long searchCnt, int rank) {
        this.searchWord = searchWord;
        this.searchCnt = searchCnt;
        this.rank = rank;
    }
}
