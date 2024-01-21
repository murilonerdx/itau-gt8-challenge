package br.com.itau.itaugt8challenge.model;

import br.com.itau.itaugt8challenge.dto.CustomerResponseDTO;
import br.com.itau.itaugt8challenge.dto.InsuranceDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private int age;
    private String location;
    private double valorVeiculo;


    public CustomerInsurance(Long id, String name, String cpf, Integer age, String location, Integer valorVeiculo) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.age = age;
        this.location = location;
        this.valorVeiculo = valorVeiculo;
    }

    @JsonIgnore
    public CustomerResponseDTO toDTO(List<InsuranceDTO> insuranceDTOS) {
        return CustomerResponseDTO.builder()
                .name(name)
                .insurances(
                        insuranceDTOS
                )
                .build();
    }


}
