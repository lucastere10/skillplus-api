package br.com.lucas.skillplus.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "FOTO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Foto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "FOTO_CD_ID")
    private Long fotoId;    

    @Column(name = "FOTO_TX_NOME")
    private String nome;

    @Column(name = "FOTO_TX_TAMANHO")
    private Long tamanho;

    @Column(name = "FOTO_TX_DESCRICAO")
    private String descricao;

    @Column(name = "FOTO_TX_URL")
    private String fotoUrl;

    @Column(name = "FOTO_TX_CONTENTTYPE")
    private String contentType;

    @OneToOne
    @MapsId
    @JoinColumn(name = "PK_USUA", nullable = true, insertable = false, updatable = false)
    private Usuario usuario;      

    @Override
    public String toString() {
        return "Foto{" +
                "fotoId=" + fotoId +
                ", nome='" + nome + '\'' +
                ", tamanho=" + tamanho +
                ", descricao='" + descricao + '\'' +
                ", fotoUrl='" + fotoUrl + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

}
