# ImobSys - Sistema Corporativo de Gestão de Locação de Imóveis

Projeto avaliativo desenvolvido para a disciplina de **Desenvolvimento de Sistemas Corporativos** do Curso de Tecnologia em Sistemas para Internet (UERN - DEAD), sob orientação do Prof. Francisco Chagas de Lima Júnior.

---

## Como Executar o Projeto Rapidamente (Guia Direto para Correção)

* **Arquivo Principal de Execução (Main):**
  * **Caminho na pasta do projeto:** `src/main/java/br/com/imobsys/ImobsysApplication.java` (ou `imobsys/src/main/java/br/com/imobsys/ImobsysApplication.java`)
  * **Localização na árvore do Eclipse (Package/Project Explorer):** Expanda a pasta `src/main/java` -> pacote `br.com.imobsys` -> clique com o botão direito em **`ImobsysApplication.java`** -> **Run As** -> **Java Application**.
* **URL de Acesso no Navegador:** `http://localhost:8080/login.html`
* **Login Administrador (Acesso Total):** Usuário `admin` | Senha `admin123`
* **Login Operador (Acesso Restrito):** Usuário `usuario` | Senha `123456`

---

## 1. Cenário da Empresa Fictícia e Arquitetura MVC (Etapa 1)

A **ImobSys Locações** é uma empresa fictícia que atua na administração de imóveis residenciais e comerciais, necessitando de um sistema web corporativo para gerenciar contratos de aluguel, inquilinos, disponibilidade de imóveis e geração de relatórios financeiros.

O sistema foi implementado em Java utilizando o framework **Spring Boot** sob o padrão arquitetural **MVC (Model-View-Controller)** dividido em três camadas:

* **Camada de Visão (Aplicação / Front-end):** Construída com HTML5, CSS3 (layout minimalista) e JavaScript puro (Fetch API). Realiza requisições HTTP assíncronas consumindo e enviando dados em formato JSON para a API REST.
* **Camada de Controle e Negócios (Controller / Service):** Implementada com `@RestController`, `@Service` e `@WebFilter`. Gerencia as rotas da API REST, as regras de negócio de autenticação com criptografia `BCrypt`, o controle de sessão (`HttpSession`) e a autorização de rotas por perfil (`ADMIN` e `USER`).
* **Camada de Modelo e Persistência (Model / Repository):** Implementada com entidades JPA (`@Entity`) e interfaces `JpaRepository` do Spring Data JPA, realizando o mapeamento objeto-relacional (ORM) e a persistência automática no banco de dados **MariaDB**.

---

## 2. Estrutura do Projeto (Etapa 2)

O código-fonte organiza-se nos seguintes pacotes e diretórios:

    imobsys/
    ├── src/main/java/br/com/imobsys/
    │   ├── ImobsysApplication.java       # <--- ARQUIVO PRINCIPAL PARA EXECUTAR O SISTEMA
    │   ├── model/
    │   │   ├── User.java                 # Entidade JPA de Usuários (tabela users)
    │   │   └── Locacao.java              # Entidade JPA de Locações (tabela locacoes)
    │   ├── repository/
    │   │   ├── UserRepository.java       # Interface Spring Data JPA para Usuários
    │   │   └── LocacaoRepository.java    # Interface Spring Data JPA para Locações
    │   ├── service/
    │   │   └── AuthService.java          # Lógica de login (BCrypt) e carga inicial automática
    │   └── controller/
    │       ├── AuthController.java       # Endpoints REST de login e logout (/auth)
    │       ├── AuthFilter.java           # Filtro Web (@WebFilter) de proteção por perfil
    │       ├── UserController.java       # Endpoints REST de usuários (/users)
    │       └── LocacaoController.java    # Endpoints REST de locações e filtros (/locacoes)
    ├── src/main/resources/
    │   ├── application.properties        # Configurações de conexão com MariaDB e Hibernate
    │   └── static/                       # Front-end (HTML5, CSS3 e JavaScript)
    │       ├── style.css
    │       ├── login.html
    │       ├── index.html
    │       ├── cadastrarLocacao.html
    │       ├── listarLocacoes.html
    │       ├── relatorioLocacoes.html
    │       ├── cadastrarUsuario.html
    │       └── listarUsuarios.html
    └── pom.xml                           # Gestão de dependências Maven

---

## 3. Transações da Empresa e Caso de Uso (Etapa 3)

