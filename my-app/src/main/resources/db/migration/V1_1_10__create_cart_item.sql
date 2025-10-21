CREATE TABLE cart_item (
    id INT PRIMARY KEY,
    cart_client_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    price_at_purchase NUMERIC(19, 2) NOT NULL,
    UNIQUE (cart_client_id, product_id),
        CONSTRAINT fk_cart FOREIGN KEY (cart_client_id) REFERENCES cart (client_id) ON DELETE CASCADE,
        CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id)
);