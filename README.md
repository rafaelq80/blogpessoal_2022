# Implementando testes com o Spring Testing no Blog Pessoal

Nesta atividade iremos implementar os testes nas Camadas Model, Repository e Controller da Classe Usuário. 



## Boas Práticas

1. <a href="#dep">Configure as Dependências no arquivo pom.xml</a>
2. <a href="#dtb">Configure o Banco de Dados (db_blogpessoaltest)</a>
3. <a href="#pac">Prepare a estrutura de pacotes para os testes</a>
4. <a href="#mcon">Crie os métodos construtores na Classe Usuario</a>
5. <a href="#rep">Crie a Classe de testes na Camada Repository: UsuarioRepositoryTest</a>
6. <a href="#ctr">Crie a Classe de testes na Camada Controller: UsuarioControllerTest</a>
7. <a href="#run">Execute os testes</a>
9. <a href="#ref">Referências</a>



<h2 id="dep"> Dependências</h2>

No arquivo, **pom.xml**, vamos alterar a linha:

```xml
    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter-test</artifactId>
    	<scope>test</scope>
    </dependency>
```
Para:
```xml
    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter-test</artifactId>
    	<scope>test</scope>
    	<exclusions>
    		<exclusion>
    			<groupId>org.junit.vintage</groupId>
    			<artifactId>junit-vintage-engine</artifactId>
    		</exclusion>
    	</exclusions>
    </dependency>
```
*Essa alteração irá ignorar as versões anteriores ao **JUnit 5** (vintage).

<br />

Para utilizar o Banco de Dados H2 no seu projeto será necessário inserir a dependência no seu arquivo **pom.xml**.

No arquivo, **pom.xml**, vamos adicionar a dependência abaixo:

```xml
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
```

*Sugerimos adicionar esta dependência logo abaixo da dependência do MySQL.

<br />

<h2 id="dtb">Banco de Dados</h2>



Agora vamos configurar o Banco de dados de testes para não usar o Banco de dados principal da aplicação.

1) No lado esquerdo superior, na Guia **Package Explorer**, clique sobre a pasta do projeto com o botão direito do mouse e clique na opção **New->Source folder**

<div align="center"><img src="https://i.imgur.com/GYKQsnW.png" title="source: imgur.com" /></div>

2) Em **Source Folder**, no item **Folder name**, informe o caminho como mostra a figura abaixo (**src/test/resources**), e clique em **Finish**:

<div align="center"><img src="https://i.imgur.com/lZ6FEDX.png" title="source: imgur.com" /></div>

3. Na nova Source Folder (**src/test/resources**) , crie o arquivo **application.properties**, para configurarmos a conexão com o Banco de Dados de testes

4. No lado esquerdo superior, na Guia **Package explorer**, na Package **src/test/resources**, clique com o botão direito do mouse e clique na opção **New 🡪 File**.

   <div align="center"><img src="https://i.imgur.com/j7ckkJy.png" title="source: imgur.com" /></div>

5. Em File name, digite o nome do arquivo (**application.properties**) e clique em **Finish**. 

<div align="center"><img src="https://i.imgur.com/TGusKTm.png" title="source: imgur.com" /></div>

6) Veja o arquivo criado na  **Package Explorer** 

<div align="center"><img src="https://i.imgur.com/qrJDQcr.png" title="source: imgur.com" /></div>

7) Insira no arquivo application.properties as seguinte linhas:

