DROP DATABASE IF EXISTS artefattodb;
CREATE DATABASE artefattodb;
USE artefattodb;

set global max_connections = 500 ;

SELECT * FROM usuarios;
SELECT * FROM productos;
SELECT * FROM categorias;
SELECT * FROM compras;