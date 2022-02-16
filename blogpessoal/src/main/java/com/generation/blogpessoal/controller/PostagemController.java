package com.generation.blogpessoal.controller;

import java.util.List;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * A anotação @RestController: indica que a Classe é uma RestController, ou seja, 
 * é responsável por responder todas as requisições http enviadas para um endpoint 
 * (endereço) definido na anotação @RequestMapping
 * 
 * A anotação @RequestMapping("/postagens"): indica o endpoint (endereço) que a 
 * controladora responderá as requisições 
 * 
 * A anotação @CrossOrigin("*"): indica que a classe controladora permitirá o 
 * recebimento de requisições realizadas de fora do domínio (localhost, em nosso caso) ao qual 
 * ela pertence. Essa anotação é essencial para que o front-end (Angular ou React), tenha
 * acesso à nossa API (O termo técnico é consumir a API)
 * 
 * Para as versões mais recentes do Angular e do React, é necessário configurar esta anotação 
 * com os seguintes parâmetros: @CrossOrigin(origins = "*", allowedHeaders = "*") 
 * 
 * Esta anotação, além de liberar todas as origens (origins), libera também todos os parâmetros
 * do cabeçalho das requisições (allowedHeaders).
 * 
 * Em produção, o * é substituido pelo endereço de domínio (exemplo: www.meudominio.com) do
 * Frontend
 * 
 */

@RestController
@RequestMapping("/postagens") 
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class PostagemController {
	
	/*
	 * 
	 * Injeção de Dependência (@Autowired): Consiste  na  maneira,  ou  seja,  na  implementação 
	 * utilizada pelo  Spring  Framework  de  aplicar  a  Inversão  de  Controle  quando  for 
	 * necessário.
	 * 
	 * A Injeção de Dependência define quais classes serão instanciadas e em quais lugares serão 
	 * injetadas quando houver necessidade. 
	 * 
	 * Em nosso exemplo a classe controladora cria um ponto de injeção da interface PostagemRepository, 
	 * e quando houver a necessidade o Spring Framework irá criar uma instância (objeto) desta interface
	 * permitindo o uso de todos os métodos (padrão ou personalizados da Interface PostagemRepository)
	 *  
	 * */
	
	@Autowired 
	private PostagemRepository postagemRepository;
	
	/**
	 * Listar todas as Postagens
	 *  
	 * A anotação @GetMapping: indica que o método abaixo responderá todas as 
	 * requisições do tipo GET que forem enviadas no endpoint /postagens
	 * 
	 * O Método getAll() será do tipo ResponseEntity porque ele responderá a requisição (Request),
	 * com uma HTTP Response (Resposta http).
	 * 
	 * <List<Postagem>>: O Método além de retornar um objeto da Classe ResponseEntity (OK 🡪 200), 
	 * no parâmetro body (Corpo da Resposta), será retornado um Objeto da Classe List (Collection), 
	 * contendo todos os Objetos da Classe Postagem persistidos no Banco de dados, na tabela 
	 * tb_postagens. Observe que nesta linha foi utilizado um recurso chamado Java Generics, 
	 * que além de simplificar o retorno do Objeto da Classe List, dispensa o uso do casting(mudança de tipos). 
	 * Observe que na definição do Método foram utilizados os símbolos <T>, onde T é o Tipo do Objeto 
	 * que será retornado no Corpo da Resposta.
	 * 
	 * return ResponseEntity.ok(postagemRepository.findAll());: Executa o método findAll() (Método padrão da 
	 * Interface JpaRepository), que retornará todos os Objetos da Classe Postagem persistidos no Banco de dados
	 * (<List<Postagem>>). Como a List sempre será gerada (vazia ou não), o Método sempre retornará o Status 200 🡪 OK.
	 * 
	 * O Mátodo findAll() é equivalente a consulta SQL: SELECT * FROM tb_postagens;
	 * 
	 */
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll (){
		return ResponseEntity.ok(postagemRepository.findAll());
	}

}