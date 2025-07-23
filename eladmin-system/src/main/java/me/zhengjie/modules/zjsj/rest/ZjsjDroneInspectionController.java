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
import me.zhengjie.modules.zjsj.domain.ZjsjDroneInspection;
import me.zhengjie.modules.zjsj.service.ZjsjDroneInspectionService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjDroneInspectionQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjDroneInspectionDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "无人机巡检")
@RequestMapping("/api/zjsjDroneInspection")
public class ZjsjDroneInspectionController {

    private final ZjsjDroneInspectionService zjsjDroneInspectionService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjDroneInspection:list')")
    public void exportZjsjDroneInspection(HttpServletResponse response, ZjsjDroneInspectionQueryCriteria criteria) throws IOException {
        zjsjDroneInspectionService.download(zjsjDroneInspectionService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询无人机巡检")
    @PreAuthorize("@el.check('zjsjDroneInspection:list')")
    public ResponseEntity<PageResult<ZjsjDroneInspectionDto>> queryZjsjDroneInspection(ZjsjDroneInspectionQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjDroneInspectionService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增无人机巡检")
    @ApiOperation("新增无人机巡检")
    @PreAuthorize("@el.check('zjsjDroneInspection:add')")
    public ResponseEntity<Object> createZjsjDroneInspection(@Validated @RequestBody ZjsjDroneInspection resources){
        zjsjDroneInspectionService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改无人机巡检")
    @ApiOperation("修改无人机巡检")
    @PreAuthorize("@el.check('zjsjDroneInspection:edit')")
    public ResponseEntity<Object> updateZjsjDroneInspection(@Validated @RequestBody ZjsjDroneInspection resources){
        zjsjDroneInspectionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除无人机巡检")
    @ApiOperation("删除无人机巡检")
    @PreAuthorize("@el.check('zjsjDroneInspection:del')")
    public ResponseEntity<Object> deleteZjsjDroneInspection(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjDroneInspectionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}