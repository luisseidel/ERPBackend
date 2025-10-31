# ERPBackend

* Sistema CRUD básico, feito com Spring e Thymeleaf
* Permite o cadastro de Usuários e Endereços e acesso ao sistema.
* Também possui uma API Restful, com os métodos GET, POST, PUT e DELETE.


## Tecnologias utilizadas

* Java 24
* Spring Web
* Spring Data
* Spring Security
* JWT
* Flyway
* Postgres SQL
* Swagger UI
* Thymeleaf
* JUnit & Mockito
* RabbitMQ 
* Redis
* Mailhog

## Swagger Docs:
http://localhost:8080/swagger-ui/index.html

## To-do

- [x] Criar CRUD Paises
- [x] Criar CRUD Estados
- [x] Criar CRUD Menus
- [x] Criar cache para menus
- [x] Criar CRUD Cidades
- [x] Criar CRUD Usuarios
- [x] Criar CRUD Roles
- [x] Criar CRUD Permissões
- [x] Criar CRUD de Permissões que são aplicáveis para Roles
- [x] Criar cache com Redis para itens comumente acessados, como menus
- [x] Criar tratamento de erros padrão
- [x] Criar exibição padrão de erros
- [x] Criar Testes Unitários
- [x] Criar serviço/processamento agendado que poderá ser cadastrado em banco de dados pelo usuário
- [x] Expandir o serviço/processamento para enviar e-mails de verdade
- [x] Criar algum serviço/processamento que possa ser assíncrono
- [x] Usar RabbitMQ ou kafka para poder disparar algum serviço de maneira assíncrona
- [x] Prometheus e Grafana
- [] Criar gerador de pdf com itext
- [] Gerar PDF/Relatorios de maneira assíncrona usando as filas
- [] Enviar o pdf/relatorios por e-mail após conclusão da task assíncrona
- [] Criar pipeline CI/CD
- [] Trocar autenticação para keycloak
