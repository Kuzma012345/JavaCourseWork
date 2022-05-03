CREATE TABLE price_stealer_user
(
    id         UUID NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255),
    password   VARCHAR(255),
    CONSTRAINT pk_price_stealer_user PRIMARY KEY (id)
);