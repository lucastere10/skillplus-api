package br.com.lucas.skillplus.api.dto.model;

import br.com.lucas.skillplus.domain.enums.UsuarioSkillDominio;
import br.com.lucas.skillplus.domain.model.Skill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSkillModel {

    @Schema(example = "1")
    private Long usuarioSkillId;

    @Schema(example = "1.18")
    private String usuarioSkillVersao;

    @Schema(example = "BASICO")
    private UsuarioSkillDominio usuarioSkillDominio;

    @Schema(example = "true")
    private Boolean ativo;

    @Schema(example = "Python")
    private Skill skill;

}
