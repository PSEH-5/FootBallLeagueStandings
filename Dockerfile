FROM openjdk:latest

ADD target/leaguestandings-0.1.0.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 8080
