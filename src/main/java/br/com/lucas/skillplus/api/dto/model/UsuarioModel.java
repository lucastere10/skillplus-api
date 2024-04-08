package br.com.lucas.skillplus.api.dto.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.lucas.skillplus.domain.enums.UsuarioStatus;
import br.com.lucas.skillplus.domain.enums.UsuarioTipo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

    @Schema(example = "1")
    private Long usuarioId;

    @Schema(example = "Lucas Caldas")
    private String nome;

    @Schema(example = "Lucas Caldas")
    private String nomeSocial;

    @Schema(example = "lucas.caldas@mail.com")
    private String email;

    @Schema(example = "(21) 9 9191-8282")
    private String telefone;

    @Schema(example = "True")
    private Boolean twoFa;

    @Schema(example = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Schema(example = "ADMIN")
    private UsuarioTipo usuarioTipo;

    @Schema(example = "ATIVO")
    private UsuarioStatus usuarioStatus;

    @Schema(example = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private OffsetDateTime dataCadastro;

    @Schema(example = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private OffsetDateTime dataAtualizacao;
}
