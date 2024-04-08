package br.com.lucas.skillplus.core.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Registrar com credenciais")
public record RegisterDTO(
    @Schema(description = "Nome do Usuário", example = "Lucas Caldas") String nome,
    @Schema(description = "Email do Usuário", example = "lucas.caldas@mail.com") String login,
    @Schema(description = "Senha do Usuário", example = "senha123") String senha
) {}
