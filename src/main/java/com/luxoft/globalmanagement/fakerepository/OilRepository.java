package com.luxoft.globalmanagement.fakerepository;

import com.luxoft.globalmanagement.enums.OilType;
import com.luxoft.globalmanagement.enums.TransactionType;
import com.luxoft.globalmanagement.exception.OilBussinessException;
import com.luxoft.globalmanagement.model.OilModel;
import com.luxoft.globalmanagement.model.OilTransactionModel;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Fake repository to follow the requirement: Please do not create any database but keep all data in memory.
 */
@Component
public class OilRepository {

    private HashMap<String, OilModel> oils;

    @Getter
    private List<OilTransactionModel> transactions = new ArrayList<>();

    public OilRepository() {
        this.populateFakeDb();
    }

    private void populateFakeDb() {
        this.oils = new HashMap<>();
        this.oils.put("AAC", new OilModel("AAC", OilType.STANDARD.getType(), 1, null, 42));
        this.oils.put("REW", new OilModel("REW", OilType.STANDARD.getType(), 7, null, 47));
        this.oils.put("BWO", new OilModel("BWO", OilType.STANDARD.getType(), 17, null, 61));
        this.oils.put("TIM", new OilModel("TIM", OilType.PREMIUM.getType(), 5, BigDecimal.valueOf(0.07), 111));
        this.oils.put("QFC", new OilModel("QFC", OilType.STANDARD.getType(), 22, null, 123));
    }

    public OilModel getOilModelBy(String oilId) throws OilBussinessException {
        OilModel oilModel = this.oils.get(oilId);
        if (oilModel == null) {
            throw new OilBussinessException("Oil Id not found");
        }
        return oilModel;
    }

    public boolean recordTransaction(@NotNull OilModel oil,
        @NotNull TransactionType type,
        @NotNull Integer quantity,
        @NotNull BigDecimal price) {
        this.transactions.add(new OilTransactionModel(new Date(), oil, quantity, type, price));

        return true;
    }

    public List<OilTransactionModel> getTransactionsBy(OilModel oilModel) {
        return this.transactions.stream()
            .filter(transaction -> transaction.getOil() == oilModel)
            .collect(Collectors.toList());
    }

}
