package br.com.lucas.skillplus.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.model.CartaoModel;
import br.com.lucas.skillplus.domain.model.Cartao;

@Component
public class CartaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CartaoModel toModel(Cartao cartao) {
        return modelMapper.map(cartao, CartaoModel.class);
    }

    public List<CartaoModel> toCollectionModel(Collection<Cartao> cartoes) {
        return cartoes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
