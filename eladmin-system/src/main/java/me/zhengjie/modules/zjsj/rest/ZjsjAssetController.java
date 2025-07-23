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
import me.zhengjie.modules.zjsj.domain.ZjsjAsset;
import me.zhengjie.modules.zjsj.service.ZjsjAssetService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "资产管理：资产")
@RequestMapping("/api/zjsjAsset")
public class ZjsjAssetController {

    private final ZjsjAssetService zjsjAssetService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjAsset:list')")
    public void exportZjsjAsset(HttpServletResponse response, ZjsjAssetQueryCriteria criteria) throws IOException {
        zjsjAssetService.download(zjsjAssetService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询资产")
    @PreAuthorize("@el.check('zjsjAsset:list')")
    public ResponseEntity<PageResult<ZjsjAssetDto>> queryZjsjAsset(ZjsjAssetQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjAssetService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增资产")
    @ApiOperation("新增资产")
    @PreAuthorize("@el.check('zjsjAsset:add')")
    public ResponseEntity<Object> createZjsjAsset(@Validated @RequestBody ZjsjAsset resources){
        zjsjAssetService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改资产")
    @ApiOperation("修改资产")
    @PreAuthorize("@el.check('zjsjAsset:edit')")
    public ResponseEntity<Object> updateZjsjAsset(@Validated @RequestBody ZjsjAsset resources){
        zjsjAssetService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除资产")
    @ApiOperation("删除资产")
    @PreAuthorize("@el.check('zjsjAsset:del')")
    public ResponseEntity<Object> deleteZjsjAsset(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjAssetService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}