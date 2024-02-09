-- 支払状態を保持する
CREATE TABLE paid (
    user_id INTEGER NOT NULL PRIMARY KEY,
    paid_amount DECIMAL(7,2) NOT NULL
);

-- 保持しているお金の枚数
CREATE TABLE cash (
    currency DECIMAL(7,2) NOT NULL PRIMARY KEY,
    currency_quantity INTEGER NOT NULL
);

-- ドリンク
CREATE TABLE drink (
    drink_id INTEGER NOT NULL PRIMARY KEY,
    drink_name VARCHAR NOT NULL,
    drink_price DECIMAL(7,2) NOT NULL
);

-- ドリンクの在庫
CREATE TABLE stock (
    drink_id INTEGER NOT NULL PRIMARY KEY,
    drink_quantity INTEGER NOT NULL
);
ALTER TABLE stock ADD FOREIGN KEY (drink_id) REFERENCES drink (drink_id) ON DELETE CASCADE;

-- 現金の初期データを作成
INSERT INTO cash (currency, currency_quantity) VALUES
(10.00, 100),
(50.00, 100),
(100.00, 100),
(500.00, 100),
(1000.00, 100);

-- ドリンクの初期データ
INSERT INTO drink (drink_id, drink_name, drink_price) VALUES
(1, 'コーラ', 120.00),
(2, 'ウーロン茶', 100.00),
(3, 'コーヒー', 150.00);

-- 在庫の初期データ
INSERT INTO stock (drink_id, drink_quantity) VALUES
(1, 10),
(2, 10),
(3, 10);
