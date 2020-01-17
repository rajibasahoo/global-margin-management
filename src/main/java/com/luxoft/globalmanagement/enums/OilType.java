package com.luxoft.globalmanagement.enums;

import lombok.Getter;

@Getter
public enum OilType {

    STANDARD("Standard"),
    PREMIUM("Premium");

    private String type;

    OilType(String type) {
        this.type = type;
    }
}
