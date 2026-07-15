CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE UNIQUE INDEX uk_users_email ON users (email);

CREATE TABLE food_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE PRECISION NOT NULL,
    category VARCHAR(255),
    image_url VARCHAR(255)
);

CREATE TABLE cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255),
    food_id BIGINT,
    food_name VARCHAR(255),
    quantity INTEGER NOT NULL
);

CREATE INDEX idx_cart_item_user_email ON cart_item (user_email);
CREATE INDEX idx_cart_item_user_email_food_id ON cart_item (user_email, food_id);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255),
    total_amount DOUBLE PRECISION NOT NULL,
    status VARCHAR(255),
    created_at TIMESTAMP(6)
);

CREATE INDEX idx_orders_user_email ON orders (user_email);
CREATE INDEX idx_orders_created_at ON orders (created_at);

CREATE TABLE order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    food_id BIGINT,
    food_name VARCHAR(255),
    quantity INTEGER NOT NULL
);

CREATE INDEX idx_order_item_order_id ON order_item (order_id);
