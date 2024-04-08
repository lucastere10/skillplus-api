package br.com.lucas.skillplus.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.lucas.skillplus.domain.exception.NegocioException;
import br.com.lucas.skillplus.domain.exception.SkillNaoEncontradoException;
import br.com.lucas.skillplus.domain.exception.UsuarioSkillNaoEncontradoException;
import br.com.lucas.skillplus.domain.model.Skill;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.model.UsuarioSkill;
import br.com.lucas.skillplus.domain.repository.SkillRepository;
import br.com.lucas.skillplus.domain.repository.UsuarioSkillRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioSkillService {

    @Autowired
    private UsuarioSkillRepository usuarioSkillRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Transactional
    public UsuarioSkill salvar(UsuarioSkill usuarioSkill, Usuario usuario, String skillNome) {
        usuarioSkill.setUsuario(usuario);

        Skill skill = skillRepository.findBySkillNome(skillNome)
                .orElseThrow(() -> new SkillNaoEncontradoException("Skill não encontrada com o nome: " + skillNome));
        usuarioSkill.setSkill(skill);

        return usuarioSkillRepository.save(usuarioSkill);
    }

    @Transactional
    public UsuarioSkill atualizar(UsuarioSkill usuarioSkill, Usuario usuario) {
        usuarioSkill.setUsuario(usuario);
        return usuarioSkillRepository.save(usuarioSkill);
    }

    public UsuarioSkill buscar(Long id) {
        return usuarioSkillRepository.findById(id)
                .orElseThrow(() -> new UsuarioSkillNaoEncontradoException(id));
    }

    public UsuarioSkill buscarOuFalhar(Long usuarioSkillId) {
        return usuarioSkillRepository.findById(usuarioSkillId)
                .orElseThrow(() -> new UsuarioSkillNaoEncontradoException(usuarioSkillId));
    }

    @Transactional
    public void excluir(Long usuarioSkillId) {
        try {
            usuarioSkillRepository.deleteById(usuarioSkillId);
            usuarioSkillRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioSkillNaoEncontradoException(usuarioSkillId);
        }
    }

    @Transactional
    public UsuarioSkill ativar(Long usuarioSkillId) {
        UsuarioSkill usuarioSkill = usuarioSkillRepository.findById(usuarioSkillId)
                .orElseThrow(() -> new NegocioException("UsuarioSkill não encontrado"));
        usuarioSkill.setAtivo(!usuarioSkill.getAtivo());

        return usuarioSkillRepository.save(usuarioSkill);
    }

}