```properties
spring.datasource.url=jdbc:h2:mem:db_blogpessoal_test
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=sa
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

Observe que o nome do Banco de dados possui a palavra **teste** para indicar que será apenas para a execução dos testes.

Não esqueça de configurar a senha do usuário root.



<h2 id="pac">Estrutura de pacotes</h2>



Na Source Folder de Testes (**src/test/java**) , observe que existe uma estrutura de pacotes semelhante a Source Folder Main (**src/main/java**). Crie na Source Folder de Testes as packages Repository e Controller como mostra a figura abaixo: 

<div align="center"><img src="https://i.imgur.com/oExK6OD.png" title="source: imgur.com" /></div>

***A Package Model não precisa ser criada**

O Processo de criação dos arquivos é o mesmo do código principal, exceto o nome dos arquivos que deverão ser iguais aos arquivos da Source Folder Main (**src/main/java**) acrescentando a palavra Test no final como mostra a figura abaixo. 

<b>Exemplo: </b>
<b>UsuarioRepository 🡪 UsuarioRepositoryTest</b>.

<div align="center"><img src="https://i.imgur.com/jWOsW9c.png" title="source: imgur.com" /></div>

***A Classe UsuarioTest não precisa ser criada**



<h2 id="mcon">Métodos Construtores - Classe Usuario</h2>



Na Classe Usuario, na canada Model do pacote principal (main), vamos criar 2 métodos construtores: o primeiro com todos os atributos, exceto o postagens e um segundo método vazio, ou seja, sem atributos.

```java
package br.org.generation.blogpessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "O atributo nome é obrigatório")
	private String nome;
	
	@NotNull(message = "O atributo usuário é obrigatório")
	private String usuario;
	
	@NotNull(message = "O atributo senha é obrigatório")
	@Size(min = 8, message = "O atributo senha deve ter no mínimo 8 caracteres")
	private String senha;
	
    private String foto;
    
	@OneToMany (mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List <Postagem> postagem;

	// Primeiro método Construtor

	public Usuario(Long id, String nome, String usuario, String senha, String foto) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
	}

	// Segundo método Construtor

	public Usuario() {	}


	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

    public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
    
	public List<Postagem> getPostagem() {
		return this.postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}

}

```



<h2 id="rep">Classe UsuarioRepositoryTest</h2>



A Classe UsuarioRepositoryTest será utilizada parta testar a Classe Repository do Usuario.  Antes de iniciar o teste, verifique se a sua Interface **UsuarioRepository**, localizada na Source Folder Principal, possui os métodos: **findByUsuario()** e **findAllByNomeContainingIgnoreCase()**, como mostra o código abaixo:

```java
package br.org.generation.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.blogpessoal.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByUsuario(String usuario);

	//Método criado para a Sessão de testes
	public List<Usuario> findAllByNomeContainingIgnoreCase(String nome);

}

