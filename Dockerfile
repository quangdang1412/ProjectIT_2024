FROM openjdk:21

ARG FILE_JAR=target/*.jar

ADD ${FILE_JAR} tap-hoa-it.jar

ENTRYPOINT ["java","-jar","tap-hoa-it.jar"]

EXPOSE 8080