package br.com.lucas.skillplus.api.openapi;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.lucas.skillplus.api.dto.input.SkillInput;
import br.com.lucas.skillplus.api.dto.model.SkillModel;
import br.com.lucas.skillplus.domain.model.Skill;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Tag(name = "Skills", description = "Endpoints de Skills")
public interface SkillControllerOpenApi {

    @Operation(summary = "Criar uma SKill", responses = {
            @ApiResponse(responseCode = "201", description = "Skill criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Verificar corpo da requisição", content = @Content(schema = @Schema(ref = "Problema")))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<SkillModel> adicionar(
            @RequestBody(description = "Cadastro de uma nova Skill") SkillInput skillInput);

    @Operation(summary = "Listar todas as Skills", parameters = {
            @Parameter(name = "page", example = "0"),
            @Parameter(name = "size", example = "10"),
            @Parameter(name = "sort", example = "skillId")
    })
    @PageableAsQueryParam
    @SecurityRequirement(name = "Bearer Authentication")
    public Page<SkillModel> listar(
            @Parameter(description = "string de pesquia", required = false) String search,
            @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Buscar uma Skill por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID de Skill inválida", content = @Content(schema = @Schema(ref = "Problema"))),
            @ApiResponse(responseCode = "404", description = "Skill não encontrada", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public SkillModel buscar(
            @Parameter(description = "ID da skill", example = "1", required = true) @PathVariable Long skillId);

    @Operation(summary = "Atualizar uma SKill", responses = {
            @ApiResponse(responseCode = "200", description = "Skill atualizada com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Verificar corpo da requisição", content = @Content(schema = @Schema(ref = "Problema"))),
            @ApiResponse(responseCode = "404", description = "Skill não encontrada", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public SkillModel atualizarSkillAdmin(
            @Parameter(description = "ID de uma Skill") @PathVariable Long skillId,
            @RequestBody(description = "Representação de uma Skill com dados atualizados") SkillInput skillInput);

    @Operation(summary = "Excluir uma Skill por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Skill excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Skill não encontrada", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public void remover(
            @Parameter(description = "ID de uma Skill") @PathVariable Long skillId);

    @Operation(summary = "Ativar uma skill por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Skill ativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Skill não encontrada", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Skill> ativar(
            @Parameter(description = "ID de uma skill") @PathVariable Long skillId);

}
