# Requisitos
[Oracle JDK 16](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)

# Base de dados
O ficheiro que serve para gerar uma base de dados inicial com algumas informações encontra-se no ficheiro generatedb.sql

As configurações da base de dados estão no ficheiro application.properties e as que se encontram atualmente no ficheiro apontam para a minha base de dados(l42470) no servidor linux da universidade. 

**É necessário estar ligado à rede interna da universidade para que a aplicação possa aceder à base de dados**
Se estiver fora da universidade pode alterar as configurações para apontar para uma base de dados local

### É necessário permissão para criar a extensão que permite à base de dados utilizador dados do tipo UUID.

# Compilação
## Sempre que alterar o local da base de dados ou outra informação qualquer precisa de recompilar o projecto!!

### Para compilar basta fazer
mvn clean install

# Execução 
### A execução da aplicação pode ser feita de várias maneiras:
### Pode trocar 9000 por outro port que não esteja a ser utilizado
***
## Execução no mesmo computador
#### Correr a aplicação servidor primeiro
java -cp target/classes/postgresql-42.2.20.jar:target/ServicoDeVacinas-0.0.1-SNAPSHOT.jar pt.uevora.ServicoDeVacinas.Servidor localhost 9000

#### Depois correr a aplicação cliente
java -cp target/classes/postgresql-42.2.20.jar:target/ServicoDeVacinas-0.0.1-SNAPSHOT.jar pt.uevora.ServicoDeVacinas.Cliente localhost 9000

# Outra maneira de execução no mesmo computador
#### Correr a aplicação servidor primeiro e incluir o jar com o conector postgres
java -cp target/classes/postgresql-42.2.20.jar:target/classes/ pt.uevora.ServicoDeVacinas.Servidor localhost 9000
### Depois correr a aplicação cliente
java -cp target/classes/ pt.uevora.ServicoDeVacinas.Cliente localhost 9000



############################
## Em diferentes computadores
############################

### Supondo que tem os ficheiros jar "postgresql-xxx" e "ServicoDeVacinas-xxx" compilados na diretoria atual 
Se não estiverem na diretoria atual basta movê-los da diretoria target/classes/ e target/ServicoDeVacinas-0.0.1-SNAPSHOT.jar para a atual

### Trocar onde está <IP> pelo Ip do computador em que está a correr a aplicação servidor

### Servidor
#### *nix
java -cp postgresql-42.2.20.jar:ServicoDeVacinas-0.0.1-SNAPSHOT.jar pt.uevora.ServicoDeVacinas.Servidor <IP> 9000

#### windows
java -cp "postgresql-42.2.20.jar;ServicoDeVacinas-0.0.1-SNAPSHOT.jar" pt.uevora.ServicoDeVacinas.Servidor <IP> 9000

### Cliente
java -cp ServicoDeVacinas-0.0.1-SNAPSHOT.jar pt.uevora.ServicoDeVacinas.Cliente IP 9000


