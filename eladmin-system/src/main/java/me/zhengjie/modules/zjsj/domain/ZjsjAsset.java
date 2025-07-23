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
package me.zhengjie.modules.zjsj.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author allenleex
* @date 2025-07-23
**/
@Entity
@Data
@Table(name="zjsj_asset")
public class ZjsjAsset implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "资产唯一ID")
    private Long id;

    @Column(name = "`asset_no`",unique = true,nullable = false)
    @NotBlank
    @ApiModelProperty(value = "固定资产编号(RFID/UUID)")
    private String assetNo;

    @Column(name = "`name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "资产名称")
    private String name;

    @Column(name = "`category_code`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "分类编码")
    private String categoryCode;

    @Column(name = "`model`")
    @ApiModelProperty(value = "规格型号")
    private String model;

    @Column(name = "`unit`")
    @ApiModelProperty(value = "计量单位")
    private String unit;

    @Column(name = "`original_value`")
    @ApiModelProperty(value = "资产原值")
    private BigDecimal originalValue;

    @Column(name = "`status`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "status")
    private Integer status;

    @Column(name = "`is_turnover`")
    @ApiModelProperty(value = "是否周转物资")
    private Integer isTurnover;

    @Column(name = "`storage_area_id`")
    @ApiModelProperty(value = "存放场地ID")
    private Long storageAreaId;

    @Column(name = "`warehouse_id`")
    @ApiModelProperty(value = "仓库ID")
    private Long warehouseId;

    @Column(name = "`storage_location`")
    @ApiModelProperty(value = "货位号")
    private String storageLocation;

    @Column(name = "`purchase_date`")
    @ApiModelProperty(value = "购置日期")
    private Timestamp purchaseDate;

    @Column(name = "`estimated_life`")
    @ApiModelProperty(value = "预计使用年限")
    private Integer estimatedLife;

    @Column(name = "`create_by`")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Column(name = "`update_by`")
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @Column(name = "`create_time`")
    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @Column(name = "`update_time`")
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(ZjsjAsset source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
