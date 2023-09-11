# CREACION DEL CONTENEDOR
    1. Cambiar el nombre del proyecto en el archivo pom.xml
    2. Ejecutar el comando: mvn clean package
    3. Ejecutar el comando: docker build -t bible-backend:0.0.? .
    4. Ejecutar el comando: docker run --name bible-backend? - it -e DATABASE_URL=""jdbc:postgresql://192.168.1.7:5432/bible_db" -p 8080:8080 -d bible-backend:0.0.?

# ANOTACIONES

## Para verificar que el contenedor esta corriendo
    1. Ejecutar el comando: docker ps -a
## Para verificar que un puerto esta libre
    sudo lsof -i :5432 -n -P | grep LISTEN 
