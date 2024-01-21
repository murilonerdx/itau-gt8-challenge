package br.com.itau.itaugt8challenge.service;

import br.com.itau.itaugt8challenge.dto.CustomerRequestDTO;
import br.com.itau.itaugt8challenge.dto.CustomerResponseDTO;
import br.com.itau.itaugt8challenge.dto.InsuranceDTO;
import br.com.itau.itaugt8challenge.model.CustomerInsurance;
import br.com.itau.itaugt8challenge.repository.CustomerInsuranceRepository;
import br.com.itau.itaugt8challenge.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class CustomerServiceTest {

    @Mock
    private CustomerInsuranceRepository customerInsuranceRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    @DisplayName("Customer menor ou igual a 70 mil")
    void testCustomerLessOrEqualBasicSeventyThousand() {
        CustomerRequestDTO customerRequestDTO = buildCustomerInsuranceRequest(null, 29, "BH", 70000);
        CustomerInsurance existingCustomer = buildCustomerInsurance(1L, 29, "BH", 70000);
        when(customerInsuranceRepository.findByCpf(customerRequestDTO.cpf())).thenReturn(existingCustomer);
        when(customerInsuranceRepository.save(existingCustomer)).thenReturn(existingCustomer);
        CustomerResponseDTO result = customerService.export(customerRequestDTO);

        assertNotNull(result);
        assertEquals(result.getName(), "João");
        assertEquals(result.getInsurances().size(), 1);
        assertEquals(result.getInsurances(), List.of(new InsuranceDTO("basic", 2)));
    }

    @Test
    @DisplayName("Customer menor ou igual a 70 mil com menos de 30 anos e se localiza em Sao Paulo(SP)")
    void testCustomerLessOrEqualBasicAndPartialSeventyThousand() {
        CustomerRequestDTO customerRequestDTO = buildCustomerInsuranceRequest(null, 29, "SP", 70000);
        CustomerInsurance existingCustomer = buildCustomerInsurance(1L, 29, "SP", 70000);
        when(customerInsuranceRepository.findByCpf(customerRequestDTO.cpf())).thenReturn(existingCustomer);
        when(customerInsuranceRepository.save(existingCustomer)).thenReturn(existingCustomer);
        CustomerResponseDTO result = customerService.export(customerRequestDTO);

        assertNotNull(result);
        assertEquals(result.getInsurances().size(), 2);
        assertEquals(result.getName(), "João");
        assertTrue(result.getInsurances().containsAll(List.of(new InsuranceDTO("basic", 2), new InsuranceDTO("partial", 3))));
    }

    @Test
    @DisplayName("Customer menor ou igual a 70 mil com mais de 30 anos e se localiza em Sao Paulo(SP)")
    void testCustomerLessOrEqualBasicAndPartialSeventyThousandAndAgeGreater() {
        CustomerRequestDTO customerRequestDTO = buildCustomerInsuranceRequest(null, 31, "SP", 70000);
        CustomerInsurance existingCustomer = buildCustomerInsurance(1L, 29, "SP", 70000);
        when(customerInsuranceRepository.findByCpf(customerRequestDTO.cpf())).thenReturn(existingCustomer);
        when(customerInsuranceRepository.save(existingCustomer)).thenReturn(existingCustomer);
        CustomerResponseDTO result = customerService.export(customerRequestDTO);

        assertNotNull(result);
        assertEquals(result.getInsurances().size(), 1);
        assertEquals(result.getName(), "João");
        assertTrue(result.getInsurances().contains(new InsuranceDTO("basic", 2)));
    }

    @Test
    @DisplayName("Customer maior que 70 mil e menor que 100 mil e não mora em Sao Paulo")
    void testCustomerGreaterThanSeventyThousandAndLessThanOneHundredThousand() {
        CustomerRequestDTO customerRequestDTO = buildCustomerInsuranceRequest(null, 29, "BH", 71000);
        CustomerInsurance existingCustomer = buildCustomerInsurance(1L, 29, "BH", 71000);
        when(customerInsuranceRepository.findByCpf(customerRequestDTO.cpf())).thenReturn(existingCustomer);
        when(customerInsuranceRepository.save(existingCustomer)).thenReturn(existingCustomer);
        CustomerResponseDTO result = customerService.export(customerRequestDTO);

        assertNotNull(result);
        assertEquals(result.getInsurances().size(), 1);
        assertEquals(result.getName(), "João");
        assertTrue(result.getInsurances().contains(new InsuranceDTO("basic", 2)));
    }

    @Test
    @DisplayName("Customer maior que 70 mil e menor que 100 mil e se localiza em Sao Paulo(SP)")
    void testCustomerGreaterThanSeventyThousandAndLessThanOneHundredThousandAndLocaleSP() {
        CustomerRequestDTO customerRequestDTO = buildCustomerInsuranceRequest(null, 29, "SP", 71000);
        CustomerInsurance existingCustomer = buildCustomerInsurance(1L, 29, "SP", 71000);
        when(customerInsuranceRepository.findByCpf(customerRequestDTO.cpf())).thenReturn(existingCustomer);
        when(customerInsuranceRepository.save(existingCustomer)).thenReturn(existingCustomer);
        CustomerResponseDTO result = customerService.export(customerRequestDTO);

        assertNotNull(result);
        assertEquals(result.getInsurances().size(), 2);
        assertEquals(result.getName(), "João");
        assertTrue(result.getInsurances().containsAll(List.of(new InsuranceDTO("basic", 2), new InsuranceDTO("partial", 3))));
    }

    @Test
    @DisplayName("Customer maior que 100 mil tem mais de 30 anos")
    void testCustomerGreaterThanOrEqualToOneHundredThousandAndAgeGreater() {
        CustomerRequestDTO customerRequestDTO = buildCustomerInsuranceRequest(null, 31, "SP", 100000);
        CustomerInsurance existingCustomer = buildCustomerInsurance(1L, 31, "SP", 100000);
        when(customerInsuranceRepository.findByCpf(customerRequestDTO.cpf())).thenReturn(existingCustomer);
        when(customerInsuranceRepository.save(existingCustomer)).thenReturn(existingCustomer);
        CustomerResponseDTO result = customerService.export(customerRequestDTO);

        assertNotNull(result);
        assertEquals(result.getInsurances().size(), 2);
        assertEquals(result.getName(), "João");
        assertTrue(result.getInsurances().containsAll(List.of(new InsuranceDTO("basic", 2), new InsuranceDTO("total", 4))));
    }

    @Test
    @DisplayName("Customer maior que 100 mil tem menos de 30 anos")
    void testCustomerGreaterThanOrEqualToOneHundredThousandAndAgeLess() {
        CustomerRequestDTO customerRequestDTO = buildCustomerInsuranceRequest(null, 29, "SP", 100000);
        CustomerInsurance existingCustomer = buildCustomerInsurance(1L, 29, "SP", 100000);
        when(customerInsuranceRepository.findByCpf(customerRequestDTO.cpf())).thenReturn(existingCustomer);
        when(customerInsuranceRepository.save(existingCustomer)).thenReturn(existingCustomer);
        CustomerResponseDTO result = customerService.export(customerRequestDTO);

        assertNotNull(result);
        assertEquals(result.getInsurances().size(), 3);
        assertEquals(result.getName(), "João");
        assertTrue(result.getInsurances()
                .containsAll(List.of(new InsuranceDTO("basic", 2),
                        new InsuranceDTO("total", 4),
                        new InsuranceDTO("partial", 3))
                )
        );
    }

    @Test
    @DisplayName("Buscar por todos insurances")
    void testFindAllInsurances() {
        List<CustomerInsurance> insurances = List.of(buildCustomerInsurance(1L, 29, "SP", 100000));
        when(customerInsuranceRepository.findAll()).thenReturn(insurances);

        List<CustomerResponseDTO> result = customerService.findAllInsurances();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.get(0).getInsurances().size());
    }

    CustomerResponseDTO buildCustomerResponse(List<InsuranceDTO> insuranceDTOS) {
        return new CustomerResponseDTO("João", insuranceDTOS);
    }

    CustomerInsurance buildCustomerInsurance(Long id, int age, String uf, double valorVeiculo) {
        return new CustomerInsurance(id, "João", "12345678910", age, uf, valorVeiculo);
    }

    CustomerRequestDTO buildCustomerInsuranceRequest(Long id, int age, String uf, double valorVeiculo) {
        return new CustomerRequestDTO("João", "12345678910", age, uf, valorVeiculo);
    }

}
