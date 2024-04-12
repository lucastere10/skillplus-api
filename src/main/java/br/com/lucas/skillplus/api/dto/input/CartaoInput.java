package br.com.lucas.skillplus.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoInput {

    @Schema(example = "DOTCARD")
    private String cartaoNome;

    @Schema(example = "LUCAS M CALDAS")
    private String cartaoUsuario;

    @Schema(example = "Palestrante")
    private String cartaoUsuarioTipo;

    @Schema(example = "https://img.freepik.com/premium-photo/black-paper-texture-background-black-cardboard-artworks_434420-1392.jpg")
    private String cartaoBackground;

    @Schema(example = "não sei o que colocar aqui :D")
    private String cartaoUrl;

    @Schema(example = "true")
    private Boolean ativo;

}
