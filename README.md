
<h1>Documentação da API com o Swagger 3.0</h1>



Nesta atividade iremos implementar a Documentação da nossa API com o Swagger. 

## Boas Práticas

1. <a href="#dep">Configurar o arquivo pom.xml</a>
2. <a href="#approp">Configurar o arquivo application.properties</a>
3. <a href="#swg">Criar a Classe SwaggerConfig na Camada Configuration</a>
4. <a href="#clsuser">Atualizar a Classe Usuario na Camada Model</a>
5. <a href="#exec">Executar o Swagger</a>

<h2 id="dep">#Passo 1 - Adicionar a Dependência</h2>

Configurando o pom.xml

Insira a seguinte dependência no projeto:
```xml
 <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.6.3</version>
</dependency>
```



<h2 id="approp">#Passo 2 - Configurar o SpringDocs</h2>


Insira as linhas abaixo no arquivo **application.properties** na Source Folder **src/main/resources**:

```properties
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.disable-swagger-default-url=true
springdoc.packagesToScan=com.generation.blogpessoal.controller
springdoc.swagger-ui.use-root-path=true
```

<h2 id="swg">#Passo 3 - Classe SwaggerConfig</h2>

Crie uma nova package no seu projeto chamada **configuration** , dentro dela crie uma classe chamada SwaggerConfig e configure segundo o  modelo abaixo:

```java
package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI springBlogPessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("Projeto Blog Pessoal")
					.description("Projeto Blog Pessoal - Generation Brasil")
					.version("v0.0.1")
				.license(new License()
					.name("Generation Brasil")
					.url("https://brazil.generation.org/"))
				.contact(new Contact()
					.name("Conteudo Generation")
					.url("https://github.com/conteudoGeneration")
					.email("conteudogeneration@gmail.com")))
				.externalDocs(new ExternalDocumentation()
					.description("Github")
					.url("https://github.com/conteudoGeneration/"));
	}

	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {

		return new ApiResponse().description(message);

	}
	
}
```



<h2 id="clsuser">#Passo 04 - Alteração na Classe Usuario</h2>

<br />

Vamos configurar o atributo **usuario**, da **Classe Usuario**, para emitir um lembrete no Swagger de que deve ser digitado um endereço e-mail no valor do atributo. Para isso, utilizaremos a anotação **@Schema**.

Abra o arquivo **Usuario**, da **Camada Model**, localize o atributo **usuario** e altere de:

```java
@NotNull(message = "O atributo Usuário é Obrigatório!")
@Email(message = "O atributo Usuário deve ser um email válido!")
private String usuario;
```

Para:

```java
@Schema(example = "email@email.com.br")
@NotNull(message = "O atributo Usuário é Obrigatório!")
@Email(message = "O atributo Usuário deve ser um email válido!")
private String usuario;
```



<h2 id="exec">#Passo 5 - Executando o Swagger</h2>



1. Abra o seu navegador da Internet e digite o seguinte endereço para abrir a sua documentação: <a href="http://localhost:8080" ><b>http://localhost:8080</b></a>

2. Efetue login com uma conta de usuário antes de exibir a sua documentação no Swagger. Utilize o usuário em memória (root) para fazer o login.

<div align="center"><img src="https://i.imgur.com/mBRxYd8.png" title="source: imgur.com" width="50%"/></div>

3. Pronto! A sua documentação no Swagger será exibida.

<div align="center"><img src="https://i.imgur.com/vKKoa2c.png" title="source: imgur.com" /></div>



<h2 id="ref">Referências</h2>

<a href="https://springdoc.org/" target="_blank">Documentação Oficial do Spring Docs</a>

<a href="https://swagger.io/tools/swagger-ui/" target="_blank">Documentação Oficial do Swagger-UI</a>

<a href="https://spec.openapis.org/oas/latest.html#introduction" target="_blank">Documentação da Especificação OpenApi</a>
