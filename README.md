# Projeto Blog Pessoal

<br />

<div align="center">
    <img src="https://i.imgur.com/w8tTOuT.png" title="source: imgur.com" /> 
    <strong>trilha</strong>
    <img src="https://i.imgur.com/zUC0bts.png" title="source: imgur.com" />
</div>

<br /><br />

## Diagrama de Classes

```mermaid
classDiagram
class Tema {
  - id : Long
  - descricao : String
  - postagem : List ~Postagem~
  + getAll()
  + getById(Long id)
  + getByDescricao(String descricao)
  + postTema(Tema tema)
  + putTema(Tema tema)
  + deleteTema(Long id)
}
class Postagem {
  - id : Long
  - titulo : String
  - texto: String
  - data: LocalDateTime
  - tema : Tema
  - usuario : Usuario
  + getAll()
  + getById(Long id)
  + getByDescricao(String descricao)
  + postTema(Tema tema)
  + putTema(Tema tema)
  + deleteTema(Long id)
}
class Usuario {
  - id : Long
  - nome : String
  - usuario : String
  - senha : String
  - foto : String
  - postagem : List ~Postagem~
  + getAll()
  + getById(Long id)
  + autenticarUsuario(UsuarioLogin usuarioLogin)
  + cadastrarUsuario(Usuario usuario)
  + atualizarUsuario(Usuario usuario)
}
Tema --> Postagem
Usuario --> Postagem
```

<br /><br />

# Referências sobre Spring

<br />

<a href="https://spring.io/" target="_blank">Documentação Oficial do Spring</a>

<a href="https://maven.apache.org/" target="_blank">Documentação Oficial do Maven</a>

<a href="https://mvnrepository.com/" target="_blank">Repositório do Maven</a>

<a href="https://www.baeldung.com/" target="_blank">Baeldung</a>

<a href="https://blog.algaworks.com/" target="_blank">Blog da Algaworks</a>

<a href="https://www.michellibrito.com/" target="_blank">Michelli Brito</a>
