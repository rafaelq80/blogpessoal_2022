package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import com.generation.blogpessoal.model.Usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Classe UserDetailsImpl 
 * 
 * Implementa a interface UserDetails, que descreve o usuário para 
 * o Spring Security,ou seja, detalha as caracteríticas do usuário.
 * 
 * Por se tratar de uma implementação de uma interface, a classe
 * deve ter em seu nome o sufixo Impl para indicar que se trata de
 * uma implementação.
 * 
 * As características descritas na interface UserDetails são:
 * 
 * 1) Credenciais do usuário (Username e Password)
 * 2) As Autorizações do usuário (o que ele pode e não pode fazer),
 *    através da Collection authorities do tipo GrantedAuthority
 * 3) As Restrições (isAccountNonExpired(), isAccountNonLocked(), 
 *    isCredentialsNonExpired() e isEnabled()) da conta do usuário.
 */

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;

	/**
	 * Método construtor com parâmetros 
	 * 
	 * Observe que este método Construtor recebe um objeto Usuario e
	 * recupera os dados necessários através dos respectivos métodos Get
	 */

	public UserDetailsImpl(Usuario usuario) {
		this.userName = usuario.getUsuario();
		this.password = usuario.getSenha();
	}

	/**
	 * Método construtor sem parâmetros 
	 */

	public UserDetailsImpl() {	}

	/**
	 *  Sobrescreve (@Override) o método que retorna as Autorizações
	 *  da conta do usuário. Nesta implementação, não há nenhuma autorização
	 *  negada
	 */

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {

		return userName;
	}

	/**
	 *  Sobrescreve (@Override) o método que Indica se a conta do usuário 
	 *  expirou.
	 */

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 *  Sobrescreve (@Override) o método que Indica se o usuário 
	 *  está bloqueado ou desbloqueado.
	 */

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 *  Sobrescreve (@Override) o método que indica se as 
	 *  credenciais do usuário (senha) expiraram.  
	 */
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Sobrescreve (@Override) o método que Indica se o usuário 
	 *  está habilitado ou desabilitado.
	 *  Se mudar para false nenhum usuário conseguirá logar.
	 */

	@Override
	public boolean isEnabled() {
		return true;
	}
}
