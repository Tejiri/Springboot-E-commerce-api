-- ${flyway:timestamp}

DROP TABLE IF EXISTS orders,product,supervisor,driver, customer, address,product_category,driver_status,order_status;

CREATE TABLE address
(
    id           INT NOT NULL AUTO_INCREMENT,
    house_number VARCHAR(10),
    street       VARCHAR(50),
    town         VARCHAR(50),
    postcode     VARCHAR(10),
    PRIMARY KEY (id)
);

CREATE TABLE product_category
(
    id          INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(255),
    name        VARCHAR(25),
    PRIMARY KEY (id)
);

CREATE TABLE product
(
    id               INT NOT NULL AUTO_INCREMENT,
    product_category INT,
    description      VARCHAR(255),
    name             VARCHAR(50),
    price            DOUBLE,
    stock_quantity   INT,
    PRIMARY KEY (id),
    FOREIGN KEY (product_category) REFERENCES product_category (id)
);

CREATE TABLE customer
(
    id         INT NOT NULL AUTO_INCREMENT,
    address    INT,
    email      VARCHAR(50),
    first_name VARCHAR(30),
    last_name  VARCHAR(30),
    password   VARCHAR(100),
    telephone  VARCHAR(30),
    token      VARCHAR(100),
    PRIMARY KEY (id),
    FOREIGN KEY (address) REFERENCES address (id)
);

CREATE TABLE supervisor
(
    id         INT NOT NULL AUTO_INCREMENT,
    email      VARCHAR(30),
    first_name VARCHAR(30),
    last_name  VARCHAR(50),
    password   VARCHAR(30),
    token      VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE driver_status
(
    id     INT NOT NULL AUTO_INCREMENT,
    status VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE driver
(
    id            INT NOT NULL AUTO_INCREMENT,
    driver_status INT,
    email         VARCHAR(30),
    first_name    VARCHAR(30),
    last_name     VARCHAR(30),
    password      VARCHAR(30),
    token         VARCHAR(100),
    PRIMARY KEY (id),
    FOREIGN KEY (driver_status) REFERENCES driver_status (id)
);

CREATE TABLE order_status
(
    id     INT NOT NULL AUTO_INCREMENT,
    status VARCHAR(40),
    PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id           INT NOT NULL AUTO_INCREMENT,
    order_status INT,
    customer     INT,
    driver       INT,
    note         VARCHAR(50),
    product      INT,
    quantity     INT,
    PRIMARY KEY (id),
    FOREIGN KEY (order_status) REFERENCES order_status (id),
    FOREIGN KEY (customer) REFERENCES customer (id),
    FOREIGN KEY (driver) REFERENCES driver (id),
    FOREIGN KEY (product) REFERENCES product (id)
);

INSERT INTO product_category(description, name)
VALUES ('Holds items like shirts, shoes and troushers', 'Fashion'),
       ('Holds eatable items like foodstuff', 'Groceries'),
       ('General  electronic items', 'Electronics'),
       ('All computing items like monitors,laptops, mouse, e.t.c', 'Computing'),
       ('Gaming items like PS4', 'Gaming');

INSERT INTO product(product_category, description, name, price, stock_quantity)
VALUES (1, 'Winter Jacket to hold the cold', 'Thick Jacket', 20.0, 100),
       (2, 'Can be used to  cook all kinds of sauces', 'Maggi seasoning cube', 10.0, 100),
       (1, 'Good for playing football and long tennis', 'Nike Sport Shoe', 30.0, 100),
       (3, 'Can be used for heavy tasks like programming, Gaming and graphics design', 'HP laptop', 100.0, 100),
       (1, 'Snack to live you speechless', 'Chocolate Biscuit', 5.0, 100);

INSERT INTO driver_status(status)
VALUES ('available'),
       ('unavailable');

INSERT INTO order_status(status)
VALUES ('pending'),
       ('processing'),
       ('completed'),
       ('canceled'),
       ('failed');

INSERT INTO driver(driver_status, email, first_name, last_name, password, token)
VALUES (1, 'driver1@mail.com', 'Charles', 'Malcom', '123456', '1'),
       (2, 'driver2@mail.com', 'Daniel', 'Ijatomi', '123456', '12'),
       (1, 'driver3@mail.com', 'Ruger', 'Boy', '123456', '123'),
       (1, 'driver4@mail.com', 'Kunle', 'Ogu', '123456', '1234'),
       (2, 'driver5@mail.com', 'Ufouma', 'Emoghene', '123456', '12345'),
       (1, 'driver6@mail.com', 'Kevwe', 'GG', '123456', '123456');

INSERT INTO address(house_number, street, town, postcode)
VALUES ('3087',
        'P.O. Box 790, 3087 Integer Ave', 'Seraing', 'Seraing'),
       ('300',
        'Ap #190-3428 Sem St.', 'Lambayeque', '131088'),
       ('600',
        'Ap #489-7405 Velit. Street', 'Berceto', '504784'),
       ('900',
        'Ap #803-4690 Ante, Ave', 'San Francisco', '633725'),
       ('7988',
        'P.O. Box 412, 7988 Tristique Rd.', 'Futrono', '2750');

insert INTO customer(address, email, first_name, last_name, password, telephone, token)
VALUES (1, 'consectetuer.cursus.et@yahoo.net', 'Brendan', 'Mccarthy', '123456', '(631) 788-5338', '1'),
       (2, 'proin.nisl@aol.org', 'Phoebe', 'Rose', '123456', '1-586-221-9081', '12'),
       (1, 'dolor.quisque@outlook.org', 'Walker', 'Kirby', '123456', '1-369-630-8159', '123'),
       (3, 'proin@hotmail.couk', 'Mona', 'Mccullough', '123456', '1-409-983-7036', '1234'),
       (4, 'omar.cursus.et@yahoo.net', 'Omar', 'Mcintosh', '123456', '1-574-751-9925', '12345');

INSERT INTO orders(order_status, customer, driver, note, product, quantity)
VALUES (1, 1, null, 'Please call me when you arrive', 2, 5),
       (1, 2, null, 'I need it asap', 1, 40),
       (1, 4, null, 'Drop with my neighbor please', 2, 44),
       (2, 5, 2, 'Hmmmmmmmmmmmmmmmmmmmmmmm', 3, 22),
       (2, 1, 5, 'I will be at home on Monday', 4, 13),
       (3, 3, 3, 'Please call me when you arrive', 5, 1);

INSERT INTO supervisor(email, first_name, last_name, password, token)
VALUES ('supervisor1@mail.com', 'John', 'Smith', '123456', '123456');

