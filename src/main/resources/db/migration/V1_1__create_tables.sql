-- Create the 'auth_user' table
CREATE TABLE auth_user
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(255),
    password      VARCHAR(255),
    name          VARCHAR(255),
    department_id BIGINT,
    salary        NUMERIC(19, 2)
);

-- Create the 'auth_user_role' table
CREATE TABLE auth_role
(
    id        BIGSERIAL PRIMARY KEY,
    role_type VARCHAR(255)
);

-- Create the 'auth_user_roles' table for the many-to-many relationship
CREATE TABLE auth_user_role
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES auth_user (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES auth_role (id) ON DELETE CASCADE
);

-- Create the 'department' table
CREATE TABLE department
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255)
);

-- Add foreign key constraint for 'auth_user' table
ALTER TABLE auth_user
    ADD CONSTRAINT fk_auth_user_department
        FOREIGN KEY (department_id)
            REFERENCES department (id);

-- Add sample roles to 'auth_user_role' table
INSERT INTO auth_role (role_type)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');
