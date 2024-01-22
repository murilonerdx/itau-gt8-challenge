# CHALLENGE - PL. SOFTWARE ENGINEER 🚀

## Índice
1. [**Desafio**](#introducao)
2. [**Premissas**](#esperado)
3. [**Pontos que não iremos avaliar**](#pontos_que_nao_iremos_avaliar)
4. [**Como esperamos receber a solução**](#como_esperamos_receber)
5. [**Dúvidas**](#duvidas)

---

## <a name="introducao">1. Introdução</a>

A proposta da aplicação que vamos desenvolver em conjunto é disponibilizar a uma pessoa as modalidades de seguro as quais melhor se encaixam com seu perfil de acordo com algumas variáveis.

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

### Utilização da aplicação:

A aplicação deve receber como entrada essas informações:

##### input

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

_Para fins de simplicidade, considere que vamos sempre receber os dados corretos (tipos e formatos)_

E deve responder essas informações:

##### output

```json
{
  "customer": {
    "name": "João",
    "insurances": [
      {
        "type": "basic",
        "cost": 2
      }
    ]
  }
}
```

## <a name="esperado">2. O que é esperado</a>
Abaixo deixamos os pontos que daremos maior atenção:
- Padrões REST *[OK]*
- Tratamento de Erros *[OK]*
- Testes de unidade e integração *[OK]*
- Cobertura de testes (Code Coverage) *[OK]*
- Arquitetura utilizada *[OK]*
- Abstração, acoplamento, extensibilidade e coesão *[OK]* 
- Utilização de Design Patterns (quando necessário) *[OK]*
- Legibilidade de Código *[OK]*
- Documentação da Solução no README.md *[OK]*
- Observabilidade (logs) *[OK]*
- Geração do relatório de cobertura de testes *[OK]*
- Dockerfile configurado para deploy da aplicação [OK]

## <a name="pontos_que_nao_iremos_avaliar">3. Pontos que não iremos avaliar</a>
Abaixo deixamos alguns pontos que não iremos avaliar
- Scripts CI/CD
- Collections do Postman, Insomnia ou qualquer outra ferramenta para execução
- IDE utilizada

## <a name="como_esperamos_receber">4. Como esperamos receber a solução</a>
Esta etapa é eliminatória, e por isso esperamos que o código reflita essa importância.

Se tiver algum imprevisto, dúvida ou problema, por favor entre em contato com a gente, estamos aqui para ajudar.

Atualmente trabalhamos com a stack Java/Spring, porém você pode utilizar a tecnologia de sua preferência.

Para candidatos externos nos envie o link de um repositório público com a sua solução e link do Docker hub com a imagem de seu aplicativo publicada (pode deixar este link no README.md).

Para candidatos internos nos envie o projeto em formato .zip

## <a name="duvidas">5. Dúvidas</a>
Em caso de dúvidas, contacto-nos o quanto antes através do e-mail `EspecialistasComunidadeSeguros@correio.itau.com.br`.

Lembrando que não existe "bala de prata" e solução perfeita!

VEM COM A GENTE! 🍊🚀