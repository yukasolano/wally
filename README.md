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
- tabela de movimentação (ok)
- exportar movimentoacaç (ok)

### CALCULOS
- Adicionar tratamento de venda
- Agrupar DIVIDENDO, ATUALIZACAO e JCP
- calculo do imposto de renda
- calculo do titulo publico
- tratamento para fundos
- usar cache para nao precisar recalcular toda vez
- fluxos de debenture

- Refatorar tratamento de produtos.
	Juntar renda fixa e renda variável (tudo será movimentação: compra, dividendo, amortização, etc). (ok)
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


