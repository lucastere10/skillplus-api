package br.com.lucas.skillplus.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioPOSTInput {
    
    @Schema(example = "Lucas Caldas")
    private String email;
    
    @Schema(example = "lucas.caldas@mail.com")
    private String nome;

}
