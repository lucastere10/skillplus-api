package br.com.lucas.skillplus.api.openapi;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.lucas.skillplus.api.dto.input.UsuarioSkillInput;
import br.com.lucas.skillplus.api.dto.model.UsuarioSkillModel;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.model.UsuarioSkill;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Tag(name = "Skills dos Usuários", description = "Endpoints de UsuarioSkill")
public interface UsuarioSkillControllerOpenApi {

        @Operation(summary = "Criar um UsuarioSKill", responses = {
                        @ApiResponse(responseCode = "201", description = "UsuarioSkill criado com sucesso!"),
                        @ApiResponse(responseCode = "400", description = "Verificar corpo da requisição", content = @Content(schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Usuário ou Skill não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<UsuarioSkillModel> adicionar(
                        @RequestBody(description = "Cadastro de um novo UsuarioSkill") UsuarioSkillInput usuarioSkillInput,
                        @AuthenticationPrincipal Usuario usuario);

        @Operation(summary = "Atualizar um UsuarioSKill", responses = {
                        @ApiResponse(responseCode = "200", description = "UsuarioSkill Atualizado com Sucesso"),
                        @ApiResponse(responseCode = "400", description = "Verificar corpo da requisição", content = @Content(schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "Usuário ou Skill não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public UsuarioSkillModel atualizarUsuarioSkill(
                        @Parameter(description = "ID de um UsuarioSkill") @PathVariable Long usuarioSkillId,
                        @RequestBody(description = "Representação de um UsuarioSkill com dados atualizados") UsuarioSkillInput usuarioSkillInput,
                        @AuthenticationPrincipal Usuario usuario);

        @Operation(summary = "Listar todos os UsuarioSkills", parameters = {
                        @Parameter(name = "page", example = "0"),
                        @Parameter(name = "size", example = "10"),
                        @Parameter(name = "sort", example = "usuarioId")
        })
        @PageableAsQueryParam
        @SecurityRequirement(name = "Bearer Authentication")
        public Page<UsuarioSkillModel> listar(
                        @Parameter(hidden = true) Pageable pageable);

        @Operation(summary = "Buscar um UsuarioSkills por ID", responses = {
                        @ApiResponse(responseCode = "200"),
                        @ApiResponse(responseCode = "400", description = "ID de UsuarioSkill inválido", content = @Content(schema = @Schema(ref = "Problema"))),
                        @ApiResponse(responseCode = "404", description = "UsuarioSkill não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public UsuarioSkillModel buscar(
                        @Parameter(description = "ID do usuario", example = "1", required = true) @PathVariable Long usuarioId);

        @Operation(summary = "Listar todos os UsuarioSkills de um usuário específico")
        @PageableAsQueryParam
        @SecurityRequirement(name = "Bearer Authentication")
        public Page<UsuarioSkillModel> listarUsuarioSkillsDoUsuario(
                        @AuthenticationPrincipal Usuario usuario,
                        @PageableDefault(size = 10, sort = "usuarioSkillId") Pageable pageable);

        @Operation(summary = "Listar todos os UsuarioSkills por ID de usuário")
        @PageableAsQueryParam
        @SecurityRequirement(name = "Bearer Authentication")
        public Page<UsuarioSkillModel> listarUsuarioSkillsPorUsuarioId(
                        @Parameter(description = "ID do usuário", example = "1", required = true) @PathVariable Long usuarioId,
                        @PageableDefault(size = 10) Pageable pageable);

        @Operation(summary = "Listar todos os UsuarioSkills públicos por ID de usuário")
        @PageableAsQueryParam
        @SecurityRequirement(name = "Bearer Authentication")
        public Page<UsuarioSkillModel> listarUsuarioSkillsPublicosPorUsuarioId(
                        @Parameter(description = "ID do usuário", example = "1", required = true) @PathVariable Long usuarioId,
                        @PageableDefault(size = 10) Pageable pageable);

        @Operation(summary = "Excluir um UsuarioSkill por ID", responses = {
                        @ApiResponse(responseCode = "204", description = "UsuarioSkill excluído com sucesso"),
                        @ApiResponse(responseCode = "404", description = "UsuarioSkill não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<Void> excluir(
                        @Parameter(description = "ID de um UsuarioSkill") @PathVariable Long usuarioSkillId);

        @Operation(summary = "Ativar um UsuarioSkill por ID", responses = {
                        @ApiResponse(responseCode = "200", description = "UsuarioSkill ativado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "UsuarioSkill não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<UsuarioSkill> ativar(
                        @Parameter(description = "ID de um UsuarioSkill") @PathVariable Long usuarioSkillId);

}
