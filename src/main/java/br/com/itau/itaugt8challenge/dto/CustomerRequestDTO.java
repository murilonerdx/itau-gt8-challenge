package br.com.itau.itaugt8challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequestDTO(
        @NotNull(message = "Nome não pode ser nulo")
        String name,
        @CPF(message = "CPF inválido")
        String cpf,
        @Min(value = 18, message = "A idade precisa ser maior ou igual a 18")
        @NotNull(message = "Idade não pode ser nula")
        Integer age,
        @NotBlank(message = "UF não pode ser nulo")
        String location,
        @NotNull
        @JsonProperty(value = "valor_veiculo")
        double valorVeiculo
) {

}