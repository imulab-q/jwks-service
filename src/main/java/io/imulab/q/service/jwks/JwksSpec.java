package io.imulab.q.service.jwks;

import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@ConfigurationProperties(prefix = "jwks")
public class JwksSpec {

    private static Logger logger = LoggerFactory.getLogger(JwksSpec.class);

    /**
     * Json representation of the key set, passed by value.
     */
    private String value = "";

    /**
     * File path to the json web key set, passed by reference.
     */
    private String path = "";

    public void setValue(String value) {
        this.value = value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Resolve the json web key set from either path or value. When both are specified, path takes precedence.
     *
     * @return the resolve json web key set.
     */
    JsonWebKeySet resolveJwks() {
        try {
            if (!path.isEmpty()) {
                String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
                return new JsonWebKeySet(json);
            } else {
                return new JsonWebKeySet(value);
            }
        } catch (JoseException | IOException e) {
            logger.error("failed to resolve jwks", e);
            throw new RuntimeException("failed to resolve jwks");
        }
    }

    @PostConstruct
    public void valueOrPath() {
        if (value.isEmpty() && path.isEmpty())
            throw new IllegalStateException("one of jwks.[value|path] must be specified.");
    }
}
