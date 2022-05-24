
# Shortener URL

O objetivo da API é pegar uma URL grande e retornar uma outra curta com menos caracteres.

- Quando cadastrar uma URL longa, irá retornar uma outra URL com menos caracteres.
- Ao acessar essa URL curta que foi gerada, você será redirecionado para a URL original.
- É possível verificar quantas vezes a URL foi acessada.
- Toda URL que estiver com mais 90 dias sem acesso, será automaticamente deletada.


## Documentação da API

#### Cadastrar uma URL.

```http
  POST /me.li
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `url` | `string` | **Obrigatório**. A chave da url. |

#### Retorna um item

```http
  GET /me.li/${urlCurta}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `urlShort`      | `string` | **Obrigatório**. A urlShort do endereço que quer converter. |

#### Retorna estatísticas da URL

```http
  GET /me.li/${urlCurta}/statistics
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `urlShort`      | `string` | **Obrigatório**. A urlShort do endereço que quer ver as estatística. |

#### Retorna estatísticas de todas as URLs

```http
  GET /me.li/statistics
```


## Autores

- [@edersonrodara](https://github.com/edersonrodara)


## Instalação

Certifique-se de estar com o Git instalado.

Clone o  repositório

```bash
  git clone git@github.com:edersonrodara/EncurtarUrl.git
```

Abra o projeto com a IDE de sua preferência.

Na IDE, instale as dependências do pom.xml
Apos instalar, rodar a aplicação:
- Clicar com botão direito em ShortenerApplication em seguida clicar em Run...
