package br.com.lucas.skillplus.api.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoModel {

    @Schema(example = "1")
    private Long fotoId;
    
    @Schema(example = "placeholder.jpg")
	private String nome;

    @Schema(example = "Homem branco de cabelo preto vestindo camisa azul")
    private String descricao;

    @Schema(example = "src\\main\\resources\\uploads\\placeholder.jpg)")
    private String fotoUrl;
    
    @Schema(example = "image/jpeg")
	private String contentType;

    @Schema(example = "3522")
	private Long tamanho;
    	
}
