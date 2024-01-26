package me.kenux.travelog.zstudy.restclient;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Disabled
class RestClientTest {

    RestClient restClient;
    private String url = "http://localhost:3000/books";

    @BeforeEach
    void setup() {
        restClient = RestClient.create();
    }

    @Test
    void createRestClientTest() {
        RestClient restClientBasic = RestClient.create();
        assertThat(restClientBasic).isNotNull();
        RestClient restClientWithRestTemplate = RestClient.create(new RestTemplate()); // 기존 RestTemplate 설정을 이용.
        assertThat(restClientWithRestTemplate).isNotNull();
        RestClient restClientWithBuilder = RestClient.builder()
                .baseUrl("http://localhost:3000")
                .build();
        assertThat(restClientWithBuilder).isNotNull();
    }

    @Test
    void restClientTest1() {
        final String result = restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
        log.info(result);
    }

    @Test
    void test2() {
        final String result = restClient.method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .body(String.class);
        log.info(result);
    }

    @Test
    void test3() {
        final ResponseEntity<String> result = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);
        log.info("Response status: {}", result.getStatusCode());
        log.info("Response headers: {}", result.getHeaders());
        log.info("Response content: {}", result.getBody());
    }

    @Test
    void test4() {
        String result = restClient.get()
                .uri(url + "/not-exist-number")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new IllegalArgumentException(response.getStatusCode() + " : "+ response.getStatusText());
                }))
                .body(String.class);
    }

    @Test
    void test5() {
        final String result = restClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange(((clientRequest, clientResponse) -> {
                    if (clientResponse.getStatusCode().is4xxClientError()) {
                        throw new IllegalArgumentException(clientResponse.getStatusCode().toString());
                    } else {
                        return Objects.requireNonNull(clientResponse.bodyTo(String.class));
                    }
                }));
        log.info("Response content: {}", result);
    }
}
