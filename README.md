<h1>Projeto Blog Pessoal (CRUD) - Parte 01</h1>

<br />

<h2>Passo 01 - Setup do Projeto</h2>

<br />

<h3>Configurações iniciais</h3>

<br />

<div align="center"><img src="https://i.imgur.com/AqSAFhu.png" title="source: imgur.com" /></div>

<br /><br />

| Item             | Descrição                                                    |
| ---------------- | ------------------------------------------------------------ |
| **Name**         | Nome do Projeto (Geralmente em letras minúsculas)            |
| **Type**         | Define o Gerenciador de Dependências (Maven)                 |
| **Packaging**    | Define como a aplicação será empacotada (JAR)                |
| **Java Version** | Versão do Java                                               |
| **Language**     | Linguagem (Java)                                             |
| **Group**        | O domínio reverso de sua empresa ou organização. **Exemplo:** *generation.com* -> ***com.generation*** |
| **Artifact**     | O artefato a ser gerado, ou seja, o nome da aplicação que será criada (Mesmo nome do projeto) |
| **Version**      | Versão da API (não alterar)                                  |
| **Description**  | Descrição do projeto                                         |
| **Package**      | Estrutura do pacote inicial da aplicação (Group + Artifact). Exemplo: ***<u>com.generation.blogpessoal</u>*** |

<br /><br />

<h3>Dependências</h3>

<br />

No projeto Blog Pessoal vamos inserir 5 dependências, conforme mostra a figura abaixo:

<br />

<div align="center"><img src="https://i.imgur.com/NHWltOT.png" title="source: imgur.com" /></div>



<br />



| Dependência               | Descrição                                                    |
| ------------------------- | ------------------------------------------------------------ |
| **Spring Web**            | Fornece todas as Bibliotecas necessárias para trabalhar com o protocolo http. |
| **Spring Boot Dev Tools** | Permite a atualização do projeto em tempo real durante o processo de Desenvolvimento da aplicação. |
| **Validation**            | Fornece um conjunto de anotações que permite validar os atributos das Classes da Camada Model. |
| **Spring Data JPA**       | Java Persistence API é uma biblioteca que armazena e recupera objetos que são armazenados em bancos de dados. |
| **MySQL Driver**          | Responsável pela conexão entre nossa API e o Banco de Dados MySQL. |

<br />

Clique no botão **Finish** para concluir.

<br />

<div align="center"> <h2>*** IMPORTANTE ***</h2></div>

Caso o projeto apresente algum erro no arquivo pom.xml, utilize o <b>Guia de correção do arquivo pom.xml</b> para corrigir o problema.

<br />


<h2>Passo 02 - Configuração do Banco de Dados</h2>

<br />

Inserir as linhas abaixo no arquivo **application.properties**, localizado em **src/main/resources**.

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database=mysql
spring.datasource.url=jdbc:mysql://localhost/db_blogpessoal?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo&useSSl=false
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect

spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=Brazil/East
```

<br />

| Item                                        | Descrição                                                    |
| ------------------------------------------- | ------------------------------------------------------------ |
| **spring.jpa.hibernate.ddl-auto**           | Define como o JPA irá gerenciar o Banco de dados: **Update** ⇨ atualiza a estrutura do banco de dados, exceto remover atributos ou tabelas, e mantém os dados persistidos. |
| **spring.jpa.database**                     | Define o Banco de dados que será utilizado (MySQL)           |
| **spring.datasource.url**                   | Define o nome do Banco (db_blogpessoal), a criação automática do banco de dados no MySQL (caso não exista), o fuso horario do servidor MySQL e desabilita a camada de segurança da conexão com o MySQL (SSl) |
| **spring.datasource.username**              | Define o usuário do MySQL                                    |
| **spring.datasource.password**              | Define a senha do usuário do MySQL                           |
| **spring.jpa.show-sql**                     | Exibe todas as Queries SQL no Console do STS                 |
| **spring.jpa.properties.hibernate.dialect** | Configura a versão do MySQL, em nosso projeto versão 8.      |
| **spring.jackson.date-format**              | Configura o formato da Data e da Hora da aplicação           |
| **spring.jackson.time-zone**                | Configura o fuso horario da aplicação                        |

<br />

<div align="center"> <h2>*** ⚠ IMPORTANTE ***</h2></div>

Caso a senha do seu MySQL não seja **root**, atualize a linha: **spring.datasource.password** inserindo a senha que você cadastrou no seu **MySQL** durante a instalação. 

Caso você tenha esquecido a senha do seu usuário root do MySQL, consulte o <b>Guia de desinstalação do MySQL </b> e siga as instruções.

<br />

<h2>Passo 04 - Próximas Etapas...</h2>

<br />


- [x] Criar a Camada Model
- [x] Criar a Camada Repository
- [x] Criar a Camada Controller



<h2>Referências para consulta</h2>



<a href="https://spring.io/" target="_blank">Documentação Oficial do Spring</a>

<a href="https://maven.apache.org/" target="_blank">Documentação Oficial do Maven</a>

<a href="https://mvnrepository.com/" target="_blank">Repositório do Maven</a>

<a href="https://blog.algaworks.com/injecao-de-dependencias-spring/" target="_blank">Injeção de Dependências</a>
