CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

-- Create the schema

CREATE TABLE IF NOT EXISTS atendimento
(
    ID                 		BIGINT AUTO_INCREMENT NOT NULL,
    ID_PEDIDO          		BIGINT                NOT NULL,
    CODIGO             		VARCHAR(100)          NOT NULL,
    SITUACAO           		VARCHAR(30)           NOT NULL,
    DATA_RECEBIDO      		DATETIME              NOT NULL,
    DATA_INICIADO 			DATETIME,
    DATA_PREPARADO			DATETIME,		
    DATA_CONCLUIDO			DATETIME,			
    DATA_CRIACAO      		DATETIME              NOT NULL,
    DATA_ULTIMA_MODIFICACAO DATETIME              NOT NULL,
    CONSTRAINT pk_atendimento PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_atendimento_id_pedido  ON atendimento (id_pedido);