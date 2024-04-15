package br.com.lucas.skillplus.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.lucas.skillplus.domain.enums.ContatoTipo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "CONT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contato {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONT_CD_ID")
    private Long contatoId;

    @Column(name = "CONT_TX_NOME", nullable = false)
    private String contatoNome;

    @Column(name = "CONT_TX_TIPO", nullable = false)
    private ContatoTipo contatoTipo;

    @Column(name = "CONT_TX_URL", nullable = false)
    private String contatoUrl;

    @Column(name = "CONT_BL_PRIVADO")
    private Boolean privado;

    @ManyToOne
    @JoinColumn(name = "PK_USUA")
    @JsonBackReference
    private Usuario usuario;

}
