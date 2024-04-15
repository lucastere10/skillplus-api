package br.com.lucas.skillplus.domain.exception;

public class CartaoNaoEncontradoException extends EntidadeNaoEncontradaException {
    
	private static final long serialVersionUID = 1L;

	public CartaoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CartaoNaoEncontradoException(Long cartaoId) {
		this(String.format("Não existe um cadastro de cartao com código %d", 
				cartaoId));
	}

}
