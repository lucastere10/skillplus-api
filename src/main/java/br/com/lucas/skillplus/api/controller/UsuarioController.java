package br.com.lucas.skillplus.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.lucas.skillplus.api.assembler.UsuarioPOSTInputDisassembler;
import br.com.lucas.skillplus.api.assembler.UsuarioPUTInputDisassembler;
import br.com.lucas.skillplus.api.assembler.FotoModelAssembler;
import br.com.lucas.skillplus.api.assembler.UsuarioModelAssembler;
import br.com.lucas.skillplus.api.dto.input.UsuarioPUTInput;
import br.com.lucas.skillplus.api.dto.input.FotoInput;
import br.com.lucas.skillplus.api.dto.input.UsuarioPOSTInput;
import br.com.lucas.skillplus.api.dto.model.FotoModel;
import br.com.lucas.skillplus.api.dto.model.UsuarioModel;
import br.com.lucas.skillplus.domain.exception.NegocioException;
import br.com.lucas.skillplus.domain.model.Foto;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.repository.UsuarioRepository;
import br.com.lucas.skillplus.domain.service.FotoService;
import br.com.lucas.skillplus.domain.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000/")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FotoService fotoService;

    @Autowired
    private FotoModelAssembler fotoModelAssembler;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioPOSTInputDisassembler postInputDisassembler;

    @Autowired
    private UsuarioPUTInputDisassembler putInputDisassembler;

    // CADASTRAR USUARIO COMO ADMIN
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody UsuarioPOSTInput usuarioInput) {
        Usuario usuario = postInputDisassembler.toDomainObject(usuarioInput);
        usuario = usuarioService.salvar(usuario);

        return usuarioModelAssembler.toModel(usuario);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UsuarioModel> listar(@RequestParam(required = false) String search, @PageableDefault(size = 10) Pageable pageable) {
        Page<Usuario> usuariosPage;
        
        if (search != null) {
            usuariosPage = usuarioRepository.findByNomeContainingIgnoreCase(search, pageable);
        } else {
            usuariosPage = usuarioRepository.findAll(pageable);
        }
    
        List<UsuarioModel> usuariosModel = usuarioModelAssembler.toCollectionModel(usuariosPage.getContent());
    
        return new PageImpl<>(usuariosModel, pageable, usuariosPage.getTotalElements());
    }

    @GetMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        return usuarioModelAssembler.toModel(usuario);
    }

    // ATUALIZAR USUARIO COMO ADMIN
    @PutMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel atualizarUsuarioAdmin(@PathVariable Long usuarioId,
            @RequestBody UsuarioPUTInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        putInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = usuarioService.salvar(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    // ATUALIZAR USUARIO COMO USUARIO
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel atualizarUsuarioLogado(@AuthenticationPrincipal Usuario usuario,
            @RequestBody UsuarioPUTInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuario.getUsuarioId());
        putInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = usuarioService.salvar(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @DeleteMapping(value = "/{usuarioId}", produces = {})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long usuarioId) {
        usuarioService.excluir(usuarioId);
    }

    @GetMapping("/verificar/{usuarioEmail}")
    @CrossOrigin(origins = "http://localhost:3000/")
    public ResponseEntity<Boolean> register(@PathVariable @Valid String usuarioEmail) {
        Optional<Usuario> user = this.usuarioRepository.findByEmail(usuarioEmail);
        if (user.isPresent()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    // FOTOS

    @GetMapping("/foto")
    public ResponseEntity<Foto> getFoto(@AuthenticationPrincipal Usuario usuario) {
        Foto foto = fotoService.getFoto(usuario);
        return ResponseEntity.ok(foto);
    }

    @PutMapping(value = "/foto/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CrossOrigin(origins = "http://localhost:3000/")
    public FotoModel uploadFoto(@AuthenticationPrincipal Usuario usuario, @Valid FotoInput fotoInput) {
        usuario = usuarioRepository.findById(usuario.getUsuarioId())
                .orElseThrow(() -> new NegocioException("Usuario not found"));
        Foto foto = fotoService.uploadFoto(usuario, fotoInput);
        return fotoModelAssembler.toModel(foto);
    }

    @GetMapping(value = "/foto/{nomeArquivo}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> recuperar(@PathVariable String nomeArquivo) {
        String[] fileExtensions = { "jpg", "jpeg", "png" };
        File file = null;

        for (String extension : fileExtensions) {
            String filePath = "src/main/resources/uploads/" + nomeArquivo + "." + extension;
            file = new File(filePath);
            if (file.exists()) {
                break;
            }
        }

        if (file == null || !file.exists()) {
            throw new NegocioException("Arquivo não encontrado");
        }

        try {
            InputStream inputStream = new FileInputStream(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));
        } catch (FileNotFoundException e) {
            throw new NegocioException("Error reading file", e);
        }
    }

    // STATUS

    @PutMapping(value = "/status/ativar/{usuarioId}")
    public UsuarioModel ativar(@PathVariable Long usuarioId) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioAtual = usuarioService.ativarUsuario(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @PutMapping(value = "/status/desativar/{usuarioId}")
    public UsuarioModel desativar(@PathVariable Long usuarioId) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioAtual = usuarioService.desativarUsuario(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @PutMapping(value = "/status/banir/{usuarioId}")
    public UsuarioModel banir(@PathVariable Long usuarioId) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioAtual = usuarioService.banirUsuario(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @PutMapping(value = "/status/bloquear/{usuarioId}")
    public UsuarioModel bloquear(@PathVariable Long usuarioId) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioAtual = usuarioService.bloquearUsuario(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    // 2FA
    @PatchMapping(value = "/update/2fa")
    public ResponseEntity<Void> update2fa(@AuthenticationPrincipal Usuario usuario) {
        usuarioService.update2fa(usuario.getUsuarioId());
        return ResponseEntity.ok().build();
    }

}
