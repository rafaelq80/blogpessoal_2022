package com.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	 * ATUALIZAÇÃO - POSTAGEM CONTROLLER
	 * 
	 * Inserir a injeção de Dependência para a Interface TemaRespository
	 */

	@Autowired
	private TemaRepository temaRepository;
	
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

	/*
	 * Listar postagem por id - utilizando Expressões Lambda
	 *  
	 * As expressões Lambda representam uma função anônima, ou seja, uma função lambda é uma função sem declaração, 
	 * isto é, não é necessário colocar um nome, um tipo de retorno e o modificador de acesso. A ideia é que o 
	 * método seja declarado no mesmo lugar em que será usado. As expressões lambda em Java tem a sintaxe definida 
	 * como: (argumento) -> (corpo). Elas são semelhantes as Arrow Functions do Javascript/Typescript.
	 * 
	 * @GetMapping("/{id}"): Anotação que indica que o método abaixo responderá todas as requisições do tipo GET 
	 * que forem enviadas no endpoint /postagens/id, onde id é uma variável de caminho (path variable), que
	 * receberá em nosso o exemplo o Id que você deseja encontrar
	 * 
	 * O Método getById(@PathVariable Long id) será do tipo ResponseEntity porque ele responderá a 
	 * requisição (Request), com uma HTTP Response (Resposta http), em nosso exemplo a Response Status 200 => OK, 
	 * caso a Postagem seja encontrada. Caso não seja encontrada, a resposta será Not Found => 404 (Não Encontrada).
	 * 
	 * @PathVariable Long id: Anotação que insere a variável de caminho (path variable) id, que foi informada no 
	 * endereço da requisição, e insere no parâmetro id do método getById.
	 * 
	 * Exemplo:
	 * 
	 * http://localhost:8080/postagens/1
	 * 
	 * o parâmetro id do método receberá o valor 1 (Id que será procurado na tabela postagens através do método 
	 * findById(id))
	 * 
	 * <Postagem>: Como o Método listará apenas 1 registro da nossa tabela, o método retornará 
	 * dentro da resposta um objeto do tipo Postagem, que são os dados encontrados na tabela.
	 * 
	 * return postagemRepository.findById(id): Retorna a execução do método findById(id)
	 * 
	 * .map(resposta -> ResponseEntity.ok(resposta)): Se a postagem existir, a função map (tipo Optional), aplica
	 * o valor do objeto resposta (objeto do tipo Postagem com o retorno do método findById(id) no método: 
	 * ResponseEntity.ok(resposta); e retorna o status OK => 200
	 * 
	 * .orElse(ResponseEntity.notFound().build()); : Se a postagem não for encontrada, retorna 
	 * o status Not Found => 404
	 *
	 * Optional (map): É um contêiner para um valor que pode estar ausente. Em nosso contexto, tem como 
	 * principal função evitar o erro do tipo NullPointerException (Objeto nulo), caso a Postagem procurada 
	 * pelo método findById(id)não seja encontrada na Model Postagens.
	 * 
	 */

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		return postagemRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}
	
	/*
	 * Consultar postagens por titulo 
	 * 
	 * @GetMapping("/titulo/{titulo}"): Anotação que indica que o método abaixo responderá todas
	 * as requisições do tipo GET que forem enviadas no endpoint /postagens/titulo/titulo, onde
	 * a segunda palavra titulo é uma variável de caminho (path variable), que receberá em nosso
	 * exemplo a String (palavra, sílaba e etc), que você deseja encontrar na tabela tb_postagens
	 *  
	 * O Método getByTitulo(@PathVariable Long titulo) será do tipo ResponseEntity porque ele responderá a 
	 * requisição (Request), com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK, 
	 * independente da Postagem ser ou não encontrada. O método retornará uma lista de Postagens
	 * vazia, caso nenhuma postagem que atenda ao critério de busca seja encontrada.
	 * 
	 * @PathVariable String titulo: Anotação que insere o valor da variável de caminho titulo, 
	 * informada no endereço da requisição e insere no parâmetro titulo do método getByTitulo
	 * 
	 * Exemplo:
	 * 
	 * http://localhost:8080/postagens/titulo/primeira
	 * 
	 * o parâmetro titulo do método receberá a String "primeira" (palavra que será procurada na tabela postagens 
	 * no campo titulo via findAllByTituloContainingIgnoreCase(titulo))
	 * 
	 * <List<Postagem>>: Como o Método listará todos os registros da nossa tabela, que possuam a string enviada
	 * pelo path, o método retornará dentro da resposta um objeto do tipo List (Collection) preenchido com 
	 * objetos do tipo Postagem, que são os dados da tabela.
	 * 
	 * return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));: Executa o 
	 * método findAllByTituloContainingIgnoreCase(titulo) e retorna o status OK => 200
	 * 
	 * Como o Método sempre irá criar a List independente ter ou não valores na tabela, ele sempre
	 * retornará 200.
	 * 
	 */
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	/*
	 * Criar nova postagem 
	 * 
	 * @PostMapping: Anotação que indica que o método abaixo responderá todas
	 * as requisições do tipo POST que forem enviadas no endpoint /postagens
	 * 
	 * O Método ResponseEntity<Postagem> postPostagem (@RequestBody Postagem postagem) será do tipo 
	 * ResponseEntity porque ele responderá a requisição (Request), com uma HTTP Response (Resposta http), 
	 * neste caso Response Status 201 => CREATED, caso a Postagem seja inserida na tabela. Caso não seja 
	 * inserida na tabela, a resposta será Internal Server Error => 500
	 * 
	 * @Valid: Valida o Objeto Postagem enviado no corpo da requisição (Request Body), conforme as regras
	 * definidas na Model Postagem. Caso algum atributo não seja validado, o método retornará um status
	 * 400 => Bad Request.
	 * 
	 * @RequestBody Postagem postagem: Anntotation (anotação) que insere o objeto do tipo Postagem enviado
	 * no corpo da requisição (Request Body) e insere no parâmetro postagem do método postPostagem
	 * 
	 * <Postagem>: O Método retornará dentro da resposta um objeto do tipo Postagem, que são os dados da tabela.
	 *  
	 * return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));: Executa o 
	 * método save(postagem) e retorna o status CREATED = 201 se o objeto Postagem foi inserido na tabela 
	 * postagens no Banco de dados.
	 * 
	 * Ao fazer o envio dos dados via Postman ou front-end, não é necessário passar o Id e a Data
	 * 
	 */
	
	@PostMapping
	public ResponseEntity<Postagem> postPostagem (@Valid @RequestBody Postagem postagem){
		
		/** Checa antes de Persistir o Objeto Postagem se o Tema existe 
		 *  Se o Objeto Tema não existir, o status devolvido será Bad Request (400).
		*/

		if (temaRepository.existsById(postagem.getTema().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	
	}
	
	/*
	 * Editar uma postagem 
	 * 
	 * @PutMapping: Annotation (Anotação), que indica que o método abaixo responderá todas
	 * as requisições do tipo PUT que forem enviadas no endpoint /postagens
	 * 
	 * O Método ResponseEntity<Postagem> putPostagem (@RequestBody Postagem postagem) será do tipo 
	 * ResponseEntity porque ele responderá a requisição (Request), com uma HTTP Response (Resposta http), 
	 * neste caso Response Status 200 => OK, caso a Postagem seja atualizada na tabela. Caso não seja 
	 * atualizada na tabela, a resposta será Internal Server Error => 500
	 * 
	 * @Valid: Valida o Objeto Postagem enviado no corpo da requisição (Request Body), conforme as regras
	 * definidas na Model Postagem. Caso algum atributo não seja validado, o método retornará um status
	 * 400 => Bad Request.
	 * 
	 * @RequestBody Postagem postagem: Anntotation (anotação) que insere o objeto do tipo Postagem enviado
	 * no corpo da requisição (Request Body) e insere no parâmetro postagem do método postPostagem
	 * 
	 * <Postagem>: O Método retornará dentro da resposta um objeto do tipo Postagem, que são os dados da tabela.
	 *  
	 * return postagemRepository.findById(postagem.getId()): Retorna a execução do método findById(postagem.getId()).
	 * Observe que o Id da postagem é obtido através do método getId() que retorna o Id da Postagem enviada no Corpo
	 * da requisição (Request Body)
	 * 
	 * .map(resposta -> ResponseEntity.ok().body(postagemRepository.save(postagem))): Se a postagem existir, a função 
	 *  map (tipo Optional), executa o método save(postagem) e retorna o status OK = 200 se o objeto Postagem foi 
	 *  atualizado na tabela postagens no Banco de dados.
	 * 
	 * .orElse(ResponseEntity.notFound().build());: Se a postagem não for encontrada pelo método findById(postagem.getId()), 
	 * retorna o status Not Found = 404
	 *
	 * Optional (map): É um contêiner para um valor que pode estar ausente. Em nosso contexto, tem como 
	 * principal função evitar o erro do tipo NullPointerException (Objeto nulo), caso a Postagem procurada 
	 * pelo método findById(id)não seja encontrada na Model Postagens.
	 * 
	 * Ao fazer o envio dos dados via Postman ou front-end é necessário passar o Id para identificar qual a 
	 * Postagem será atualizada.
	 *  
	 */
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem (@Valid @RequestBody Postagem postagem){
		
		/** Substituimos o Lambda por 2 Condicionais, que testam respectivamente se o
		 *  Objeto Postagem existe e se o Objeto Tema existe antes de atualizar o Objeto
		 *  Postagem.
		 * 
		 *  Se o Objeto Postagem não existir, o Objeto Tema não será verificado e o 
		 *  status devolvido será Not Found (404).
		 * 
		 *  Se o Objeto Tema não existir, o status devolvido será Bad Request (400).
		*/

		if (postagemRepository.existsById(postagem.getId())){
			
			if (temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(postagemRepository.save(postagem));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			
		}			
			
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
			
	/*
	 * Deletar uma postagem 
	 *   
	 * @DeleteMapping("/{id}"): Annotation (Anotação), que indica que o método abaixo responderá todas
	 * as requisições do tipo DELETE que forem enviadas no endpoint /postagens/id
	 * 
	 * O Método deletePostagem(@PathVariable Long id)  será do tipo ResponseEntity porque ele responderá a 
	 * requisição (Request), com uma HTTP Response (Resposta http), neste caso Response Status 204 => NO_CONTENT 
	 * caso a Postagem seja encontrada e excluída da tabela. Caso não seja encontrada, a resposta será 
	 * Not Found => 404
	 * 
	 * A Annotation @PathVariable Long id: insere a variável de path (caminho ou url do endpoint), 
	 * passada no endereço da requisição, e insere no parâmetro id do método deletePostagem
	 * 
	 * Exemplo:
	 * 
	 * http://localhost:8080/postagens/1
	 * 
	 * o parâmetro id do método receberá o valor 1 (Id que será procurado na tabela postagens e deletado via deleteById())
	 * 
	 * <?>: O ?, no contexto de genéricos, basicamente serve como um coringa, ou seja, ele representa "qualquer tipo". 
	 * Sua função é permitir o uso do polimorfismo junto com genéricos, logo você não está definindo um tipo específico 
	 * para retorno. Como Delete não retorna nada, o tipo ResponseEntity<Void> é o tipo genérico que será retornado.
	 * 
	 * return postagemRepository.findById(id): Retorna a execução do método findById(id). Observe que o Id da postagem
	 * foi passado via path (caminho ou url do endpoint)
	 * 
	 * .map(resposta -> {
	 *				postagemRepository.deleteById(id);
	 *				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	 *			})
	 *
	 * O Trecho de código acima será executado se a postagem for encontrada pelo método findById. Primeiro, o método 
	 * deleteById(id) irá excluir a postagem da tabela postagens. Na sequência, será retornado o status NO_CONTENT = 204 
	 * indicando que a postagem foi excluída e não existe mais.
	 * 
	 * .orElse(ResponseEntity.notFound().build()): Caso a Postagem não seja encontrada pelo método findById, a resposta 
	 * será Not Found => 404
	 * 
	 */
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePostagem(@PathVariable Long id) {
		
		return postagemRepository.findById(id)
				.map(resposta -> {
					postagemRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
}