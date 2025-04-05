# TaskBoard

Este é o TaskBoard, um sistema simplificado de gerenciamento de tarefas baseado em quadros, colunas e cartões. Ele foi adaptado e personalizado para manter a funcionalidade essencial de um kanban digital, com ênfase em organização e clareza no código.

## ⚙️ Estrutura do Projeto

A estrutura segue um modelo tradicional com as seguintes entidades principais:

- **Board**: representa o quadro principal de tarefas.
- **Column**: colunas dentro do quadro (ex: To Do, Doing, Done).
- **Card**: tarefa em si, com campos adicionais como prioridade e tags.
- **CardMovement**: histórico de movimentações dos cartões.
- **CardBlock**: permite bloqueio temporário dos cartões por usuários.

Campos adicionais incluídos:
- `Card.priority`: low, medium, high.
- `Card.tags`: lista de tags.
- `CardBlock.blockedByUser`: usuário responsável pelo bloqueio.

Funcionalidades diferenciadas:
- Filtro por prioridade ou tags.
- Restrições de movimentação (ex: não mover mais de uma vez em 10 segundos).
- Interface visual diferenciada (layout lateral, nova paleta de cores).

## 🧪 Executando o Projeto

Requisitos:
- Java 17+
- Node.js (para frontend, se aplicável)
- Docker (para serviços auxiliares como banco de dados)

### Desenvolvimento

```bash
./mvnw
```

### Testes

```bash
./mvnw verify
```

### Construção para Produção

```bash
./mvnw -Pprod clean verify
```

### Execução do JAR

```bash
java -jar target/*.jar
```

A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

## 🐳 Suporte a Docker

```bash
docker compose -f src/main/docker/app.yml up -d
```

## 📁 Localização dos Arquivos

- `src/main/java`: código backend (entidades, repositórios, serviços, etc).
- `src/main/resources`: arquivos de configuração (application.yml, etc).
- `src/test/java`: testes automatizados.
- `src/main/webapp` (se existir): frontend Angular.

---

Este projeto foi iniciado com JHipster mas adaptado para ser mais leve, modular e focado nas funcionalidades essenciais de um task board.