package br.com.itau.itaugt8challenge.utils.enums;

public enum InsuranceEnum {
    BASIC_INSURANCE(2, "basic"),
    PARTIAL_INSURANCE(3, "partial"),
    FULL_INSURANCE(4, "total");

    private Integer percentage;
    private String type;

    InsuranceEnum(Integer percentaget, String type) {
        this.type = type;
        this.percentage = percentaget;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
