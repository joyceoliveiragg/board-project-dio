FROM openjdk:21-jdk-slim

# Define diretório de trabalho
WORKDIR /app

# Copia o script de espera e o .jar da aplicação
COPY wait-for-it.sh wait-for-it.sh
COPY build/libs/app.jar app.jar

# Dá permissão de execução ao script
RUN chmod +x wait-for-it.sh

# (Opcional) expõe a porta usada pela aplicação
EXPOSE 8080

# Comando de entrada
ENTRYPOINT ["./wait-for-it.sh", "mysql-dio:3307", "--", "java", "-jar", "app.jar"]
