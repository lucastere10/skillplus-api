package br.com.lucas.skillplus.api.openapi;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.lucas.skillplus.api.dto.input.CartaoInput;
import br.com.lucas.skillplus.api.dto.model.CartaoModel;
import br.com.lucas.skillplus.domain.model.Cartao;
import br.com.lucas.skillplus.domain.model.Usuario;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Tag(name = "Cartão", description = "Endpoints de Cartões")
public interface CartaoControllerOpenApi {

    @Operation(summary = "Criar um Cartão", responses = {
            @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Verificar corpo da requisição", content = @Content(schema = @Schema(ref = "Problema")))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CartaoModel> adicionar(
            @RequestBody(description = "Cadastro de um novo Cartão") CartaoInput cartaoInput,
            @AuthenticationPrincipal Usuario usuario);

    @Operation(summary = "Atualizar um Contato", responses = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Verificar corpo da requisição", content = @Content(schema = @Schema(ref = "Problema"))),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public CartaoModel atualizarCartao(
            @Parameter(description = "ID de um Cartão") @PathVariable Long cartaoId,
            @RequestBody(description = "Representação de um Cartão com dados atualizados") CartaoInput cartaoInput,
            @AuthenticationPrincipal Usuario usuario);

    @Operation(summary = "Listar todos os Cartões", parameters = {
            @Parameter(name = "page", example = "0"),
            @Parameter(name = "size", example = "10"),
            @Parameter(name = "sort", example = "cartaoId")
    })
    @PageableAsQueryParam
    @SecurityRequirement(name = "Bearer Authentication")
    public Page<CartaoModel> listar(@PageableDefault(size = 10) Pageable pageable);

    @Operation(summary = "Buscar um cartão por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID de cartão inválido", content = @Content(schema = @Schema(ref = "Problema"))),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public CartaoModel buscar(
            @Parameter(description = "ID do cartão", example = "1", required = true) @PathVariable Long cartaoId);

    @Operation(summary = "Listar todos os cartões de um usuário específico")
    @PageableAsQueryParam
    @SecurityRequirement(name = "Bearer Authentication")
    public Page<CartaoModel> listarCartoesDoUsuario(@AuthenticationPrincipal Usuario usuario,
            @PageableDefault(size = 10, sort = "cartaoId") Pageable pageable);

    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do cartão", example = "1", required = true) @PathVariable Long cartaoId);

    @Operation(summary = "Ativar/Desativar um cartão por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Cartão ativado ou desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cartão não encontrado", content = @Content(schema = @Schema(ref = "Problema"))),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Cartao> ativar(
            @Parameter(description = "ID do cartão", example = "1", required = true) @PathVariable Long cartaoId);

}
