package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_postagens") 
public class Postagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 

	@NotBlank(message = "O atributo título é Obrigatório e não pode utilizar espaços em branco!") 
	@Size(min = 5, max = 100, message = "O atributo título deve conter no mínimo 05 e no máximo 100 caracteres")
	private String titulo; 

	@NotNull(message = "O atributo texto é Obrigatório!")
	@Size(min = 10, max = 1000, message = "O atributo texto deve conter no mínimo 10 e no máximo 500 caracteres")
	private String texto;

	@UpdateTimestamp
	private LocalDateTime data;

	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;

	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

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

	public Tema getTema() {
		return this.tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
