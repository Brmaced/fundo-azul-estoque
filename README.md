# Fundo Azul Estoque

API REST de controle de estoque e vendas para uma loja de aquarismo **fictícia** (Fundo Azul Aquarismo), feita em Java, Spring Boot e MySQL. Projeto de portfólio, em desenvolvimento.

> Todos os dados deste repositório são fictícios. A loja e os produtos não existem.

## Problema que resolve

Uma loja pequena de aquarismo vende tanques, peixes, corais, ração, skimmers e acessórios. Sem controle, é fácil vender o que não existe em estoque, perder o histórico de entradas e saídas e não saber quanto se perdeu com animais vivos.

Este sistema registra os produtos, cada movimentação de estoque e as vendas, e impede que o estoque fique negativo.

## Status do projeto

- [x] Projeto base com Spring Boot
- [x] Banco de dados MySQL com migrations (Flyway)
- [ ] CRUD de produtos com validação
- [ ] Movimentações de estoque (entrada, venda, perda, ajuste)
- [ ] Vendas com vários itens, em transação
- [ ] Login com perfis (`ADMIN` e `VENDEDOR`)
- [ ] Testes das regras de estoque e venda
- [ ] Documentação da API (Swagger/OpenAPI)

## Tecnologias

- Java 21
- Spring Boot 3.5.x
- Spring Data JPA e Bean Validation
- MySQL
- Flyway (migrations)
- Maven
- Previstas: Spring Security, JUnit, Mockito, Swagger/OpenAPI

## Modelo de dados

| Entidade | Papel |
|---|---|
| `Categoria` | Agrupa produtos (peixes, corais, ração etc.) |
| `Produto` | Item vendido, com tipo, preços e quantidade em estoque |
| `MovimentacaoEstoque` | Histórico de entradas, vendas, perdas e ajustes |
| `Venda` | Cabeçalho da venda |
| `ItemVenda` | Produtos e quantidades de cada venda |
| `Usuario` | Quem opera o sistema, com perfil de acesso |

## Regras de negócio (planejadas)

1. O estoque nunca fica negativo.
2. A venda baixa o estoque dentro de uma transação.
3. Toda alteração de estoque gera uma movimentação com tipo, usuário e data.
4. A baixa por perda de animal vivo é registrada separadamente da venda.
5. Valores em dinheiro usam `BigDecimal`.
6. Vendas simultâneas do mesmo item são tratadas com [PREENCHER: `@Version` ou lock].
7. Cancelamento de venda: [PREENCHER: regra escolhida, incluindo animais vivos].

## Decisões técnicas

- O schema do banco é criado pelo Flyway, e o Hibernate apenas valida (`ddl-auto=validate`).
- A quantidade atual fica guardada no produto, e as movimentações são registradas à parte, sempre na mesma transação.
- A senha do banco vem de variáveis de ambiente e nunca é escrita no código.

## Fora do escopo

Nota fiscal, pagamento online, várias lojas, front-end elaborado, upload de fotos, ração vendida por peso e relatórios avançados.

## Como executar

### Pré-requisitos

- Java 21
- MySQL 8 ou superior
- Git (o Maven vem junto, pelo Maven Wrapper `mvnw`)

### 1. Clonar o repositório

```bash
git clone https://github.com/Brmaced/fundo-azul-estoque.git
cd fundo-azul-estoque
```

### 2. Criar o banco e o usuário no MySQL

```sql
CREATE DATABASE fundo_azul CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'fundo_azul_app'@'localhost' IDENTIFIED BY 'ESCOLHA_UMA_SENHA';
GRANT ALL PRIVILEGES ON fundo_azul.* TO 'fundo_azul_app'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Definir as variáveis de ambiente

Linux ou macOS:

```bash
export DB_USER=fundo_azul_app
export DB_PASSWORD=a_senha_que_voce_escolheu
```

Windows (PowerShell):

```powershell
$env:DB_USER="fundo_azul_app"
$env:DB_PASSWORD="a_senha_que_voce_escolheu"
```

O arquivo `application.properties.example` mostra as propriedades usadas.

### 4. Executar

```bash
./mvnw spring-boot:run
```

No Windows, use `.\mvnw.cmd spring-boot:run`. As migrations são aplicadas automaticamente na primeira execução, e a aplicação sobe em `http://localhost:8080`.

## Endpoints

[PREENCHER: lista de endpoints ou link do Swagger, quando o CRUD estiver pronto]

## Dados pessoais e LGPD

O sistema guarda apenas dados dos usuários que operam o sistema (nome, e-mail e senha com hash). Todos os dados de teste são fictícios. Não há cadastro de clientes.
