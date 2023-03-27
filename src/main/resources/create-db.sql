DROP DATABASE IF EXISTS webshopdat22b;
CREATE SCHEMA webshopdat22b;
DROP TABLE IF EXISTS products;
CREATE TABLE `webshopdat22b`.`products` (
                                            `id` INT NOT NULL AUTO_INCREMENT,
                                            `name` VARCHAR(30) NOT NULL,
                                            `price` DECIMAL(13,2) NULL,
                                            PRIMARY KEY (`id`));
INSERT INTO `webshopdat22b`.`products` (`name`, `price`) VALUES ('Tuborg', '25');
INSERT INTO `webshopdat22b`.`products` (`name`, `price`) VALUES ('Carlsberg', '25');

