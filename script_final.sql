CREATE TABLE autor (
	cod_autor  SERIAL     NOT NULL,
	nome_autor VARCHAR(40) NOT NULL,
	CONSTRAINT pk_autor
	 PRIMARY KEY (cod_autor)
);

CREATE TABLE genero(
	cod_genero   SERIAL	 NOT NULL,
	tipo_genero  VARCHAR(40) NOT NULL,
	CONSTRAINT pk_genero
	 PRIMARY KEY (cod_genero)
);

CREATE TABLE cliente(
	cod_cliente   SERIAL     NOT NULL,
	telefone_c    bigint     NOT NULL,
	nome_cliente  VARCHAR(40) NOT NULL,
    cep_cliente   VARCHAR(40) NOT NULL,
	CONSTRAINT pk_clientes
	 PRIMARY KEY (cod_cliente)
);

CREATE TABLE livro(
	cod_livro     SERIAL     NOT NULL,
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
	cod_venda       SERIAL         NOT NULL,
	valor_venda     NUMERIC(6,2)    NOT NULL,
	cod_cliente_v   INTEGER         NOT NULL,
	data_venda		DATE			NOT NULL,
    copia_fisica  bit	  NULL,
	CONSTRAINT pk_venda
	 PRIMARY KEY (cod_venda),
	CONSTRAINT fk_venda_cliente
	 FOREIGN KEY (cod_cliente_v) REFERENCES cliente (cod_cliente)
);

CREATE TABLE itens_venda(
	cod_itens_venda  SERIAL         NOT NULL,
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
INSERT INTO autor (cod_autor, nome_autor) VALUES ('3', 'Lovecraft');
INSERT INTO autor (cod_autor, nome_autor) VALUES ('4', 'Douglas Adams');
INSERT INTO autor (cod_autor, nome_autor) VALUES ('5', 'Alvares de Azevedo');
INSERT INTO autor (cod_autor, nome_autor) VALUES ('6', 'Jane Austen');
INSERT INTO autor (cod_autor, nome_autor) VALUES ('7', 'Dostoievski');
INSERT INTO autor (cod_autor, nome_autor) VALUES ('8', 'Leigh Bardugo');


INSERT INTO genero (cod_genero, tipo_genero) VALUES ('1', 'Suspense');
INSERT INTO genero (cod_genero, tipo_genero) VALUES ('2', 'Ação');
INSERT INTO genero (cod_genero, tipo_genero) VALUES ('3', 'Romance');
INSERT INTO genero (cod_genero, tipo_genero) VALUES ('4', 'Ficção');
INSERT INTO genero (cod_genero, tipo_genero) VALUES ('5', 'Horror');
INSERT INTO genero (cod_genero, tipo_genero) VALUES ('6', 'Poesia');


INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('1', 'A ilha', '3', '2', '1');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('2', 'Veloz 9', '9', '1', '2');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('3', 'Orgulho e preconceito', '20', '2', '3');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('4', 'A cor que caiu do espaço', '15', '3', '5');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('5', 'O chamado de Cthulhu', '3', '3', '5');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('6', 'A lira dos 20 anos', '1', '5', '6');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('7', 'Crime e Castigo', '12', '7', '1');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('8', 'O Guia do mochileiro das galáxias', '6', '4', '4');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('9', 'A Vida, o Universo e Tudo Mais', '7', '4', '4');
INSERT INTO livro (cod_livro, titulo, edicao, cod_autor_l, cod_genero_l) VALUES ('10', 'Sombra e Ossos', '9', '8', '3');

INSERT INTO cliente (cod_cliente, nome_cliente, telefone_c,cep_cliente) VALUES ('1', 'Gabriel', '1111111','29315339');
INSERT INTO cliente (cod_cliente, nome_cliente, telefone_c,cep_cliente) VALUES ('2', 'Hilda', '22222222','2911711');
INSERT INTO cliente (cod_cliente, nome_cliente, telefone_c,cep_cliente) VALUES ('3', 'Lincoln', '33333333','22113322');
INSERT INTO cliente (cod_cliente, nome_cliente, telefone_c,cep_cliente) VALUES ('4', 'Julia', '44444444','21000000');
INSERT INTO cliente (cod_cliente, nome_cliente, telefone_c,cep_cliente) VALUES ('5', 'Vicenzo', '55555555','1911711');

INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('1', '100.00', '1','2021-01-15');
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('2', '50.00','3','2021-01-15');
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('3', '50.00', '2','2021-01-15');
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('4', '70.00','4','2021-02-15'); 
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('5', '80.00', '5','2021-03-15'); 
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('6', '90.00','1','2021-03-15');
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('7', '200.00', '2','2021-03-15');
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('8', '20.00','3','2021-03-15');
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('9', '50.00', '4','2021-04-15');
INSERT INTO venda (cod_venda, valor_venda, cod_cliente_v,data_venda) VALUES ('10', '35.00','5','2021-04-15');

INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('1', '100.00', '1', '1','1');
INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('2', '50.00', '1', '2' ,'2');
 INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('3', '50.00', '1', '2' ,'3');
 INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('4', '35.00', '1', '6' ,'4');
  INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('5', '35.00', '1', '4' ,'4');
 INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('6', '80.00', '1', '5' ,'5');
  INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('7', '50.00', '1', '2' ,'6');
 INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('8', '40.00', '1', '3' ,'6');
  INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('9', '100.00', '1', '10' ,'7');
 INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('10', '50.00', '2', '7' ,'7');
  INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('11', '20.00', '1', '9' ,'8');
 INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('12', '50.00', '1', '7' ,'9');
  INSERT INTO itens_venda (cod_itens_venda, valor_item, quantidade, cod_livro_iv, cod_venda_iv) 
 VALUES ('13', '35.00', '1', '4' ,'10');

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

/* 
 #3 :: DESCRIÇÃO VENDA == venda COM MESMO COD DE VENDA
SELECT cod_itens_venda,
	   valor_item,
	   quantidade
 FROM itens_venda iv
 WHERE cod_venda_iv IN ( SELECT cod_venda
						 FROM venda
						 WHERE (SELECT SUM(valor_venda)					
 
 );
 */
 
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
 