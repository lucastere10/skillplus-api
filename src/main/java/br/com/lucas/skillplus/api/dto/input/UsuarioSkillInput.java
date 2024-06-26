package br.com.lucas.skillplus.api.dto.input;

import br.com.lucas.skillplus.domain.enums.UsuarioSkillDominio;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSkillInput {

    @Schema(example = "Python")
    private String skillNome;

    @Schema(example = "1.18")
    private String usuarioSkillVersao;

    @Schema(example = "BASICO")
    private UsuarioSkillDominio usuarioSkillDominio;

    @Schema(example = "true")
    private Boolean ativo;
}
