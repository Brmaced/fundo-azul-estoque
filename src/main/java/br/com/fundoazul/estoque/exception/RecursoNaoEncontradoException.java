package br.com.fundoazul.estoque.exception;

// Exceção personalizada para indicar que um recurso não foi encontrado no sistema
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    
}
