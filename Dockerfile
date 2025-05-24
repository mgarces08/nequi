FROM eclipse-temurin:21-jdk-alpine

# Crear un usuario no root
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

COPY build/libs/nequi.jar nequi.jar
COPY wait-for-mysql.sh wait-for-mysql.sh

RUN chmod +x wait-for-mysql.sh

# Cambiar al usuario no root
USER spring

EXPOSE 8080

ENTRYPOINT ["./wait-for-mysql.sh"]
