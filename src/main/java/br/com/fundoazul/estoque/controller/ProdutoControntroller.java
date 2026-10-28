package br.com.fundoazul.estoque.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import br.com.fundoazul.estoque.dto.ProdutoRequest;
import br.com.fundoazul.estoque.dto.ProdutoResponse;
import br.com.fundoazul.estoque.service.ProdutoService;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/produtos")
public class ProdutoControntroller {

    private final ProdutoService service;

    public ProdutoControntroller(ProdutoService service) {
        this.service = service;
    }

    @PostMapping 
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest req) {
        ProdutoResponse criado = service.criar(req);
        return ResponseEntity.created(URI.create("/produtos/" + criado.id())).body(criado);
    }

    @GetMapping
    public List<ProdutoResponse> lista() {
        return service.listar();
    }

    @GetMapping ("/{id}")
    public ProdutoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping ("/{id}")
    public ProdutoResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping ("/{id}")
    public ProdutoResponse desativar(@PathVariable Long id) {
        service.desativar(id);
        return service.buscar(id);
    }

    
}
