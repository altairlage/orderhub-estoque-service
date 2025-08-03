
# Módulo 3: Microsserviço de Gerenciamento de Estoque (estoque-service)

## 3.1. Introdução e Objetivo

### Descrição do Problema

No contexto do sistema de gerenciamento de pedidos **OrderHub**, o controle preciso e em tempo real do estoque é essencial. A ausência de um gerenciamento eficaz pode ocasionar vendas de produtos indisponíveis, gerando insatisfação do cliente e problemas operacionais.  
Para mitigar esses riscos, foi desenvolvido um **microsserviço dedicado** para centralizar todas as operações relacionadas ao estoque, como consulta, reposição e baixa de itens, garantindo a consistência e integridade dos dados.

### Objetivo

Desenvolver um microsserviço de estoque robusto, modular e desacoplado. Esse serviço será responsável por gerenciar a quantidade de produtos disponíveis, oferecendo **endpoints claros, seguros e transacionais** para integração com outros módulos do sistema.

---

## 3.2. Arquitetura do Sistema

### Descrição da Arquitetura

A arquitetura do `estoque-service` segue os princípios do padrão **MVC** e do **Clean Code**, com forte separação de responsabilidades, legibilidade e foco em manutenibilidade.  
A lógica de negócio centralizada está no **orderhub-core**, promovendo reuso e desacoplamento entre os serviços.

### Camadas do Sistema

- **Controller**  
  Responsável por expor a API REST, receber requisições HTTP, validar entradas e delegar o processamento à camada de negócio.  
  *Classe principal:* `EstoqueApiController`

- **Service (Core)**  
  Contém a lógica de negócio encapsulada nos use cases do módulo `orderhub-core`, como:  
  `BaixarEstoqueUseCase`, `ReporEstoqueUseCase` e `ConsultarEstoquePorSkuUseCase`.  
  Garante regras como: **"não permitir estoque negativo"**.

- **Gateway / Persistence**  
  Responsável pela comunicação com o banco de dados, usando **Spring Data JPA**.  
  *Classes principais:* `EstoqueRepositoryJpaGatewayImpl` e `EstoqueRepository`

- **Banco de Dados**  
  Utiliza **H2 em memória** durante o desenvolvimento e testes. O versionamento do schema é feito via **Flyway**, garantindo consistência e rastreabilidade.

- **Uso de Docker**  
  O projeto é containerizado via **Docker**, possibilitando ambientes homogêneos para desenvolvimento, testes e produção.  
  O `Dockerfile` define todas as etapas de build e empacotamento da aplicação.

### Outros Detalhes Técnicos

- **Build Tool:** Maven  
- **Porta padrão:** 8081  
- **Gerenciamento de configurações:** `application.properties`  
- **Testes:** Unitários e de integração  
- **Cobertura de código:** JaCoCo

---

## 3.3. Descrição dos Endpoints da API

### Tabela de Endpoints

| Endpoint                               | Método | Descrição                                                  |
|----------------------------------------|--------|------------------------------------------------------------|
| `/api/estoques/{id}`                  | GET    | Consulta o estado atual do estoque de um item pelo seu ID |
| `/api/estoques/{id}/repor`           | POST   | Adiciona uma quantidade específica ao estoque de um item  |
| `/api/estoques/{id}/baixar`          | POST   | Dá baixa (reduz) uma quantidade específica do estoque     |
| `/api/estoques/baixar-multiplos`     | POST   | Dá baixa em múltiplos itens de forma atômica              |

---

### Exemplos de Requisição e Resposta

#### 🔄 Repor Estoque

**Requisição:**

```http
POST /api/estoques/1/repor
Content-Type: application/json
```

```json
{
  "quantidade": 50
}
```

**Resposta: 200 OK**

```json
{
  "idProduto": 1,
  "quantidadeDisponivel": 150,
  "criadoEm": "2025-08-03T01:08:26.896151",
  "atualizadoEm": "2025-08-03T04:15:00.123456"
}
```

---

#### 🔽 Baixar Múltiplos Itens

**Requisição:**

```http
POST /api/estoques/baixar-multiplos
Content-Type: application/json
```

```json
[
  {
    "produto": {
      "id": 2
    },
    "quantidade": 5
  },
  {
    "produto": {
      "id": 4
    },
    "quantidade": 3
  }
]
```

**Resposta: 204 No Content**  
(Sem corpo na resposta)

---

## 3.4. Configuração e Execução do Projeto

### Pré-requisitos

- Docker e Docker Compose  
- Java 21 ou superior  
- Maven 3.8+

### Passos para Execução

**Construir o Projeto**

```bash
mvn clean install
```

**Executar pela IDE**  
Rodar a classe `EstoqueServiceApplication.java`

**Executar via Maven**

```bash
mvn spring-boot:run
```

**Executar com Docker**

```bash
docker build -t estoque-service .
docker run -p 8081:8081 estoque-service
```

---

## 3.5. Qualidade do Código

### Boas Práticas Adotadas

- **DRY (Don't Repeat Yourself):** Regras centralizadas no `orderhub-core`
- **SOLID - SRP:** Separação entre Controller, Use Case, Mapper, Gateway e Entity
- **DIP:** Dependência por interfaces facilita testes e manutenções
- **Padrões Spring Boot:** Uso de `@RestController`, `@Component`, `@Repository`, `@Service`, etc.

---

## 3.6. Collections para Teste

Uma collection do **Postman** está disponível para facilitar os testes manuais.

**Arquivo:** `estoque-service.postman_collection.json`

---

## 3.7. Repositório do Código

O código-fonte está disponível no GitHub:

🔗 [GitHub - orderhub-estoque-service](https://github.com/altairlage/orderhub-estoque-service)