/*
*  Copyright 2019-2025 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.eco.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import me.zhengjie.aspect.LimitAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate; // 新增导入
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpClientErrorException; // 新增导入
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
* @website https://allenleex.top
* @author allenleex
* @date 2025-08-07
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "ECO接口")
@RequestMapping("/api/eco")
public class EcoController {
    private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);
    private final RestTemplate restTemplate; // 注入RestTemplate

    @GetMapping(value = "/test")
    public Object ecoTest(
        @ApiParam(value = "日期，格式为yyyyMMdd", example = "20250701", required = true)
        @RequestParam("collectionName") String collectionName,
        
        @ApiParam(value = "筛选字段名", example = "bid", required = true)
        @RequestParam("filterField") String filterField,
        
        @ApiParam(value = "筛选字段值", example = "221", required = true)
        @RequestParam("filterValue") String filterValue,
        
        @ApiParam(value = "返回记录数量", example = "10", required = true)
        @RequestParam("limit") Integer limit
    ) {
        logger.info("===================== ecoTest =====================");
        logger.info("params: collectionName={}, filterField={}, filterValue={}, limit={}", collectionName, filterField, filterValue, limit);
        
        // String apiUrl = "http://localhost:8001/mongo?field=time&value=1751328002&projection=bid"; // 目标API地址
        // String apiUrl = "http://localhost:8001/records/count"; // 目标API地址
        String apiUrl = "http://localhost:8001/v1/list/all"; // 目标API地址
        
        try {
            // 构建查询参数
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("collectionName", collectionName)
                .queryParam("filterField", filterField)
                .queryParam("filterValue", filterValue)
                .queryParam("limit", limit);
            
            String fullUrl = builder.toUriString();
            logger.info("API URL: {}", fullUrl);
            
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // 发起GET请求并获取JSON响应
            // ResponseEntity<Object> response = restTemplate.getForEntity(apiUrl, Object.class);
            ResponseEntity<Object> response = restTemplate.exchange(
                fullUrl, 
                HttpMethod.GET, 
                entity, 
                Object.class
            );

            logger.info("API status code: {}", response.getStatusCode());
            logger.info("response type: {}", response.getBody().getClass().getName());

            return response.getBody(); // 直接返回JSON对象

        } catch (HttpClientErrorException e) {
            logger.error("API status code: {}, error: {}", e.getStatusCode(), e.getMessage());
            return "error: " + e.getStatusCode();
        } catch (Exception e) {
            logger.error("API error: {}", e.getMessage());
            return "error: " + e.getMessage();
        }
    }

    @GetMapping(value = "/energy/daily")
    public Object ecoEnergyDaily(
        @ApiParam(value = "bid", example = "201", required = true)
        @RequestParam("bid") String bid,
        
        @ApiParam(value = "mid", example = "1", required = true)
        @RequestParam("mid") String mid,

        @ApiParam(value = "开始日期，格式为yyyyMMdd", example = "20250701", required = true)
        @RequestParam("start_date") String start_date,
        
        @ApiParam(value = "结束日期，格式为yyyyMMdd", example = "20250801", required = true)
        @RequestParam("end_date") String end_date        
    ) {
        logger.info("===================== ecoEppByDate =====================");
        logger.info("params: bid={}, mid={}, start_date={}, end_date={}", bid, mid, start_date, end_date);
        
        String apiUrl = "http://localhost:8001/v1/energy/daily"; // 目标API地址
        
        try {
            // 构建查询参数
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("bid", bid)
                .queryParam("mid", mid)
                .queryParam("start_date", start_date)
                .queryParam("end_date", end_date);
            
            String fullUrl = builder.toUriString();
            logger.info("API URL: {}", fullUrl);
            
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // 发起GET请求并获取JSON响应
            // ResponseEntity<Object> response = restTemplate.getForEntity(apiUrl, Object.class);
            ResponseEntity<Object> response = restTemplate.exchange(
                fullUrl, 
                HttpMethod.GET, 
                entity, 
                Object.class
            );

            logger.info("API status code: {}", response.getStatusCode());
            logger.info("response type: {}", response.getBody().getClass().getName());

            return response.getBody(); // 直接返回JSON对象

        } catch (HttpClientErrorException e) {
            logger.error("API status code: {}, error: {}", e.getStatusCode(), e.getMessage());
            return "error: " + e.getStatusCode();
        } catch (Exception e) {
            logger.error("API error: {}", e.getMessage());
            return "error: " + e.getMessage();
        }
    }    

}