### Ferramentas utilizadas no desenvolvimento 
* Ubuntu 21.04
* Intellij IDEA Ultimate Edition
* openjdk 16.0.1 2021-04-20
* Apache Maven 3.8.1
* psql (PostgreSQL) 12.7 

# Como correr o sistema

## Criar uma base de dados no computador onde correr o módulo DGS e nunca tenha sido corrido antes
sudo su - postgres
createuser --no-superuser --no-createdb --no-createrole user1 ;
### Estes valores podem ser diferentes mas terão de ser atualizados no ficheiro application.properties
createdb -O user1 bd1; 
psql bd1
alter user user1 with password 'umaPass'; 
psql bd1 -U user1 -h localhost
### Correr o SQL script para gerar as tabelas
\i gerar.sql

## Correr primeiro o módulo DGS (de momento não existem valores na base de dados)
cd sdv
mvn clean install 
java -jar target/sdv-0.0.1-SNAPSHOT.jar 
### Utilizar as funções do módulo

## Correr módulos Centro
cd centro
mvn clean install
java -jar target/centro-0.0.1-SNAPSHOT.jar 
### Utilizar as funções do módulo (ex: gerar um novo centro)

## Correr módulos Utente
cd utente
mvn clean install
java -jar target/utente-0.0.1-SNAPSHOT.jar 
### Utilizar as funções do módulo (ex: realizar agendamento)
