package br.com.lucas.skillplus.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.lucas.skillplus.domain.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    UserDetails findLoginByEmail(String email);

    @Query(value = "SELECT * FROM USUA u WHERE LOWER(u.USUA_TX_NOME) LIKE LOWER(CONCAT('%', :nome, '%'))", nativeQuery = true)
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    
}
