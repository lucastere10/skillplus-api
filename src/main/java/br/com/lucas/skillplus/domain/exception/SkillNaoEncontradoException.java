package br.com.lucas.skillplus.domain.exception;

public class SkillNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public SkillNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public SkillNaoEncontradoException(Long skillId) {
        this(String.format("Não existe um cadastro de skill com código %d", skillId));
    }

}
