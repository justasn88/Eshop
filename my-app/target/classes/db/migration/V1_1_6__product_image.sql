CREATE TABLE IF NOT EXISTS "product_image" (
    "id" INT NOT NULL PRIMARY KEY,
    "product_id" INT NOT NULL,
    "path" VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
)