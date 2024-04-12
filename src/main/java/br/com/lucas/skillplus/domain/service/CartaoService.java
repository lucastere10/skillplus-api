package br.com.lucas.skillplus.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.lucas.skillplus.domain.exception.CartaoNaoEncontradoException;
import br.com.lucas.skillplus.domain.exception.NegocioException;
import br.com.lucas.skillplus.domain.exception.UsuarioNaoEncontradoException;
import br.com.lucas.skillplus.domain.model.Cartao;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.repository.CartaoRepository;
import jakarta.transaction.Transactional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Transactional
    public Cartao salvar(Cartao cartao, Usuario usuario) {

        cartao.setUsuario(usuario);
        cartao.setQrcode(Long.toString(usuario.getUsuarioId()));

        return cartaoRepository.save(cartao);
    }

    public Cartao buscar(Long id) {
        return cartaoRepository.findById(id)
                .orElseThrow(() -> new CartaoNaoEncontradoException(id));
    }

    public Cartao buscarOuFalhar(Long cartaoId) {
        return cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(cartaoId));
    }

    @Transactional
    public void excluir(Long cartaoId) {
        try {
            cartaoRepository.deleteById(cartaoId);
            cartaoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new CartaoNaoEncontradoException(cartaoId);
        }
    }

    @Transactional
    public Cartao ativar(Long cartaoId) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new NegocioException("Cartão não encontrado"));
        cartao.setAtivo(!cartao.getAtivo());
        return cartaoRepository.save(cartao);
    }
}
