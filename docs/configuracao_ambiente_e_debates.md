# Relatório de Configuração de Ambiente e Guia de Defesa Oral

**Disciplina:** Programação com Acesso a Banco de Dados / Engenharia de Software  
**Instituição:** Instituto Federal de Educação, Ciência e Tecnologia do Rio Grande do Norte (IFRN)  
**Aluno:** Cauã Araújo da Silva  
**Repositório:** [lostpethub.01](https://github.com/cauaaraujodasilvaaraujo-crypto/lostpethub.01.git)  

---

## 1. Parte 2: Atividade Prática de Configuração

### 1.1. Estrutura de Diretórios (`C:\dev`)
Para manter um ambiente de desenvolvimento limpo, padronizado e independente de privilégios administrativos no sistema operacional, a estrutura foi organizada da seguinte forma:

```
C:\dev\
├── tools\
│   ├── apache-maven-3.9.14\      # Apache Maven (gerenciador de dependências e build)
│   │   └── bin\
│   │       └── mvn.cmd
│   └── PortableGit\              # Git Portable (controle de versão e terminal Bash)
│       └── bin\
│           ├── git.exe
│           └── bash.exe
└── lostpethub.01\                # Repositório do projeto clonado localmente
    ├── docs/
    ├── lostpethub/
    └── README.md
```

### 1.2. Extensões no Visual Studio Code
Foi instalado o **Extension Pack for Java** (Microsoft), que inclui:
- **Language Support for Java™ by Red Hat**: Suporte à linguagem, intellisense e navegação de código.
- **Debugger for Java**: Depuração de código Java.
- **Test Runner for Java**: Execução de testes unitários.
- **Maven for Java**: Gerenciamento de projetos e dependências via Maven.
- **Project Manager for Java**: Visão geral dos projetos Java na árvore de arquivos.

### 1.3. Configuração do `settings.json` do VS Code
O arquivo de configurações do usuário (`%APPDATA%\Code\User\settings.json`) foi configurado mapeando os caminhos das ferramentas portáteis:

```json
{
  "maven.executable.path": "C:\\dev\\tools\\apache-maven-3.9.14\\bin\\mvn.cmd",
  "git.path": "C:\\dev\\tools\\PortableGit\\bin\\git.exe",
  "terminal.integrated.profiles.windows": {
    "Git Bash Portable": {
      "path": "C:\\dev\\tools\\PortableGit\\bin\\bash.exe",
      "icon": "terminal-bash"
    }
  },
  "terminal.integrated.defaultProfile.windows": "Git Bash Portable",
  "terminal.integrated.env.windows": {
    "PATH": "C:\\dev\\tools\\apache-maven-3.9.14\\bin;${env:PATH}",
    "MAVEN_HOME": "C:\\dev\\tools\\apache-maven-3.9.14"
  }
}
```

### 1.4. Identidade no Git e Clonagem
Configuração da identidade global do desenvolvedor:
```bash
git config --global user.name "Cauã Araújo da Silva"
git config --global user.email "cauaaraujodasilvaaraujo@gmail.com"
```

Clonagem do repositório no diretório `C:\dev`:
```bash
cd C:\dev
git clone https://github.com/cauaaraujodasilvaaraujo-crypto/lostpethub.01.git
```

---

## 2. Parte 3: Critério de Avaliação & Encontro de Debates

### 2.1. Tópico 1: O papel do diretório `C:\dev\tools` e a necessidade de mapear o `settings.json`

> **Para anotar no Caderno e apresentar oralmente:**

1. **Por que usar `C:\dev\tools`?**
   - Em computadores de laboratório institucional ou corporativo, o usuário comum geralmente **não possui privilégios de administrador** para executar instaladores padrão (`.msi` ou `.exe`) em `C:\Program Files`, nem permissão para alterar as variáveis de ambiente globais do sistema (`System PATH`).
   - O diretório `C:\dev\tools` funciona como um **repositório isolado de ferramentas portáteis (portables)**. Essas ferramentas funcionam sem necessidade de instalação no Registro do Windows, garantindo portabilidade, facilidade de atualização e independência entre diferentes perfis de usuários na mesma máquina.

2. **Por que mapear no `settings.json`?**
   - Como os utilitários estão contidos em pastas portáteis e não foram registrados no `PATH` global do Windows, o sistema operacional e os editores não conseguem localizá-los automaticamente apenas digitando seus nomes.
   - O arquivo `settings.json` do VS Code atua como uma **ponte de configuração a nível de usuário**: ele indica expressamente ao VS Code o caminho absoluto de cada executável (`git.path`, `maven.executable.path` e perfil do terminal). Dessa forma, a IDE passa a enxergar as ferramentas sem que seja necessária nenhuma alteração no Windows em nível administrativo.

---

### 2.2. Tópico 2: Como o VS Code se conecta ao Git e ao Maven instalados na máquina

> **Para anotar no Caderno e apresentar oralmente:**

1. **Conexão com o Git:**
   - O VS Code possui um subsistema nativo de controle de versão (Source Control / SCM). Ele busca o executável do Git na ordem: primeiro na chave `"git.path"` definida nas configurações do usuário/workspace; caso não exista, ele tenta encontrar o comando `git` no `PATH` do sistema.
   - Ao apontar `"git.path": "C:\\dev\\tools\\PortableGit\\bin\\git.exe"`, o VS Code passa a instanciar processos filhos em segundo plano (`git status`, `git diff`, `git log`, etc.) e renderiza o status dos arquivos diretamente na interface gráfica (ícone do Source Control, marcadores laterais de alteração e histórico de commits).

2. **Conexão com o Maven:**
   - A extensão **Maven for Java** lê a propriedade `"maven.executable.path": "C:\\dev\\tools\\apache-maven-3.9.14\\bin\\mvn.cmd"`.
   - Quando o projeto Java é aberto, a extensão usa esse executável para ler o `pom.xml`, baixar as dependências declaradas (como o `mysql-connector-j`), disparar compilações e executar os testes unitários.
   - Além disso, a configuração `"terminal.integrated.env.windows"` define:
     - `MAVEN_HOME`: aponta para o diretório raiz do Maven (`C:\dev\tools\apache-maven-3.9.14`).
     - `PATH`: adiciona `C:\dev\tools\apache-maven-3.9.14\bin` no início da variável `PATH` de cada terminal instanciado no VS Code, garantindo que o comando `mvn` fique disponível no terminal integrado.

3. **Terminal Integrado (Git Bash Portable):**
   - Com `"terminal.integrated.defaultProfile.windows": "Git Bash Portable"` e o caminho apontando para `bash.exe`, o VS Code abre diretamente o ambiente Unix/Bash, permitindo usar a sintaxe padrão de comandos Git e Linux (`ls`, `cat`, `curl`, `export`), simulando fielmente o ambiente de produção.

---

### 2.3. Tópico 3: Validação Prática no Terminal Integrado

Evidências de teste executadas com sucesso no terminal integrado do VS Code:

#### 1. Verificação da versão do Maven (`mvn -version`)
```text
Apache Maven 3.9.15 (98b2cdbfdb5f1ac8781f537ea9acccaed7922349)
Maven home: C:\dev\tools\apache-maven-3.9.14
Java version: 21.0.7, vendor: Amazon.com Inc., runtime: C:\Program Files\Amazon Corretto\jdk21.0.7_6
Default locale: pt_BR, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

#### 2. Verificação do Git e do Repositório (`git status`)
```text
On branch main
Your branch is up to date with 'origin/main'.

nothing to commit, working tree clean
```

#### 3. Verificação da Identidade Global do Git (`git config --global --list`)
```text
user.name=Cauã Araújo da Silva
user.email=cauaaraujodasilvaaraujo@gmail.com
```

---

## 3. Resumo Rápido para a Apresentação Oral com o Professor

| Pergunta do Professor | Resposta Direta e Objetiva |
| :--- | :--- |
| **Qual é o papel do diretório `C:\dev\tools`?** | Centralizar binários portáteis (como Maven e Git) em computadores de laboratório onde o aluno não tem acesso de administrador, permitindo trabalhar sem modificar os arquivos de sistema da máquina. |
| **Por que foi necessário configurar o `settings.json`?** | Porque as ferramentas portáteis não estão registradas no `PATH` global do Windows. O `settings.json` informa ao VS Code o caminho exato de onde encontrar o `git.exe`, o `mvn.cmd` e o `bash.exe`. |
| **Como o VS Code executa o Git?** | O VS Code utiliza a chave `"git.path"` para rodar o `git.exe` em segundo plano, alimentando a aba de Source Control e os comandos de commit/push/pull. |
| **Como o VS Code executa o Maven?** | A extensão Maven for Java utiliza `"maven.executable.path"` para ler o `pom.xml` e compilar o projeto; as variáveis adicionadas em `"terminal.integrated.env.windows"` injetam o `mvn` no terminal integrado. |
