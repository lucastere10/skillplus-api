package br.com.lucas.skillplus.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.lucas.skillplus.infra.totp.DataUriForImage;

import org.springframework.stereotype.Service;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrDataFactory;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;

@Service
public class TotpService {

    @Autowired
    private SecretGenerator secretGenerator;

    @Autowired
    private QrDataFactory qrDataFactory;

    @Autowired
    private QrGenerator qrGenerator;

    private String secret;

    public String setupDevice() throws QrGenerationException {
        // Generate a new secret
        secret = secretGenerator.generate();

        QrData data = qrDataFactory.newBuilder()
                .label("springtech@springtech.com")
                .secret(secret)
                .issuer("SpringTech")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        System.out.println(secret);

        return DataUriForImage.getDataUriForImage(
                qrGenerator.generate(data),
                qrGenerator.getImageMimeType());
    }

    public String verify(@RequestParam String code) {
        System.out.println("Secret: " + secret);
        System.out.println("Code: " + code);

        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        boolean isValid = verifier.isValidCode(secret, code);

        System.out.println("Is valid: " + isValid);
        if (isValid) {
            return "CORRECT CODE";
        }
        return "INCORRECT CODE";
    }

    public String verifyMobile() throws CodeGenerationException {
        System.out.println("Secret: " + secret);

        TimeProvider timeProvider = new SystemTimeProvider();
        long counter = Math.floorDiv(timeProvider.getTime(), 30);

        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        String generatedCode = codeGenerator.generate(secret, counter);
        
        System.out.println("Generated Code: " + generatedCode);
        System.out.println("Counter: " + counter);
        
        return generatedCode;
    }

}
