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
import me.zhengjie.modules.zjsj.domain.ZjsjProject;
import me.zhengjie.modules.zjsj.service.ZjsjProjectService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjProjectQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjProjectDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "资产管理：工程项目")
@RequestMapping("/api/zjsjProject")
public class ZjsjProjectController {

    private final ZjsjProjectService zjsjProjectService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjProject:list')")
    public void exportZjsjProject(HttpServletResponse response, ZjsjProjectQueryCriteria criteria) throws IOException {
        zjsjProjectService.download(zjsjProjectService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询工程项目")
    @PreAuthorize("@el.check('zjsjProject:list')")
    public ResponseEntity<PageResult<ZjsjProjectDto>> queryZjsjProject(ZjsjProjectQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjProjectService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增工程项目")
    @ApiOperation("新增工程项目")
    @PreAuthorize("@el.check('zjsjProject:add')")
    public ResponseEntity<Object> createZjsjProject(@Validated @RequestBody ZjsjProject resources){
        zjsjProjectService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改工程项目")
    @ApiOperation("修改工程项目")
    @PreAuthorize("@el.check('zjsjProject:edit')")
    public ResponseEntity<Object> updateZjsjProject(@Validated @RequestBody ZjsjProject resources){
        zjsjProjectService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除工程项目")
    @ApiOperation("删除工程项目")
    @PreAuthorize("@el.check('zjsjProject:del')")
    public ResponseEntity<Object> deleteZjsjProject(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjProjectService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}