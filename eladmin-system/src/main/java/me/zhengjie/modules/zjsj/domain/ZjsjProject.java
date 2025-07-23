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
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author allenleex
* @date 2025-07-23
**/
@Entity
@Data
@Table(name="zjsj_project")
public class ZjsjProject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`project_no`",unique = true,nullable = false)
    @NotBlank
    @ApiModelProperty(value = "项目编号")
    private String projectNo;

    @Column(name = "`name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "项目名称")
    private String name;

    @Column(name = "`type`")
    @ApiModelProperty(value = "项目类型")
    private unknowType type;

    @Column(name = "`scale`")
    @ApiModelProperty(value = "项目规模描述")
    private String scale;

    @Column(name = "`location`")
    @ApiModelProperty(value = "地理位置(GIS坐标)")
    private unknowType location;

    @Column(name = "`responsible_org`")
    @ApiModelProperty(value = "责任主体单位")
    private String responsibleOrg;

    @Column(name = "`status`")
    @ApiModelProperty(value = "status")
    private unknowType status;

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

    public void copy(ZjsjProject source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
