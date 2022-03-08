package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
    
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start(){

		/** 
		 * Persiste (Grava) 4 Objetos Usuario no Banco de dados
		 */ 

		usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", "https://i.imgur.com/NtyGneo.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278", "https://i.imgur.com/mB3VM2N.jpg"));

        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));

	}

	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {

		/**
		 *  Executa o método findByUsuario para buscar um usuario pelo nome (joao@email.com.br)
		 */
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");

		/**
		 *  Verifica se a afirmação: "É verdade que a busca retornou o usuario joao@email.com.br" é verdadeira
		 *  Se for verdaeira, o teste passa, senão o teste falha. 
		 */
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}

	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {

		/**
		 *  Executa o método findAllByNomeContainingIgnoreCase para buscar todos os usuarios cujo nome contenha
		 *  a palavra "Silva"
		 */
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");

		/**
		 * Verifica se a afirmação: "É verdade que a busca retornou 3 usuarios, cujo nome possua a palavra Silva" 
		 * é verdadeira
		 * Se for verdadeira, o teste passa, senão o teste falha.
		 */
		assertEquals(3, listaDeUsuarios.size());

		/**
		 *  Verifica se a afirmação: "É verdade que a busca retornou na primeira posição da Lista o usuario 
		 * João da Silva" é verdadeira
		 * Se for verdadeira, o teste passa, senão o teste falha.
		 */
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));

		/**
		 *  Verifica se a afirmação: "É verdade que a busca retornou na segunda posição da Lista a usuaria 
		 * Manuela da Silva" é verdadeira
		 * Se for verdadeira, o teste passa, senão o teste falha.
		 */
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));

		/**
		 *  Verifica se a afirmação: "É verdade que a busca retornou na primeira posição da Lista a usuaria 
		 * Adriana da Silva" é verdadeira
		 * Se for verdadeira, o teste passa, senão o teste falha.
		 */
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}

	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
	
}