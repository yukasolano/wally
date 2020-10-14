# Wally
Sistema para controle de investimentos

Ferramentas utilizadas: Maven, Spring, MySQL5.7, Angular 8

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
- linha com total
- modularizar frontend
- organizar detalhes... formatação tabela
- separar tabela de dadosde mercado... 

### CALCULOS
- calculo do imposto de renda
- calculo do titulo publico
- tratamento para fundos
- usar cache para nao precisar recalcular toda vez
- fluxos de debenture
- contas apenas para dias uteis <<<<<
- aplicação  - dividendos
- rentabilidade sem aplicação

- criar teste para caso q compra investimentoo e caso de vencimento... colocar resgato manul ou calculado? e qndo for difernete?

### DADOS DE MERCADO
- recuperar dividendos de forma automatica

### GRAFICOS
- adicionar cdi/ipca/ibovespa para comparacao


