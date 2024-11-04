# mvn clean package -P dev => build file jar với action Dev
# docker build -t api-image-sample .  => build image từ thư mục đang đứng
# docker run -it -p 80:80 --name=api-container api-image-sample => run image vừa build port 80
# docker-compose up -d  => chạy các service trong file docker-compose
# docker-compose up -d api-service => chạy 1 service api-service trong file docker-compose
# docker-compose down -v   => tắt các service
# docker-compose ps => show hoạt động của các container
FROM openjdk:21

ARG FILE_JAR=target/*.jar

ADD ${FILE_JAR} TapHoaIT.jar

ENTRYPOINT ["java","-jar","TapHoaIT.jar"]

EXPOSE 80