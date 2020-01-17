package com.luxoft.globalmanagement.service;

import com.luxoft.globalmanagement.enums.TransactionType;
import com.luxoft.globalmanagement.exception.OilBussinessException;

import java.math.BigDecimal;

public interface OilService {

    /**
     * Given any price as the input, please calculate the Revenue yield
     *
     * @param oilId Oil Id
     * @param price Price
     * @return Revenue yield calculated
     */
    BigDecimal getRevenueYield(String oilId,
        BigDecimal price) throws OilBussinessException;

    /**
     * Given any price as the input, please calculate the Price-Earnings Ratio
     *
     * @param oilId Oil Id
     * @param price Price
     * @return Price-Earnings Ratio calculated
     */
    BigDecimal getPriceEarningsRatio(String oilId,
        BigDecimal price) throws OilBussinessException;

    /**
     * Record a transaction with timestamp, quantity, buy or sell indicator and price
     *
     * @param oilId Oil Id
     * @param type Transaction type
     * @param quantity Barrel quantity
     * @param price Price
     * @return Recorded success
     */
    boolean recordTransaction(String oilId,
        TransactionType type,
        Integer quantity,
        BigDecimal price) throws OilBussinessException;

    /**
     * Calculate Volume Weighted Oil Price based on transaction in the past 30 minutes
     *
     * @param oilId Oil Id
     * @return Volume Weighted Oil Price calculated
     */
    BigDecimal getVolumeWeightedOilPrice(String oilId) throws OilBussinessException;

    /**
     * Calculate the Inventory Index using the geometric mean of prices for all the types of oil
     *
     * @return Geometric mean of prices
     */
    double getInventoryIndex();

}
