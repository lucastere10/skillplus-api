package br.com.lucas.skillplus.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.model.UsuarioSkill;

@Repository
public interface UsuarioSkillRepository extends JpaRepository<UsuarioSkill, Long>{
    
    Page<UsuarioSkill> findByUsuario(Usuario usuario, Pageable pageable);
    Page<UsuarioSkill> findByUsuarioAndAtivoTrue(Usuario usuario, Pageable pageable);

}
