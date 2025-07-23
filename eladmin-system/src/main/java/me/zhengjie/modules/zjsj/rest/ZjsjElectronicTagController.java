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
import me.zhengjie.modules.zjsj.domain.ZjsjElectronicTag;
import me.zhengjie.modules.zjsj.service.ZjsjElectronicTagService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjElectronicTagQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjElectronicTagDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "电子标签")
@RequestMapping("/api/zjsjElectronicTag")
public class ZjsjElectronicTagController {

    private final ZjsjElectronicTagService zjsjElectronicTagService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjElectronicTag:list')")
    public void exportZjsjElectronicTag(HttpServletResponse response, ZjsjElectronicTagQueryCriteria criteria) throws IOException {
        zjsjElectronicTagService.download(zjsjElectronicTagService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询电子标签")
    @PreAuthorize("@el.check('zjsjElectronicTag:list')")
    public ResponseEntity<PageResult<ZjsjElectronicTagDto>> queryZjsjElectronicTag(ZjsjElectronicTagQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjElectronicTagService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增电子标签")
    @ApiOperation("新增电子标签")
    @PreAuthorize("@el.check('zjsjElectronicTag:add')")
    public ResponseEntity<Object> createZjsjElectronicTag(@Validated @RequestBody ZjsjElectronicTag resources){
        zjsjElectronicTagService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改电子标签")
    @ApiOperation("修改电子标签")
    @PreAuthorize("@el.check('zjsjElectronicTag:edit')")
    public ResponseEntity<Object> updateZjsjElectronicTag(@Validated @RequestBody ZjsjElectronicTag resources){
        zjsjElectronicTagService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除电子标签")
    @ApiOperation("删除电子标签")
    @PreAuthorize("@el.check('zjsjElectronicTag:del')")
    public ResponseEntity<Object> deleteZjsjElectronicTag(@ApiParam(value = "传ID数组[]") @RequestBody String[] ids) {
        zjsjElectronicTagService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}