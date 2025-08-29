# Desafio_Votacao

"No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você \n" +
"deve criar uma solução para dispositivos móveis para gerenciar e participar dessas sessões de votação. "

## Tecnologias usadas

- Java 17
- Spring Boot
- PostgreSQL
- Docker & Docker Compose
- MapperStruct
- Gernciador de dependências Maven
---

## Pré-requisitos

- Docker instalado ([link](https://docs.docker.com/get-docker/))
- Docker Compose instalado (normalmente já vem com Docker Desktop)

---

## Do Banco de Dados

O projeto faz uso do **postgres**, que deve se instalado em um
container no docker;

Na raiz do projeto, o arqivo .env contém todos os dados do banco.

---

## Configuração do ambiente


O `docker-compose.yml` está na pasta docker na raiz do projeto, e 
configurado para usar as variáveis contidas no arquivo **.env**, já citado.

---
## Executando o projeto com Docker

No terminal, na raiz do projeto, execute:

```bash
docker-compose up --build
```

Isso vai:

- Construir a imagem da aplicação
- Subir os containers da aplicação e do banco PostgreSQL
- Configurar o banco com as variáveis do `.env`

A aplicação ficará disponível em: [http://localhost:8080](http://localhost:8080)

---

## Parando a execução

Para parar e remover os containers, execute:

```bash
docker-compose down
```

---

## Estrutura do projeto

- `src/main/java`: Código fonte da aplicação
- `src/main/resources`: Configurações e arquivos estáticos
- `docker-compose.yml`: Configuração do Docker Compose
- `.env`: Variáveis de ambiente (não versionar se conter dados sensíveis)
- `Dockerfile`: Dockerfile para a aplicação
- `src/main/java`: Testes automatizados
---

## Documentação Swagger

http://localhost:8080/swagger-ui/index.html
---

## Considerações finais

- Certifique-se que as portas 8080 (app) e 5433 (Postgres) estão livres no seu sistema.
- Para limpar volumes e dados do banco, remova o volume `postgres-data` com:

```bash
docker volume rm postgres-data
```

---
##### Projeto no GitHub:  https://github.com/SauloHrodrigues/Desafio_Votacao

## Autor:

### Nome: Saulo Henrique Rodrigues

##### LinkedIn: https://www.linkedin.com/in/saulohenriquerodrigues/
