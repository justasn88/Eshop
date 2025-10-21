CREATE TABLE IF NOT EXISTS "order_items"(
    "id" INT NOT NULL,
    "order_id" INT,
    "product_id" INT,
    "quantity" INT,
    "price_at_purchase" DECIMAL(10, 2),
FOREIGN KEY ("order_id") REFERENCES "order"(id),
FOREIGN KEY ("product_id") REFERENCES "product"(id)
);