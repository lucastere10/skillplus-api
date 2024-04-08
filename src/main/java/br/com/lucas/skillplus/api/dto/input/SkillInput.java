package br.com.lucas.skillplus.api.dto.input;

import br.com.lucas.skillplus.domain.enums.SkillCategoria;
import br.com.lucas.skillplus.domain.enums.SkillDificuldade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillInput {

    @Schema(example = "Python")
    private String skillNome;

    @Schema(example = "Linguagem de Programação")
    private String skillDescricao;

    @Schema(example = "PROGRAMACAO")
    private SkillCategoria skillCategoria;

    @Schema(example = "INICIANTE")
    private SkillDificuldade skillDificuldade;

    @Schema(example = "www.python.com")
    private String skillUrl;

    @Schema(example = "true")
    private Boolean ativo;
}
