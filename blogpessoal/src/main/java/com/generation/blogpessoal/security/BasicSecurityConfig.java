package com.generation.blogpessoal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe BasicSecurityConfig
 * 
 * Esta classe é responsável por habilitar a segurança básica da aplicação e o login
 * na aplicação. Nesta Classe vamos habilitar a autenticação http.
 * 
 * A configuração padrão garante que qualquer requisição enviada para a API 
 * seja autenticada com login baseado em formulário.
 * 
 */

/**
 *  A annotation @Configuration: indica que a Classe é do tipo configuração, ou seja, 
 *  define uma Classe como fonte de definições de Beans, além de ser uma das anotações 
 *  essenciais ao utilizar uma configuração baseada em Java.
 * 
 *  A annotation @EnableWebSecurity: habilita a configuração de segurança padrão 
 *  do Spring Security na nossa api.
 */
@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {
	
    /**
	 *  A annotation @Bean transforma a instância retornada pelo método como um 
	 *  objeto gerenciado pelo Spring, desta forma, ele pode ser injetado em qualquer
	 *  classe, a qualquer momento que você precisar sem a necessidade de usar a 
	 *  annotation @Autowired
	 * 
	 *  O método abaixo é responsável por criptografar a senha do usuário utilizando o
	 *  método hash Bcrypt.
	 */
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
     * O Método authenticationManager(AuthenticationConfiguration authenticationConfiguration), 
     * implementa a configuração de autenticação. 
     * 
     * Este Método utiliza o Método authenticationConfiguration.getAuthenticationManager() para 
     * procurar uma implementação da Interface UserDetailsService e utilizá-la para identificar 
     * se o usuário é válido ou não. 
     * 
     * Em nosso projeto Blog Pessoal, será utilizada a Classe UserDetailsServiceImpl, que valida 
     * o usuário no Banco de dados.
     * 
     */
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	/** O Método SecurityFilterChain filterChain(HttpSecurity http), informao ao
	 *  Spring que a configuração padrão da Spring Security será substituída 
	 *  por uma nova configuração. 
	 * 
	 * Nesta configuração iremos customizar a autenticação da aplicação desabilitando
	 * o formulário de login e habilitando a autenticação via http. 
	 * 
	 * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		
		/**
		 * antMatchers().permitAll -> Endpoint liberado de autenticação
		 * 
		 * HttpMethod.OPTIONS -> O parâmetro HttpMethod.OPTIONS permite que 
		 * o cliente (frontend), possa descobrir quais são as opções de 
		 * requisição permitidas para um determinado recurso em um servidor. 
		 * Nesta implementação, está sendo liberada todas as opções das 
		 * requisições através do método permitAll().
		 * 
		 * anyRequest().authenticated() -> Todos os demais endpoints 
		 * serão autenticados
		 * 
		 * Http Sessions: As sessões HTTP são um recurso que permite 
		 * que os servidores da Web mantenham a identidade do usuário 
		 * e armazenem dados específicos do usuário durante várias 
		 * interações (request/response) entre um aplicativo 
		 * cliente e um aplicativo da Web.
		 * 
		 * sessionManagement() -> Cria um gerenciador de Sessões
		 * 
		 * sessionCreationPolicy(SessionCreationPolicy.STATELESS) -> Define
		 * como o Spring Secuiryt irá criar (ou não) as sessões
		 * 
		 * STATELESS -> Nunca será criada uma sessão, ou seja, basta enviar
		 * o token através do cabeçalho da requisição que a mesma será processada.
		 * 
		 * cors -> O compartilhamento de recursos de origem cruzada (CORS) surgiu 
		 * porquê os navegadores não permitem solicitações feitas por um domínio
		 * (endereço) diferente daquele de onde o site foi carregado. Desta forma o 
		 * Frontend da aplicação, por exemplo, obrigatoriamente teria que estar 
		 * no mesmo domínio que o Backend. Habilitando o CORS, o Spring desabilita 
		 * esta regra e permite conexões de outros domínios.
		 * 
		 * CSRF: O cross-site request forgery (falsificação de 
		 * solicitação entre sites), é um tipo de ataque no qual comandos não 
		 * autorizados são transmitidos a partir de um usuário em quem a 
		 * aplicação web confia. 
		 * 
		 * csrf().disabled() -> Esta opção de proteção é habilitada por padrão no 
		 * Spring Security, entretanto precisamos desabilitar, caso contrário, todos 
		 * os endpoints que respondem ao verbo POST não serão executados.
		 * 
		 */

		http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable()
            .cors();

        http
            .authorizeHttpRequests((auth) -> auth
                .antMatchers("/usuarios/logar").permitAll()
                .antMatchers("/usuarios/cadastrar").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated())
            .httpBasic();

        return http.build();
			
	}
}
