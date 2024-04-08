package br.com.lucas.skillplus.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lucas.skillplus.domain.model.Contato;
import br.com.lucas.skillplus.domain.model.Usuario;

@Repository
public interface ContatoRepository extends JpaRepository<Contato,Long>{

    Page<Contato> findByUsuario(Usuario usuario, Pageable pageable);
    Page<Contato> findByUsuarioAndPrivadoTrue(Usuario usuario, Pageable pageable);
    
}
