package br.com.lucas.skillplus.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.model.SkillModel;
import br.com.lucas.skillplus.domain.model.Skill;

@Component
public class SkillModelAssembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public SkillModel toModel(Skill skill) {
        return modelMapper.map(skill, SkillModel.class);
    }

    public List<SkillModel> toCollectionModel(Collection<Skill> skills) {
        return skills.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
