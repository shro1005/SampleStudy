package study.yk.kim.sample.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.yk.kim.sample.dto.Top10WordDto;
import study.yk.kim.sample.entity.SearchWord;

import java.util.List;
import java.util.Optional;

public interface SearchWordRepository extends JpaRepository<SearchWord, String> {
    Optional<SearchWord> findBySearchWord(String searchWord);

    @Query(nativeQuery = true, name = "SearchWord.GetTop10OrderBySerachCntDesc")
    List<Top10WordDto> findTop10ByOrderBySerachCntDesc();
}
