CREATE TABLE products (
    id              SERIAL PRIMARY KEY,
    company_id      INTEGER NOT NULL,
    sku             VARCHAR(100) NOT NULL,
    name            VARCHAR(255) NOT NULL,
    description     TEXT,
    purchase_price  NUMERIC(15, 2) NOT NULL DEFAULT 0,
    sale_price      NUMERIC(15, 2) NOT NULL DEFAULT 0,
    unit            VARCHAR(20) NOT NULL,
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_products_company
        FOREIGN KEY (company_id)
        REFERENCES companies (id),

    CONSTRAINT uk_products_company_sku
        UNIQUE (company_id, sku)
);

CREATE INDEX idx_products_company_id
    ON products (company_id);
