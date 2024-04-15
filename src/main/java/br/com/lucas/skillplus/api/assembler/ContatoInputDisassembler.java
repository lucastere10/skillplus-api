package br.com.lucas.skillplus.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.input.ContatoInput;
import br.com.lucas.skillplus.domain.model.Contato;

@Component
public class ContatoInputDisassembler {
    
	@Autowired
	private ModelMapper modelMapper;
	
	public Contato toDomainObject(ContatoInput contatoInput) {
		return modelMapper.map(contatoInput, Contato.class);
	}
	
	public void copyToDomainObject(ContatoInput contatoInput, Contato contato) {
		modelMapper.map(contatoInput, contato);
	}

}
