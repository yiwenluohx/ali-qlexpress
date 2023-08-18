package com.study.aliqlexpress.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.study.aliqlexpress.model.Promotion;
import com.study.aliqlexpress.model.RuleTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/8/17 下午4:34
 * @menu
 */
@Service
public class QLExpressService {

    /**
     * 计算最优促销活动价格
     *
     * @param cart        购物车信息
     * @param shippingFee 运费
     * @return 计算出最优价格
     * @throws Exception
     */
    public double queryMinPromotionPrice(String cart, double shippingFee) {
        JSONArray cartArray = JSONObject.parseArray(cart);
        double sum = 0.0, sumNicePrice = 0.0, sumNiceShipping = 0.0;
        for (int i = 0; i < cartArray.size(); i++) {
            double minNicePrice = 0.0d;
            String productCode = cartArray.getJSONObject(i).getString("productCode");
            double productPrice = cartArray.getJSONObject(i).getDoubleValue("price");
            //查询商品参与的促销活动
            //数据源
            List<Promotion> promotionList = queryPromotion(productCode);
            sum += productPrice;
            //根据促销活动匹配的规则计算出优惠价格
            List<Double> nicePrices = querySinglePromotionPrice(promotionList, productPrice);
            //计算出最便宜的优惠价格
            minNicePrice = Collections.min(nicePrices);
            sumNicePrice += minNicePrice;
            //运费
            double niceShipping = queryMinShippingFree(promotionList, productPrice, shippingFee);
            sumNiceShipping += niceShipping;
        }
        sum = sum + shippingFee;
        System.out.println("参与促销之前购物车结算价格为：" + sum);
        sumNicePrice = sumNicePrice + sumNiceShipping;
        System.out.println("参与促销之后购物车结算价格金额：" + sumNicePrice);

        return sumNicePrice;
    }


    /**
     * 查询商品促销活动匹配单品促销规则计算后的优惠金额
     *
     * @param promotionList 参与的促销活动
     * @param productPrice  商品价格
     * @return
     */
    private List<Double> querySinglePromotionPrice(List<Promotion> promotionList, double productPrice) {
        List<Double> nicePrices = Lists.newArrayList();
        DefaultContext<String, Object> context = new DefaultContext<>();
        ExpressRunner expressRunner = new ExpressRunner();
        promotionList = promotionList.stream().filter(r -> "singlePromotion".equals(r.getPromotionType())).collect(Collectors.toList());
        for (Promotion promotion : promotionList) {
            //查询促销活动对应的规则模版配置信息
            RuleTemplate template = queryRuleTemplate(promotion.getPromotionType());
            String express = template.getExpress();
            context.put("price", productPrice);
            context.put("promotionType", promotion.getPromotionWay());
            context.put("promotionValue", Double.parseDouble(promotion.getPromotionValue()));

            System.out.println("参与促销活动【" + promotion.getPromotionName() + "】优惠前的商品金额：" + productPrice);
            Object execute = null;
            try {
                execute = expressRunner.execute(express, context, null, true, true);
            } catch (Exception e) {
                System.out.println("计算规则[" + template.getTemplateName() + "]时发生异常，原因：" + e.getMessage());
            }
            System.out.println("优惠后的金额：" + execute);
            nicePrices.add((Double) execute);
        }

        return nicePrices;
    }

    /**
     * 查询最小运输免费
     *
     * @param promotionList 促销活动列表
     * @param productPrice  产品价格
     * @param freeShipping  运费
     * @return double
     */
    private double queryMinShippingFree(List<Promotion> promotionList, double productPrice, double freeShipping) {
        DefaultContext<String, Object> context = new DefaultContext<>();
        double minShipping = 0.0;
        List<Double> niceShipping = Lists.newArrayList();
        ExpressRunner expressRunner = new ExpressRunner();
        promotionList = promotionList.stream().filter(r -> "freeShipping".equals(r.getPromotionType())).collect(Collectors.toList());
        for (Promotion promotion : promotionList) {
            RuleTemplate template = queryRuleTemplate(promotion.getPromotionType());
            String express = template.getExpress();
            String condition = promotion.getConditionConfig();
            context.put("price", productPrice);
            context.put("amount", Double.parseDouble(JSONObject.parseObject(condition).getString("amountCondition")));
            context.put("shippingPrice", freeShipping);
            context.put("promotionValue", Double.parseDouble(JSONObject.parseObject(condition).getString("promotionAmount")));

            System.out.println("参与促销活动【" + promotion.getPromotionName() + "】优惠前的运费：" + freeShipping);
            Object execute = null;
            try {
                execute = expressRunner.execute(express, context, null, true, true);
            } catch (Exception e) {
                System.out.println("计算规则[" + template.getTemplateName() + "]时发生异常，原因：" + e.getMessage());
            }
            niceShipping.add((Double) execute);
        }

        if (niceShipping.isEmpty()) {
            return minShipping;
        }
        minShipping = Collections.min(niceShipping);
        System.out.println("优惠后的运费：" + minShipping);
        return minShipping;
    }

    /**
     * 根据促销类型查询规则模板（数据）
     *
     * @param promotionType 促销类型
     * @return {@link RuleTemplate}
     */
    private RuleTemplate queryRuleTemplate(String promotionType) {
        List<RuleTemplate> templates = Lists.newArrayList(
                new RuleTemplate(
                        "singlePromotion",
                        "单品促销",
                        "if promotionType == 'discount' then {return price * promotionValue;}" +
                                " else if promotionType == 'special' then {return promotionValue;}" +
                                " else {return price-promotionValue}",
                        1),
                new RuleTemplate(
                        "freeShipping",
                        "包邮促销",
                        "if price >= amount then {return shippingPrice-promotionValue;} else {return shippingPrice;}",
                        1),
                new RuleTemplate(
                        "orderPromotion",
                        "整单促销",
                        "if orderPrice >= amount then { return orderPrice - promotionValue;} else {return orderPrice;}",
                        1),
                new RuleTemplate(
                        "orderShippingPromotion",
                        "整单促销运费优惠",
                        "if orderPrice >= amount then { return shippingPrice - promotionValue;} else {return shippingPrice;}",
                        1),
                new RuleTemplate(
                        "orderProductPromotion",
                        "整单促销满减优惠",
                        "if orderPrice >= amount then { return orderPrice - promotionValue;} else {return orderPrice;}",
                        1)
        );
//        templates.stream().filter(k->k.get)

        return null;
    }

    /**
     * 查询促销商品（数据）
     *
     * @param productCode 产品代码
     * @return {@link List}<{@link Promotion}>
     */
    private List<Promotion> queryPromotion(String productCode) {
        List<Promotion> proList = Lists.newArrayList(
                new Promotion("11120782", "15.89", "discount", "单品促销打折", ""),
                new Promotion("11120782", "115.2", "discount", "单品促销打折", ""),
                new Promotion("11120782", "89", "discount", "单品促销打折", ""),
                new Promotion("11120781", "52", "special", "单品促销优惠", ""),
                new Promotion("11120781", "78.16", "special", "单品促销优惠", ""),
                new Promotion("11120781", "8.8", "special", "单品促销优惠", "")
        );
        return proList.stream().filter(k -> k.getProductCode().equals(productCode)).collect(Collectors.toList());
    }
}
