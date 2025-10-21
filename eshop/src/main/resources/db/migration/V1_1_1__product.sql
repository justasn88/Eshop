CREATE TABLE IF NOT EXISTS "product" (
    "id" INT NOT NULL,
    "name" VARCHAR(20),
    "description" VARCHAR(100),
    "price" DECIMAL(10, 2),
    "category_id" INT,
    PRIMARY KEY ("id"),
    FOREIGN KEY (category_id) REFERENCES category(id)
    );