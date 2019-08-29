FROM openjdk:latest

ADD target/leaguestandings-0.1.0.jar appFootBall.jar

ENTRYPOINT ["java","-jar","appFootBall.jar"]

EXPOSE 8080
