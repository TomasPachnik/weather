# docker build -f Dockerfile -t tomas487/erp-arm:0.2 .
FROM openjdk:11-ea-28-jre-slim
MAINTAINER tomas487
VOLUME /log
ADD target/wm-0.0.1-SNAPSHOT.jar wm.jar
ENV TZ Europe/Bratislava
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/wm.jar"]
