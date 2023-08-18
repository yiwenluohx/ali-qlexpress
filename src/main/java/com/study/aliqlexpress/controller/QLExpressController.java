package com.study.aliqlexpress.controller;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.study.aliqlexpress.service.QLExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 阿里QLExpress学习
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/8/17 下午4:07
 * @menu 阿里QLExpress学习
 */
@RequestMapping("/ql/express")
@RestController
public class QLExpressController {

    @Autowired
    private QLExpressService expressService;

    @GetMapping(value = "/test")
    public ResponseEntity<String> test() throws Exception{
        //初试
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "a + b * c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);

        System.out.println("开始获取商品优惠金额");
        long startTime = System.currentTimeMillis();
        String cart = "{\n" +
                "\t\"productCode\": \"11120782\",\n" +
                "\t\"price\": \"150.0\"\n" +
                "}";
        double minPrice =  expressService.queryMinPromotionPrice(cart,50);
        System.out.println("获取商品优惠金额结束，耗时："+(System.currentTimeMillis()-startTime)+"毫秒");

        return new ResponseEntity(minPrice+"", HttpStatus.OK);
    }
}
