--drop tables--
drop table if exists certificate_tag;
drop table if exists certificate;
drop table if exists tag;

-- certificate table--
CREATE TABLE gift_certificate (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  name VARCHAR(255) NOT NULL,
                                  description VARCHAR(255) NOT NULL,
                                  price DECIMAL(10,2) NOT NULL,
                                  duration INT NOT NULL,
                                  create_date TIMESTAMP NOT NULL,
                                  last_update_date TIMESTAMP NOT NULL
);

CREATE TABLE tag (
                     id INT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE certificate_tag (
                                 certificate_id INT NOT NULL,
                                 tag_id INT NOT NULL,
                                 PRIMARY KEY (certificate_id, tag_id),
                                 FOREIGN KEY (certificate_id) REFERENCES gift_certificate(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                 FOREIGN KEY (tag_id) REFERENCES tag(id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES ('name', 'description', 0.0, 0, NOW(), NOW());
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES ('name1', 'description1', 0.0, 0, NOW(), NOW());
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES ('name2', 'description2', 0.0, 0, NOW(), NOW());
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES ('name3', 'description3', 0.0, 0, NOW(), NOW());

INSERT INTO tag (name) VALUES ('name');
INSERT INTO tag (name) VALUES ('name1');
INSERT INTO tag (name) VALUES ('name2');
INSERT INTO tag (name) VALUES ('name3');

INSERT INTO certificate_tag (certificate_id, tag_id) VALUES (1, 1);
INSERT INTO certificate_tag (certificate_id, tag_id) VALUES (2, 2);
INSERT INTO certificate_tag (certificate_id, tag_id) VALUES (3, 3);
INSERT INTO certificate_tag (certificate_id, tag_id) VALUES (4, 4);
