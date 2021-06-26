/*
#################
#DB: biblioteca #
#pass: 123      #
#################
*/

CREATE TABLE autor (
	cod_autor  INTEGER     NOT NULL,
	nome_autor VARCHAR(40) NOT NULL,
	CONSTRAINT pk_autor
	 PRIMARY KEY (cod_autor)
);

CREATE TABLE genero(
	cod_genero   INTEGER	 NOT NULL,
	tipo_genero  VARCHAR(40) NOT NULL,
	CONSTRAINT pk_genero
	 PRIMARY KEY (cod_genero)
);

CREATE TABLE cliente(
	cod_cliente   INTEGER     NOT NULL,
	telefone_c    INTEGER     NOT NULL,
	nome_cliente  VARCHAR(40) NOT NULL,
	CONSTRAINT pk_cliente
	 PRIMARY KEY (cod_cliente)
);

CREATE TABLE livro(
	cod_livro     INTEGER     NOT NULL,
	titulo        VARCHAR(40) NOT NULL,
	edicao        INTEGER     NOT NULL,
	cod_genero_l  INTEGER     NOT NULL,
	cod_autor_l   INTEGER     NOT NULL,
	CONSTRAINT pk_livro
	 PRIMARY KEY (cod_livro),
	CONSTRAINT fk_livro_genero
	 FOREIGN KEY  (cod_genero_l) REFERENCES genero (cod_genero),
	CONSTRAINT fk_livro_autor
	 FOREIGN KEY  (cod_autor_l)  REFERENCES autor(cod_autor)
);

CREATE TABLE venda (
	cod_venda       INTEGER         NOT NULL,
	valor_venda     NUMERIC(6,2)    NOT NULL,
	cod_cliente_v   INTEGER         NOT NULL,
	data_venda		DATE			NOT NULL,
	CONSTRAINT pk_venda
	 PRIMARY KEY (cod_venda),
	CONSTRAINT fk_venda_cliente
	 FOREIGN KEY (cod_cliente_v) REFERENCES cliente (cod_cliente)
);

CREATE TABLE itens_venda(
	cod_itens_venda  INTEGER         NOT NULL,
	valor_item       NUMERIC(6,2)    NOT NULL,
	quantidade       INTEGER         NOT NULL,
	cod_livro_iv     INTEGER         NOT NULL,
	cod_venda_iv     INTEGER         NOT NULL,
	CONSTRAINT pk_itens_venda
	 PRIMARY KEY (cod_itens_venda),
	CONSTRAINT fk_iv_venda
	 FOREIGN KEY (cod_venda_iv) REFERENCES venda (cod_venda),
	CONSTRAINT fk_iv_livro
	 FOREIGN KEY (cod_livro_iv) REFERENCES livro (cod_livro)
);

INSERT INTO autor (cod_autor, nome_autor) VALUES ('1', 'Benjamin');
INSERT INTO autor (cod_autor, nome_autor) VALUES ('2', 'Aurora');

INSERT INTO genero (cod_genero, tipo_genero) VALUES ('1', 'Suspense');
INSERT INTO genero (cod_genero, tipo_genero) VALUES ('2', 'Ação');

INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('1', 'A ilha', '3', '2', '1');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('2', 'Veloz 9', '9', '1', '2');

INSERT INTO cliente (cod_cliente, nome_cliente, telefone_c) VALUES ('1', 'Gabriel', '1111111');
INSERT INTO cliente (cod_cliente, nome_cliente, telefone_c) VALUES ('2', 'Hilda', '22222222');
INSERT INTO cliente (cod_cliente, nome_cliente, telefone_c) VALUES ('3', 'Lincoln', '33333333');

INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v) VALUES ('1', '100.00', '1');
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v) VALUES ('2', '50.00','3');

INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('1', '100.00', '1', '1' '1');
INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('2', '50.00', '1', '2' '2');

 /*DESCRIÇÃO DO LIVRO == livro*/
SELECT l.cod_livro,
	   l.titulo,
	   l.edicao,
	   a.nome_autor,
	   g.tipo_genero
 FROM livro l
 INNER JOIN autor a  ON l.cod_autor_l  = a.cod_autor
 INNER JOIN genero g ON l.cod_genero_l = cod_genero;
 
 /*DESCRIÇÃO ITENS DE VENDA == itens_venda */
 SELECT iv.cod_itens_venda,
		iv.valor_item,
		iv.quantidade,
		l.titulo,
		v.cod_venda
  FROM  itens_venda iv
  INNER JOIN livro l ON l.cod_livro = iv.cod_livro_iv
  INNER JOIN venda v ON v.cod_venda = iv.cod_venda_iv;
 
  /*DESCRIÇÃO VENDA == venda */
SELECT v.cod_venda,
	   v.valor_venda,
	   c.nome_cliente,
	   iv.cod_itens_venda
 FROM venda v
 INNER JOIN cliente c ON v.cod_cliente_v = c.cod_cliente
 INNER JOIN itens_venda iv ON v.cod_venda = iv.cod_venda_iv;
 
 /*2 :: DESCRIÇÃO VENDA == venda */
SELECT v.cod_venda,
	   v.valor_venda,
	   c.nome_cliente,
	   iv.cod_itens_venda,
	   iv.valor_item,
	   iv.quantidade,
	   l.titulo
 FROM venda v
 INNER JOIN cliente c ON v.cod_cliente_v = c.cod_cliente
 INNER JOIN itens_venda iv ON v.cod_venda = iv.cod_venda_iv
 INNER JOIN livro l ON iv.cod_livro_iv = l.cod_livro;

 /* 3 :: SOMANDO A VENDA COM O VALOR DOS ITENS*/
SELECT c.nome_cliente     AS "cliente",
	   SUM(iv.valor_item) AS "valor venda"
 FROM itens_venda iv
 INNER JOIN venda v ON iv.cod_venda_iv = v.cod_venda 
 INNER JOIN cliente c ON v.cod_cliente_v = c.cod_cliente
 INNER JOIN livro l ON iv.cod_livro_iv = l.cod_livro
 GROUP BY (c.nome_cliente);
 
 /*4 :: SOMANDO VENDA */
SELECT  l.titulo,
        v.cod_venda        AS "código venda",
	    SUM(iv.quantidade) AS "quantidade",	
		c.nome_cliente     AS "cliente",
		iv.valor_item	   AS "valor item",
	    SUM(iv.valor_item) AS "valor venda"
 FROM itens_venda iv
 INNER JOIN venda v   ON iv.cod_venda_iv = v.cod_venda 
 INNER JOIN cliente c ON v.cod_cliente_v = c.cod_cliente
 INNER JOIN livro l   ON iv.cod_livro_iv = l.cod_livro
 GROUP BY (c.nome_cliente, l.titulo,iv.valor_item, v.cod_venda);
 
 
 /*DESCRIÇÃO CLIENTE*/
 SELECT cod_cliente,
		nome_cliente,
		telefone_c
 FROM cliente;
 
 /*DESCRIÇÃO AUTOR*/
 SELECT cod_autor,
		nome_autor
 FROM autor;
 
 /*DESCRIÇÃO GENERO*/
 SELECT cod_genero,
		tipo_genero
FROM genero;
 