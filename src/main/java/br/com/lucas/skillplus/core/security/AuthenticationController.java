package br.com.lucas.skillplus.core.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucas.skillplus.api.assembler.UsuarioModelAssembler;
import br.com.lucas.skillplus.api.dto.model.UsuarioModel;
import br.com.lucas.skillplus.api.openapi.AuthenticationControllerOpenApi;
import br.com.lucas.skillplus.core.security.dto.AuthenticationDTO;
import br.com.lucas.skillplus.core.security.dto.LoginResponseDTO;
import br.com.lucas.skillplus.core.security.dto.RegisterDTO;
import br.com.lucas.skillplus.domain.exception.AcessoNegadoException;
import br.com.lucas.skillplus.domain.model.Usuario;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.exceptions.QrGenerationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000/")
public class AuthenticationController implements AuthenticationControllerOpenApi {

    @Autowired
    private AuthenticationService userService;

    @Autowired
    private TotpService totpService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            return ResponseEntity.ok(userService.login(data));
        } catch (AuthenticationException e) {
            throw new AcessoNegadoException("Credenciais não encontradas");
        }
    }

    @PostMapping("/login-social")
    @CrossOrigin(origins = "http://localhost:3000/")
    public ResponseEntity<LoginResponseDTO> socialLogin(@RequestBody @Valid RegisterDTO data) {
        return ResponseEntity.ok(userService.socialLogin(data));
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000/")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        userService.register(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registerAdmin")
    @CrossOrigin(origins = "http://localhost:3000/")
    public ResponseEntity<Void> registerAdmin(@RequestBody @Valid RegisterDTO data) {
        userService.registerAdmin(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    @CrossOrigin(origins = "http://localhost:3000/")
    public ResponseEntity<UsuarioModel> currentUser(@AuthenticationPrincipal Usuario usuario) {
        UsuarioModel usuarioModel = usuarioModelAssembler.toModel(usuario);
        return ResponseEntity.ok(usuarioModel);
    }

    @GetMapping("/totp/setup")
    public String setupTOTP() throws QrGenerationException {
        return totpService.setupDevice();
    }

    @PostMapping("/totp/validar")
    public String verify(@RequestParam String code) {
        return totpService.verify(code);
    }

    @GetMapping("/totp/mobile")
    @CrossOrigin(origins = "http://localhost:3000/")
    public String verifyMobile() throws CodeGenerationException {
        return totpService.verifyMobile();
    }

    // Verify if has picture and profile is complete
    @GetMapping("/verify")
    public ResponseEntity<Map<String, Boolean>> verifyProfileComplete(@AuthenticationPrincipal Usuario usuario) {
        boolean hasPicture = userService.hasPicture(usuario);
        boolean isProfileComplete = userService.isProfileComplete(usuario);

        Map<String, Boolean> response = new HashMap<>();
        response.put("twoFa", usuario.getTwoFa());
        response.put("hasPicture", hasPicture);
        response.put("isProfileComplete", isProfileComplete);

        return ResponseEntity.ok(response);
    }

}
