CREATE TABLE products (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    additional_info TEXT,
    price MONEY
);

CREATE TABLE inventory_products(
    inventory_id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (inventory_id, product_id)
);