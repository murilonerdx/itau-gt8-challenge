package br.com.itau.itaugt8challenge.controller;


import br.com.itau.itaugt8challenge.dto.CustomerRequestDTO;
import br.com.itau.itaugt8challenge.dto.CustomerResponseDTO;
import br.com.itau.itaugt8challenge.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> insurance(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.ok().body(customerService.export(customerRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAllInsurances() {
        return ResponseEntity.ok().body(customerService.findAllInsurances());
    }
}
