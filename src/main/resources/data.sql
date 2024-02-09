INSERT INTO permission (permission_type, created_at, updated_at)
VALUES ('SELECT', NOW(), NOW()),
       ('INSERT', NOW(), NOW()),
       ('UPDATE', NOW(), NOW()),
       ('DELETE', NOW(), NOW());

INSERT INTO users (name, user_type, created_at, updated_at)
VALUES ('oneny', 'LEADER', NOW(), NOW()),
       ('twony', 'MANAGER', NOW(), NOW());

INSERT INTO user_permission (user_id, permission_id, created_at, updated_at)
VALUES (1, 1, NOW(), NOW()),
       (1, 2, NOW(), NOW()),
       (1, 3, NOW(), NOW()),
       (1, 4, NOW(), NOW()),
       (2, 1, NOW(), NOW()),
       (2, 2, NOW(), NOW());