INSERT INTO companies (
    id,
    razao_social,
    nome_fantasia,
    cnpj,
    email,
    telefone,
    status,
    created_at,
    updated_at
)
VALUES (
           2,
           'Scalo Tecnologia LTDA',
           'Scalo',
           '12345678000199',
           'admin@scalo.com.br',
           '(12) 99999-9999',
           'ATIVA',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

INSERT INTO users (
    name,
    email,
    password,
    active,
    company_id,
    role,
    created_at,
    updated_at
)
VALUES
    (
        'Administrador',
        'admin@scalo.com.br',
        '$2b$10$LJAWAk7uzi32gTiQ3G6dcea0vj7gb1LLmRO4FefNM7eP7FV3spKPq',
        TRUE,
        2,
        'ADMIN',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'José Vitor',
        'vitor@scalo.com.br',
        '$2b$10$LJAWAk7uzi32gTiQ3G6dcea0vj7gb1LLmRO4FefNM7eP7FV3spKPq',
        TRUE,
        2,
        'OWNER',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Usuário Teste',
        'user@scalo.com.br',
        '$2b$10$LJAWAk7uzi32gTiQ3G6dcea0vj7gb1LLmRO4FefNM7eP7FV3spKPq',
        TRUE,
        2,
        'USER',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );