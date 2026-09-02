# LostPetHub - Sistema de Cadastro e Localização de Animais Perdidos

Projeto desenvolvido para a disciplina de **Programação com Acesso a Banco de Dados** do IFRN, seguindo os princípios de **Engenharia de Software**, **Separação de Responsabilidades (SoC)** e arquitetura em **3 Camadas (Modelo, Repositório e Serviço)** com persistência em banco de dados relacional **MySQL**.

---

## 🎯 Contextualização do Projeto

O **LostPetHub** é uma plataforma comunitária criada para auxiliar tutores e voluntários a encontrarem animais de estimação desaparecidos. O sistema permite o cadastro de tutores, registro de animais, sinalização de desaparecimentos e lançamento de relatos/avistamentos com geolocalização e referências para acelerar o reencontro.

---

## 🏛️ Arquitetura do Software

O projeto segue a separação estrita de camadas e o ciclo de vida padrão do desenvolvimento:

```
lostpethub/
│
├── docs/
│   ├── requisitos/
│   │   ├── RF.001.md       # Cadastro de Tutores / Responsáveis
│   │   ├── RF.002.md       # Cadastro de Animais de Estimação (Pets)
│   │   ├── RF.003.md       # Gerenciamento de Status e Desaparecimento
│   │   └── RF.004.md       # Registro e Consulta de Avistamentos
│   └── schema_lostpethub.sql # Script DDL para criação do Banco MySQL
│
├── lostpethub/
│   ├── pom.xml             # Dependências Maven (MySQL Connector/J)
│   └── src/main/java/br/edu/ifrn/
│       ├── Main.java       # Execução e Demonstração do Fluxo Completo
│       └── lostpethub/
│           ├── modelo/         # POJOs e Enums (Tutor, Pet, StatusPet, Avistamento)
│           ├── repositorio/    # Camada DML / JDBC (GerenciadorDeConexao, Repositorios)
│           └── servico/        # Regras de Negócio e Critérios de Aceitação
│
└── .gitignore
```

### 1. Modelo (`br.edu.ifrn.lostpethub.modelo`)
- **Tutor.java**: Representa os dados dos tutores responsáveis.
- **Pet.java**: Representa os animais cadastrados.
- **StatusPet.java**: Enum com os estados `COM_TUTOR`, `PERDIDO`, `ENCONTRADO`, `RESGATADO`.
- **Avistamento.java**: Representa os relatos de localização de animais desaparecidos.

### 2. Repositório (`br.edu.ifrn.lostpethub.repositorio`)
- **GerenciadorDeConexao.java**: Gerencia a conexão com o banco MySQL `lostpethub_db`.
- **TutorRepositorio.java**: Operações CRUD e busca por e-mail no MySQL.
- **PetRepositorio.java**: Operações CRUD, busca por tutor e filtros por status e espécie.
- **AvistamentoRepositorio.java**: Operações CRUD e consulta cronológica de avistamentos.

### 3. Serviço (`br.edu.ifrn.lostpethub.servico`)
- **TutorService.java**: Validações de e-mail único, formato e preenchimento obrigatório.
- **PetService.java**: Validação de vínculo com tutor existente, definição de status padrão e alertas de desaparecimento.
- **AvistamentoService.java**: Regra que permite avistamento apenas para pets com status `PERDIDO` e emissão de notificações.

---

## 🗄️ Configuração do Banco de Dados (MySQL Workbench)

1. Abra o **MySQL Workbench** e conecte-se ao seu servidor local (`localhost:3306`).
2. Abra e execute o script [`docs/schema_lostpethub.sql`](docs/schema_lostpethub.sql).
3. Verifique se o banco `lostpethub_db` e as tabelas `tutor`, `pet` e `avistamento` foram criadas.

---

## 🚀 Como Executar o Projeto

1. Abra o projeto no VS Code ou IDE de sua preferência.
2. Certifique-se de configurar as credenciais do seu MySQL no arquivo [`GerenciadorDeConexao.java`](lostpethub/src/main/java/br/edu/ifrn/lostpethub/repositorio/GerenciadorDeConexao.java).
3. Execute a classe [`Main.java`](lostpethub/src/main/java/br/edu/ifrn/Main.java) para verificar todos os fluxos e regras em funcionamento.

---

## 🔄 Ciclo de Sanidade do Git

```bash
# 1. Adicionar os arquivos alterados
git add .

# 2. Criar o ponto de evolução local (Commit)
git commit -m "Estruturação inicial do projeto LostPetHub com arquitetura em 3 camadas e MySQL"

# 3. Publicar no GitHub
git push
```