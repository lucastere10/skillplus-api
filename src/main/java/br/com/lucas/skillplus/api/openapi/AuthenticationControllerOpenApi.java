package br.com.lucas.skillplus.api.openapi;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.lucas.skillplus.api.dto.model.UsuarioModel;
import br.com.lucas.skillplus.core.security.dto.AuthenticationDTO;
import br.com.lucas.skillplus.core.security.dto.LoginResponseDTO;
import br.com.lucas.skillplus.core.security.dto.RegisterDTO;
import br.com.lucas.skillplus.domain.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Autenticação", description = "Endpoints do sistema de autenticação")
public interface AuthenticationControllerOpenApi {

        @Operation(summary = "Fazer Login", responses = {
                        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Login ou Senha incorretos", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data);

        @Operation(summary = "Cadastrar novo usuário", responses = {
                        @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Verifique o corpo da requisição", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data);

        @Operation(summary = "Buscar informações do usuário logado")
        @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<UsuarioModel> currentUser(@AuthenticationPrincipal Usuario usuario);

}
