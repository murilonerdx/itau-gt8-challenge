package br.com.itau.itaugt8challenge.utils;



import br.com.itau.itaugt8challenge.dto.InsuranceDTO;
import br.com.itau.itaugt8challenge.utils.enums.InsuranceEnum;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TypeInsuranceUtil {
    private static final Predicate<Double> isBasicAndPartialTree = value -> value <= 70000;
    private static final Predicate<Double> isBasicAndPartialTwo = value -> value > 70000 && value < 100000;
    private static final Predicate<Double> isBasicAndPartialOneAndTotal = value -> value >= 100000;

    public static List<InsuranceDTO> calculateInsurance(double valorVeiculo, int age, String location) {
        return TypeInsuranceUtil
                .getMainInsurance(valorVeiculo, age, location)
                .stream().map(it -> {
                    return new InsuranceDTO(it.getType(), it.getPercentage());
                }).collect(Collectors.toList());
    }

    public static List<InsuranceEnum> getMainInsurance(double value, Integer age, String uf) {
        if (isBasicAndPartialTree.test(value)) {
            return age < 30 && uf.equals("SP") ?
                    List.of(InsuranceEnum.PARTIAL_INSURANCE, InsuranceEnum.BASIC_INSURANCE) :
                    List.of(InsuranceEnum.BASIC_INSURANCE);
        }

        if (isBasicAndPartialTwo.test(value)) {
            return uf.equals("SP") ?
                    List.of(InsuranceEnum.PARTIAL_INSURANCE, InsuranceEnum.BASIC_INSURANCE) :
                    List.of(InsuranceEnum.BASIC_INSURANCE);
        }

        if (isBasicAndPartialOneAndTotal.test(value)) {
            return age < 30 ?
                    List.of(InsuranceEnum.values()) :
                    List.of(InsuranceEnum.BASIC_INSURANCE, InsuranceEnum.FULL_INSURANCE);
        }

        return List.of(InsuranceEnum.BASIC_INSURANCE);
    }
}
