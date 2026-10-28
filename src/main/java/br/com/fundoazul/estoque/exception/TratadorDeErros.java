package br.com.fundoazul.estoque.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.OptimisticLockException;

// Classe responsável por tratar exceções lançadas durante a execução da aplicação
@RestControllerAdvice
public class TratadorDeErros {
    // Aqui você pode adicionar métodos para tratar exceções específicas e retornar respostas apropriadas
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> naoEncontrado(RecursoNaoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> dadosInvalidos(MethodArgumentNotValidException e) {
        Map<String, String> erros = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
        .forEach(f -> erros.put(f.getField(), f.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler (HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> corpoInvalido(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", "Corpo da requisição inválido: " + e.getMessage()));
    }
    
    @ExceptionHandler (RegraDeNegocioException.class)
    public ResponseEntity<Map<String, String>> regraDeNegocio(RegraDeNegocioException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("erro", e.getMessage()));
    }
    @ExceptionHandler (OptimisticLockException.class)
    public ResponseEntity<Map<String, String>> otimista(OptimisticLockException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", e.getMessage()));
    }

} 


