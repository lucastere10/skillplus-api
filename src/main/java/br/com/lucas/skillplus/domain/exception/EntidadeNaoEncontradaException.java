package br.com.lucas.skillplus.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	protected EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
}
