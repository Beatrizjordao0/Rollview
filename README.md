# Rollview

Aplicativo Android para descobrir, pesquisar, favoritar e avaliar filmes.

O projeto utiliza Java, Firebase e a API do The Movie Database (TMDB).

## Funcionalidades

- cadastro e autenticação de usuários;
- pesquisa de filmes;
- detalhes, elenco, direção e trailers;
- favoritos e avaliações;
- perfil do usuário;
- persistência de dados com Firebase.

## Tecnologias

- Java
- Android SDK
- Firebase Authentication e Firestore
- Retrofit e Gson
- Glide
- TMDB API

## Configuração

1. Abra o projeto no Android Studio.
2. Use uma JDK compatível com a versão do Android Gradle Plugin configurada no projeto.
3. Crie uma chave na TMDB e forneça-a pela variável de ambiente `TMDB_API_KEY`.

Linux/macOS:

```bash
export TMDB_API_KEY="sua-chave"
./gradlew assembleDebug
```

PowerShell:

```powershell
$env:TMDB_API_KEY="sua-chave"
.\gradlew.bat assembleDebug
```

A chave é inserida em `BuildConfig.TMDB_API_KEY` durante o build e não deve ser gravada no código.

## Firebase

O arquivo `google-services.json` contém identificadores de cliente necessários à configuração do aplicativo. A segurança dos dados depende das regras do Firebase, autenticação e restrições configuradas no console. Nunca adicione contas de serviço ou chaves privadas ao repositório.

## Estrutura

- `app/src/main/java`: Activities, adapters e modelos;
- `app/src/main/res`: layouts, drawables e demais recursos Android;
- `app/google-services.json`: configuração do cliente Firebase;
- `app/build.gradle.kts`: dependências e configuração do aplicativo.

## Estado

Projeto acadêmico funcional em evolução. Antes de publicar uma versão de produção, revise as regras do Firebase, restrinja as chaves de API e substitua os testes de exemplo por testes das funções reais.
