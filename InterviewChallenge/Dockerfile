FROM openjdk:latest
VOLUME /tmp
COPY databasePublishers.json databasePublishers.json
COPY publishersFileList.txt publishersFileList.txt
COPY target/InterviewChallenge-0.0.1-SNAPSHOT.jar InterviewChallenge.jar
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=docker", "/InterviewChallenge.jar" ]