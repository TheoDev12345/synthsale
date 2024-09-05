package com.doranco.synthsale.model;

public enum CategoryEnum {

    ANALOGIC("Analogique"),
    NUMERIC("Numérique"),
    HYBRID("Hybrid");

    private String label;

    CategoryEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
