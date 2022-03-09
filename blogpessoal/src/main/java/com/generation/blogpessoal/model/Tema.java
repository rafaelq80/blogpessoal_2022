package com.generation.blogpessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_temas")
public class Tema{
	    
	    @Id	
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@NotBlank(message = "O atributo Descrição é obrigatório e não pode conter espaços em branco")
		private String descricao;
		
		/**
		 *  A Anotação @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL): indica 
		 *  que a Classe Tema terá um relacionamento do tipo One To Many (Um para Muitos) com a Classe 
		 *  Postagem
		 *  
		 *  mappedBy = "tema": Indica qual Objeto será utilizado como "chave estrangeira" no relacionamento,
		 *  em nosso exemplo será o objeto tema inserido na Classe Postagem
		 * 
		 *  cascade = CascadeType.REMOVE: Indica que apenas a ação apagar um Objeto da Classe Tema,
		 *  se propagará para todos os respectivos objetos associados ao Objeto Tema apagado.
		 *  Exemplo: Se eu apagar um tema (Java), todas as postagens associadas ao tema apagado também serão apagadas.
		 * 
		 *  A Anotação @JsonIgnoreProperties("postagem") desabilita a recursividade
		 *  infinita durante a exibição dos dados no formato JSON
		 *  
		 *  private List<Postagem> postagem: Collection do tipo List composta por Objetos do tipo Postagem 
		 *  que listará todas as postagens associadas com os respectivos temas.
		 * 
		 *  Não esqueça de criar os métodos getters e setters para a Collection postagem.
		 * 
		 */
		@OneToMany(mappedBy = "tema", cascade = CascadeType.REMOVE)
		@JsonIgnoreProperties("tema")
		private List<Postagem> postagem;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		/**
		 *  Métodos Get e Set para a Collection postagem
		 */
		public List<Postagem> getPostagem() {
			return postagem;
		}

		public void setPostagem(List<Postagem> postagem) {
			this.postagem = postagem;
		}
		
}