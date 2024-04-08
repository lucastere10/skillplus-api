package br.com.lucas.skillplus.domain.repository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import br.com.lucas.skillplus.domain.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findBySkillNome(String nome);

    @Query(value = "SELECT * FROM SKIL u WHERE LOWER(u.SKIL_TX_NOME) LIKE LOWER(CONCAT('%', :nome, '%'))", nativeQuery = true)
    Page<Skill> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
