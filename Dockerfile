FROM openjdk:8
ADD build/libs/wowlunteer-0.0.1-SNAPSHOT.jar wowlunteer-0.0.1-SNAPSHOT.jar
EXPOSE 8033
ENTRYPOINT ["java", "-jar", "wowlunteer-0.0.1-SNAPSHOT.jar"]