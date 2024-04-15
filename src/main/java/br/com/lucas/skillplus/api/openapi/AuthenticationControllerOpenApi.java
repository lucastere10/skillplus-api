package br.com.lucas.skillplus.api.openapi;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import br.com.lucas.skillplus.api.dto.model.UsuarioModel;
import br.com.lucas.skillplus.core.security.dto.AuthenticationDTO;
import br.com.lucas.skillplus.core.security.dto.LoginResponseDTO;
import br.com.lucas.skillplus.core.security.dto.RegisterDTO;
import br.com.lucas.skillplus.domain.model.Usuario;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.exceptions.QrGenerationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
        public ResponseEntity<LoginResponseDTO> login(
                        @RequestBody(description = "Email e Senha do usuário", required = true) @Valid AuthenticationDTO data);

        @Operation(summary = "Fazer Login Social", description = "Sistema para login social usando mediadores como a Google ou Github", responses = {
                        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Login ou Senha incorretos", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        public ResponseEntity<LoginResponseDTO> socialLogin(
                        @RequestBody(description = "Email e Senha do usuário", required = true) @Valid RegisterDTO data);

        @Operation(summary = "Cadastrar novo usuário", responses = {
                        @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Verifique o corpo da requisição", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        public ResponseEntity<Void> register(
                        @RequestBody(description = "Nome, Email e Senha do usuário", required = true) @Valid RegisterDTO data);

        @Operation(summary = "Cadastrar novo admin", responses = {
                        @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Verifique o corpo da requisição", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        public ResponseEntity<Void> registerAdmin(
                        @RequestBody(description = "Nome, Email e Senha do usuário", required = true) @Valid RegisterDTO data);

        @Operation(summary = "Buscar informações do usuário logado")
        @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<UsuarioModel> currentUser(@AuthenticationPrincipal Usuario usuario);

        @Operation(summary = "Gera uma totp em formato de imagem base64 com códio de autenticação")
        public String setupTOTP() throws QrGenerationException;

        @Operation(summary = "Verificar se o código de 6 digitos está correto")
        public String verify(@Parameter(description = "Código de verificação") String code);

        @Operation(summary = "Gera um código de 6 digitos para realizar a autenticação do totp com duração de 30 segundos")
        public String verifyMobile() throws CodeGenerationException;

        @Operation(summary = "Verifica metadata do usuario, se ja tem foto, perfil completo e se o 2FA está habilitado")
        @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<Map<String, Boolean>> verifyProfileComplete(@AuthenticationPrincipal Usuario usuario);

}
