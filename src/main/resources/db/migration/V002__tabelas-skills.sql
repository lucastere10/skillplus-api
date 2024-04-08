CREATE TABLE SKIL(
    SKIL_CD_ID SERIAL PRIMARY KEY,
    SKIL_TX_NOME VARCHAR(255) NOT NULL UNIQUE,
    SKIL_TX_DESCRICAO VARCHAR(255),
    SKIL_TX_CATEGORIA VARCHAR(255) NOT NULL,
    SKIL_TX_DIFICULDADE VARCHAR(255),
    SKIL_BL_ATIVO BOOLEAN NOT NULL,
    SKIL_TX_URL VARCHAR(255),
    SKIL_DT_CADASTRO TIMESTAMP NOT NULL,
    SKIL_DT_ATUALIZACAO TIMESTAMP NOT NULL
);

CREATE TABLE USSK(
    USSK_CD_ID SERIAL PRIMARY KEY,
    USSK_TX_VERSAO VARCHAR(255),
    USSK_TX_DOMINIO VARCHAR(255) NOT NULL,
    USSK_BL_ATIVO BOOLEAN NOT NULL,
    PK_USUA BIGINT NOT NULL,
    PK_SKIL BIGINT NOT NULL,
    CONSTRAINT FK_USUA_USSK FOREIGN KEY (PK_USUA) REFERENCES USUA (USUA_CD_ID),
    CONSTRAINT FK_SKIL_USSK FOREIGN KEY (PK_SKIL) REFERENCES SKIL (SKIL_CD_ID)
);