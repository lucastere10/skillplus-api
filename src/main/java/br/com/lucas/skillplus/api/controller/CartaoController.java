package br.com.lucas.skillplus.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucas.skillplus.api.assembler.CartaoInputDisassembler;
import br.com.lucas.skillplus.api.assembler.CartaoModelAssembler;
import br.com.lucas.skillplus.api.dto.input.CartaoInput;
import br.com.lucas.skillplus.api.dto.model.CartaoModel;
import br.com.lucas.skillplus.api.openapi.CartaoControllerOpenApi;
import br.com.lucas.skillplus.domain.model.Cartao;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.repository.CartaoRepository;
import br.com.lucas.skillplus.domain.service.CartaoService;
import br.com.lucas.skillplus.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/api/cartoes")
@CrossOrigin(origins = "http://localhost:3000/")
public class CartaoController implements CartaoControllerOpenApi{

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CartaoModelAssembler cartaoModelAssembler;

    @Autowired
    private CartaoInputDisassembler cartaoInputDisassembler;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartaoModel> adicionar(@RequestBody CartaoInput cartaoInput,
            @AuthenticationPrincipal Usuario usuario) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuario.getUsuarioId());
        Cartao cartao = cartaoInputDisassembler.toDomainObject(cartaoInput);
        cartao = cartaoService.salvar(cartao, usuarioAtual);

        CartaoModel cartaoModel = cartaoModelAssembler.toModel(cartao);
        return ResponseEntity.ok(cartaoModel);
    }

    @PutMapping(value = "/{cartaoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartaoModel atualizarCartao(@PathVariable Long cartaoId,
            @RequestBody CartaoInput cartaoInput, @AuthenticationPrincipal Usuario usuario) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuario.getUsuarioId());
        Cartao cartaoAtual = cartaoService.buscarOuFalhar(cartaoId);
        cartaoInputDisassembler.copyToDomainObject(cartaoInput, cartaoAtual);
        cartaoAtual = cartaoService.salvar(cartaoAtual, usuarioAtual);

        return cartaoModelAssembler.toModel(cartaoAtual);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CartaoModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cartao> cartoesPage = cartaoRepository.findAll(pageable);

        List<CartaoModel> cartoesModel = cartaoModelAssembler
                .toCollectionModel(cartoesPage.getContent());

        return new PageImpl<>(cartoesModel, pageable,
                cartoesPage.getTotalElements());
    }

    @GetMapping(value = "/{cartaoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartaoModel buscar(@PathVariable Long cartaoId) {
        Cartao cartao = cartaoService.buscarOuFalhar(cartaoId);

        return cartaoModelAssembler.toModel(cartao);
    }

    @GetMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CartaoModel> listarCartoesDoUsuario(@AuthenticationPrincipal Usuario usuario,
            @PageableDefault(size = 10, sort = "cartaoId") Pageable pageable) {
        Page<Cartao> cartoesPage = cartaoRepository.findByUsuario(usuario, pageable);
        List<CartaoModel> cartoesModel = cartaoModelAssembler.toCollectionModel(cartoesPage.getContent());
        return new PageImpl<>(cartoesModel, pageable, cartoesPage.getTotalElements());
    }    

    @DeleteMapping("/{cartaoId}")
    public ResponseEntity<Void> excluir(@PathVariable Long cartaoId) {
        cartaoService.excluir(cartaoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/ativar/{cartaoId}")
    public ResponseEntity<Cartao> ativar(@PathVariable Long cartaoId) {
        Cartao cartao = cartaoService.ativar(cartaoId);
        return ResponseEntity.ok(cartao);
    }

}
