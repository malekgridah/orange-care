# https://hub.docker.com/_/eclipse-temurin/
FROM eclipse-temurin:17

VOLUME /tmp

COPY target/orange-care-gateway.jar /opt/app/orange-care-gateway.jar

CMD ["bash", "-c", "java $JAVA_OPTS -jar /opt/app/orange-care-gateway.jar"]
