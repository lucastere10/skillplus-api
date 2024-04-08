package br.com.lucas.skillplus.api.dto.model;

import br.com.lucas.skillplus.domain.enums.ContatoTipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoModel {

    @Schema(example = "1")
    private Long contatoId;

    @Schema(example = "Instagram")
    private String contatoNome;

    @Schema(example = "url")
    private String contatoUrl;

    @Schema(example = "INSTAGRAM")
    private ContatoTipo contatoTipo;

    @Schema(example = "true")
    private Boolean privado;

}
