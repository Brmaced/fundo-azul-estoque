CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(50) NOT NULL
);

CREATE TABLE categoria (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    categoria_id INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    preco_custo DECIMAL(10, 2) NOT NULL,
    preco_venda DECIMAL(10, 2) NOT NULL,
    quantidade_atual INT NOT NULL DEFAULT 0,
    estoque_maximo INT NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    versao INT NOT NULL DEFAULT 0,
    CONSTRAINT FK_produto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id),
    CONSTRAINT ck_produto_quantidade CHECK (quantidade_atual >= 0 )
);

create table venda (
    id SERIAL PRIMARY KEY,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_venda_produto FOREIGN KEY (produto_id) REFERENCES produto(id)
);

CREATE TABLE itemVenda(
    id SERIAL PRIMARY KEY,
    venda_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_ItemVenda_venda FOREIGN KEY (venda_id) REFERENCES venda(id),
    CONSTRAINT FK_ItemVenda_produto FOREIGN KEY (produto_id) REFERENCES produto(id)
);

CREATE TABLE movimentacaoEstoque (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    quantidade INT NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT NOT NULL,
    venda_id BIGINT,
    observacao VARCHAR(255),
    CONSTRAINT FK_movimentacaoEstoque_produto FOREIGN KEY (produto_id) REFERENCES produto(id),
    CONSTRAINT FK_movimentacaoEstoque_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT FK_movimentacaoEstoque_venda FOREIGN KEY (venda_id) REFERENCES venda(id)
);

