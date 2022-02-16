package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * A anotação @Entity indica que a classe é uma entidade, ou seja,
 * ele será utilizada para gerar uma tabela no Banco de Dados.
 * 
 * A anotação @Table indica o nome da tabela no Banco de dados.
 * Caso ela não seja declarada, o Banco criará a tabela com o mesmo
 * nome da classe.
 */
@Entity
@Table(name = "tb_postagens") 
public class Postagem {

	/**
	 * A anotação @ID inidica que o atributo é a chave primária da tabela
	 * 
	 * A anotação @GeneratedValue indica que a chave primária será gerada
	 * automaticamente pelo Banco de Dados.
	 * 
	 * O parâmetro strategy indica como a Chave Primária será gerada. 
	 * 
     * A opção GenerationType.IDENTITY indica que será uma sequência numérica iniciando
	 * em 1 e será responsabilidade do Banco de dados gerar esta sequência, ou seja,
	 * a propriedade auto_increment do SQL. 
	 * 
	 * Não confundir o auto-incremento do Banco de Dados que inicia em 1 com o
	 * indice de um Array (Vetor ou Matriz) que inicia em 0.
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 

	/**
	 * A anotação @NotNull indica que um atributo não pode ser nulo
	 * 
	 * O parâmtero message insere uma mensagem que será exibida caso o
	 * atributo seja nulo
	 * 
     * A anotação @NotBlank indica que um atributo não pode ser nulo e 
	 * também não pode ser deixado em branco (vazio).
	 * 
	 * A anotação @Size tem a função de definir o tamanho minimo e máximo de
	 * caracteres de um atributo String. Não é obrigatório definir os 2 parâmetros, 
	 * você pode definir apenas um deles de acordo com as necessidades do seu
     * projeto.
	 * 
	 *                            ***IMPORTANTE*** 
	 * 
	 * Para utilizar as anotações acima, não esqueça de inserir a Dependência 
	 * Validation na criação do projeto ou insira manualmente no arquivo pom.xml
	 * 
	 */
	@NotBlank(message = "O atributo título é Obrigatório e não pode utilizar espaços em branco!") 
	@Size(min = 5, max = 100, message = "O atributo título deve conter no mínimo 05 e no máximo 100 caracteres")
	private String titulo; 

	@NotNull(message = "O atributo texto é Obrigatório!")
	@Size(min = 10, max = 1000, message = "O atributo texto deve conter no mínimo 10 e no máximo 500 caracteres")
	private String texto;

	/**
	 * A anotação @UpdateTimestamp Indica se o atributo receberá um Timestamp (Data e hora do sistema)
	 * e sempre que a Postagem for atualizada o atributo também será atualizado.
	 */
	@UpdateTimestamp
	private LocalDateTime data;

	/**
	 * 
	 * Os Métodos Get e Set obrigatoriamente devem ser criados para todos os atributos
     * da Classe, inclusive os novos atributos que forem adicionados no decorrer do
     * processo de Desenvolvimento.
	 * 
	 */	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

}
