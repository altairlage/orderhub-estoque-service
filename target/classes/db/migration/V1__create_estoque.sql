CREATE TABLE IF NOT EXISTS estoque (
    sku VARCHAR(100) PRIMARY KEY,
    quantidade_disponivel INTEGER NOT NULL CHECK (quantidade_disponivel >= 0),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);