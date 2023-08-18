package com.study.aliqlexpress.model;

import java.io.Serializable;

/**
 * Promotion
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/8/17 下午6:01
 * @menu Promotion
 */
public class Promotion implements Serializable {

    /**
     * 产品code
     */
    private String productCode;

    /**
     * 产品价格
     */
    private String price;

    /**
     * 促销活动类型
     */
    private String promotionType;

    /**
     * 促销活动名称
     */
    private String promotionName;

    /**
     * 条件配置
     */
    private String conditionConfig;

    /**
     *
     */
    private String promotionWay;

    /**
     *
     */
    private String promotionValue;

    public Promotion() {

    }

    public Promotion(String productCode, String price, String promotionType, String promotionName, String conditionConfig) {
        this.productCode = productCode;
        this.price = price;
        this.promotionType = promotionType;
        this.promotionName = promotionName;
        this.conditionConfig = conditionConfig;
    }

    /**
     * Gets the value of productCode.
     *
     * @return the value of productCode
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Sets the productCode. *
     * <p>You can use getProductCode() to get the value of productCode</p>
     * * @param productCode productCode
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Gets the value of price.
     *
     * @return the value of price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the price. *
     * <p>You can use getPrice() to get the value of price</p>
     * * @param price price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets the value of promotionType.
     *
     * @return the value of promotionType
     */
    public String getPromotionType() {
        return promotionType;
    }

    /**
     * Sets the promotionType. *
     * <p>You can use getPromotionType() to get the value of promotionType</p>
     * * @param promotionType promotionType
     */
    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    /**
     * Gets the value of promotionName.
     *
     * @return the value of promotionName
     */
    public String getPromotionName() {
        return promotionName;
    }

    /**
     * Sets the promotionName. *
     * <p>You can use getPromotionName() to get the value of promotionName</p>
     * * @param promotionName promotionName
     */
    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    /**
     * Gets the value of conditionConfig.
     *
     * @return the value of conditionConfig
     */
    public String getConditionConfig() {
        return conditionConfig;
    }

    /**
     * Sets the conditionConfig. *
     * <p>You can use getConditionConfig() to get the value of conditionConfig</p>
     * * @param conditionConfig conditionConfig
     */
    public void setConditionConfig(String conditionConfig) {
        this.conditionConfig = conditionConfig;
    }

    /**
     * Gets the value of promotionWay.
     *
     * @return the value of promotionWay
     */
    public String getPromotionWay() {
        return promotionWay;
    }

    /**
     * Sets the promotionWay. *
     * <p>You can use getPromotionWay() to get the value of promotionWay</p>
     * * @param promotionWay promotionWay
     */
    public void setPromotionWay(String promotionWay) {
        this.promotionWay = promotionWay;
    }

    /**
     * Gets the value of promotionValue.
     *
     * @return the value of promotionValue
     */
    public String getPromotionValue() {
        return promotionValue;
    }

    /**
     * Sets the promotionValue. *
     * <p>You can use getPromotionValue() to get the value of promotionValue</p>
     * * @param promotionValue promotionValue
     */
    public void setPromotionValue(String promotionValue) {
        this.promotionValue = promotionValue;
    }
}
