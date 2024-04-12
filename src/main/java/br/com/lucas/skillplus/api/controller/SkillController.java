package br.com.lucas.skillplus.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.lucas.skillplus.api.assembler.SkillInputDisassembler;
import br.com.lucas.skillplus.api.assembler.SkillModelAssembler;
import br.com.lucas.skillplus.api.dto.input.SkillInput;
import br.com.lucas.skillplus.api.dto.model.SkillModel;
import br.com.lucas.skillplus.api.openapi.SkillControllerOpenApi;
import br.com.lucas.skillplus.domain.model.Skill;
import br.com.lucas.skillplus.domain.repository.SkillRepository;
import br.com.lucas.skillplus.domain.service.SkillService;

@RestController
@RequestMapping(value = "/api/skills")
@CrossOrigin(origins = "http://localhost:3000/")
public class SkillController implements SkillControllerOpenApi {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillService skillService;

    @Autowired
    SkillInputDisassembler skillInputDisassembler;

    @Autowired
    SkillModelAssembler skillModelAssembler;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SkillModel> adicionar(@RequestBody SkillInput skillInput) {
        Skill skill = skillInputDisassembler.toDomainObject(skillInput);
        skill = skillService.salvar(skill);
        SkillModel skillModel = skillModelAssembler.toModel(skill);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(skillModel.getSkillId()).toUri();

        return ResponseEntity.created(location).body(skillModel);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SkillModel> listar(@RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Skill> skillsPage;

        if (search != null) {
            skillsPage = skillRepository.findByNomeContainingIgnoreCase(search, pageable);
        } else {
            skillsPage = skillRepository.findAll(pageable);
        }

        List<SkillModel> skillsModel = skillModelAssembler.toCollectionModel(skillsPage.getContent());

        return new PageImpl<>(skillsModel, pageable, skillsPage.getTotalElements());
    }

    @GetMapping(value = "/publico", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<SkillModel> listarAtivos(@RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<Skill> skillsPage;

        if (search != null) {
            skillsPage = skillRepository.findByAtivoTrueAndSkillNomeContainingIgnoreCase(search, pageable);
        } else {
            skillsPage = skillRepository.findByAtivoTrue(pageable);
        }

        List<SkillModel> skillsModel = skillModelAssembler.toCollectionModel(skillsPage.getContent());
        return new PageImpl<>(skillsModel, pageable, skillsPage.getTotalElements());
    }

    @GetMapping(value = "/{skillId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SkillModel buscar(@PathVariable Long skillId) {
        Skill skill = skillService.buscarOuFalhar(skillId);

        return skillModelAssembler.toModel(skill);
    }

    @PutMapping(value = "/{skillId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SkillModel atualizarSkillAdmin(@PathVariable Long skillId,
            @RequestBody SkillInput skillInput) {
        Skill skillAtual = skillService.buscarOuFalhar(skillId);
        skillInputDisassembler.copyToDomainObject(skillInput, skillAtual);
        skillAtual = skillService.salvar(skillAtual);

        return skillModelAssembler.toModel(skillAtual);
    }

    @DeleteMapping(value = "/{skillId}", produces = {})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long skillId) {
        skillService.excluir(skillId);
    }

    @PatchMapping(value = "/ativar/{skillId}")
    public ResponseEntity<Skill> ativar(@PathVariable Long skillId) {
        Skill skill = skillService.ativar(skillId);
        return ResponseEntity.ok(skill);
    }

}
