CREATE TABLE companies (
                           id SERIAL PRIMARY KEY,
                           razao_social VARCHAR(255) NOT NULL,
                           nome_fantasia VARCHAR(255) NOT NULL,
                           cnpj VARCHAR(14) NOT NULL UNIQUE,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           telefone VARCHAR(20),
                           status VARCHAR(20) NOT NULL DEFAULT 'ATIVA',
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       active BOOLEAN NOT NULL DEFAULT TRUE,
                       company_id INTEGER NOT NULL,
                       role VARCHAR(20) NOT NULL DEFAULT 'USER',
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,

                       CONSTRAINT fk_users_company
                           FOREIGN KEY (company_id)
                               REFERENCES companies(id)
);