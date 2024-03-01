# JWT Validation

## Descrição
Aplicação que expõe uma API que recebe por parâmetro um JWT (string) e verifica se é válido conforme as regras abaixo:

- Deve ser um JWT válido
- Deve conter apenas 3 claims (Name, Role e Seed) no payload da JWT
- A claim Name não pode ter caractere de números
- O tamanho máximo da claim Name é de 256 caracteres
- A claim Role deve conter apenas 1 dos três valores (Admin, Member e External)
- A claim Seed deve ser um número primo


## Input e Output
- Input: um JWT (string).
- Output: um boolean indicando se é válido ou não.


## Requisitos
- Java 17 ou superior
- Maven 3.6.3 ou superior
- Git 2.44.0 ou superior
- Docker 20.10.5 ou superior
- AWS CLI 2.1.29 ou superior
- Insomnia 8.6.1 ou superior
- conta na AWS 


## Detalhes da API

### Arquitetura
A API foi implementada com Spring Boot 3 e Java 17, é uma solução simples tendo basicamente uma classe JwtController, JwtService e JwtValidator que faz toda validação de negócios para a API, conforme regras da descrição. Existem também as classes JwtApplication para fazer o start da aplicação Spring Boot e a classe de testes unitários JwtValidationTest para testes dos casos da espeficação.


### Tecnologias utilizadas
- Linguagem de programação: Java 17
- Framework web: Spring Boot 3
- Biblioteca de Logging: Log4j
- Biblioteca para testes unitários: Junit
- Biblioteca para tratamento de json: org.json
- Ferramenta de containerização: Docker
- Provedor de cloud: AWS
- Ferramenta de versionamento: GitHub / GitHub Desktop
- Ferramenta de teste de api: Insomnia


### Detalhes da descrição das classes/métodos

Classe 'JwtApplication'
- Método 'main': método principal da API que permite fazer o start da aplicação Spring Boot.

Classe 'JwtController'
- Método 'validate': método que recebe a requisição GET tendo como parâmetro de entrada a String JWT, chama o método 'validateJwt' da classe service JwtService para fazer a validação da JWT. Retorna o boolean referente a esta validação.
Respostas:
- `200 OK`: O JWT é válido (true) ou não atende uma das regras de negócios da descrição (false);
- `400 Bad Request`: O JWT é inválido (false), a String JWT não é compatível a uma estrutura JWT;

Classe 'JwtService'
- Método 'validateJwt': método que recebe a String JWT da classe JwtController e chama o método 'isValidJwt' da classe util JwtValidator. Retorna o boolean desta validação para 'JwtController'.

Classe 'JwtValidator'
- Método 'isValidJwt': método que recebe a String JWT da classe JwtService e faz a validação da String JWT para os seguintes casos:
	- Verifica se o JWT válido (tem 3 partes e esta usando o algoritmo HS256)
	- Contem apenas 3 claims (Name, Role e Seed) no payload da JWT
	- A claim Name não pode ter caractere de números
	- O tamanho máximo da claim Name é de 256 caracteres
	- A claim Role deve conter apenas 1 dos três valores (Admin, Member e External)
Para verificar se o valor de Seed é um número primo, é chamado o método 'isPrime'. Retorna o boolean desta validação para 'JwtService'.

- Método 'isPrime': método que verifica se o valor numérico do Seed é primo ou não. Um número é primo se tem como divisores apenas o 1 e ele mesmo, então este método verifica se o número do Seed possui mais divisores além destes (não primo). Retorna para o método 'isValidJwt' o boolean desta validação.

Classe 'JwtValidationTest'
- Método 'setUp': Inicializa o objeto antes da execução dos testes

- Método 'testValidJwt': teste unitário para Caso 1: JWT válido

- Método 'testInvalidJwt': teste unitário para Caso 2: JWT inválido

- Método 'testInvalidNameClaim': teste unitário para Caso 3: JWT com claim Name inválida

- Método 'testExtraClaim': teste unitário para Caso 4: JWT com mais de 3 claims


### Arquivos da API 

- Arquivo 'application.yaml': arquivo yaml com propriedades para a API durante a execução.

- Arquivo 'log4j.properties': arquivo com o layout dos logs do log4j para saída console.

