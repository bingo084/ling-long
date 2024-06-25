CREATE TYPE user_state AS ENUM ('ACTIVE', 'INACTIVE', 'BANNED');
CREATE TABLE sys_user
(
    id          SERIAL PRIMARY KEY,
    username    VARCHAR(20) NOT NULL,
    email       VARCHAR(50),
    phone       VARCHAR(32),
    password    VARCHAR(64) NOT NULL,
    real_name   VARCHAR(20),
    nickname    VARCHAR(20),
    avatar      VARCHAR(255),
    dept_id     INT,
    state       user_state  NOT NULL DEFAULT 'ACTIVE',
    create_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    creator_id  INT         NOT NULL,
    updater_id  INT         NOT NULL
);
ALTER TABLE sys_user
    ADD CONSTRAINT fk_user_creator_id FOREIGN KEY (creator_id) REFERENCES sys_user (id),
    ADD CONSTRAINT fk_user_updater_id FOREIGN KEY (updater_id) REFERENCES sys_user (id);

CREATE TABLE role
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    create_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    creator_id  INT         NOT NULL,
    updater_id  INT         NOT NULL
);
ALTER TABLE role
    ADD CONSTRAINT fk_role_creator_id FOREIGN KEY (creator_id) REFERENCES sys_user (id),
    ADD CONSTRAINT fk_role_updater_id FOREIGN KEY (updater_id) REFERENCES sys_user (id);

CREATE TYPE menu_type AS ENUM ('CATALOG','MENU', 'BUTTON');
CREATE CAST ( VARCHAR AS menu_type ) WITH INOUT AS ASSIGNMENT;
CREATE TABLE menu
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(20) NOT NULL,
    permission VARCHAR(50),
    icon        VARCHAR(20),
    route       VARCHAR(255),
    sort        INT         NOT NULL,
    hidden      BOOLEAN     NOT NULL DEFAULT FALSE,
    enabled     BOOLEAN     NOT NULL DEFAULT TRUE,
    type        menu_type   NOT NULL,
    parent_id   INT,
    create_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    creator_id  INT         NOT NULL,
    updater_id  INT         NOT NULL
);
ALTER TABLE menu
    ADD CONSTRAINT fk_menu_parent_id FOREIGN KEY (parent_id) REFERENCES menu (id),
    ADD CONSTRAINT fk_menu_creator_id FOREIGN KEY (creator_id) REFERENCES sys_user (id),
    ADD CONSTRAINT fk_menu_updater_id FOREIGN KEY (updater_id) REFERENCES sys_user (id);

CREATE TABLE dept
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    parent_id   INT,
    create_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    creator_id  INT         NOT NULL,
    updater_id  INT         NOT NULL
);
ALTER TABLE dept
    ADD CONSTRAINT fk_dept_parent_id FOREIGN KEY (parent_id) REFERENCES dept (id),
    ADD CONSTRAINT fk_dept_creator_id FOREIGN KEY (creator_id) REFERENCES sys_user (id),
    ADD CONSTRAINT fk_dept_updater_id FOREIGN KEY (updater_id) REFERENCES sys_user (id);
ALTER TABLE sys_user
    ADD CONSTRAINT fk_user_dept_id FOREIGN KEY (dept_id) REFERENCES dept (id);

CREATE TABLE user_role_mapping
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);
ALTER TABLE user_role_mapping
    ADD CONSTRAINT fk_user_role_mapping_user_id FOREIGN KEY (user_id) REFERENCES sys_user (id),
    ADD CONSTRAINT fk_user_role_mapping_role_id FOREIGN KEY (role_id) REFERENCES role (id);

CREATE TABLE role_menu_mapping
(
    role_id INT NOT NULL,
    menu_id INT NOT NULL,
    PRIMARY KEY (role_id, menu_id)
);
ALTER TABLE role_menu_mapping
    ADD CONSTRAINT fk_role_menu_mapping_role_id FOREIGN KEY (role_id) REFERENCES role (id),
    ADD CONSTRAINT fk_role_menu_mapping_menu_id FOREIGN KEY (menu_id) REFERENCES menu (id);