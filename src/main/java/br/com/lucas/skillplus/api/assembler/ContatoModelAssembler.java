package br.com.lucas.skillplus.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.model.ContatoModel;
import br.com.lucas.skillplus.domain.model.Contato;

@Component
public class ContatoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ContatoModel toModel(Contato contato) {
        return modelMapper.map(contato, ContatoModel.class);
    }

    public List<ContatoModel> toCollectionModel(Collection<Contato> contatos) {
        return contatos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
