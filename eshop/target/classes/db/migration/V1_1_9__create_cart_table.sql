DROP TABLE IF EXISTS cart;
CREATE TABLE cart (
    client_id BIGINT NOT NULL,
    total_amount NUMERIC(19, 2) DEFAULT 0.00,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (client_id),
    CONSTRAINT fk_client
        FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE
);