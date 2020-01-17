package com.luxoft.globalmanagement.model;

import com.luxoft.globalmanagement.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class OilTransactionModel {

    private Date timestamp;
    private OilModel oil;
    private Integer quantity;
    private TransactionType type;
    private BigDecimal price;

}
