package br.com.lucas.skillplus.domain.model;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

@Entity(name = "CART")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cartao {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_CD_ID")
    private Long cartaoId;

    @Column(name = "CART_TX_NOMECARTAO",nullable = false)
    private String cartaoNome;

    @Column(name = "CART_TX_NOMEUSUARIO",nullable = false)
    private String cartaoUsuario;

    @Column(name = "CART_TX_USUARIOTIPO")
    private String cartaoUsuarioTipo;

    @Column(name = "CART_TX_BACKGROUND")
    private String cartaoBackground;

    @Column(name = "CART_TX_URL")
    private String cartaoUrl;

    @Column(name = "CART_TX_QRCODE",nullable = false)
    private String qrcode;

    @Column(name = "CART_BL_ATIVO",nullable = false)
    private Boolean ativo;

    @CreationTimestamp
    @Column(name = "CART_DT_CADASTRO",nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "CART_DT_ATUALIZACAO",nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "PK_USUA")
    @JsonBackReference
    private Usuario usuario;    

}
