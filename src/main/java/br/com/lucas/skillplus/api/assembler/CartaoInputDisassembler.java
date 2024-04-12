package br.com.lucas.skillplus.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lucas.skillplus.api.dto.input.CartaoInput;
import br.com.lucas.skillplus.domain.model.Cartao;

@Component
public class CartaoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cartao toDomainObject(CartaoInput cartaoInput) {
        return modelMapper.map(cartaoInput, Cartao.class);
    }

    public void copyToDomainObject(CartaoInput cartaoInput, Cartao cartao) {
        modelMapper.map(cartaoInput, cartao);
    }

}
