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
package me.zhengjie.modules.zjsj.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;

/**
* @website https://eladmin.vip
* @description /
* @author allenleex
* @date 2025-07-23
**/
@Data
public class ZjsjAssetDto implements Serializable {

    @ApiModelProperty(value = "资产唯一ID")
    private Long id;

    @ApiModelProperty(value = "固定资产编号(RFID/UUID)")
    private String assetNo;

    @ApiModelProperty(value = "资产名称")
    private String name;

    @ApiModelProperty(value = "分类编码")
    private String categoryCode;

    @ApiModelProperty(value = "规格型号")
    private String model;

    @ApiModelProperty(value = "计量单位")
    private String unit;

    @ApiModelProperty(value = "资产原值")
    private BigDecimal originalValue;

    @ApiModelProperty(value = "status")
    private Integer status;

    @ApiModelProperty(value = "是否周转物资")
    private Integer isTurnover;

    @ApiModelProperty(value = "存放场地ID")
    private Long storageAreaId;

    @ApiModelProperty(value = "仓库ID")
    private Long warehouseId;

    @ApiModelProperty(value = "货位号")
    private String storageLocation;

    @ApiModelProperty(value = "购置日期")
    private Timestamp purchaseDate;

    @ApiModelProperty(value = "预计使用年限")
    private Integer estimatedLife;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;
}