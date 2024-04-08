package br.com.lucas.skillplus.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.input.SkillInput;
import br.com.lucas.skillplus.domain.model.Skill;

@Component
public class SkillInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public SkillInputDisassembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.modelMapper.getConfiguration().setSkipNullEnabled(true);
	}

	public Skill toDomainObject(SkillInput skillInput) {
		return modelMapper.map(skillInput, Skill.class);
	}

	public void copyToDomainObject(SkillInput skillInput, Skill skill) {
		modelMapper.map(skillInput, skill);
	}

}
