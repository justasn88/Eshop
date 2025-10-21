CREATE TABLE IF NOT EXISTS "category" (
    "id" INT NOT NULL,
    "parent_category_id" INT,
    "name" VARCHAR(20),
    PRIMARY KEY(id),
    FOREIGN KEY (parent_category_id) REFERENCES category(id)
)