### Principais Transações e Funcionalidades
1. **Autenticação e Autorização:** Login validado com hash BCrypt e filtro de sessão (`AuthFilter`) que restringe páginas administrativas exclusivamente ao perfil `ADMIN`, permitindo ao perfil `USER` apenas consultas e relatórios.
2. **Transações da Empresa:** Cadastro e exclusão de contratos de locação de imóveis e cadastro de novos usuários do sistema.
3. **Consultas:** Listagem dinâmica de locações com filtro por status (`ATIVO`, `DISPONIVEL`, `PENDENTE`) e busca textual em tempo real por endereço ou inquilino.
4. **Relatórios:** Relatório gerencial consolidado apresentando o total de imóveis, contratos ativos, imóveis vagos, somatório da receita mensal ativa e opção de impressão/exportação em PDF.

### Especificação de Caso de Uso: Cadastrar Locação de Imóvel
* **Ator Principal:** Usuário Administrador (`ADMIN`).
* **Pré-condição:** O usuário deve estar autenticado na sessão com o papel `ADMIN`.
* **Fluxo Principal:**
  1. O administrador acessa a página `cadastrarLocacao.html` através do painel principal.
  2. O administrador preenche os dados do imóvel (endereço, tipo de imóvel, nome do inquilino, valor mensal do aluguel e status do contrato) e aciona o botão "Salvar Locação".
  3. O front-end envia uma requisição assíncrona `POST /locacoes/add` com os dados serializados em JSON.
  4. O `LocacaoController` recebe o objeto e aciona o `LocacaoRepository` para persistir o registro no MariaDB.
  5. O sistema retorna status HTTP 200 (OK) e redireciona o usuário para a tela de consulta atualizada (`listarLocacoes.html`).

---

## 4. Instruções Detalhadas de Execução (Etapas 4 e 5)

### Ambiente Necessário
* **Java JDK 21**
* **IDE Eclipse** (com suporte a Maven)
* **Banco de Dados MariaDB** em execução

### Configuração Automática do Banco de Dados (Zero Configuração Manual)
O sistema está configurado com os parâmetros `createDatabaseIfNotExist=true` e `spring.jpa.hibernate.ddl-auto=update`, além de uma rotina `@PostConstruct` na classe `AuthService`. Isso significa que **não é necessário rodar scripts SQL manuais**: ao iniciar a aplicação pela primeira vez, o Spring Boot criará automaticamente o banco `imobsys`, as tabelas `users` e `locacoes`, os usuários de teste e 3 registros iniciais de imóveis.

* **Atenção à porta do MariaDB:** Por padrão, o arquivo `src/main/resources/application.properties` está configurado para a porta **`3306`** (`jdbc:mariadb://localhost:3306/imobsys...`) com usuário `root` e senha vazia. Caso o servidor MariaDB local utilize a porta **`3307`** (como no exemplo da disciplina) ou possua senha definida, basta ajustar esses valores no arquivo `application.properties` antes de iniciar.

### Passo a Passo para Importar e Rodar no Eclipse
1. Clone o repositório na máquina:

    git clone https://github.com/AleffPaulo/imobsys-spring.git

2. No **Eclipse IDE**, acesse **File -> Import... -> Maven -> Existing Maven Projects**, clique em **Browse...**, selecione a pasta clonada onde consta o arquivo `pom.xml` e clique em **Finish**.
3. No painel esquerdo do Eclipse, navegue até o caminho:
   `src/main/java` -> `br.com.imobsys` -> **`ImobsysApplication.java`**
   *(Caminho relativo do arquivo: `src/main/java/br/com/imobsys/ImobsysApplication.java`)*.
4. Clique com o botão direito sobre **`ImobsysApplication.java`** e selecione **Run As -> Java Application**.
5. Abra o navegador e acesse:

    http://localhost:8080/login.html

---

## 5. Endpoints da API REST

* `POST /auth/login` — Autentica o usuário e inicia a sessão HTTP.
* `GET /auth/logout` — Encerra a sessão HTTP e redireciona para a tela de login.
* `GET /locacoes/list?status={status}` — Retorna a lista de locações em JSON (permite filtrar por status).
* `POST /locacoes/add` — Cadastra uma nova locação no banco de dados.
* `DELETE /locacoes/delete/{id}` — Remove uma locação pelo ID.
* `GET /users/list` — Lista todos os usuários cadastrados (ocultando o hash de senha via `@JsonProperty`).
* `POST /users/add` — Cadastra um novo usuário criptografando a senha com BCrypt.
