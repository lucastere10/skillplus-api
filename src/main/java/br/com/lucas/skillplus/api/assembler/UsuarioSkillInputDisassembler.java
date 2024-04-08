package br.com.lucas.skillplus.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.input.UsuarioSkillInput;
import br.com.lucas.skillplus.domain.model.UsuarioSkill;

@Component
public class UsuarioSkillInputDisassembler {

    private ModelMapper modelMapper;

	@Autowired
	public UsuarioSkillInputDisassembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.modelMapper.getConfiguration().setSkipNullEnabled(true);
	}

	public UsuarioSkill toDomainObject(UsuarioSkillInput usuarioSkillInput) {
		return modelMapper.map(usuarioSkillInput, UsuarioSkill.class);
	}

	public void copyToDomainObject(UsuarioSkillInput usuarioSkillInput, UsuarioSkill usuarioSkill) {
		modelMapper.map(usuarioSkillInput, usuarioSkill);
	}
    
}
