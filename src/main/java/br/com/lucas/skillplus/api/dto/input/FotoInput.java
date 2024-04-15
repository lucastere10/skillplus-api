package br.com.lucas.skillplus.api.dto.input;

import org.springframework.web.multipart.MultipartFile;

import br.com.lucas.skillplus.core.validation.FileContentType;
import br.com.lucas.skillplus.core.validation.FileSize;

import org.springframework.http.MediaType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoInput {

    @FileSize(max = "500KB")
    @FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    private MultipartFile arquivo;

    @Schema(example = "Homem branco de cabelo preto vestindo camisa azul")
    private String descricao;

}
