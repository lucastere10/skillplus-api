package br.com.lucas.skillplus.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lucas.skillplus.domain.model.Cartao;
import br.com.lucas.skillplus.domain.model.Usuario;



@Repository
public interface CartaoRepository extends JpaRepository<Cartao,Long> {

    Page<Cartao> findByUsuario(Usuario usuario, Pageable pageable);
        
}
