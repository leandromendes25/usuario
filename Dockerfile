from gradle:jdk21 as build
WORKDIR /app
COPY . .
run gradle build --no-daemon #daemon acelera o build


FROM eclipse-temurin:21-jre
WORKDIR /app

COPY build/libs/*.jar /app/usuario.jar
expose 8080
CMD ["java","-jar","/app/usuario.jar"]