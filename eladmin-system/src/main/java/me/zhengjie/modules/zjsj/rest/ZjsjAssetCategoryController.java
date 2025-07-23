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
import me.zhengjie.modules.zjsj.domain.ZjsjAssetCategory;
import me.zhengjie.modules.zjsj.service.ZjsjAssetCategoryService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetCategoryQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetCategoryDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "资产分类")
@RequestMapping("/api/zjsjAssetCategory")
public class ZjsjAssetCategoryController {

    private final ZjsjAssetCategoryService zjsjAssetCategoryService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjAssetCategory:list')")
    public void exportZjsjAssetCategory(HttpServletResponse response, ZjsjAssetCategoryQueryCriteria criteria) throws IOException {
        zjsjAssetCategoryService.download(zjsjAssetCategoryService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询资产分类")
    @PreAuthorize("@el.check('zjsjAssetCategory:list')")
    public ResponseEntity<PageResult<ZjsjAssetCategoryDto>> queryZjsjAssetCategory(ZjsjAssetCategoryQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjAssetCategoryService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增资产分类")
    @ApiOperation("新增资产分类")
    @PreAuthorize("@el.check('zjsjAssetCategory:add')")
    public ResponseEntity<Object> createZjsjAssetCategory(@Validated @RequestBody ZjsjAssetCategory resources){
        zjsjAssetCategoryService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改资产分类")
    @ApiOperation("修改资产分类")
    @PreAuthorize("@el.check('zjsjAssetCategory:edit')")
    public ResponseEntity<Object> updateZjsjAssetCategory(@Validated @RequestBody ZjsjAssetCategory resources){
        zjsjAssetCategoryService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除资产分类")
    @ApiOperation("删除资产分类")
    @PreAuthorize("@el.check('zjsjAssetCategory:del')")
    public ResponseEntity<Object> deleteZjsjAssetCategory(@ApiParam(value = "传ID数组[]") @RequestBody String[] ids) {
        zjsjAssetCategoryService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}