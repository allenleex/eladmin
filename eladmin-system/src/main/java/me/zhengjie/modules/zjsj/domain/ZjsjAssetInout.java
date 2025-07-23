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
@Table(name="zjsj_asset_inout")
public class ZjsjAssetInout implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`asset_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "资产ID")
    private Long assetId;

    @Column(name = "`type`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "type")
    private unknowType type;

    @Column(name = "`ref_order_id`")
    @ApiModelProperty(value = "关联单号")
    private Long refOrderId;

    @Column(name = "`warehouse_id`")
    @ApiModelProperty(value = "仓库ID")
    private Long warehouseId;

    @Column(name = "`before_location`")
    @ApiModelProperty(value = "移库前货位")
    private String beforeLocation;

    @Column(name = "`after_location`")
    @ApiModelProperty(value = "移库后货位")
    private String afterLocation;

    @Column(name = "`operator_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "操作人")
    private Long operatorId;

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

    public void copy(ZjsjAssetInout source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
