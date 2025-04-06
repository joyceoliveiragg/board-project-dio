FROM openjdk:21

WORKDIR /app

COPY build/libs/app.jar app.jar

CMD ["java", "-jar", "app.jar"]
