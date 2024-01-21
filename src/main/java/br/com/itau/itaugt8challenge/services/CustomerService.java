package br.com.itau.itaugt8challenge.services;


import br.com.itau.itaugt8challenge.dto.CustomerRequestDTO;
import br.com.itau.itaugt8challenge.dto.CustomerResponseDTO;
import br.com.itau.itaugt8challenge.handler.BusinessException;
import br.com.itau.itaugt8challenge.model.CustomerInsurance;
import br.com.itau.itaugt8challenge.repository.CustomerInsuranceRepository;
import br.com.itau.itaugt8challenge.utils.InsuranceCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerInsuranceRepository customerInsuranceRepository;
    private final InsuranceCalculationStrategy insuranceCalculationStrategy;

    public CustomerResponseDTO export(CustomerRequestDTO customerRequestDTO) {
        try {
            CustomerInsurance customerInsurance = customerInsuranceRepository.findByCpf(customerRequestDTO.cpf());
            if (customerInsurance == null) {
                log.info("Saving new customer in the base");
                customerInsurance = new CustomerInsurance();
                customerInsurance.setCpf(customerRequestDTO.cpf());
            } else {
                log.info("Update customer in the base");
            }
            updateCustomerInsurance(customerInsurance, customerRequestDTO);
            return customerInsuranceRepository.save(customerInsurance)
                    .toDTO(insuranceCalculationStrategy.calculateInsurance(customerRequestDTO.valorVeiculo(), customerRequestDTO.age(), customerRequestDTO.location()));
        } catch (BusinessException e) {
            log.error("Error when trying to convert DTO and save Customer");
            throw new BusinessException("Error when trying to convert DTO and save Customer");
        }
    }

    public List<CustomerResponseDTO> findAllInsurances() {
        return customerInsuranceRepository.findAll()
                .stream()
                .map(customer -> customer.toDTO(insuranceCalculationStrategy.calculateInsurance(customer.getValorVeiculo(), customer.getAge(), customer.getLocation())))
                .collect(Collectors.toList());
    }

    private void updateCustomerInsurance(CustomerInsurance customerInsurance, CustomerRequestDTO dto) {
        customerInsurance.setName(dto.name());
        customerInsurance.setAge(dto.age());
        customerInsurance.setLocation(dto.location());
        customerInsurance.setValorVeiculo(dto.valorVeiculo());
    }
}