# Utiliza una imagen base de Java
FROM arm64v8/eclipse-temurin:17-jdk

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR compilado a la imagen
COPY target/*.jar app.jar
ENV DATABASE_URL "jdbc:postgresql://localhost:5432/bible_db"

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/backend.jar"]