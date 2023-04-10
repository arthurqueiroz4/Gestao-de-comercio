# Gestão de Comércio

<h2>Documentação Back-end</h2>

//descricação do projeto

<h4> PRODUTO - Documentação da api:</h4>

- Cadastro de produtos: (POST - /api/produtos))

    - Funcionalidade: ao criar um produto com um código de barras que já existe, é retornado uma exception com código 400.

    - Corpo do json:

        - ```json 
            {
            "codBarras":"123496789",     
            "descricao":"Biscoito Bono Sabor Chocolate 200g" 
            } 
          ```    

    - Retorno: ```201 - Created```.

- Busca por produtos: (GET - /api/produtos)
    - Funcionalidade: retorna todos os produtos cujo a descrição contém "Biscoito ".
    - Corpo do json:
        - ```json
          {
          "descricao":"Biscoito "
          }
          ```
    - Retorno: ```200 - OK```.

- Atualização do nome do produto: (PATCH - /api/produtos/nomeProduto)
    - Corpo do json:

        - ```json
            {
              "novoNome":"Biscoito Bono Sabor Chocolate 350g",
              "codigoBarras":"123496789"
            }
          ```


- Retorno: ```204 - No Content```.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
  <h4> MERCADO - Documentação da api:</h4>

- Cadastro de usuario: (POST - /api/usuarios)

    - Funcionalidade: ao criar um produto com um código de barras que já existe, é retornado uma exception com código 400.

    - Corpo do json:
        - ```json
                {
                  "login":"java",
                  "cnpj":"0000000",
                  "senha":"123",
                  "admin":false
                }
          ```

- Retorno: ```201 - Created```

- Busca por usuário: (GET - /api/usuarios)
    - Funcionalidade: retorna um json com o login e o admin.

    - Corpo do json:
        - ```json
            {
            "login":"java"
            }
          ```

    - Retorno: ```200 - OK```.

- Deletar usuário: (DELETE- /api/usuarios/{login})

    - Funcionamento: ao passar o login (string) pelo path, ela é deletada no banco de dados.

    - Retorno: ```204 - No Content```

- Mudar senha: (PATCH -  /api/usuarios)
    - Corpo do json:
        - ```json
           {
             "login":"java",
             "cnpj":"0000000",
             "senha":"1234"
           }
           ```
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
 <h4> ESTOQUE - Documentação da api:</h4>

- Cadastro de estoque: (POST - /api/estoque)
    - Funcionamento: ao passar o json, é necessário que o nome do Mercado e o código de barras passados já tenha sido previamente cadastros, caso contrário, a api retornará ```404 - NOT FOUND```.
    - Corpo do json:
        - ```json
            {
              "codigoBarras":"123498709",
              "nomeMercado":"java",
              "quantidade":10,
              "precoUnitario":3.5
            }
          ```
    - Retorno: ```201 - Created```.
- Atualização do estoque: (PUT - /api/estoque)
  - Funcionamento: é necessário ter o atributo quantidade ou precoUnitario, mas não é necessário passar os dois ao mesmo tempo. É possível atualizar somente a quantidade ou somente o precoUnitario, basta passar o atributo que irá atualizar e o outro nulo ou não passar. Se o atributo codigoBarras ou nomeMercado não forem encontrados no banco de dados será retornado ```404 - NOT FOUND```.
  - Corpo json:
    - ```json
            {
                "codigoBarras":"123438700",
                "nomeMercado":"java2",
                "quantidade":1,
                "precoUnitario":null
            }
      ```
   - Retorno: ```200 - OK```
   
- Listar o estoque de um Mercado: (GET - /api/estoque)
  - Funcionamento: será retornado sempre uma lista, se o Mercado passado tiver estoque cadastrado, então a lista estará composta pelos estoques encontrados.
  - Corpo json:
    - ```json
            {
                "login":"java03"
            }
      ```
  - Retorno ```200 - OK```.
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
<h4>Organizaçao e relação das tabelas no banco de dados</h4>
<img src="https://user-images.githubusercontent.com/110779984/230939378-070fee41-1ddc-46bf-ba80-f5169aa99c40.png">

