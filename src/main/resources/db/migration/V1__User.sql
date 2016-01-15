CREATE TABLE user_account (
    id bigserial NOT NULL,
    email_address varchar(255) NOT NULL UNIQUE,
    password_hash varchar(255),
    activation_token varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE persistent_logins (
    username varchar(64) not null REFERENCES user_account (email_address),
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp with time zone not null,
    PRIMARY KEY (series)
);


CREATE TABLE user_has_role (
    user_id bigserial NOT NULL REFERENCES user_account,
    role varchar(25),
    PRIMARY KEY (user_id, role)
);

-- The value of password_hash below is 'changeme' (without the quotes) hashed.
INSERT INTO user_account(email_address, activation_token, password_hash) VALUES ('admin@mysite.com', '1','$2a$10$cFdq2XKknuQhiZDI61Pl/eslGg7GOOwZVvg/NAcno5bZHuIocQq12');
INSERT INTO user_has_role (user_id, role) (SELECT id, 'ADMIN' FROM user_account WHERE email_address='admin@mysite.com');