package br.com.lucas.skillplus.api.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lucas.skillplus.api.assembler.UsuarioSkillInputDisassembler;
import br.com.lucas.skillplus.api.assembler.UsuarioSkillModelAssembler;
import br.com.lucas.skillplus.api.dto.input.UsuarioSkillInput;
import br.com.lucas.skillplus.api.dto.model.UsuarioSkillModel;
import br.com.lucas.skillplus.api.openapi.UsuarioSkillControllerOpenApi;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.model.UsuarioSkill;
import br.com.lucas.skillplus.domain.repository.UsuarioRepository;
import br.com.lucas.skillplus.domain.repository.UsuarioSkillRepository;
import br.com.lucas.skillplus.domain.service.SkillService;
import br.com.lucas.skillplus.domain.service.UsuarioService;
import br.com.lucas.skillplus.domain.service.UsuarioSkillService;

@RestController
@RequestMapping(value = "/api/usuarioskills")
@CrossOrigin(origins = "http://localhost:3000/")
public class UsuarioSkillController implements UsuarioSkillControllerOpenApi {

    @Autowired
    private UsuarioSkillRepository usuarioSkillRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioSkillService usuarioSkillService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private UsuarioSkillModelAssembler usuarioSkillModelAssembler;

    @Autowired
    private UsuarioSkillInputDisassembler usuarioSkillInputDisassembler;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioSkillModel> adicionar(@RequestBody UsuarioSkillInput usuarioSkillInput,
            @AuthenticationPrincipal Usuario usuario) {
        UsuarioSkill usuarioSkill = usuarioSkillInputDisassembler.toDomainObject(usuarioSkillInput);

        usuarioSkill = usuarioSkillService.salvar(usuarioSkill, usuario, usuarioSkillInput.getSkillNome());
        UsuarioSkillModel usuarioSkillModel = usuarioSkillModelAssembler.toModel(usuarioSkill);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioSkillModel.getUsuarioSkillId()).toUri();

        return ResponseEntity.created(location).body(usuarioSkillModel);
    }

    @PutMapping(value = "/{usuarioSkillId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioSkillModel atualizarUsuarioSkill(@PathVariable Long usuarioSkillId,
            @RequestBody UsuarioSkillInput usuarioSkillInput, @AuthenticationPrincipal Usuario usuario) {

        UsuarioSkill usuarioSkillAtual = usuarioSkillService.buscarOuFalhar(usuarioSkillId);
        usuarioSkillInputDisassembler.copyToDomainObject(usuarioSkillInput, usuarioSkillAtual);
        usuarioSkillAtual = usuarioSkillService.atualizar(usuarioSkillAtual, usuario);

        return usuarioSkillModelAssembler.toModel(usuarioSkillAtual);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UsuarioSkillModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<UsuarioSkill> usuarioSkillsPage = usuarioSkillRepository.findAll(pageable);

        List<UsuarioSkillModel> usuarioSkillsModel = usuarioSkillModelAssembler
                .toCollectionModel(usuarioSkillsPage.getContent());

        return new PageImpl<>(usuarioSkillsModel, pageable,
                usuarioSkillsPage.getTotalElements());
    }

    @GetMapping(value = "/{usuarioSkillId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioSkillModel buscar(@PathVariable Long usuarioSkillId) {
        UsuarioSkill usuarioSkill = usuarioSkillService.buscarOuFalhar(usuarioSkillId);

        return usuarioSkillModelAssembler.toModel(usuarioSkill);
    }

    @GetMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UsuarioSkillModel> listarUsuarioSkillsDoUsuario(@AuthenticationPrincipal Usuario usuario,
            @PageableDefault(size = 10, sort = "usuarioSkillId") Pageable pageable) {
        Page<UsuarioSkill> usuarioSkillsPage = usuarioSkillRepository.findByUsuario(usuario, pageable);

        List<UsuarioSkillModel> usuarioSkillsModel = usuarioSkillModelAssembler
                .toCollectionModel(usuarioSkillsPage.getContent());

        return new PageImpl<>(usuarioSkillsModel, pageable, usuarioSkillsPage.getTotalElements());
    }

    @GetMapping(value = "/usuario/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UsuarioSkillModel> listarUsuarioSkillsPorUsuarioId(@PathVariable Long usuarioId,
            @PageableDefault(size = 10) Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        Page<UsuarioSkill> usuarioSkillsPage = usuarioSkillRepository.findByUsuario(usuario, pageable);
        List<UsuarioSkillModel> usuarioSkillsModel = usuarioSkillModelAssembler
                .toCollectionModel(usuarioSkillsPage.getContent());

        return new PageImpl<>(usuarioSkillsModel, pageable, usuarioSkillsPage.getTotalElements());
    }

    @GetMapping(value = "/usuario/{usuarioId}/publico", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UsuarioSkillModel> listarUsuarioSkillsPublicosPorUsuarioId(@PathVariable Long usuarioId,
            @PageableDefault(size = 10) Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        Page<UsuarioSkill> usuarioSkillsPage = usuarioSkillRepository.findByUsuarioAndAtivoTrue(usuario, pageable);
        List<UsuarioSkillModel> usuarioSkillsModel = usuarioSkillModelAssembler
                .toCollectionModel(usuarioSkillsPage.getContent());

        return new PageImpl<>(usuarioSkillsModel, pageable, usuarioSkillsPage.getTotalElements());
    }

    @DeleteMapping("/{usuarioSkillId}")
    public ResponseEntity<Void> excluir(@PathVariable Long usuarioSkillId) {
        usuarioSkillService.excluir(usuarioSkillId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/ativar/{usuarioSkillId}")
    public ResponseEntity<UsuarioSkill> ativar(@PathVariable Long usuarioSkillId) {
        UsuarioSkill usuarioSkill = usuarioSkillService.ativar(usuarioSkillId);
        return ResponseEntity.ok(usuarioSkill);
    }

}
