![Projeto Customers](Hackaton-Logo-dark.png)

By [Stefanini](https://stefanini.com/).

# Desafio Final - Hackaton Orbitall 2025 - API de Gestão de Clientes e Transações

### 📜 Contexto:
Você está participando do Hackaton do setor financeiro e sua missão é desenvolver uma API REST em Java + Spring Boot para gerenciamento de clientes e registro de transações.
A API será utilizada por sistemas internos para cadastrar, consultar, atualizar e remover dados, além de processar transações simuladas.

### 🛠 Requisitos Técnicos
- Java 21
- Spring Boot
- Banco de dados em memória H2
- Retornar status codes corretos (HTTP)
- Uso correto dos verbos HTTP (GET, POST, PUT, DELETE)
- Boas práticas de nomenclatura REST (ex: /customers, /transactions)
- Tratamento de erros e exceções

### 📌 Funcionalidades Obrigatórias

1. Clientes (/customers)
- POST /customers → Cadastrar um novo cliente.
- GET /customers/{id} → Buscar cliente por ID.
- PUT /customers/{id} → Atualizar dados do cliente.
- DELETE /customers/{id} → Excluir cliente.
- GET /customers → Listar todos os clientes ativos no sistema.

```sh
+--------------------------------------+
| Customer                             | -> nome da classe
+--------------------------------------+
| + id: java.util.UUID                 |
| + fullName: String                   |
| + email: String                      |
| + phone: String                      | -> atributos da classe
| + createdAt: java.time.LocalDateTime |
| + updatedAt: java.time.LocalDateTime |
| + active: boolean                    |
+--------------------------------------+
| + getters                            |
| - setters                            | -> getters / setters da classe
+--------------------------------------+
```

2. Transações (/transactions)
- POST /transactions → Criar uma nova transação vinculada a um cliente. A transação é composta por id, id do cliente (Customer), valor (amount), tipo de cartão como DÉBITO/CRÉDITO (cardType) e data da transação (createdAt).
- GET /transactions?customerId=... → Listar todas as transações de um cliente.

```sh
+--------------------------------------+
| Transaction                          | -> nome da classe
+--------------------------------------+
| + id: java.util.UUID                 |
| + customerId: java.util.UUID         |
| + amount: java.math.BigDecimal       |
| + cardType: String                   | -> atributos da classe
| + createdAt: java.time.LocalDateTime |
| + active: boolean                    |
+--------------------------------------+
| + getters                            |
| - setters                            | -> getters / setters da classe
+--------------------------------------+
```

### 📈 Regras de Negócio
- Necessário validar o atributo nome completo (fullName) do Cliente (Customer) como obrigatório e tem que preencher até no máximo 255 caracteres.
- Necessário validar o atributo e-mail (email) do Cliente (Customer) como obrigatório e tem que preencher até no máximo 100 caracteres.
- Necessário validar o atributo telefone (phone) do Cliente (Customer) como obrigatório.
- Não permitir transações para clientes inexistentes (retornar 404 Not Found).
- Necessário validar o atributo cliente (customerId) da Transação (Transaction) como obrigatório e cliente válido.
- Necessário validar o atributo valor (amount) da transação (Transaction) como obrigatório e maior que zero.
- Necessário validar o atributo tipo de cartão (cardType) da Transação (Transaction) como obrigatório e valor como DÉBITO OU CRÉDITO.

### 💡 Diferenciais (Extra Points)
- Implementar tratamento global de erros com @ControllerAdvice.

### 📂 Entrega
- Código fonte em repositório git público (GitHub).
- README.md explicando:
  - Passo a passo para rodar a aplicação. 
  - Endpoints e exemplos de requisições/respostas (com Postman).
  - Dependências utilizadas.

Desejamos uma boa prova, sucesso e BOA SORTE!!!

Orbitall Payments Teams - 20/Setembro/2025
- - Amanda Queiroz
- - Gabriela De Oliveira
- - Leandro Capuano
- - Luis Forcinnetti
- - Maysa Hoffmann
- - Rodrigo Bibiano 
- - Sérgio Sampaio

<br>

![Projeto Customers](codigo-de-barras.png)

<br>

### *** DICAS ****

#### ** GIT **
```sh
$ git clone <branch>
$ git add .
$ git commit -m 'Seu comentário'
$ git push origin <branch>
```
###### PS: Cuidado com o artefato oculto chamado .git, você tem que basear na sua repositório e não no que foi clonado!

#### ** MAVEN **
```sh
$ mvn clean
$ mvn install
$ mvn spring-boot:run
```

#### ** Lombok **
- Não esqueça de habilitar o Lombok como Plugin dentro do IntelliJ.
- Apele para o Lombok gerar os setters/getters através da anotação @Data.

#### ** Spring Boot **
- Use o módulo Validation do Spring Boot para fazer a validação dos campos e não esqueça de implementar o GlobalExceptionHandler que aprendemos durante o hackathon.
- Use a camada Service para aplicar a regra de negócio.
- Não esqueça de setar os valores padrão como id (UUID randômico), datas (createdAt/updatedAt) com a data/hora corrente e ativar o registro (active) como verdadeiro (true).

#### ** IA/LLM **
- Não esquecer de instalar e habilitar o Junie dentro do IntelliJ, fica dentro dos Plugins.
- Apele ao Junie do IntelliJ para gerar o arquivo README.md do pedido do enunciado.
- Não perca tempo criando teste unitário, não é requisito deste desafio final.
- Com o Junie é possível criar o CRUD, mas cuidado com a adrenalina e o tempo, principalmente para não cair no labirinto.


## Extras

- Validação de celular e email
- Validar card type
- Handling Erros

---

## Como rodar a aplicação (Passo a passo)

Pré-requisitos:
- Java 21 instalado (JAVA_HOME configurado)
- Maven (você pode usar o wrapper do projeto)

Passos:
1. Clone este repositório e abra um terminal na raiz do projeto.
2. Entre no módulo da aplicação:
   - Windows: `cd channels`
3. Compile e execute com o Maven Wrapper:
   - Windows: `./mvnw.cmd spring-boot:run`
   - Alternativa: `./mvnw.cmd clean package` e depois `java -jar target/channels-0.0.1-SNAPSHOT.jar`
4. A aplicação subirá por padrão em: `http://localhost:8080`
5. Health check rápido: acesse `http://localhost:8080/` e você deverá ver `Welcome to Orbital Channels 2025!`.

Banco em memória (H2):
- Console H2 habilitado: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:channels`
- Usuário: `sa`
- Senha: (vazia)

Observações:
- Os dados são voláteis (H2 em memória). Ao reiniciar a aplicação, os dados são reiniciados.
- Não há configuração de contexto adicional; todos os endpoints estão diretamente sob `http://localhost:8080`.


## Endpoints e exemplos (Postman/cURL)

Base URL: `http://localhost:8080`

Coleção Postman pronta:
- Arquivo: `postman/Hackathon 2025 Challenge.postman_collection.json`
- No Postman: File > Import > escolha o arquivo acima.

### 1) Welcome
- GET `/`
- 200 OK (texto):
```
Welcome to Orbital Channels 2025!
```

### 2) Customers

Modelo (request body):
```
{
  "fullName": "Maria Silva",
  "email": "maria.silva@example.com",
  "phone": "+55 11 98888-7777"
}
```

Campos e validações:
- fullName: obrigatório, 1..255 chars
- email: obrigatório, 1..100 chars
- phone: obrigatório

Resposta (CustomerOutput):
```
{
  "id": "0c8d3d0a-8b4e-4a2d-9d56-0a6f0b2c1a11",
  "fullName": "Maria Silva",
  "email": "maria.silva@example.com",
  "phone": "+55 11 98888-7777",
  "createdAt": "2025-09-20T13:30:00.123",
  "updatedAt": "2025-09-20T13:30:00.123",
  "active": true
}
```

- POST `/customers`
  - cURL:
```
curl -X POST http://localhost:8080/customers \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Maria Silva",
    "email": "maria.silva@example.com",
    "phone": "+55 11 98888-7777"
  }'
```
  - 201/200 OK com o corpo acima (retorna o recurso criado).
  - 400 Bad Request (validação):
```
{
  "fullName": "Full name must be between 1 and 255 characters",
  "email": "Email must be between 1 and 100 characters"
}
```

- GET `/customers/{id}`
  - 200 OK: CustomerOutput
  - 404 Not Found:
```
{ "message": "Could not find active customer with id: {id}" }
```

- PUT `/customers/{id}`
  - Envie o mesmo corpo do POST para atualizar.
  - 200 OK: CustomerOutput (com updatedAt atualizado)
  - 400/404 conforme regras acima.

- DELETE `/customers/{id}`
  - Soft delete (marca active=false)
  - 200 OK: CustomerOutput (active=false)
  - 404 Not Found se não encontrado/ativo.

- GET `/customers`
  - 200 OK: array de CustomerOutput apenas com clientes ativos.

### 3) Transactions

Modelo (request body):
```
{
  "customerId": "0c8d3d0a-8b4e-4a2d-9d56-0a6f0b2c1a11",
  "amount": 149.90,
  "cardType": "CREDITO"
}
```

Campos e validações:
- customerId: obrigatório (deve existir)
- amount: obrigatório, positivo (> 0)
- cardType: obrigatório (string). Observação: a validação de domínio (ex.: aceitar apenas DEBITO/CREDITO) não é imposta pelo código neste momento; use valores padronizados como "DEBITO" ou "CREDITO".

Resposta (TransactionOutput):
```
{
  "id": "a7f23b43-1b0e-4b1f-9a6f-0e9a1b2c3d4e",
  "amount": 149.90,
  "cardType": "CREDITO",
  "createdAt": "2025-09-20T13:35:22.987",
  "active": true,
  "customer": {
    "id": "0c8d3d0a-8b4e-4a2d-9d56-0a6f0b2c1a11",
    "fullName": "Maria Silva",
    "email": "maria.silva@example.com",
    "phone": "+55 11 98888-7777",
    "createdAt": "2025-09-20T13:30:00.123",
    "updatedAt": "2025-09-20T13:30:00.123",
    "active": true
  }
}
```

- POST `/transactions`
  - cURL:
```
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "0c8d3d0a-8b4e-4a2d-9d56-0a6f0b2c1a11",
    "amount": 149.90,
    "cardType": "CREDITO"
  }'
```
  - 200 OK: TransactionOutput
  - 404 Not Found se customerId não existir:
```
{ "message": "Customer not found with id: ..." }
```
  - 400 Bad Request se falhar validação de campos (ex.: amount <= 0):
```
{ "amount": "Amount must be a positive value" }
```

- GET `/transactions?customerId={uuid}`
  - 200 OK: array de TransactionOutput do cliente informado
  - 404 Not Found se customerId não existir

Dicas para uso no Postman:
- Importe a coleção fornecida e ajuste a variável `baseUrl` se existir, para `http://localhost:8080`.
- Crie primeiro um Customer (POST /customers). Copie o `id` retornado para usar nas transações.


## Dependências utilizadas

Do arquivo `channels/pom.xml`:
- spring-boot-starter-web: API REST (Tomcat embutido, JSON via Jackson)
- spring-boot-starter-data-jpa: persistência com JPA/Hibernate
- spring-boot-starter-validation: validação de bean (Jakarta Validation)
- spring-boot-starter-actuator: endpoints de monitoramento/health (opcionais)
- h2 (runtime): banco em memória H2
- lombok: redução de boilerplate (getters/setters, @Data). É necessário habilitar o plugin no IDE
- spring-boot-starter-test (test): dependências de teste

Configuração relevante (`channels/src/main/resources/application.properties`):
- spring.h2.console.enabled=true
- spring.h2.console.path=/h2-console
- spring.datasource.url=jdbc:h2:mem:channels


## Observações finais
- As regras de negócio de validação presentes no código cobrem obrigatoriedade e tamanhos de campos de Customer, obrigatoriedade e positividade de amount em Transaction, e existência de Customer ao criar/listar transações.
- O tratamento global de erros (@ControllerAdvice) retorna:
  - 404 Not Found: `{ "message": "..." }`
  - 400 Bad Request (validação): `{ "campo": "mensagem" }` por cada erro.
- Este README complementa o enunciado original com instruções práticas de execução e uso da API.