```

Crie a classe UsuarioRepositoryTest na package **repository**, na Source Folder de Testes (**src/test/java**)

**Importante:** O Teste da Classe UsuarioRepository da camada Repository, utiliza o Banco de Dados, entretanto ele não criptografa a senha ao gravar um novo usuario no Banco de dados. O teste não utiliza a Classe de Serviço UsuarioService para gravar o usuário. O Teste utiliza o método save(), da Classe JpaRepository de forma direta. 

```java
package br.org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.org.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
    
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start(){

		usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278",
                                           "https://i.imgur.com/FETvs2O.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", 
                                           "https://i.imgur.com/NtyGneo.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278",
                                           "https://i.imgur.com/mB3VM2N.jpg"));

        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", 
                                           "https://i.imgur.com/JR7kUFU.jpg"));

	}

	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}

	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}

    @AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
    
}
```



<h2 id="ctr">Classe UsuarioControllerTest</h2>



A Classe UsuarioControllerTest será utilizada parta testar a Classe Controller do Usuario. Crie a classe UsuarioControllerTest na package **controller**, na Source Folder de Testes (**src/test/java**) 

```java
package br.org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.org.generation.blogpessoal.model.Usuario;
import br.org.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Test
	@Order(1)
	@DisplayName("Cadastrar Um Usuário")
	public void deveCriarUmUsuario() {

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
			"Paulo Antunes", "paulo_antunes@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));

		ResponseEntity<Usuario> resposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}

	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));

		ResponseEntity<Usuario> resposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}

	@Test
	@Order(3)
	@DisplayName("Alterar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Juliana Andrews", "juliana_andrews@email.com.br", 
			"juliana123", "https://i.imgur.com/yDRVeK7.jpg"));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), 
			"Juliana Andrews Ramos", "juliana_ramos@email.com.br", 
			"juliana123", "https://i.imgur.com/yDRVeK7.jpg");
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}

	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Sabrina Sanches", "sabrina_sanches@email.com.br", 
			"sabrina123", "https://i.imgur.com/5M2p5Wb.jpg"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Ricardo Marques", "ricardo_marques@email.com.br", 
			"ricardo123", "https://i.imgur.com/Sk5SjWE.jpg"));

		ResponseEntity<String> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

}
```

Observe que como habilitamos em nosso Blog Pessoal o **Spring Security** com autenticação do tipo **BasicAuth** na API, o Objeto **testRestTemplate** dos métodos que exigem autenticação, deverá passar o usuário **root**, senha **root** (criado em memória) para realizar os testes. 

**Exemplo:**

```java
ResponseEntity<String> resposta = testRestTemplate
.withBasicAuth("root", "root")
.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
```

Verifique na **Camada Security** se a Classe **BasicSecurityConfig** está igual ao código abaixo:

```java
package br.org.generation.blogpessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);

		auth.inMemoryAuthentication()
			.withUser("root")
			.password(passwordEncoder().encode("root"))
			.authorities("ROLE_USER");
			
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/usuarios/logar").permitAll()
			.antMatchers("/usuarios/cadastrar").permitAll()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest().authenticated()
			.and().httpBasic()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().cors()
			.and().csrf().disable();
	}
}
```



<h2  id="run">Executando os testes no Eclipse / STS</h2>



1) No lado esquerdo superior, na Guia **Project**, na Package **src/test/java**, clique com o botão direito do mouse sobre o teste que você deseja executar e clique na opção **Run As 🡪 JUnit Test**.

<div align="center"><img src="https://i.imgur.com/Ol2N93J.png" title="source: imgur.com" /></div>


2) Para acompanhar os testes, ao lado da Guia **Project**, clique na Guia **JUnit**.

<div align="center"><img src="https://i.imgur.com/JvC0kS3.png" title="source: imgur.com" /></div>

 3) Se todos os testes passarem, a Guia do JUnit ficará com uma faixa verde (janela 01). Caso algum teste não passe, a Guia do JUnit ficará com uma faixa vermelha (janela 02). Neste caso, observe o item <b>Failure Trace</b> para identificar o (s) erro (s).

<div align="center">
<table width=100%>
	<tr>
		<td width=50%><div align="center"><img src="https://i.imgur.com/TeiTjQW.png" title="source: imgur.com" /></div>
		<td width=50%><div align="center"><img src="https://i.imgur.com/7b13sd6.png" title="source: imgur.com" /></div>
	</tr>
	<tr>
		<td><div align="center">Janela 01: <i> Testes aprovados.
		<td><div align="center">Janela 02: <i> Testes reprovados.
	</tr>
</table>
</div>
Ao escrever testes, sempre verifique se a importação dos pacotes do JUnit na Classe de testes estão corretos. O JUnit 5 tem como pacote base <b><i>org.junit.jupiter.api</i></b>.


<h2 id="ref">Referências</h2>

<a href="https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testing-introduction" target="_blank">Documentação Oficial do Spring Testing</a>

<a href="https://junit.org/junit5/" target="_blank">Página Oficial do JUnit5</a>

<a href="https://junit.org/junit5/docs/current/user-guide/" target="_blank">Documentação Oficial do JUnit 5</a>

<a href="https://www.h2database.com/html/main.html" target="_blank">Documentação Oficial do Banco de dados  H2</a>

<a href="https://gasparbarancelli.com/post/banco-de-dados-h2-com-spring-boot" target="_blank">Banco de dados H2 com Spring Boot</a>

