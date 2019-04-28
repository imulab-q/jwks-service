package io.imulab.q.service.jwks;

import org.jose4j.jwk.JsonWebKeySet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class AppTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testAppHasAGreeting() {
        this.webClient.get().uri("/jwks.json").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody().consumeWith(entityExchangeResult -> {
            Assert.assertNotNull(entityExchangeResult.getResponseBody());
            String json = new String(entityExchangeResult.getResponseBody(), StandardCharsets.UTF_8);
            try {
                new JsonWebKeySet(json);
            } catch (Throwable t) {
                Assert.fail(t.getMessage());
            }
        });
    }
}
