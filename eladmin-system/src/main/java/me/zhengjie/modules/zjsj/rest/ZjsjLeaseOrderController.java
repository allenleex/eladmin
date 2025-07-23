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
package me.zhengjie.modules.zjsj.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.zjsj.domain.ZjsjLeaseOrder;
import me.zhengjie.modules.zjsj.service.ZjsjLeaseOrderService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjLeaseOrderQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;
import me.zhengjie.modules.zjsj.service.dto.ZjsjLeaseOrderDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "租赁订单")
@RequestMapping("/api/zjsjLeaseOrder")
public class ZjsjLeaseOrderController {

    private final ZjsjLeaseOrderService zjsjLeaseOrderService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjLeaseOrder:list')")
    public void exportZjsjLeaseOrder(HttpServletResponse response, ZjsjLeaseOrderQueryCriteria criteria) throws IOException {
        zjsjLeaseOrderService.download(zjsjLeaseOrderService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询租赁订单")
    @PreAuthorize("@el.check('zjsjLeaseOrder:list')")
    public ResponseEntity<PageResult<ZjsjLeaseOrderDto>> queryZjsjLeaseOrder(ZjsjLeaseOrderQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjLeaseOrderService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增租赁订单")
    @ApiOperation("新增租赁订单")
    @PreAuthorize("@el.check('zjsjLeaseOrder:add')")
    public ResponseEntity<Object> createZjsjLeaseOrder(@Validated @RequestBody ZjsjLeaseOrder resources){
        zjsjLeaseOrderService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改租赁订单")
    @ApiOperation("修改租赁订单")
    @PreAuthorize("@el.check('zjsjLeaseOrder:edit')")
    public ResponseEntity<Object> updateZjsjLeaseOrder(@Validated @RequestBody ZjsjLeaseOrder resources){
        zjsjLeaseOrderService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除租赁订单")
    @ApiOperation("删除租赁订单")
    @PreAuthorize("@el.check('zjsjLeaseOrder:del')")
    public ResponseEntity<Object> deleteZjsjLeaseOrder(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjLeaseOrderService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}