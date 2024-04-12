package br.com.lucas.skillplus.api.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoModel {

    @Schema(example = "1")
    private Long cartaoId;

    @Schema(example = "DOTCARD")
    private String cartaoNome;

    @Schema(example = "LUCAS M CALDAS")
    private String cartaoUsuario;

    @Schema(example = "Admin")
    private String cartaoUsuarioTipo;

    @Schema(example = "https://img.freepik.com/premium-photo/black-paper-texture-background-black-cardboard-artworks_434420-1392.jpg")
    private String cartaoBackground;

    @Schema(example = "www.url.com")
    private String cartaoUrl;

    @Schema(example = "")
    private String qrcode;

    @Schema(example = "true")
    private Boolean ativo;

}
