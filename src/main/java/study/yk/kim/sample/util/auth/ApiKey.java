package study.yk.kim.sample.util.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class ApiKey {
    @Value("${kakao-api-key}")
    private String kakoApiKey;

    @Value("${naver-client-id}")
    private String naverClientId;

    @Value("${naver-client-secret}")
    private String naverClientSecret;

}
