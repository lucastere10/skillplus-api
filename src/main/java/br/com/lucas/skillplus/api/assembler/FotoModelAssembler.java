package br.com.lucas.skillplus.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.model.FotoModel;
import br.com.lucas.skillplus.domain.model.Foto;

@Component
public class FotoModelAssembler {
    
    @Autowired
	private ModelMapper modelMapper;
	
	public FotoModel toModel(Foto foto) {
		return modelMapper.map(foto, FotoModel.class);
	}
}
