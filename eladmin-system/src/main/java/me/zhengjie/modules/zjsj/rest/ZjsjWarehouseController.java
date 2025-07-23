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
import me.zhengjie.modules.zjsj.domain.ZjsjWarehouse;
import me.zhengjie.modules.zjsj.service.ZjsjWarehouseService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjWarehouseQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjWarehouseDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "资产管理：仓库信息")
@RequestMapping("/api/zjsjWarehouse")
public class ZjsjWarehouseController {

    private final ZjsjWarehouseService zjsjWarehouseService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjWarehouse:list')")
    public void exportZjsjWarehouse(HttpServletResponse response, ZjsjWarehouseQueryCriteria criteria) throws IOException {
        zjsjWarehouseService.download(zjsjWarehouseService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询仓库信息")
    @PreAuthorize("@el.check('zjsjWarehouse:list')")
    public ResponseEntity<PageResult<ZjsjWarehouseDto>> queryZjsjWarehouse(ZjsjWarehouseQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjWarehouseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增仓库信息")
    @ApiOperation("新增仓库信息")
    @PreAuthorize("@el.check('zjsjWarehouse:add')")
    public ResponseEntity<Object> createZjsjWarehouse(@Validated @RequestBody ZjsjWarehouse resources){
        zjsjWarehouseService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改仓库信息")
    @ApiOperation("修改仓库信息")
    @PreAuthorize("@el.check('zjsjWarehouse:edit')")
    public ResponseEntity<Object> updateZjsjWarehouse(@Validated @RequestBody ZjsjWarehouse resources){
        zjsjWarehouseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除仓库信息")
    @ApiOperation("删除仓库信息")
    @PreAuthorize("@el.check('zjsjWarehouse:del')")
    public ResponseEntity<Object> deleteZjsjWarehouse(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjWarehouseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}