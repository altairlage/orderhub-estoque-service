# Etapa 1: Build do core
FROM maven:3.9.6-eclipse-temurin-21 AS core-builder
WORKDIR /usr/src/app

COPY ../orderhub-core/orderhub-core ./  # assume que o core está um nível acima
RUN mvn clean install -DskipTests

# Etapa 2: Build do microserviço
FROM maven:3.9.6-eclipse-temurin-21 AS service-builder
WORKDIR /usr/src/app

# Copia o repositório Maven local com o core já instalado
COPY --from=core-builder /root/.m2 /root/.m2

# Copia o projeto atual (estoque-service)
COPY . ./
RUN mvn clean package -DskipTests

# Etapa 3: Imagem final do microserviço
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia o .jar gerado para a imagem final
COPY --from=service-builder /usr/src/app/target/orderhub-estoque-service-1.0.0.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
