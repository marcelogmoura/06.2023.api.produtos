package com.mgmoura.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgmoura.dtos.ProdutosPostRequestDTO;
import com.mgmoura.dtos.ProdutosPutRequestDTO;
import com.mgmoura.dtos.ProdutosResponseDTO;
import com.mgmoura.entities.Produto;
import com.mgmoura.repositories.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/produtos")
public class ProdutosController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@PostMapping
	public ResponseEntity<ProdutosResponseDTO> post(@RequestBody @Valid ProdutosPostRequestDTO dto) {

		ProdutosResponseDTO response = new ProdutosResponseDTO();

		try {

			Produto produto = new Produto();
			produto.setNome(dto.getNome());
			produto.setDescricao(dto.getDescricao());
			produto.setPreco(dto.getPreco());
			produto.setQuantidade(dto.getQuantidade());

			produtoRepository.save(produto);

			response.setStatus(HttpStatus.CREATED); // 201
			response.setMensagem("Produto cadastrado com sucesso.");
			response.setProduto(produto);

		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMensagem(e.getMessage());
		}
		return ResponseEntity.status(response.getStatus().value()).body(response);
	}

	@PutMapping
	public ResponseEntity<ProdutosResponseDTO> put(@RequestBody ProdutosPutRequestDTO dto) {

		ProdutosResponseDTO response = new ProdutosResponseDTO();

		try {
			Optional<Produto> produto = produtoRepository.findById(dto.getIdProduto());

			if (produto.isEmpty()) {

				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMensagem("Produto não encontrado, verifique o ID.");
			} else {
				Produto item = produto.get();
				item.setNome(dto.getNome());
				item.setDescricao(dto.getDescricao());
				item.setPreco(dto.getPreco());
				item.setQuantidade(dto.getQuantidade());

				produtoRepository.save(item);

				response.setStatus(HttpStatus.OK); // 200
				response.setMensagem("Produto atualizado com sucesso.");
				response.setProduto(item);

			}
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMensagem(e.getMessage());
		}

		return ResponseEntity.status(response.getStatus().value()).body(response);
	}

	@DeleteMapping("{idProduto}")
	public ResponseEntity<ProdutosResponseDTO> delete(@PathVariable("idProduto") Integer idProduto) {

		ProdutosResponseDTO response = new ProdutosResponseDTO();
		
		try {
			Optional<Produto> produto = produtoRepository.findById(idProduto);
			
			if(produto.isEmpty()) {
				response.setStatus(HttpStatus.BAD_REQUEST); 
				response.setMensagem("Produto não encontrado, verifique o ID informado.");
			}
			else {
				if(produtoRepository.countByMovimentacao(idProduto) > 0) {
					response.setStatus(HttpStatus.BAD_REQUEST); 
					response.setMensagem("Nâo é possível realizar a exclusão, pois o produto possui movimentações vinculadas.");
				}
				else {				
					produtoRepository.delete(produto.get());
					
					response.setStatus(HttpStatus.OK); 
					response.setMensagem("Produto excluído com sucesso.");
					response.setProduto(produto.get());
				}				
			}
		}
		catch(Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR); 
			response.setMensagem(e.getMessage());
		}
		
		return ResponseEntity.status(response.getStatus().value()).body(response);
	}

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {

		try {
			List<Produto> produtos = produtoRepository.findAll();
			if (produtos.size() > 0) {
				return ResponseEntity.status(200).body(produtos);

			} else {
				return ResponseEntity.status(204).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("{idProduto}")
	public ResponseEntity<Produto> getById(@PathVariable("idProduto") Integer idProduto) {

		try {
			Optional<Produto> produto = produtoRepository.findById(idProduto);
			if (produto.isPresent()) {
				return ResponseEntity.status(200).body(produto.get());
			} else {
				return ResponseEntity.status(204).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}
}
