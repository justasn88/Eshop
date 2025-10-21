CREATE TABLE IF NOT EXISTS "cart" (
    "id" INT NOT NULL,
    "product_id" int,
    "client_id" INT NOT NULL UNIQUE,
    "quantity" INT,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (client_id) REFERENCES "client"(id)
)