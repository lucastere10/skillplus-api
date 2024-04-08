package br.com.lucas.skillplus.api.openapi;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.lucas.skillplus.api.dto.input.UsuarioPOSTInput;
import br.com.lucas.skillplus.api.dto.input.UsuarioPUTInput;
import br.com.lucas.skillplus.api.dto.model.UsuarioModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuários", description = "Endpoints de usuários")
public interface UsuarioControllerOpenApi {

        @PageableAsQueryParam
        @Operation(summary = "Listar todas as usuários", parameters = {
                        @Parameter(name = "page", example = "0"),
                        @Parameter(name = "size", example = "10"),
                        @Parameter(name = "sort", example = "")
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public Page<UsuarioModel> listar(@Parameter(hidden = true) Pageable pageable);

        @Operation(summary = "Buscar uma usuário por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID de usuário inválido", content = @Content(schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public UsuarioModel buscar(
                        @Parameter(description = "ID do usuario", example = "1", required = true) @PathVariable Long usuarioId);

        /**
         * @deprecated This method will be removed in favor of the new registration
         *             system. Please use the '/auth/register' endpoint instead.
         */
        @Operation(summary = "Adicionar novo usuário", deprecated = true, description = "Esse método será removido em detrimento ao novo sistema de registro. Por favor, utilize o endpoint '/auth/register' no lugar.", responses = {
                        @ApiResponse(responseCode = "201", description = "Usuario Cadastrado com Sucesso")
        })
        @Deprecated(since = "version 0.01", forRemoval = true)
        public UsuarioModel adicionar(
                        @RequestBody(description = "Cadastro de um novo usuário", required = true) UsuarioPOSTInput usuarioInput);

        @Operation(summary = "Atualizar um usuário por ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Usuario Atualizado com Sucesso"),
                        @ApiResponse(responseCode = "400", description = "ID de usuário inválido", content = @Content(schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public UsuarioModel atualizarUsuarioAdmin(
                        @Parameter(description = "ID de um usuário") @PathVariable Long usuarioId,
                        @RequestBody(description = "Representação de um usuario com dados atualizados") UsuarioPUTInput usuarioInput);

        @Operation(summary = "Atualizar um usuário por Usuário Logado", responses = {
                        @ApiResponse(responseCode = "200", description = "Usuario Atualizado com Sucesso"),
                        @ApiResponse(responseCode = "400", description = "ID de usuário inválido", content = @Content(schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public UsuarioModel atualizarUsuarioLogado(
                        @RequestBody(description = "Representação de um usuario com dados atualizados") UsuarioPUTInput usuarioInput);

        /**
         * @deprecated This method will be removed in favor of the new inactivation
         *             system. Please use the '/usuarios/inativar' endpoint instead.
         */
        @Operation(summary = "Remover um usuario por ID", deprecated = true, description = "Esse método será removido em detrimento ao novo sistema de inativação de usuários. Por favor, utilize o endpoint '/usuarios/desativar' no lugar.")
        @Deprecated(since = "version 0.01", forRemoval = true)
        public void remover(@Parameter(description = "ID de um usuario") @PathVariable Long usuarioId);

        @Operation(summary = "Mudar Status do Usuário para Ativo")
        @SecurityRequirement(name = "Bearer Authentication")
        public UsuarioModel ativar(@Parameter(description = "ID de um usuário") Long usuarioId);

        @Operation(summary = "Mudar Status do Usuário para Inativo")
        @SecurityRequirement(name = "Bearer Authentication")
        public UsuarioModel desativar(@Parameter(description = "ID de um usuário") Long usuarioId);

        @Operation(summary = "Mudar Status do Usuário para Banido")
        @SecurityRequirement(name = "Bearer Authentication")
        public UsuarioModel banir(@Parameter(description = "ID de um usuário") Long usuarioId);

        @Operation(summary = "Mudar Status do Usuário para Bloqueado")
        @SecurityRequirement(name = "Bearer Authentication")
        public UsuarioModel bloquear(@Parameter(description = "ID de um usuário") Long usuarioId);

}
