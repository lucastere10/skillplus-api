package br.com.lucas.skillplus.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.lucas.skillplus.api.assembler.ContatoInputDisassembler;
import br.com.lucas.skillplus.api.assembler.ContatoModelAssembler;
import br.com.lucas.skillplus.api.dto.input.ContatoInput;
import br.com.lucas.skillplus.api.dto.model.ContatoModel;
import br.com.lucas.skillplus.domain.model.Contato;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.repository.ContatoRepository;
import br.com.lucas.skillplus.domain.repository.UsuarioRepository;
import br.com.lucas.skillplus.domain.service.ContatoService;
import br.com.lucas.skillplus.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/api/contatos")
@CrossOrigin(origins = "http://localhost:3000/")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ContatoService contatoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContatoModelAssembler contatoModelAssembler;

    @Autowired
    private ContatoInputDisassembler contatoInputDisassembler;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContatoModel> adicionar(@RequestBody ContatoInput contatoInput,
            @AuthenticationPrincipal Usuario usuario) {
        Contato contato = contatoInputDisassembler.toDomainObject(contatoInput);
        contato = contatoService.salvar(contato, usuario);
        ContatoModel contatoModel = contatoModelAssembler.toModel(contato);

        return ResponseEntity.ok(contatoModel);
    }

    @PutMapping(value = "/{contatoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ContatoModel atualizarContato(@PathVariable Long contatoId,
            @RequestBody ContatoInput contatoInput, @AuthenticationPrincipal Usuario usuario) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuario.getUsuarioId());
        Contato contatoAtual = contatoService.buscarOuFalhar(contatoId);
        contatoInputDisassembler.copyToDomainObject(contatoInput, contatoAtual);
        contatoAtual = contatoService.salvar(contatoAtual, usuarioAtual);

        return contatoModelAssembler.toModel(contatoAtual);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ContatoModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Contato> contatosPage = contatoRepository.findAll(pageable);

        List<ContatoModel> contatosModel = contatoModelAssembler
                .toCollectionModel(contatosPage.getContent());

        return new PageImpl<>(contatosModel, pageable,
                contatosPage.getTotalElements());
    }

    @GetMapping(value = "/{contatoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ContatoModel buscar(@PathVariable Long contatoId) {
        Contato contato = contatoService.buscarOuFalhar(contatoId);

        return contatoModelAssembler.toModel(contato);
    }

    @GetMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ContatoModel> listarContatosDoUsuario(@AuthenticationPrincipal Usuario usuario,
            @PageableDefault(size = 10, sort = "contatoId") Pageable pageable) {
        Page<Contato> contatosPage = contatoRepository.findByUsuario(usuario, pageable);

        List<ContatoModel> contatosModel = contatoModelAssembler.toCollectionModel(contatosPage.getContent());

        return new PageImpl<>(contatosModel, pageable, contatosPage.getTotalElements());
    }

    @GetMapping(value = "/usuario/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ContatoModel> listarContatosPorUsuarioId(@PathVariable Long usuarioId,
            @PageableDefault(size = 10) Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        Page<Contato> contatosPage = contatoRepository.findByUsuario(usuario, pageable);
        List<ContatoModel> contatosModel = contatoModelAssembler.toCollectionModel(contatosPage.getContent());

        return new PageImpl<>(contatosModel, pageable, contatosPage.getTotalElements());
    }

    @GetMapping(value = "/usuario/{usuarioId}/publico", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ContatoModel> listarContatosPublicosPorUsuarioId(@PathVariable Long usuarioId,
            @PageableDefault(size = 10) Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        Page<Contato> contatosPage = contatoRepository.findByUsuarioAndPrivadoTrue(usuario, pageable);
        List<ContatoModel> contatosModel = contatoModelAssembler.toCollectionModel(contatosPage.getContent());

        return new PageImpl<>(contatosModel, pageable, contatosPage.getTotalElements());
    }

    @DeleteMapping("/{contatoId}")
    public ResponseEntity<Void> excluir(@PathVariable Long contatoId) {
        contatoService.excluir(contatoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/privado/{contatoId}")
    public ResponseEntity<Contato> privar(@PathVariable Long contatoId) {
        Contato contato = contatoService.privar(contatoId);
        return ResponseEntity.ok(contato);
    }

}
