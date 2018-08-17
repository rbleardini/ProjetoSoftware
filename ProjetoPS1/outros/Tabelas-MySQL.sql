https://dev.mysql.com/downloads/

Download MySQL Community Server

Últimas versões:
- 5.5
- 5.6
- 5.7
- 8.0.12 (atual)

DROP TABLE PRODUTO;

CREATE TABLE banco.produto (
  id INT(11) NOT NULL AUTO_INCREMENT,
  nome VARCHAR(30) NOT NULL,
  lance_minimo DECIMAL(8, 2) NOT NULL,
  data_cadastro DATE NOT NULL,
  data_venda DATE DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

INSERT INTO PRODUTO(NOME, LANCE_MINIMO, DATA_CADASTRO)
VALUES('TV SAMSUNG 20 POL', 2000, now());

INSERT INTO PRODUTO(NOME, LANCE_MINIMO, DATA_CADASTRO)
VALUES('TV SAMSUNG 22 POL', 2500, now());
