package br.com.lucas.skillplus.domain.exception;

public class UsuarioSkillNaoEncontradoException extends EntidadeNaoEncontradaException {
    
	private static final long serialVersionUID = 1L;

	public UsuarioSkillNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public UsuarioSkillNaoEncontradoException(Long UsuarioSkillId) {
		this(String.format("Não existe um cadastro de UsuarioSkill com código %d", 
				UsuarioSkillId));
	}

}
