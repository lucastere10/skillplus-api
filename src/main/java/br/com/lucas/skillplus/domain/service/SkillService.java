package br.com.lucas.skillplus.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.lucas.skillplus.domain.exception.NegocioException;
import br.com.lucas.skillplus.domain.exception.SkillNaoEncontradoException;
import br.com.lucas.skillplus.domain.model.Skill;
import br.com.lucas.skillplus.domain.repository.SkillRepository;
import jakarta.transaction.Transactional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Transactional
    public Skill salvar(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill buscar(Long id){
        return skillRepository.findById(id)
        .orElseThrow(() -> new SkillNaoEncontradoException(id));
    }

    public Skill buscarOuFalhar(Long skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNaoEncontradoException(skillId));
    }

    @Transactional
    public void excluir(Long skillId) {
        try {
            skillRepository.deleteById(skillId);
            skillRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new SkillNaoEncontradoException(skillId);
        }
    }

    @Transactional
    public Skill ativar(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new NegocioException("Skill não encontrado"));
        skill.setAtivo(!skill.getAtivo());

        return skillRepository.save(skill);
    }

}
