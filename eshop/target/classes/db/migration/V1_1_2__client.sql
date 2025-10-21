CREATE TABLE IF NOT EXISTS "client" (
    "id" INT NOT NULL,
    "name" VARCHAR(20),
    "lastname" VARCHAR(20),
    "email" VARCHAR(50),
    "address" VARCHAR(255),
    "phone_num" INT,
    PRIMARY KEY ("id")
    );