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
import me.zhengjie.modules.zjsj.domain.ZjsjStorageArea;
import me.zhengjie.modules.zjsj.service.ZjsjStorageAreaService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjStorageAreaQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjStorageAreaDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "存放场地")
@RequestMapping("/api/zjsjStorageArea")
public class ZjsjStorageAreaController {

    private final ZjsjStorageAreaService zjsjStorageAreaService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjStorageArea:list')")
    public void exportZjsjStorageArea(HttpServletResponse response, ZjsjStorageAreaQueryCriteria criteria) throws IOException {
        zjsjStorageAreaService.download(zjsjStorageAreaService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询存放场地")
    @PreAuthorize("@el.check('zjsjStorageArea:list')")
    public ResponseEntity<PageResult<ZjsjStorageAreaDto>> queryZjsjStorageArea(ZjsjStorageAreaQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjStorageAreaService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增存放场地")
    @ApiOperation("新增存放场地")
    @PreAuthorize("@el.check('zjsjStorageArea:add')")
    public ResponseEntity<Object> createZjsjStorageArea(@Validated @RequestBody ZjsjStorageArea resources){
        zjsjStorageAreaService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改存放场地")
    @ApiOperation("修改存放场地")
    @PreAuthorize("@el.check('zjsjStorageArea:edit')")
    public ResponseEntity<Object> updateZjsjStorageArea(@Validated @RequestBody ZjsjStorageArea resources){
        zjsjStorageAreaService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除存放场地")
    @ApiOperation("删除存放场地")
    @PreAuthorize("@el.check('zjsjStorageArea:del')")
    public ResponseEntity<Object> deleteZjsjStorageArea(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjStorageAreaService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}