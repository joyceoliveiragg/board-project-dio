FROM openjdk:21

WORKDIR /app

COPY build/libs/app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.profiles.active=prod"]
