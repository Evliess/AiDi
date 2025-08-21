-- 创建 consumer 表
DROP TABLE consumer IF EXISTS;
CREATE TABLE consumer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    name VARCHAR(50),
    left_count INT DEFAULT 0
);
CREATE INDEX idx_consumer_phone ON consumer(phone);

-- 创建 consumer_charge 表
DROP TABLE consumer_charge IF EXISTS;
CREATE TABLE consumer_charge (
    id INT AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    money VARCHAR(20) NOT NULL,
    charge_at BIGINT
);
CREATE INDEX idx_consumer_charge_phone ON consumer_charge(phone);

-- 创建 consumer_play 表
DROP TABLE consumer_play IF EXISTS;
CREATE TABLE consumer_play (
    id INT AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    consume_at BIGINT,
    item_name VARCHAR(20)
);
CREATE INDEX idx_consumer_play_phone ON consumer_play(phone);

-- 创建 history_play 表
DROP TABLE history_play IF EXISTS;
CREATE TABLE history_play (
    id INT AUTO_INCREMENT PRIMARY KEY,
    money VARCHAR(20) NOT NULL,
    consume_at BIGINT,
    item_name VARCHAR(20)
);
CREATE INDEX idx_history_play_consume_at ON history_play(consume_at);

-- 创建 play_item 表
DROP TABLE play_item IF EXISTS;
CREATE TABLE play_item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    money VARCHAR(20) NOT NULL
);
