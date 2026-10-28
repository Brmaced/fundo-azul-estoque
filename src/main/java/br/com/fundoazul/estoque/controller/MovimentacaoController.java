package br.com.fundoazul.estoque.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fundoazul.estoque.dto.MovimentacaoRequest;
import br.com.fundoazul.estoque.dto.MovimentacaoResponse;
import br.com.fundoazul.estoque.service.MovimentacaoService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/produtos/{produtoId}/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService service;

    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimentacaoResponse registrar(@PathVariable Long produtoId,
                                          @Valid @RequestBody MovimentacaoRequest req) {
        return service.registrar(produtoId, req);
    }

    @GetMapping
    public List<MovimentacaoResponse> listar(@PathVariable Long produtoId) {
        return service.listar(produtoId);
    }
}