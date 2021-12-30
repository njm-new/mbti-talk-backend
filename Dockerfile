#!/bin/bash
FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#!/bin/bash

# docker build -t kwh6543/mbti-talk-backend:1.2 --platform linux/amd64 .
# docker push kwh6543/mbti-talk-backend:1.0