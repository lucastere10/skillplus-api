package br.com.lucas.skillplus.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.lucas.skillplus.domain.enums.UsuarioStatus;
import br.com.lucas.skillplus.domain.exception.NegocioException;
import br.com.lucas.skillplus.domain.exception.UsuarioNaoEncontradoException;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public void excluir(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(usuarioId);
        }
    }

    public Usuario ativarUsuario(Usuario usuario) {
        usuario.setUsuarioStatus(UsuarioStatus.ATIVO);
        return usuarioRepository.save(usuario);
    }

    public Usuario desativarUsuario(Usuario usuario) {
        usuario.setUsuarioStatus(UsuarioStatus.INATIVO);
        return usuarioRepository.save(usuario);
    }

    public Usuario banirUsuario(Usuario usuario) {
        usuario.setUsuarioStatus(UsuarioStatus.BANIDO);
        return usuarioRepository.save(usuario);
    }

    public Usuario bloquearUsuario(Usuario usuario) {
        usuario.setUsuarioStatus(UsuarioStatus.BLOQUEADO);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update2fa(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NegocioException("Usuário não encontrado"));

        usuario.setTwoFa(!usuario.getTwoFa());

        return usuarioRepository.save(usuario);
    }

}
