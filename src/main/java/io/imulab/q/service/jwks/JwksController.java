package io.imulab.q.service.jwks;

import org.jose4j.jwk.JsonWebKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/jwks.json")
public class JwksController {

    @Autowired
    private JwksSpec jwksSpec;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private Mono<String> renderJwks() {
        return Mono.just(jwksSpec.resolveJwks())
                .map(jwks -> jwks.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY));
    }
}
