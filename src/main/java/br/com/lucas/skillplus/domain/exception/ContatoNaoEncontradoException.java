package br.com.lucas.skillplus.domain.exception;

public class ContatoNaoEncontradoException extends EntidadeNaoEncontradaException {
    
	private static final long serialVersionUID = 1L;

	public ContatoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ContatoNaoEncontradoException(Long contatoId) {
		this(String.format("Não existe um cadastro de contato com código %d", 
				contatoId));
	}


}
