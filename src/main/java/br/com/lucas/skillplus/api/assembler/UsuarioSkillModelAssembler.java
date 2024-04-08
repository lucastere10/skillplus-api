package br.com.lucas.skillplus.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.model.UsuarioSkillModel;
import br.com.lucas.skillplus.domain.model.UsuarioSkill;

@Component
public class UsuarioSkillModelAssembler {
    
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioSkillModel toModel(UsuarioSkill usuarioSkill) {
		return modelMapper.map(usuarioSkill, UsuarioSkillModel.class);
	}
	
	public List<UsuarioSkillModel> toCollectionModel(Collection<UsuarioSkill> usuarioSkills) {
		return usuarioSkills.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	

}
