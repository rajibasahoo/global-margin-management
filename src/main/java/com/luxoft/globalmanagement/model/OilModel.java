package com.luxoft.globalmanagement.model;

import com.luxoft.globalmanagement.enums.OilType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OilModel {

    @NotNull String id;
    @NotNull String type;
    @NotNull Integer fixedRevenue;
    BigDecimal variableRevenue;
    @NotNull Integer oilBarrelValue;

    public boolean isPremium() {
        return OilType.PREMIUM.getType().equals(this.type) && this.variableRevenue != null;
    }
}
