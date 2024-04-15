package br.com.lucas.skillplus.api.openapi;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.lucas.skillplus.api.dto.input.ContatoInput;
import br.com.lucas.skillplus.api.dto.model.ContatoModel;
import br.com.lucas.skillplus.domain.model.Contato;
import br.com.lucas.skillplus.domain.model.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Tag(name = "Contatos", description = "Endpoints de Contatos")
public interface ContatoControllerOpenApi {

    @Operation(summary = "Criar um Contato", responses = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Verificar corpo da requisição", content = @Content(schema = @Schema(ref = "Problema")))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ContatoModel> adicionar(
            @RequestBody(description = "Cadastro de um novo Contato") ContatoInput contatoInput,
            @AuthenticationPrincipal Usuario usuario);

    @Operation(summary = "Atualizar um Contato", responses = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Verificar corpo da requisição", content = @Content(schema = @Schema(ref = "Problema"))),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ContatoModel atualizarContato(
            @Parameter(description = "ID de um Contato") @PathVariable Long contatoId,
            @RequestBody(description = "Representação de um Contato com dados atualizados") ContatoInput contatoInput,
            @AuthenticationPrincipal Usuario usuario);

    @Operation(summary = "Listar todo os Contatos", parameters = {
            @Parameter(name = "page", example = "0"),
            @Parameter(name = "size", example = "10"),
            @Parameter(name = "sort", example = "contatoId")
    })
    @PageableAsQueryParam
    @SecurityRequirement(name = "Bearer Authentication")
    public Page<ContatoModel> listar(
        @Parameter(description = "string de pesquia", required = false) String search,
        @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Buscar um contato por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID de contato inválida", content = @Content(schema = @Schema(ref = "Problema"))),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ContatoModel buscar(
            @Parameter(description = "ID do contato", example = "1", required = true) @PathVariable Long contatoId);

    @Operation(summary = "Listar todos os contatos de um usuário específico")
    @PageableAsQueryParam
    @SecurityRequirement(name = "Bearer Authentication")
    public Page<ContatoModel> listarContatosDoUsuario(
            @AuthenticationPrincipal Usuario usuario,
            @PageableDefault(size = 10, sort = "contatoId") Pageable pageable);

    @Operation(summary = "Listar todos os contatos por ID de usuário")
    @PageableAsQueryParam
    @SecurityRequirement(name = "Bearer Authentication")
    public Page<ContatoModel> listarContatosPorUsuarioId(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 10) Pageable pageable);

    @Operation(summary = "Listar todos os contatos públicos por ID de usuário")
    @PageableAsQueryParam
    @SecurityRequirement(name = "Bearer Authentication")
    public Page<ContatoModel> listarContatosPublicosPorUsuarioId(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 10) Pageable pageable);

    @Operation(summary = "Excluir um contato por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Contato excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do contato", example = "1", required = true) @PathVariable Long contatoId);

    @Operation(summary = "Privar um contato por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Contato ativado ou desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Contato> privar(
            @Parameter(description = "ID do contato", example = "1", required = true) @PathVariable Long contatoId);

}
