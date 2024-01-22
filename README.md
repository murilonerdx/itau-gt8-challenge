# ITAU GT8 CHALLENGE

Projeto proposto para uma vaga no Itau

## Índice

- [Introdução](#introdução)
- [Link do Docker Hub](#link-do-docker-hub)
- [Padrão REST](#padrão-rest)
- [Tratamento de Erros](#tratamento-de-erros)
- [Testes](#testes)
    - [Testes de Unidade](#testes-de-unidade)
    - [Testes de Integração](#testes-de-integração)
- [Arquitetura](#arquitetura)
- [Como Usar](#como-usar)
- [Contribuições](#contribuições)
- [Licença](#licença)

## Introdução

O projeto tem uma só controller por lá nós enviamos uma request com os padrões de input abaixo
```json
{
  "customer": {
    "name": "João",
    "cpf": "123.456.789-10",
    "age": 29,
    "location": "BH",
    "valor_veiculo": 70000
  }
}
```

Ao enviar essa informações temos que tratar os dados, fiz algumas vaidações referente ao objeto principal como por exemplo

CPF precisa ser valido, a idade precisa ser maior ou igual a 18

A ideia é simples porém a forma de implementar precisa ser customizavel a ponto de baseado na idade, localização e valor do veiculo conseguirmos definir o nivel de seguro.

Devemos prover os seguintes modelos de seguros:

- Seguro Básico. Valor: 2% do valor do veículo
- Seguro Parcial. Valor: 3% do valor do veículo
- Seguro Total. Valor: 4% do valor do veículo

Abaixo seguem as regras de negócio relacionadas ao seguro de acordo com o valor do veículo:

|                              | Seguro Básico | Seguro Parcial | Seguro Total |
|------------------------------|---------------|:--------------:|--------------|
| Veículo <= 70.000            | Sim           |   Sim\*\*\*    | Não          |
| Veículo > 70.000 e < 100.000 | Sim           |    Sim\*\*     | Não          |
| Veículo => 100.000           | Sim           |     Sim\*      | Sim          |

- \* Clientes com menos de 30 anos
- \*\* Clientes que residem em SP
- \*\*\* Clientes com menos de 30 anos que residem em SP


O que está sendo salvo no banco é os dados do usuario, e há partir disso conseguimos manipular os dados por fora pela service


## Link do Docker Hub

[Link para a imagem Docker](https://hub.docker.com/repository/docker/murilonerdx/itau-gt8-challenge)

## Implementação do Swagger

http://localhost:8080/docs-insurance (Visão geral)

http://localhost:8080/swagger-ui/index.html (Interface Visual)

O Swagger mais atualizado disponibiliza outros endpoints interressantes para integração com actuator um serviço de monitoramento compartilhado que também possibilita o CLOUD além da integração com HATEOS

O unico endpoint que vamos focar é 

POST /api/v1/customers (Salva e organiza o padrão especifico da requisição)

GET /api/v1/customers (Na qual lista todos os customers validando os padrões mensionados acima e trazendo todos que foram salvos)

## Padrão REST

Estou utilizando Swagger para documentação

ExceptionHandlerController o controle dos erros relacionados ao serviço, podendo customizar cada um.

Padrão MVC para Spring boot sendo assim tendo serviços, implementações, repositorios, controller

Os testes unitarios e integrativos

Usando o out port, isso quer dizer que um serviço que não precisa utilizar JPA ou dados consultados no banco não é um serviço eficiente.

## Abstração, acoplamento, extensibilidade e coesão

Abstração envolve destacar as características essenciais de um objeto, ignorando suas características menos importantes ou acidentais. No código, abstração é aplicada ao modelar o domínio do problema, como CustomerInsurance, CustomerRequestDTO e CustomerResponseDTO. Cada uma dessas classes representa uma abstração de entidades do mundo real, destacando atributos e comportamentos relevantes para o sistema.

Acoplamento refere-se ao grau de interdependência entre módulos de software. Um baixo acoplamento é desejável, pois torna o sistema mais modular e fácil de modificar. No serviço, há um acoplamento entre CustomerService e CustomerInsuranceRepository, indicado pela injeção de dependência do repositório no serviço. Isso é um exemplo de acoplamento, mas é um acoplamento baixo e gerenciável, pois a dependência é injetada (e não criada diretamente pelo serviço), permitindo flexibilidade e facilidade em testes e manutenção.

Extensibilidade é a capacidade de um sistema ser estendido com novas funcionalidades sem alterar as existentes significativamente. O serviço eu consigo adicionar novas implementações de Predicados como uma extenção

Coesão refere-se ao grau em que os elementos de um módulo pertencem juntos. Uma alta coesão dentro de classes ou módulos é geralmente preferível. A classe CustomerService é um bom exemplo de coesão, pois todos os métodos estão relacionados ao gerenciamento de CustomerInsurance. As operações de criação, atualização e listagem de seguros são agrupadas de forma lógica na mesma classe, indicando alta coesão.
## Testes

### Observabilidade (logs)

### Jacoco (Code Coverage)
Fiz a implementação basica do Jacoco que faz a mesma cobertura que o reports que os proprios testes disponibilizam

Existe logs simples feito com lombok espalhados na service o qual podemos observar o que está acontecendo por trás dos panos

### Geração do relatório de cobertura de testes

Ao executar os testes ele gera o reports, um relatorio dos testes ajudando no monitoramento dos testes, o quantidade de falhas, testes ignorados etc.

Ele já gera uma interface visual para visualização

Path: *itau-gt8-challenge/build/reports/tests/test/index.html*

### Testes de Unidade

*(CustomerServiceTest)*

Fiz os testes dos serviços referente aos detalhes para cada um dos seguros especificos.

### Testes de Integração
*(CustomerControllerTest)*

Fiz os testes das controllers referente aos detalhes para cada um dos seguros especificos

## Arquitetura

Utilizei arquitetura padrão para projetos Spring Boot que é o MVC, os pacotes são separados por seus modelos, repositorios e serviços
Escolhi essa arquitetura pois ela é padronizada e util simples sem uma complexidade maior para um projeto pequeno

Para projetos maiores seria interessante aplicar arquitetura hexagonal, que ajude a longo prazo, as manutenções, ajudando também no clean code e separando as prioridades em portas de entrada e saida.

## Como Usar
git clone https://github.com/murilonerdx/itau-gt8-challenge.git
cd itau-gt8-challenge
docker compose up --build -d
(Acesse: http://localhost:8080/swagger-ui/index.html)

OU
docker run --name some-postgres -e POSTGRES_DB=dockerdb -e POSTGRES_USER=dockeruser -e POSTGRES_PASSWORD=dockerpassword -p 5432:5432 -d postgres

docker pull murilonerdx/itau-gt8-challenge:v1.0
docker images
docker run murilonerdx/itau-gt8-challenge:v1.0
(Acesse: http://localhost:8080/swagger-ui/index.html)

Para acessar as soluções e passo a passo: [SOLUÇÃO](https://github.com/murilonerdx/itau-gt8-challenge/blob/feature/initial/SOLUTION.md) - [2024]

---

© [Murilo Pereira](https://github.com/murilonerdx) - [2024]