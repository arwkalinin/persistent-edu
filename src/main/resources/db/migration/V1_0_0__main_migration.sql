CREATE TABLE "category"
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(10),
    code BIGINT UNIQUE
);

CREATE TABLE "product"
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(50) UNIQUE,
    description   VARCHAR,
    category_code BIGINT REFERENCES "category" (code),
    quantity      BIGINT
);
CREATE TABLE "order"
(
    id           BIGSERIAL PRIMARY KEY,
    payment      BIGINT,
    uid          VARCHAR(14) UNIQUE,
    payment_date TIMESTAMP,
    state        VARCHAR(16)
);

CREATE TABLE "basket"
(
    id               BIGSERIAL PRIMARY KEY,
    order_uid        VARCHAR REFERENCES "order" (uid),
    product_name     VARCHAR REFERENCES "product" (name),
    count_of_product BIGINT
);