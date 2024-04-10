# Orientações Gerais: order-manager
Este projeto é uma API de controle de pedidos construído em Java 17 com Spring Boot e banco de dados MySQL. O código está localizado na branch "master".

*Observações:*
  - O projeto foi programado todo em inglês, com exceção dos DTOs e VOs de entrada de pedidos, para que o envio do arquivo JSON/XML possa ser todo em português;
    
  - De acordo com a conexão definida no arquivo "application.properties", o servidor de banco de dados MySQL deve estar hospedado localmente na porta padrão (3306) com usuário root e senha 123456 e deve ser criado o DataBase ORDER_MANAGER. Caso haja alguma alteração nessa estrutura, os dados deste arquivo properties devem ser alterados;
    
  - Deve ser executado o script encontrado em: https://github.com/vlucasp/order-manager/blob/master/database/script.sql ;
    
  - Todas as requisições devolvem um padrão de ResponseEntity que contém o status HTTP, mensagem negocial e, caso espere algum objeto, o body da requisição contendo o retorno, conforme exemplo abaixo:
    {
    	"status": 200,
    	"message": "Cliente foi alterado com sucesso.",
    	"body": {
    		"id": 3,
    		"name": "Pedro Alterado"
    	}
    }
    
  - Para fins de apoio, foi criado a camada REST de Cliente (Customer), onde é possível fazer consulta de todos, consulta por id, cadastrar novo cliente, alterar cliente e deletar cliente. As requisições a serem feitas são:
    - Consultar todos os Clientes: [GET] http://localhost:8080/customer
      
    - Consultar Cliente por ID: [GET] http://localhost:8080/customer/{id}
      
    - Cadastrar Cliente: [POST] http://localhost:8080/customer que recebe um body JSON
      {
       	"name": "Pedro"
      }
      
    - Atualizar Cliente: [PUT] http://localhost:8080/customer que recebe um body JSON
      {
        "id": 3,
       	"name": "Pedro Alterado"
      }
      
    - Excluir Cliente por ID: [DELETE] http://localhost:8080/customer/{id}
      
- A camada REST de Pedidos (Order) possui dois endpoints:
  - Consultar pedidos por filtro: [GET] http://localhost:8080/order que recebe um body JSON:
    {
    	"numero_pedido": "0001AB",
    	"data_cadastro": "10/04/2024"
    }
    Sendo que ambos podem estar nulos ou serem populados. O campo de data deve estar no formato DD/MM/AAAA.
    Os objetos a serem recebidos chegam da seguinte forma:
    {
			"numero_controle": "00010A",
			"data_cadastro": "09/04/2024",
			"nome": "Pen Drive",
			"valor": 25.00,
			"quantidade": 10,
			"codigo_cliente": 7,
			"valor_total": 225.00
		}
    
  - Criar pedidos: [POST] http://localhost:8080/order que pode receber um body JSON:
    {
    	"pedidos": [
    		{
    			"numero_controle": "00012A",
    			"nome": "SSD",
    			"valor": 250,
    			"codigo_cliente": 8
    		},
    		{
    			"numero_controle": "00013A",
    			"data_cadastro": "01/03/2024",
    			"nome": "HUB",
    			"valor": 129.99,
    			"quantidade": 5,
    			"codigo_cliente": 4
    		}
    	]
    }
    
    Ou pode receber um body XML:
    <pedidos>
        <pedido>
            <numero_controle>00010A</numero_controle>
            <nome>Pen Drive</nome>
            <valor>25</valor>
            <quantidade>10</quantidade>
            <codigo_cliente>7</codigo_cliente>
        </pedido>
        <pedido>
            <numero_controle>00011A</numero_controle>
            <data_cadastro>01/03/2024</data_cadastro>
            <nome>Impressora</nome>
            <valor>1299.99</valor>
            <quantidade>2</quantidade>
            <codigo_cliente>3</codigo_cliente>
        </pedido>
    </pedidos>
    
    Sendo que para cada pedido o campo "data_cadastro" e "quantidade" não são obrigatórios. O campo "numero_controle" não pode ser o mesmo de algum já cadastrado e "data_cadastro" deve estar no formato DD/MM/AAAA.

*Sugestões de Melhorias:*
	- Seria interessante criar um cadastro de produto, que envolveria uma validação no cadastro de pedidos onde só deveria ser feito caso exista um produto com o id enviado. Poderia também possuir um controle de quantidades para fins de controle de estoque;
 	- Criar um swagger para melhor visualização e testes das chamadas REST;
  	- Criação de testes unitários, que, apesar de serem trabalhosos por conta da arquitetura utilizada, garantem funcionamento das funcionalidades criadas;
   	- Alteração dos outros endpoints para receberem body XML.
