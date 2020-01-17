package com.luxoft.globalmanagement.service.impl;

import com.luxoft.globalmanagement.enums.TransactionType;
import com.luxoft.globalmanagement.exception.OilBussinessException;
import com.luxoft.globalmanagement.fakerepository.OilRepository;
import com.luxoft.globalmanagement.model.OilModel;
import com.luxoft.globalmanagement.model.OilTransactionModel;
import com.luxoft.globalmanagement.service.OilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OilServiceImpl implements OilService {

    private final OilRepository oilRepository;

    @Override
    public BigDecimal getRevenueYield(@NotNull String oilId,
        @NotNull BigDecimal price) throws OilBussinessException {

        OilModel oilModelById = this.oilRepository.getOilModelBy(oilId);
        if (oilModelById.isPremium()) {
            return oilModelById.getVariableRevenue().multiply(BigDecimal.valueOf(oilModelById.getOilBarrelValue()))
                .divide(price, 2, RoundingMode.FLOOR);
        } else {
            return BigDecimal.valueOf(oilModelById.getFixedRevenue()).divide(price, 2, RoundingMode.FLOOR);
        }
    }

    @Override
    public BigDecimal getPriceEarningsRatio(@NotNull String oilId,
        @NotNull BigDecimal price) throws OilBussinessException {

        OilModel oilModelById = this.oilRepository.getOilModelBy(oilId);

        return price.divide(BigDecimal.valueOf(oilModelById.getFixedRevenue()), 2, RoundingMode.FLOOR);
    }

    @Override
    public boolean recordTransaction(String oilId,
        TransactionType type,
        Integer quantity,
        BigDecimal price) throws OilBussinessException {

        OilModel oilModel = this.oilRepository.getOilModelBy(oilId);
        return this.oilRepository.recordTransaction(oilModel, type, quantity, price);
    }

    @Override
    public BigDecimal getVolumeWeightedOilPrice(String oilId) throws OilBussinessException {
        OilModel oilModelById = this.oilRepository.getOilModelBy(oilId);
        List<OilTransactionModel> transactionsBy = this.oilRepository.getTransactionsBy(oilModelById);

        Calendar past30minutes = Calendar.getInstance();
        past30minutes.add(Calendar.MINUTE, -30);

        double sumPricebyQuantity = transactionsBy.stream()
            .filter(transaction -> transaction.getTimestamp().after(past30minutes.getTime()))
            .mapToDouble(transaction -> transaction.getPrice().multiply(BigDecimal.valueOf(transaction.getQuantity())).doubleValue())
            .sum();

        int sumQuantity = transactionsBy.stream()
            .filter(transaction -> transaction.getTimestamp().after(past30minutes.getTime()))
            .mapToInt(OilTransactionModel::getQuantity)
            .sum();

        return BigDecimal.valueOf(sumPricebyQuantity / sumQuantity);
    }

    @Override
    public double getInventoryIndex() {
        List<OilTransactionModel> transactions = this.oilRepository.getTransactions();
        if (CollectionUtils.isEmpty(transactions)) {
            return 0;
        }

        double sumPrices = transactions.stream()
            .mapToDouble(oilTransactionModel -> oilTransactionModel.getPrice().doubleValue())
            .sum();

        return Math.pow(sumPrices, 1.0 / transactions.size());
    }
}
