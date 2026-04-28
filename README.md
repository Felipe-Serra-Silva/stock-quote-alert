# Stock Quote Alert

Aplicação de console que monitora cotações de ações da B3 e envia alertas por e-mail quando os preços ultrapassam limites definidos.

## Sobre o Projeto

Esta aplicação monitora continuamente um ativo da B3 e envia alertas por e-mail quando:
- O preço sobe **acima** do limite de venda definido
- O preço cai **abaixo** do limite de compra definido

## Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Build:** Maven
- **HTTP Client:** OkHttp
- **Parser JSON:** Gson
- **E-mail:** Jakarta Mail (Angus Mail 2.0.4)
- **API de Cotações:** Alpha Vantage (endpoint Global Quote)

## Como Executar

### Pré-requisitos
- Java 17+
- Maven

### Configuração

Crie o arquivo `src/main/resources/config.properties`:

```properties
api.key=SUA_CHAVE_ALPHA_VANTAGE
email.recipient=destino@gmail.com
smtp.host=smtp.gmail.com
smtp.port=587
smtp.user=seu-email@gmail.com
smtp.password=sua-senha-de-app
```

### Executando

```bash
mvn compile
java -cp target/classes com.felipesilva.Main <ativo> <precoVenda> <precoCompra>
```

**Exemplo:**
```bash
java -cp target/classes com.felipesilva.Main PETR4 22.67 22.59
```

O programa ficará rodando e verificará o preço a cada 60 segundos, enviando um e-mail sempre que o preço ultrapassar algum dos limites.

## Escolha da API

Este projeto utiliza a **Alpha Vantage** como fonte de dados de cotações. Ela exige uma API key gratuita e suporta ativos brasileiros da B3 usando o sufixo `.SAO` (ex: `PETR4.SAO`).

Uma alternativa totalmente gratuita e sem autenticação é a **[Brapi.dev](https://brapi.dev)**, feita especificamente para ativos da B3 e com respostas JSON simples. A Alpha Vantage foi escolhida para demonstrar o uso de autenticação via API key e o gerenciamento de configurações sensíveis.

## Arquitetura

```
Main (argumentos CLI)
  └── AppConfig        # Carrega o config.properties
  └── StockService     # Busca o preço na API Alpha Vantage
  └── EmailService     # Envia e-mail via SMTP
  └── AlertMonitor     # Loop de monitoramento com ScheduledExecutorService
```

## Uso de IA

Este projeto foi desenvolvido com o auxílio do **JetBrains AI Assistant** como ferramenta de facilitação ao longo do desenvolvimento. A IA auxiliou em:
- Acelerar processos repetitivos
- Revisar o código e apontar warnings e boas práticas
- Sugerir a estrutura de pacotes e organização do projeto

Todas as decisões técnicas, escrita do código e resolução de problemas foram feitas pelo desenvolvedor. O maior desafio pessoal foi entender o fluxo de envio de e-mail — como `Session`, `MimeMessage` e `Transport` funcionam juntos no Jakarta Mail.