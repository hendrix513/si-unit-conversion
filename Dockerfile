FROM openjdk:8-slim
RUN mkdir /code
WORKDIR /code
COPY . /code/
CMD cd ./complete && ./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8000