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
import me.zhengjie.modules.zjsj.domain.ZjsjEmployee;
import me.zhengjie.modules.zjsj.service.ZjsjEmployeeService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjEmployeeQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjEmployeeDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-22
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "员工档案")
@RequestMapping("/api/zjsjEmployee")
public class ZjsjEmployeeController {

    private final ZjsjEmployeeService zjsjEmployeeService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjEmployee:list')")
    public void exportZjsjEmployee(HttpServletResponse response, ZjsjEmployeeQueryCriteria criteria) throws IOException {
        zjsjEmployeeService.download(zjsjEmployeeService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询员工档案")
    @PreAuthorize("@el.check('zjsjEmployee:list')")
    public ResponseEntity<PageResult<ZjsjEmployeeDto>> queryZjsjEmployee(ZjsjEmployeeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjEmployeeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增员工档案")
    @ApiOperation("新增员工档案")
    @PreAuthorize("@el.check('zjsjEmployee:add')")
    public ResponseEntity<Object> createZjsjEmployee(@Validated @RequestBody ZjsjEmployee resources){
        zjsjEmployeeService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改员工档案")
    @ApiOperation("修改员工档案")
    @PreAuthorize("@el.check('zjsjEmployee:edit')")
    public ResponseEntity<Object> updateZjsjEmployee(@Validated @RequestBody ZjsjEmployee resources){
        zjsjEmployeeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除员工档案")
    @ApiOperation("删除员工档案")
    @PreAuthorize("@el.check('zjsjEmployee:del')")
    public ResponseEntity<Object> deleteZjsjEmployee(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjEmployeeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}