- Arquivo 'build.bat': arquivo bat utilizado para gerar a imagem da aplicação na AWS ECR, através do arquivo Dockerfile (vide ECS-FARGATE)

- Arquivo 'Dockerfile': arquivo com os comandos para gerar a imagem da aplicação.

- Arquivo 'pom.xml': arquivo Maven com todas as dependências utilizadas nesta API.

## Como executar o projeto

### Localmente
- Clone o repositório do GitHub: `git clone https://github.com/syaiso/jwt-validation.git`
- Entre na pasta do projeto: `cd jwt-validator`
- Execute o comando `mvn clean package` para gerar o jar executável
- Execute o comando `java -jar target/jwt-validation-1.0.0.jar` para iniciar a aplicação
- No Insomnia fazer a chamada GET `http://localhost:8080/jwt-validation/api/v1/validate?jwt=<seu_jwt>` para testar a validação do seu JWT
- No projeto jwt-validation existe uma pasta 'insomnia' com a collection com esta chamada, fazer o import da collection no Insomnia


### AWS

#### ECS-FARGATE
- na AWS criar um usuário no IAM usando a apólice para o serviço ECR da AWS
- na máquina local usar o comando `aws configure` com as credenciais geradas para o novo usuário da AWS
- na AWS criar um repositório no serviço ECR da AWS e copiar as 4 opções de comando em 'View Push Commands'
- editar o build.bat do projeto jwt-validation, passando os 4 comandos apontando para o Dockerfile 'docker build -t jwt-validation -f Dockerfile'
- rodar o build.bat e após a execução verificar que foi criada a imagem da aplicação no repositório do ECR
- na AWS criar um cluster no serviço AWS ECS usando a opção Fargate
- no ECS criar uma task definition com a opção Fargate, no campo 'Image URI' colocar a URI da imagem que aparece no ECR
- fazer o deploy pela opção 'run task' da task definition e aguardar a task do cluster ficar como 'Running' 
- nos detalhes da task do cluster, copie o IP público e substitua no endpoint da API e faça a chamada GET


#### EC2
- Execute o comando `mvn clean package` para gerar o jar executável
- na AWS criar uma vpc pelo serviço VPC da AWS
- no serviço EC2 da AWS, criar security group
- copiar o conteúdo da chave pública do SSH (id_*.pub) que fica na pasta '.ssh' da máquina local
- no EC2 na opção 'Pares de Chaves' criar par de chaves colando o conteúdo da chave pública
- no EC2 na opção 'Instâncias" criar uma instância usando o par de chaves e o security group criados
- aguardar até que a instância esteja com status 'Running'
- nos detalhes da instância, copie o DNS IPV4 público
- abra um terminal na máquina local e execute o comando 'ssh ec2-user@<DNS IPV4 público>' para se conectar a instância do EC2
- execute o comando 'sudo yum update' para atualizar a instância
- execute o comando 'sudo yum install java-17-amazon-corretto-headless' para instalar o Java na instância
- execute o comando 'java --version' para verificar se a instalação do Java foi com sucesso
- execute o comando 'scp <Path arquivo jar> ec2-user@<DNS IPV4 público>:/home/ec2-user' para copiar o jar na instância do EC2
- execute o comando 'ls' para verificar se o arquivo jar está na instância do EC2
- execute o comando 'java -jar <arquivo jar>' para subir a aplicação Java na instância do EC2
- substitua no endpoint da API pelo DNS IPV4 público e faça a chamada GET


## Premissas e decisões

- Escolhi a linguagem Java e o framework Spring Boot por ter mais conhecimento e experiência.
- Escolhi usar o Docker para containerizar a aplicação e facilitar o deploy na AWS.
- Por simplicidade, o JWT da API foi tratado como String, pois conseguiu satisfazer todos os critérios da especificação, não sendo necessária utilização de bibliotecas como 'io.jsonwebtoken'.
- Embora não especificado nos requisitos, foi feito deploy da aplicação no EC2 como teste de integração com o ambiente da AWS, poderia ser utilizado como uma contingência caso o deploy no ECS-Fargate não fosse realizado com sucesso, possibilitando um ECS-EC2.
- Por simplicidade, acabaram não sendo feitos os processos de deploy automático, helm chart e aprovisionamento de infraestrutura pelo OpenTerraform, dando-se mais prioridade na utilização dos serviços da AWS. 
