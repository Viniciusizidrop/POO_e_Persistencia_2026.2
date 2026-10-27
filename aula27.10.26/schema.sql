-- Criação do esquema relacional no SQLite / MySQL
CREATE TABLE IF NOT EXISTS ativos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patrimonio TEXT NOT NULL UNIQUE,
    nome TEXT NOT NULL,
    categoria TEXT NOT NULL,
    detalhe_especifico TEXT
);

CREATE TABLE IF NOT EXISTS chamados (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    descricao TEXT NOT NULL,
    ativo_id INTEGER NOT NULL,
    FOREIGN KEY (ativo_id) REFERENCES ativos(id) ON DELETE CASCADE
);
