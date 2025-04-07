
# 🧩 Board Project - DIO

Este projeto é uma aplicação de gerenciamento de quadros (boards), desenvolvida como estudo prático com base em um desafio da Digital Innovation One (DIO). Utiliza **Java**, **PostgreSQL** e **Docker** para simular uma estrutura de aplicação real.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Gradle
- PostgreSQL
- Docker e Docker Compose
- JDBC
- Liquibase (para versionamento de banco)

---

## 📁 Estrutura do Projeto

```
├── src
│   └── main/java/br/com/dio
│       ├── Main.java
│       ├── persistence
│       │   ├── config/ConnectionConfig.java
│       │   ├── entity
│       │   ├── repository
│       │   └── service
│       └── utils
├── build.gradle.kts
├── docker-compose.yml
└── README.md
```

---

## ⚙️ Como Rodar o Projeto

### 1. 🔄 Limpar e compilar o projeto

```bash
./gradlew clean build
```

### 2. 🐳 Subir os containers com Docker Compose

```bash
docker-compose up -d
```

Isso criará o serviço PostgreSQL no container `board_postgres`.

### 3. ✅ Verificar se o banco está rodando

```bash
docker ps
```

---

## 📦 Rodar o .JAR da aplicação

Após compilar, rode:

```bash
java -jar build/libs/app.jar
```

A aplicação tentará se conectar ao banco PostgreSQL no container Docker.

---

## 🐘 Configuração do PostgreSQL

Certifique-se que o host do banco seja o mesmo do container Docker.

### Exemplo no `ConnectionConfig.java`:

```java
String url = "jdbc:postgresql://localhost:5432/postgres";
```

Caso esteja rodando **dentro de um container** com rede, use:

```java
String url = "jdbc:postgresql://board_postgres:5432/postgres";
```

---

## 📄 Scripts de Banco

Scripts de criação de tabelas e dados iniciais são gerenciados com **Liquibase**, dentro do projeto.

---

## ✅ Objetivo

Aprender na prática como:

- Configurar uma aplicação Java com banco de dados
- Utilizar Docker e Docker Compose
- Gerenciar estrutura de banco com Liquibase
- Rodar aplicações com `.jar` e conectar com containers externos

---

## 📚 Créditos

Baseado no projeto do repositório oficial da DIO com personalizações para prática e estudo individual.

---
