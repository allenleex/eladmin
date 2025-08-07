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
import lombok.RequiredArgsConstructor;
import me.zhengjie.aspect.LimitAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate; // 新增导入
import org.springframework.web.client.HttpClientErrorException; // 新增导入

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
    public Object ecoTest() {
        logger.info("===================== ecoTest =====================");
        
        // String apiUrl = "http://localhost:8001/mongo?field=time&value=1751328002&projection=bid"; // 目标API地址
        String apiUrl = "http://localhost:8001/records/count"; // 目标API地址
        
        try {
            // 发起GET请求并获取JSON响应
            ResponseEntity<Object> response = restTemplate.getForEntity(apiUrl, Object.class);
            logger.info(response.getBody().toString());
            logger.info("API status code: {}", response.getStatusCode());
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