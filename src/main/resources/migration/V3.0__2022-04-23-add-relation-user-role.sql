CREATE TABLE users_roles
(
    role_id UUID NOT NULL,
    user_id UUID NOT NULL
);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_price_stealer_user FOREIGN KEY (user_id) REFERENCES price_stealer_user (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES role (id);