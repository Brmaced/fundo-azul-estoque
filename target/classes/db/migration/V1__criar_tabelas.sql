CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    senha_hash VARCHAR(100) NOT NULL,
    perfil VARCHAR(20) NOT NULL
);

CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    categoria_id BIGINT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    preco_custo DECIMAL(10,2) NOT NULL,
    preco_venda DECIMAL(10,2) NOT NULL,
    quantidade_atual INT NOT NULL DEFAULT 0,
    estoque_minimo INT NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    versao BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id),
    CONSTRAINT ck_produto_quantidade CHECK (quantidade_atual >= 0)
);

CREATE TABLE venda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_hora DATETIME NOT NULL,
    usuario_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_venda_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);

CREATE TABLE item_venda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    venda_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_item_venda FOREIGN KEY (venda_id) REFERENCES venda (id),
    CONSTRAINT fk_item_produto FOREIGN KEY (produto_id) REFERENCES produto (id)
);

CREATE TABLE movimentacao_estoque (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    quantidade INT NOT NULL,
    data_hora DATETIME NOT NULL,
    usuario_id BIGINT NOT NULL,
    venda_id BIGINT NULL,
    observacao VARCHAR(255),
    CONSTRAINT fk_mov_produto FOREIGN KEY (produto_id) REFERENCES produto (id),
    CONSTRAINT fk_mov_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
    CONSTRAINT fk_mov_venda FOREIGN KEY (venda_id) REFERENCES venda (id)
);