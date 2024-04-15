-- delete from FOTO;
-- delete from CONT;
-- delete from USUA;
delete from USSK;
delete from SKIL;

-- PROGRAMACAO
INSERT INTO
    SKIL (
        SKIL_TX_NOME,
        SKIL_TX_DESCRICAO,
        SKIL_TX_CATEGORIA,
        SKIL_TX_DIFICULDADE,
        SKIL_BL_ATIVO,
        SKIL_DT_CADASTRO,
        SKIL_DT_ATUALIZACAO
    )
VALUES
    (
        'Python Fundamentos',
        'Aprenda os conceitos básicos de Python.',
        'PROGRAMACAO',
        'INICIANTE',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Desenvolvimento Web com Django',
        'Crie aplicações web robustas com Django.',
        'PROGRAMACAO',
        'INTERMEDIARIO',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Algoritmos Avançados',
        'Estude algoritmos complexos e estruturas de dados.',
        'PROGRAMACAO',
        'AVANCADO',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Arquitetura de Software',
        'Domine os princípios de design e arquitetura de software.',
        'PROGRAMACAO',
        'ESPECIALISTA',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

-- DESIGN_GRAFICO
INSERT INTO
    SKIL (
        SKIL_TX_NOME,
        SKIL_TX_DESCRICAO,
        SKIL_TX_CATEGORIA,
        SKIL_TX_DIFICULDADE,
        SKIL_BL_ATIVO,
        SKIL_DT_CADASTRO,
        SKIL_DT_ATUALIZACAO
    )
VALUES
    (
        'Princípios do Design',
        'Introdução aos fundamentos do design gráfico.',
        'DESIGN_GRAFICO',
        'INICIANTE',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Illustrator Intermediário',
        'Técnicas intermediárias para ilustração digital.',
        'DESIGN_GRAFICO',
        'INTERMEDIARIO',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Photoshop para Profissionais',
        'Edição avançada de imagens e fotografia.',
        'DESIGN_GRAFICO',
        'AVANCADO',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

-- MARKETING_DIGITAL
INSERT INTO
    SKIL (
        SKIL_TX_NOME,
        SKIL_TX_DESCRICAO,
        SKIL_TX_CATEGORIA,
        SKIL_TX_DIFICULDADE,
        SKIL_BL_ATIVO,
        SKIL_DT_CADASTRO,
        SKIL_DT_ATUALIZACAO
    )
VALUES
    (
        'SEO para Iniciantes',
        'Conceitos básicos de SEO para melhorar a visibilidade online.',
        'MARKETING_DIGITAL',
        'INICIANTE',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Campanhas AdWords',
        'Crie e gerencie campanhas eficazes no Google AdWords.',
        'MARKETING_DIGITAL',
        'INTERMEDIARIO',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

-- GESTAO_PROJETOS
INSERT INTO
    SKIL (
        SKIL_TX_NOME,
        SKIL_TX_DESCRICAO,
        SKIL_TX_CATEGORIA,
        SKIL_TX_DIFICULDADE,
        SKIL_BL_ATIVO,
        SKIL_DT_CADASTRO,
        SKIL_DT_ATUALIZACAO
    )
VALUES
    (
        'Fundamentos do Scrum',
        'Aprenda os princípios e práticas do Scrum.',
        'GESTAO_PROJETOS',
        'INICIANTE',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Gerenciamento Ágil de Projetos',
        'Técnicas avançadas para gerenciamento ágil.',
        'GESTAO_PROJETOS',
        'AVANCADO',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

-- VENDAS
INSERT INTO
    SKIL (
        SKIL_TX_NOME,
        SKIL_TX_DESCRICAO,
        SKIL_TX_CATEGORIA,
        SKIL_TX_DIFICULDADE,
        SKIL_BL_ATIVO,
        SKIL_DT_CADASTRO,
        SKIL_DT_ATUALIZACAO
    )
VALUES
    (
        'Técnicas de Vendas',
        'Métodos eficazes para aumentar suas vendas.',
        'VENDAS',
        'INICIANTE',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Negociação Avançada',
        'Estratégias de negociação para profissionais de vendas.',
        'VENDAS',
        'AVANCADO',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

-- IDIOMAS
INSERT INTO
    SKIL (
        SKIL_TX_NOME,
        SKIL_TX_DESCRICAO,
        SKIL_TX_CATEGORIA,
        SKIL_TX_DIFICULDADE,
        SKIL_BL_ATIVO,
        SKIL_DT_CADASTRO,
        SKIL_DT_ATUALIZACAO
    )
VALUES
    (
        'Inglês Básico',
        'Comece a aprender inglês com lições para iniciantes.',
        'IDIOMAS',
        'INICIANTE',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Espanhol Intermediário',
        'Desenvolva suas habilidades no idioma espanhol.',
        'IDIOMAS',
        'INTERMEDIARIO',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Francês para Negócios',
        'Aprimore seu francês para contextos profissionais.',
        'IDIOMAS',
        'AVANCADO',
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );