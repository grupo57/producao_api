CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

CREATE TABLE IF NOT EXISTS atendimento
(
    id                 		BIGINT AUTO_INCREMENT NOT NULL,
    id_pedido          		BIGINT                NOT NULL,
    codigo             		VARCHAR(100)          NOT NULL,
    situacao           		VARCHAR(30)           NOT NULL,
    data_recebido      		DATETIME              NOT NULL,
    data_iniciado 			DATETIME,
    data_preparado			DATETIME,			
    data_concluido			DATETIME,			
    data_criacao      		DATETIME              NOT NULL,
    data_ultima_modificacao DATETIME              NOT NULL,
    CONSTRAINT pk_atendimento PRIMARY KEY (id)
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX idx_atendimento_id_pedido  ON atendimento (id_pedido);