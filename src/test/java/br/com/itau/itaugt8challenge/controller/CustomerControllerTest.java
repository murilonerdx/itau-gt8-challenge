package br.com.itau.itaugt8challenge.controller;


import br.com.itau.itaugt8challenge.dto.CustomerRequestDTO;
import br.com.itau.itaugt8challenge.dto.CustomerResponseDTO;
import br.com.itau.itaugt8challenge.dto.InsuranceDTO;
import br.com.itau.itaugt8challenge.services.CustomerService;
import br.com.itau.itaugt8challenge.utils.TypeInsuranceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {
    static String CUSTOMER_API = "/api/v1/customers";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerRequestDTO customerRequestDTO;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("POST /api/v1/customers - Cliente com valor do veículo menor ou igual a 70 mil")
    void whenPostCustomerWithVehicleValueLessOrEqualSeventyThousand_thenReturnsInsurance() throws Exception {
        customerRequestDTO = createCustomerRequestDTO(70000, 29, "BH");

        var customerResponse = buildCustomerResponse(TypeInsuranceUtil.calculateInsurance(customerRequestDTO.valorVeiculo(), customerRequestDTO.age(), customerRequestDTO.location()));
        BDDMockito.given(customerService.export(Mockito.any(CustomerRequestDTO.class))).willReturn(customerResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequestDTO));

        mockMvc.perform(post(CUSTOMER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insurances[0].type").value("basic"))
                .andExpect(jsonPath("$.insurances[0].cost").value(2));
    }

    @Test
    @DisplayName("POST /api/v1/customers - Cliente menor ou igual a 70 mil com menos de 30 anos e se localiza em Sao Paulo(SP)")
    void whenPostCustomerLessOrEqualBasicAndPartialSeventyThousand() throws Exception {
        customerRequestDTO = createCustomerRequestDTO(70000, 29, "SP");

        var customerResponse = buildCustomerResponse(TypeInsuranceUtil.calculateInsurance(customerRequestDTO.valorVeiculo(), customerRequestDTO.age(), customerRequestDTO.location()));
        BDDMockito.given(customerService.export(Mockito.any(CustomerRequestDTO.class))).willReturn(customerResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequestDTO));

        mockMvc.perform(post(CUSTOMER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.insurances[0].type").value("partial"),
                        jsonPath("$.insurances[0].cost").value(3),
                        jsonPath("$.insurances[1].type").value("basic"),
                        jsonPath("$.insurances[1].cost").value(2)
                );
    }

    @Test
    @DisplayName("POST /api/v1/customers - Cliente menor ou igual a 70 mil com mais de 30 anos e se localiza em Sao Paulo(SP)")
    void whenPostCustomerLessOrEqualBasicAndPartialSeventyThousandAndAgeGreater() throws Exception {
        customerRequestDTO = createCustomerRequestDTO(70000, 31, "SP");

        var customerResponse = buildCustomerResponse(TypeInsuranceUtil.calculateInsurance(customerRequestDTO.valorVeiculo(), customerRequestDTO.age(), customerRequestDTO.location()));
        BDDMockito.given(customerService.export(Mockito.any(CustomerRequestDTO.class))).willReturn(customerResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequestDTO));

        mockMvc.perform(post(CUSTOMER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.insurances[0].type").value("basic"),
                        jsonPath("$.insurances[0].cost").value(2)
                );
    }

    @Test
    @DisplayName("POST /api/v1/customers - Cliente maior que 100 mil tem menos de 30 anos")
    void whenPostCustomerGreaterThanOrEqualToOneHundredThousandAndAgeLess() throws Exception {
        customerRequestDTO = createCustomerRequestDTO(100000, 29, "SP");

        var customerResponse = buildCustomerResponse(TypeInsuranceUtil.calculateInsurance(customerRequestDTO.valorVeiculo(), customerRequestDTO.age(), customerRequestDTO.location()));
        BDDMockito.given(customerService.export(Mockito.any(CustomerRequestDTO.class))).willReturn(customerResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequestDTO));

        mockMvc.perform(post(CUSTOMER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.insurances[0].type").value("basic"),
                        jsonPath("$.insurances[0].cost").value(2),
                        jsonPath("$.insurances[1].type").value("partial"),
                        jsonPath("$.insurances[1].cost").value(3),
                        jsonPath("$.insurances[2].type").value("total"),
                        jsonPath("$.insurances[2].cost").value(4)
                );
    }

    private CustomerRequestDTO createCustomerRequestDTO(long valorVeiculo, int age, String location) {
        return new CustomerRequestDTO("João", "24160747094", age, location, valorVeiculo);
    }

    CustomerResponseDTO buildCustomerResponse(List<InsuranceDTO> insuranceDTOS) {
        return new CustomerResponseDTO("João", insuranceDTOS);
    }
}
