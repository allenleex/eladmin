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
import me.zhengjie.modules.zjsj.type.StorageAreaType;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
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
@Table(name="zjsj_storage_area")
public class ZjsjStorageArea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "场地名称")
    private String name;

    @Column(name = "`type`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "类型")
    @Enumerated(EnumType.STRING) // 关键：按字符串值映射
    private StorageAreaType type;

    @Column(name = "`location`")
    @ApiModelProperty(value = "地理坐标")
    private String location;

    @Column(name = "`capacity`")
    @ApiModelProperty(value = "场地容量描述")
    private String capacity;

    @Column(name = "`manager_emp_id`")
    @ApiModelProperty(value = "负责人ID")
    private Long managerEmpId;

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

    public void copy(ZjsjStorageArea source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
