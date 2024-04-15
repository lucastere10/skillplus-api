package br.com.lucas.skillplus.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.lucas.skillplus.domain.enums.UsuarioStatus;
import br.com.lucas.skillplus.domain.enums.UsuarioTipo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "USUA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements UserDetails {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUA_CD_ID")
    private Long usuarioId;

    @Column(name = "USUA_TX_NOME", nullable = false)
    private String nome;

    @Column(name = "USUA_TX_NOMESOCIAL")
    private String nomeSocial;

    @Column(name = "USUA_TX_EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USUA_TX_TELEFONE")
    private String telefone;

    @Column(name = "USUA_TX_SENHA", nullable = false)
    private String senha;

    @Column(name = "USUA_TX_SEGREDO")
    private String segredo;

    @Column(name = "USUA_BL_TWOFACTOR", nullable = false)
    private Boolean twoFa;

    @Column(name = "USUA_DT_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "USUA_TX_TIPO", nullable = false)
    @Enumerated(EnumType.STRING)
    private UsuarioTipo usuarioTipo;

    @Column(name = "USUA_TX_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private UsuarioStatus usuarioStatus;

    @CreationTimestamp
    @Column(name = "USUA_DT_CADASTRO", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "USUA_DT_ATUALIZACAO", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Foto foto;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private transient List<Contato> contatos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private transient List<UsuarioSkill> usuarioSkills;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private transient List<Cartao> cartoes;

    public Usuario(String nome, String email, String senha, UsuarioTipo usuarioTipo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.usuarioTipo = usuarioTipo;
    }

    public Usuario(String nome, String email, String senha, UsuarioTipo usuarioTipo, UsuarioStatus usuarioStatus) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.usuarioTipo = usuarioTipo;
        this.usuarioStatus = usuarioStatus;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.usuarioTipo == UsuarioTipo.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuarioId=" + usuarioId +
                ", nome='" + nome + '\'' +
                ", nomeSocial='" + nomeSocial + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", senha='" + senha + '\'' +
                ", segredo='" + segredo + '\'' +
                ", twoFa=" + twoFa +
                ", dataNascimento=" + dataNascimento +
                ", usuarioTipo=" + usuarioTipo +
                ", usuarioStatus=" + usuarioStatus +
                ", dataCadastro=" + dataCadastro +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }

}
