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
import me.zhengjie.modules.zjsj.domain.ZjsjMaintenanceRecord;
import me.zhengjie.modules.zjsj.service.ZjsjMaintenanceRecordService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjMaintenanceRecordQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjMaintenanceRecordDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "资产管理：维护记录")
@RequestMapping("/api/zjsjMaintenanceRecord")
public class ZjsjMaintenanceRecordController {

    private final ZjsjMaintenanceRecordService zjsjMaintenanceRecordService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjMaintenanceRecord:list')")
    public void exportZjsjMaintenanceRecord(HttpServletResponse response, ZjsjMaintenanceRecordQueryCriteria criteria) throws IOException {
        zjsjMaintenanceRecordService.download(zjsjMaintenanceRecordService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询维护记录")
    @PreAuthorize("@el.check('zjsjMaintenanceRecord:list')")
    public ResponseEntity<PageResult<ZjsjMaintenanceRecordDto>> queryZjsjMaintenanceRecord(ZjsjMaintenanceRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjMaintenanceRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增维护记录")
    @ApiOperation("新增维护记录")
    @PreAuthorize("@el.check('zjsjMaintenanceRecord:add')")
    public ResponseEntity<Object> createZjsjMaintenanceRecord(@Validated @RequestBody ZjsjMaintenanceRecord resources){
        zjsjMaintenanceRecordService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改维护记录")
    @ApiOperation("修改维护记录")
    @PreAuthorize("@el.check('zjsjMaintenanceRecord:edit')")
    public ResponseEntity<Object> updateZjsjMaintenanceRecord(@Validated @RequestBody ZjsjMaintenanceRecord resources){
        zjsjMaintenanceRecordService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除维护记录")
    @ApiOperation("删除维护记录")
    @PreAuthorize("@el.check('zjsjMaintenanceRecord:del')")
    public ResponseEntity<Object> deleteZjsjMaintenanceRecord(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjMaintenanceRecordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}