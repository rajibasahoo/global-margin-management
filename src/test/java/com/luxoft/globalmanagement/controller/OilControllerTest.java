package com.luxoft.globalmanagement.controller;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.luxoft.globalmanagement.controller.OilController.BASE_PATH;
import static com.luxoft.globalmanagement.controller.OilController.INVENTORY_INDEX_PATH;
import static com.luxoft.globalmanagement.controller.OilController.PRICE_EARNINGS_RATIO_PATH;
import static com.luxoft.globalmanagement.controller.OilController.REVENUE_YIELD_PATH;
import static com.luxoft.globalmanagement.controller.OilController.VOLUME_WEIGHTED_PATH;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class OilControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldNotFoundRevenueYieldWithoutRequiredParans() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + REVENUE_YIELD_PATH)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotFoundRevenueYieldAbc() throws Exception {
        String oilId = "ABC";
        BigDecimal input = BigDecimal.TEN;
        String expect = "404 - Oil Id not found";

        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + REVENUE_YIELD_PATH + "/" + oilId + "/" + input)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string(equalTo(expect)));
    }

    @Test
    public void shouldReturnRevenueYieldForStandardBwo() throws Exception {
        String oilId = "BWO";
        BigDecimal input = BigDecimal.TEN;
        BigDecimal expect = BigDecimal.valueOf(1.7).setScale(2, RoundingMode.FLOOR);

        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + REVENUE_YIELD_PATH + "/" + oilId + "/" + input)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(expect.toString())));
    }

    @Test
    public void shouldReturnRevenueYieldForPremiumTim() throws Exception {
        String oilId = "TIM";
        BigDecimal input = BigDecimal.TEN;
        BigDecimal expect = BigDecimal.valueOf(0.77);

        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + REVENUE_YIELD_PATH + "/" + oilId + "/" + input)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(expect.toString())));
    }

    @Test
    public void shouldNotFoundPriceEarningsRatioWithoutRequiredParans() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + PRICE_EARNINGS_RATIO_PATH)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotFoundPriceEarningsRatioAbc() throws Exception {
        String oilId = "ABC";
        BigDecimal input = BigDecimal.TEN;
        String expect = "404 - Oil Id not found";

        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + PRICE_EARNINGS_RATIO_PATH + "/" + oilId + "/" + input)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string(equalTo(expect)));
    }

    @Test
    public void shouldReturnPriceEarningsRatioForStandardBwo() throws Exception {
        String oilId = "BWO";
        BigDecimal input = BigDecimal.TEN;
        BigDecimal expect = BigDecimal.valueOf(0.58);

        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + PRICE_EARNINGS_RATIO_PATH + "/" + oilId + "/" + input)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(expect.toString())));
    }

    @Test
    public void shouldReturnPriceEarningsRatioForPremiumTim() throws Exception {
        String oilId = "TIM";
        BigDecimal input = BigDecimal.TEN;
        BigDecimal expect = BigDecimal.valueOf(2).setScale(2, RoundingMode.FLOOR);

        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + PRICE_EARNINGS_RATIO_PATH + "/" + oilId + "/" + input)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(expect.toString())));
    }

    @Test
    @Order(1)
    public void shouldBuySellAndCalculateVolumeWeightedForRew() throws Exception {
        String oilId = "REW";
        int buyQnt = 50;
        double buyPrice = 69.90;
        int sellQnt = 20;
        double sellPrice = 71.10;
        double expect = (buyPrice * buyQnt + sellPrice * sellQnt) / (buyQnt + sellQnt);

        mvc.perform(MockMvcRequestBuilders.post(BASE_PATH + "/buy/" + oilId + "/" + buyQnt + "/" + buyPrice)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.post(BASE_PATH + "/sell/" + oilId + "/" + sellQnt + "/" + sellPrice)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + VOLUME_WEIGHTED_PATH + "/" + oilId)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(String.valueOf(expect))));
    }

    @Test
    @Order(2)
    public void shouldReturnInventoryIndexBasedOnLastTransactions() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_PATH + INVENTORY_INDEX_PATH)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsStringIgnoringCase("11.")));
    }
}