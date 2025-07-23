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
import me.zhengjie.modules.zjsj.domain.ZjsjAssetInout;
import me.zhengjie.modules.zjsj.service.ZjsjAssetInoutService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetInoutQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetInoutDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "出入库")
@RequestMapping("/api/zjsjAssetInout")
public class ZjsjAssetInoutController {

    private final ZjsjAssetInoutService zjsjAssetInoutService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjAssetInout:list')")
    public void exportZjsjAssetInout(HttpServletResponse response, ZjsjAssetInoutQueryCriteria criteria) throws IOException {
        zjsjAssetInoutService.download(zjsjAssetInoutService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询出入库")
    @PreAuthorize("@el.check('zjsjAssetInout:list')")
    public ResponseEntity<PageResult<ZjsjAssetInoutDto>> queryZjsjAssetInout(ZjsjAssetInoutQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjAssetInoutService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增出入库")
    @ApiOperation("新增出入库")
    @PreAuthorize("@el.check('zjsjAssetInout:add')")
    public ResponseEntity<Object> createZjsjAssetInout(@Validated @RequestBody ZjsjAssetInout resources){
        zjsjAssetInoutService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改出入库")
    @ApiOperation("修改出入库")
    @PreAuthorize("@el.check('zjsjAssetInout:edit')")
    public ResponseEntity<Object> updateZjsjAssetInout(@Validated @RequestBody ZjsjAssetInout resources){
        zjsjAssetInoutService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除出入库")
    @ApiOperation("删除出入库")
    @PreAuthorize("@el.check('zjsjAssetInout:del')")
    public ResponseEntity<Object> deleteZjsjAssetInout(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjAssetInoutService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}