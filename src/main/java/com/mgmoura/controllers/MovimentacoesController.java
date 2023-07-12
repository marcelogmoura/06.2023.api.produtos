package com.mgmoura.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgmoura.dtos.MovimentacoesPostRequestDTO;
import com.mgmoura.dtos.MovimentacoesResponseDTO;
import com.mgmoura.entities.Movimentacao;
import com.mgmoura.entities.Produto;
import com.mgmoura.repositories.MovimentacaoRepository;
import com.mgmoura.repositories.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/movimentacoes")
public class MovimentacoesController {
	
	@Autowired
	private MovimentacaoRepository movimentacaoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PostMapping
	public ResponseEntity<MovimentacoesResponseDTO> post(@RequestBody @Valid MovimentacoesPostRequestDTO dto) {
		
		MovimentacoesResponseDTO response = new MovimentacoesResponseDTO();
		
		try {
			Optional<Produto> produto = produtoRepository.findById(dto.getIdProduto());
			
			if(produto.isEmpty()) {
				response.setStatus(HttpStatus.BAD_REQUEST); // 400
				response.setMensagem("Produto não encontrado, verifique o ID.");
				
			}else {
				
				Movimentacao movimentacao = new Movimentacao();
				
				movimentacao.setProduto(produto.get());
				movimentacao.setQuantidade(dto.getQuantidade());
				movimentacao.setObservacoes(dto.getObservacoes());
				movimentacao.setTipo(dto.getTipo());
				movimentacao.setDataMovimentacao(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getDataMovimentacao()));
				
				movimentacaoRepository.save(movimentacao);
				
				response.setStatus(HttpStatus.CREATED); // 201
				response.setMensagem("Movimentação cadastrada com sucesso.");
				response.setMovimentacao(movimentacao);
				
			}
			
		}catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMensagem(e.getMessage());
		}
		return ResponseEntity.status(response.getStatus().value()).body(response);
	}
	
	@GetMapping("{dataInicio}/{dataFim}")
	public ResponseEntity<List<Movimentacao>> getAll(@PathVariable("dataInicio") String dataInicio , @PathVariable("dataFim") String dataFim) {
		
		try {
			Date dataMin = new SimpleDateFormat("yyyy-MM-dd").parse(dataInicio);
			Date dataMax = new SimpleDateFormat("yyyy-MM-dd").parse(dataFim);
			
			List<Movimentacao> movimentacoes = movimentacaoRepository.findByDatas(dataMin, dataMax);
			
			return ResponseEntity.status(200).body(movimentacoes);
			
		}catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

}





