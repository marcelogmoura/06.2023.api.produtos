package com.mgmoura;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.mgmoura.dtos.AuthPostRequestDTO;
import com.mgmoura.dtos.AuthResponseDTO;
import com.mgmoura.dtos.MovimentacoesPostRequestDTO;
import com.mgmoura.dtos.ProdutosPostRequestDTO;
import com.mgmoura.dtos.ProdutosPutRequestDTO;
import com.mgmoura.dtos.ProdutosResponseDTO;
import com.mgmoura.entities.Produto;

@SpringBootTest
@AutoConfigureMockMvc // habilitar biblioteca MockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiProdutosApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	// atributo para guardar o produto cadastrado no teste
	private static Produto produto;
	
	//atributo para guardar o token gerado na autenticação
	private static String accessToken;

	
	@Test
	@Order(1)
	public void testAuthPost() throws Exception{
		
		AuthPostRequestDTO dto = new AuthPostRequestDTO();
		dto.setEmail("ana@email");
		dto.setSenha("Admin@123");
		
		MvcResult result = mockMvc.perform(post("/api/auth")
				.contentType("application/json")
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk())
				.andReturn();
		
		String responseBody = result.getResponse().getContentAsString();
		AuthResponseDTO response = mapper.readValue(responseBody, AuthResponseDTO.class);
		
		accessToken = response.getAccessToken();
		
	}
	

	@Test
	@Order(2)
	public void testProdutosPost() throws Exception{
		
		Faker faker = new Faker();
		
		ProdutosPostRequestDTO dto = new ProdutosPostRequestDTO();
		dto.setNome(faker.commerce().productName());
		dto.setDescricao(faker.commerce().productName());
		dto.setPreco(Double.valueOf(faker.number().randomNumber(3 , false)));
		dto.setQuantidade(faker.number().randomDigit());
		
		MvcResult result = mockMvc.perform
				(post("/api/produtos") // 
				.contentType("application/json")
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isCreated())
				.andReturn();
		
		String responseBody = result.getResponse().getContentAsString();
		ProdutosResponseDTO response = mapper.readValue(responseBody, ProdutosResponseDTO.class);
		
		produto = response.getProduto();
		
	}
	
	@Test
	@Order(3)
	public void testProdutosPut() throws Exception{
		
		Faker faker = new Faker();
		
		ProdutosPutRequestDTO dto = new ProdutosPutRequestDTO();
		dto.setIdProduto(produto.getIdProduto());
		dto.setNome(faker.commerce().productName());
		dto.setDescricao(faker.commerce().productName());
		dto.setPreco(Double.valueOf(faker.number().randomNumber(3 , false)));
		dto.setQuantidade(faker.number().randomDigit());
		
		mockMvc.perform(put("/api/produtos") 
				.contentType("application/json")
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(4)
	public void testProdutosGetAll() throws Exception{
		mockMvc.perform(get("/api/produtos")) 
		.andExpect(status().isOk());
	}
	
	@Test
	@Order(5)
	public void testProdutosGetById() throws Exception{
		mockMvc.perform(get("/api/produtos/"
				+ produto.getIdProduto())) 
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(6)
	public void testMovimentacoesPost() throws Exception{
		
		MovimentacoesPostRequestDTO dto	= new MovimentacoesPostRequestDTO();
		
		dto.setIdProduto(produto.getIdProduto());
		dto.setDataMovimentacao("2023-06-27");
		dto.setObservacoes("Movimentação teste");
		dto.setQuantidade(10);
		dto.setTipo(1);

		mockMvc.perform(post("/api/movimentacoes") 
		.contentType("application/json")
		.content(mapper.writeValueAsString(dto)))
		.andExpect(status().isCreated());
	}
	
	@Test
	@Order(7)
	public void testMovimentacoesGetAll() throws Exception{
		mockMvc.perform(get("/api/movimentacoes/2023-06-01/2023-06-30"))
		.andExpect(status().isOk());
	}
	
	@Test
	@Order(8)
	public void testProdutosDelete() throws Exception{
		testProdutosPost(); //criando um novo produto
		mockMvc.perform(delete("/api/produtos/"
		+ produto.getIdProduto()))
		.andExpect(status().isOk());
	}

}
