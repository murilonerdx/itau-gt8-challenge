package br.com.itau.itaugt8challenge.utils.impl;

import br.com.itau.itaugt8challenge.dto.InsuranceDTO;
import br.com.itau.itaugt8challenge.utils.InsuranceCalculationStrategy;
import br.com.itau.itaugt8challenge.utils.enums.InsuranceEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class ComprehensiveInsuranceStrategy implements InsuranceCalculationStrategy {
    private static final Predicate<Double> isSmallerOrEqualVehicle = value -> value <= 70000;
    private static final Predicate<Double> isVehicleLargerThanAndSmallerThan = value -> value > 70000 && value < 100000;
    private static final Predicate<Double> isVehicleGreaterThanOrEqualTo = value -> value >= 100000;

    @Override
    public List<InsuranceDTO> calculateInsurance(double value, Integer age, String uf) {
        return getInsuranceEnums(value, age, uf)
                .stream().map(it -> {
                    return new InsuranceDTO(it.getType(), it.getPercentage());
                }).collect(Collectors.toList());
    }

    private List<InsuranceEnum> getInsuranceEnums(double value, Integer age, String uf) {
        if (ComprehensiveInsuranceStrategy.isSmallerOrEqualVehicle.test(value))
            return age < 30 && uf.equals("SP") ?
                    List.of(InsuranceEnum.PARTIAL_INSURANCE, InsuranceEnum.BASIC_INSURANCE) :
                    List.of(InsuranceEnum.BASIC_INSURANCE);

        if (ComprehensiveInsuranceStrategy.isVehicleLargerThanAndSmallerThan.test(value))
            return uf.equals("SP") ?
                    List.of(InsuranceEnum.PARTIAL_INSURANCE, InsuranceEnum.BASIC_INSURANCE) :
                    List.of(InsuranceEnum.BASIC_INSURANCE);

        if (ComprehensiveInsuranceStrategy.isVehicleGreaterThanOrEqualTo.test(value))
            return age < 30 ?
                    List.of(InsuranceEnum.values()) :
                    List.of(InsuranceEnum.BASIC_INSURANCE, InsuranceEnum.FULL_INSURANCE);

        return List.of(InsuranceEnum.BASIC_INSURANCE);
    }
}
