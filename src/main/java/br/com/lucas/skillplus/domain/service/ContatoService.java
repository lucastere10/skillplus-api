package br.com.lucas.skillplus.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.lucas.skillplus.domain.exception.ContatoNaoEncontradoException;
import br.com.lucas.skillplus.domain.exception.NegocioException;
import br.com.lucas.skillplus.domain.exception.UsuarioNaoEncontradoException;
import br.com.lucas.skillplus.domain.model.Contato;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.repository.ContatoRepository;
import jakarta.transaction.Transactional;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Transactional
    public Contato salvar(Contato contato, Usuario usuario) {
        contato.setUsuario(usuario);

        return contatoRepository.save(contato);
    }

    public Contato buscar(Long id) {
        return contatoRepository.findById(id)
                .orElseThrow(() -> new ContatoNaoEncontradoException(id));
    }

    public Contato buscarOuFalhar(Long contatoId) {
        return contatoRepository.findById(contatoId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(contatoId));
    }

    @Transactional
    public void excluir(Long contatoId) {
        try {
            contatoRepository.deleteById(contatoId);
            contatoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new ContatoNaoEncontradoException(contatoId);
        }
    }

    @Transactional
    public Contato privar(Long contatoId) {
        Contato contato = contatoRepository.findById(contatoId)
                .orElseThrow(() -> new NegocioException("Contato não encontrado"));

        contato.setPrivado(!contato.getPrivado());

        return contatoRepository.save(contato);

    }
    
}
