# https://hub.docker.com/_/eclipse-temurin/
FROM eclipse-temurin:17

VOLUME /tmp

COPY target/orange-care-bscs.jar /opt/app/orange-care-bscs.jar

CMD ["bash", "-c", "java $JAVA_OPTS -jar /opt/app/orange-care-bscs.jar"]
