package br.com.lucas.skillplus.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.lucas.skillplus.domain.enums.SkillCategoria;
import br.com.lucas.skillplus.domain.enums.SkillDificuldade;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "SKIL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Skill {
    
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SKIL_CD_ID")
    private Long skillId;

    @Column(name = "SKIL_TX_NOME", nullable = false, unique = true)
    private String skillNome;

    @Column(name = "SKIL_TX_DESCRICAO")
    private String skillDescricao;

    @Column(name = "SKIL_TX_CATEGORIA", nullable = false)
    private SkillCategoria skillCategoria;

    @Column(name = "SKIL_TX_DIFICULDADE")
    private SkillDificuldade skillDificuldade;
    
    @Column(name = "SKIL_TX_URL")
    private String skillUrl;

    @Column(name = "SKIL_BL_ATIVO", nullable = false)
    private Boolean ativo;

    @CreationTimestamp
    @Column(name = "SKIL_DT_CADASTRO", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "SKIL_DT_ATUALIZACAO", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private transient List<UsuarioSkill> usuarioSkills;

}
