package com.luxoft.globalmanagement.enums;

import lombok.Getter;

@Getter
public enum TransactionType {

    BUY("buy"),
    SELL("sell");

    String type;

    TransactionType(String type) {
        this.type = type;
    }
}
