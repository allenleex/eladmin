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
@Table(name="zjsj_electronic_tag")
public class ZjsjElectronicTag implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "标签物理ID")
    private String id;

    @Column(name = "`type`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "type")
    private Integer type;

    @Column(name = "`bind_asset_id`")
    @ApiModelProperty(value = "绑定资产ID")
    private Long bindAssetId;

    @Column(name = "`bind_time`")
    @ApiModelProperty(value = "绑定时间")
    private Timestamp bindTime;

    @Column(name = "`health_status`")
    @ApiModelProperty(value = "healthStatus")
    private Integer healthStatus;

    @Column(name = "`last_check_time`")
    @ApiModelProperty(value = "最后检测时间")
    private Timestamp lastCheckTime;

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

    public void copy(ZjsjElectronicTag source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
