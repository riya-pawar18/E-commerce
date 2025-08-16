CREATE TABLE `store_api`.`carts` (
  `id` BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `date_created` DATE NOT NULL DEFAULT (curdate()),
  PRIMARY KEY (`id`));

CREATE TABLE `store_api`.`cart_items` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `cart_id` BINARY(16) NOT NULL ,
    `product_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`),
    CONSTRAINT `uq_cart_product` UNIQUE (`cart_id`, `product_id`),
    CONSTRAINT `product_fk`
      FOREIGN KEY (`product_id`)
      REFERENCES `store_api`.`products` (`id`)
      ON DELETE NO ACTION
      ON UPDATE CASCADE,

    CONSTRAINT `cart_fk`
      FOREIGN KEY (`cart_id`)
      REFERENCES `store_api`.`carts` (`id`)
      ON DELETE NO ACTION
      ON UPDATE CASCADE
      );