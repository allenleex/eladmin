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
import me.zhengjie.modules.zjsj.enumeration.LeaseOrderStatus;
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
@Table(name="zjsj_lease_order")
public class ZjsjLeaseOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`order_no`",unique = true,nullable = false)
    @NotBlank
    @ApiModelProperty(value = "租赁单号")
    private String orderNo;

    @Column(name = "`project_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "工程项目ID")
    private Long projectId;

    @Column(name = "`start_date`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "租赁开始日")
    private Timestamp startDate;

    @Column(name = "`end_date`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "租赁结束日")
    private Timestamp endDate;

    @Column(name = "`total_fee`")
    @ApiModelProperty(value = "租赁总费用")
    private BigDecimal totalFee;

    @Column(name = "`status`")
    @ApiModelProperty(value = "状态")
    @Enumerated(EnumType.STRING) // 关键：按字符串值映射
    private LeaseOrderStatus status;

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

    public void copy(ZjsjLeaseOrder source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
