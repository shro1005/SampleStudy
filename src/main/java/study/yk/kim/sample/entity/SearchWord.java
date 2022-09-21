package study.yk.kim.sample.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import study.yk.kim.sample.dto.Top10WordDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "SearchResultMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = Top10WordDto.class,
                                columns = {
                                        @ColumnResult(name = "search_word", type = String.class),
                                        @ColumnResult(name = "search_cnt", type = Long.class),
                                        @ColumnResult(name = "rank", type = Integer.class)
                                }
                        )
                }
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "SearchWord.GetTop10OrderBySerachCntDesc", query = "" +
                "select sw.search_word,         " +
                "       sw.search_cnt," +
                "       (ROW_NUMBER() OVER(order by sw.search_cnt desc, sw.search_word asc)) as rank      " +
                "  from search_word sw       " +
                " order by sw.search_cnt desc," +
                "          sw.search_word asc "+
                " limit 10"
                , resultSetMapping = "SearchResultMapping")
})
@Table(name = "search_word")
@NoArgsConstructor
public class SearchWord {
    @Id
    @Column(name = "search_word")
    private String searchWord;

    @Column(name = "search_cnt")
    private Long searchCnt;

    @CreationTimestamp
    @Column(name = "rgt_dtm", nullable = false)
    private LocalDateTime rgtDtm;

    @UpdateTimestamp
    @Column(name = "udt_dtm")
    private LocalDateTime udtDtm;

    public SearchWord(String searchWord, Long searchCnt, LocalDateTime rgtDtm, LocalDateTime udtDtm) {
        this.searchWord = searchWord;
        this.searchCnt = searchCnt;
        this.rgtDtm = rgtDtm;
        this.udtDtm = udtDtm;
    }

    public void increaseSearchCnt() {
        searchCnt ++;
    }
}
