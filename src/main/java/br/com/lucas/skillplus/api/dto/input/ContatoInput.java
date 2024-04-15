package br.com.lucas.skillplus.api.dto.input;

import br.com.lucas.skillplus.domain.enums.ContatoTipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoInput {

    @Schema(example = "INSTAGRAM")
    private ContatoTipo contatoTipo;

    @Schema(example = "Instagram Pessoal")
    private String contatoNome;

    @Schema(example = "www.com.br")
    private String contatoUrl;

    @Schema(example = "true")
    private Boolean privado;
    
}
