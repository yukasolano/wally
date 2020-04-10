# Wally
Sistema para controle de investimentos

Ferramentas utilizadas: Spring, MySQL5.7, Angular 8

Antes de rodar a aplicação, atualize o application.properties com as configurações do seu banco de dados.
```
db.name=wally
db.username=root
db.password=root
```

Acesse o servidor de banco de dados e crie o banco de dados que será utilizado na aplicação. 
```
mysql -u root -p 
create database wally;
```

## TODO


### FRONTEND:
- mvn com front-end
- subir aplicação com frontend	
- linha com total
- snackbar com mesnagens
- loading bar
- modularizar produtos

### CALCULOS
- Refatorar tratamento de produtos.
	Juntar renda fixa e renda variável (tudo será movimentação: compra, dividendo, amortização, etc). (ok)
	Importação de arquivo produto
	Tela de cadastro movimento 
	Importacao de arquivo movimento
	Tela de produtos
	Cadastro de produto ja adiciona movimentacao de compra
	Cadastro de movimento - se nao existe ignora
	Realizar mias testes para cadastro
	padronizar e segregar tratamento de adição de produto e movimento
- Adicionar tratamento de venda
- Agrupar DIVIDENDO, ATUALIZACAO e JCP
- calculo do imposto de renda
- calculo do titulo publico
- tratamento para fundos
- usar cache para nao precisar recalcular toda vez

- Adicionar estrutura de mapa para salvar resultado do portfolio para cada dia  (ok)
- Refatorar cálculo para FII (ok)
	Dividend Yield = somatório do valor unitário dos dividendos do último ano dividido pelo preço médio
- multidata (ok)

### DADOS DE MERCADO
- atualizar lista de dividendos passados 
- puxar preços tickers
- atualizar feriados
- atualizar valores de mercado de CDI e IPCA (ok)
- arrumar calculos usando nova estrutura de dados de mercado (CDI e IPCA) (ok)

### GRAFICOS
- grafico da evolução da rentabilidade (com dividend yield)


