ALTER TABLE travel_log
    CHANGE content contents varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NUll;
ALTER TABLE travel_log
    MODIFY COLUMN contents varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL;
ALTER TABLE book
    CHANGE content contents TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL;
ALTER TABLE book
    MODIFY COLUMN contents varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL;
