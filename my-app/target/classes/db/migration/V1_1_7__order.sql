CREATE TABLE IF NOT EXISTS "order" (
    "id" INT NOT NULL,
    PRIMARY KEY ("id"),
    "client_id" INT,
    "total_amount" DECIMAL(10, 2),
    FOREIGN KEY (client_id) REFERENCES client(id)
)