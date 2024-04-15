package br.com.lucas.skillplus.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.lucas.skillplus.domain.enums.UsuarioSkillDominio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "USSK")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioSkill {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USSK_CD_ID")
    private Long usuarioSkillId;

    @Column(name = "USSK_TX_VERSAO")
    private String usuarioSkillVersao;

    @Column(name = "USSK_TX_DOMINIO", nullable = false)
    @Enumerated(EnumType.STRING)
    private UsuarioSkillDominio usuarioSkillDominio;

    @Column(name = "USSK_BL_ATIVO",nullable = false)
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "PK_USUA")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PK_SKIL")
    @JsonBackReference
    private Skill skill;    

}
