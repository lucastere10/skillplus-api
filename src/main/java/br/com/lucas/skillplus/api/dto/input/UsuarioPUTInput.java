package br.com.lucas.skillplus.api.dto.input;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioPUTInput {

    @Schema(example = "Lucas Caldas")
    private String nome;

    @Schema(example = "Lucas Caldas")
    private String nomeSocial;

    @Schema(example = "lucas.caldas@mail.com")
    private String email;

    @Schema(example = "(21) 99191-8282")
    private String telefone;

    @Schema(example = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

}
