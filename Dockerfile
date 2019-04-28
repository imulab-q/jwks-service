FROM openjdk:8-jre-alpine3.9

ADD build/libs/jwks-service-*.jar jwks-service.jar

ENTRYPOINT ["java", "-jar", "-noverify", "-Dspring.config.location=classpath:/application.yaml", "jwks-service.jar"]
