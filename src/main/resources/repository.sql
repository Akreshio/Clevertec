CREATE TABLE discount_card (
  id BIGINT NOT NULL,
   discount INTEGER NOT NULL,
   CONSTRAINT pk_discount_card PRIMARY KEY (id)
);

CREATE TABLE product (
  id BIGINT NOT NULL,
   name VARCHAR(255) NOT NULL,
   cost INTEGER NOT NULL,
   promotion BOOLEAN NOT NULL,
   CONSTRAINT pk_product PRIMARY KEY (id)
);