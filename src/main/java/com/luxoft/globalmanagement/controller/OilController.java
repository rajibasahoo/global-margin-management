package com.luxoft.globalmanagement.controller;

import com.luxoft.globalmanagement.enums.TransactionType;
import com.luxoft.globalmanagement.exception.OilBussinessException;
import com.luxoft.globalmanagement.service.OilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(OilController.BASE_PATH)
@RequiredArgsConstructor
public class OilController {

    public static final String BASE_PATH = "/oil";
    public static final String REVENUE_YIELD_PATH = "/revenue-yield";
    public static final String PRICE_EARNINGS_RATIO_PATH = "/price-earnings-ratio";
    public static final String VOLUME_WEIGHTED_PATH = "/volume-weighted";
    public static final String INVENTORY_INDEX_PATH = "/inventory-index";

    private final OilService oilService;

    @GetMapping(REVENUE_YIELD_PATH + "/{oilId}/{price}")
    public ResponseEntity<String> getRevenueYield(
        @PathVariable("oilId") String oilId,
        @PathVariable("price") BigDecimal price) {
        try {
            return ResponseEntity.ok(oilService.getRevenueYield(oilId, price).toString());
        } catch (Exception ex) {
            HttpStatus httpStatus =
                ex.getClass().isAssignableFrom(OilBussinessException.class) ? NOT_FOUND : INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus)
                .body(String.format("%s - %s", httpStatus.value(), ex.getMessage()));
        }
    }

    @GetMapping(PRICE_EARNINGS_RATIO_PATH + "/{oilId}/{price}")
    public ResponseEntity<String> getPriceEarningsRatio(
        @PathVariable("oilId") String oilId,
        @PathVariable("price") BigDecimal price) {
        try {
            return ResponseEntity.ok(oilService.getPriceEarningsRatio(oilId, price).toString());
        } catch (Exception ex) {
            HttpStatus httpStatus =
                ex.getClass().isAssignableFrom(OilBussinessException.class) ? NOT_FOUND : INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus)
                .body(String.format("%s - %s", httpStatus.value(), ex.getMessage()));
        }
    }

    @PostMapping("/buy/{oilId}/{quantity}/{price}")
    public ResponseEntity<String> buyOilBarrel(
        @PathVariable("oilId") String oilId,
        @PathVariable("quantity") Integer quantity,
        @PathVariable("price") BigDecimal price) {
        try {
            if (oilService.recordTransaction(oilId, TransactionType.BUY, quantity, price)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            HttpStatus httpStatus =
                ex.getClass().isAssignableFrom(OilBussinessException.class) ? NOT_FOUND : INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus)
                .body(String.format("%s - %s", httpStatus.value(), ex.getMessage()));
        }
    }

    @PostMapping("/sell/{oilId}/{quantity}/{price}")
    public ResponseEntity<String> sellOilBarrel(
        @PathVariable("oilId") String oilId,
        @PathVariable("quantity") Integer quantity,
        @PathVariable("price") BigDecimal price) {
        try {
            if (oilService.recordTransaction(oilId, TransactionType.SELL, quantity, price)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            HttpStatus httpStatus =
                ex.getClass().isAssignableFrom(OilBussinessException.class) ? NOT_FOUND : INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus)
                .body(String.format("%s - %s", httpStatus.value(), ex.getMessage()));
        }
    }

    @GetMapping(VOLUME_WEIGHTED_PATH + "/{oilId}")
    public ResponseEntity<String> getVolumeWeightedOilPrice(
        @PathVariable("oilId") String oilId) {
        try {
            return ResponseEntity.ok(oilService.getVolumeWeightedOilPrice(oilId).toString());
        } catch (Exception ex) {
            HttpStatus httpStatus =
                ex.getClass().isAssignableFrom(OilBussinessException.class) ? NOT_FOUND : INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus)
                .body(String.format("%s - %s", httpStatus.value(), ex.getMessage()));
        }
    }

    @GetMapping(INVENTORY_INDEX_PATH)
    public ResponseEntity<String> getInventoryIndex() {
        try {
            return ResponseEntity.ok(String.valueOf(oilService.getInventoryIndex()));
        } catch (Exception ex) {
            HttpStatus httpStatus = INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus)
                .body(String.format("%s - %s", httpStatus.value(), ex.getMessage()));
        }
    }
}
