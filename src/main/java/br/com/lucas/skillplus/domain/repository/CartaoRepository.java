package br.com.lucas.skillplus.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lucas.skillplus.domain.model.Cartao;
import br.com.lucas.skillplus.domain.model.Usuario;
import org.springframework.data.jpa.repository.Query;




@Repository
public interface CartaoRepository extends JpaRepository<Cartao,Long> {

    Page<Cartao> findByUsuario(Usuario usuario, Pageable pageable);

    @Query(value = "SELECT * FROM CART u WHERE LOWER(u.CART_TX_NOMECARTAO) LIKE LOWER(CONCAT('%', :nome, '%'))", nativeQuery = true)
    Page<Cartao> findByCartaoNomeContainingIgnoreCase(String nome, Pageable pageable);
        
}
