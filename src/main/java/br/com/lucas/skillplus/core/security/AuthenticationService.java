package br.com.lucas.skillplus.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lucas.skillplus.core.security.dto.AuthenticationDTO;
import br.com.lucas.skillplus.core.security.dto.LoginResponseDTO;
import br.com.lucas.skillplus.core.security.dto.RegisterDTO;
import br.com.lucas.skillplus.domain.enums.UsuarioStatus;
import br.com.lucas.skillplus.domain.enums.UsuarioTipo;
import br.com.lucas.skillplus.domain.exception.NegocioException;
import br.com.lucas.skillplus.domain.model.Usuario;
import br.com.lucas.skillplus.domain.repository.UsuarioRepository;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario createUsuario(RegisterDTO data, UsuarioTipo usuarioTipo) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario usuario = new Usuario(data.nome(), data.login(), encryptedPassword, usuarioTipo);
        usuario.setTwoFa(false);
        usuario.setUsuarioStatus(UsuarioStatus.ATIVO);
        return usuario;
    }    

    public LoginResponseDTO login(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        Usuario usuario = (Usuario) auth.getPrincipal();
        var token = tokenService.generateToken(usuario);
    
        // Check if the user has a picture and if their profile is complete
        boolean hasPicture = hasPicture(usuario);
        boolean isProfileComplete = isProfileComplete(usuario);
    
        return new LoginResponseDTO(token, hasPicture, isProfileComplete);
    }

    public void registerAdmin(RegisterDTO data) {
        if (this.usuarioRepository.findLoginByEmail(data.login()) != null)
            throw new NegocioException(
                    String.format("Já existe um usuário cadastrado com o e-mail %s", data.login()));
    
        Usuario usuario = createUsuario(data, UsuarioTipo.ADMIN);
        this.usuarioRepository.save(usuario);
    }
    
    public void register(RegisterDTO data) {
        if (this.usuarioRepository.findLoginByEmail(data.login()) != null)
            throw new NegocioException(
                    String.format("Já existe um usuário cadastrado com o e-mail %s", data.login()));
    
        Usuario usuario = createUsuario(data, UsuarioTipo.CLIENTE);
        this.usuarioRepository.save(usuario);
    }
    
    public LoginResponseDTO socialLogin(RegisterDTO data) {
        this.usuarioRepository.findByEmail(data.login())
            .orElseGet(() -> {
                Usuario newUser = createUsuario(data, UsuarioTipo.CLIENTE);
                this.usuarioRepository.save(newUser);
                return newUser;
            });
    
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
    
        // Check if the user has a picture and if their profile is complete
        boolean hasPicture = hasPicture((Usuario) auth.getPrincipal());
        boolean isProfileComplete = isProfileComplete((Usuario) auth.getPrincipal());
    
        return new LoginResponseDTO(token, hasPicture, isProfileComplete);
    }

    public boolean hasPicture(Usuario usuario){
        return usuario.getFoto() != null;
    }

    public boolean isProfileComplete(Usuario usuario) {
        return usuario.getDataNascimento() != null && usuario.getNome() != null;
    }
    
}
