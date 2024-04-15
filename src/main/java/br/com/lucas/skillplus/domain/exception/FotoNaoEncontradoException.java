package br.com.lucas.skillplus.domain.exception;

public class FotoNaoEncontradoException extends NegocioException {
    
	private static final long serialVersionUID = 1L;

	public FotoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public FotoNaoEncontradoException(Long fotoPerfilId) {
		this(String.format("Não existe um cadastro de foto de perfil com código %d", 
				fotoPerfilId));
	}

}
