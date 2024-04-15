package br.com.lucas.skillplus.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lucas.skillplus.domain.model.Foto;
import br.com.lucas.skillplus.domain.model.Usuario;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    Optional<Foto> findByUsuario(Usuario usuario);

}
