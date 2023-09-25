# Utiliza una imagen base de Java
FROM arm64v8/eclipse-temurin:17-jdk

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR compilado a la imagen
COPY target/*.jar /app/backend.jar
ENV DATABASE_URL "jdbc:postgresql://localhost:5432/bible_db"
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/backend.jar"]


# Path: Dockerfile

FROM arm64v8/eclipse-temurin:17-jdk as build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM arm64v8/eclipse-temurin:17
ARG DEPENDENCY=/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENV USERNAME "postgres"
ENV PASSWORD "123456"
ENV DATABASE_URL "jdbc:postgresql://localhost:5432/bible_db"

ENTRYPOINT ["java","-cp","app:app/lib/*","bo.edu.ucb.bible.BibleApplicationKt"]