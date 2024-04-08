package br.com.lucas.skillplus.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.input.UsuarioPUTInput;
import br.com.lucas.skillplus.domain.model.Usuario;

@Component
public class UsuarioPUTInputDisassembler {

	private ModelMapper modelMapper;

	@Autowired
	public UsuarioPUTInputDisassembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.modelMapper.getConfiguration().setSkipNullEnabled(true);
	}

	public Usuario toDomainObject(UsuarioPUTInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}

	public void copyToDomainObject(UsuarioPUTInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
}
