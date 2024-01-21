package br.com.itau.itaugt8challenge.utils;


import br.com.itau.itaugt8challenge.dto.InsuranceDTO;

import java.util.List;

public interface InsuranceCalculationStrategy {
    List<InsuranceDTO> calculateInsurance(double value, Integer age, String uf);
}
