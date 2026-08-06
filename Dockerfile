#Toma una imagen base que tiene Java 21 JDK ( necesario para compilar el proyecto)
#Le pongo el nombre "builder" para usarla despues
FROM maven:3.9.4-eclipse-temurin-21 AS builder

#Crea una carpeta llada /app dentro del contenedor
#y nos metemos ahi (como hacer cd /app)
WORKDIR /app

#Copia todos tus archivos (código, pom.xml, etc) desde tu computadora hacia
#dentro de la carpeta /app del contenedor
COPY . .

#Ejecuta Maven para compilar el código y generar el archivo .jar
#(tu aplicacion empaquetada)
RUN mvn clean package -DskipTests

#Segunda etapa. Ahora usamos una imagen mucho mas liviana que solo tiene 
#el JRE ( Java Runtime Environment ) para ejecutar la app, no para compilarla
FROM eclipse-temurin:21-jre

#Otra vez creamos la carpeta de trabajo para le etapa final
WORKDIR /app

#Copia mágica:toma el .jar que se generó en la primera etapa (builder) y
#lo copia a la nueva carpeta, renombrandolo como app.jar
COPY --from=builder /app/target/*.jar app.jar

#Le dice a Docker: "Oye, cuando este contenedor corra, voy a usar el puerto 8080 internamente"
#"(solo es informativo)"
EXPOSE 8080

#El comando que se ejecuta cuando el contenedor arranca: lanza tu aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

#Resumen del Dockerfile: Coges tu código, lo compilas con Maven en un entorno pesado, 
#te quedas solo con el .jar resultante, lo pones en un entorno liviano con solo Java y 
#lo ejecutas. Así tu imagen final pesa muy poco.