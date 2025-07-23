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
import me.zhengjie.modules.zjsj.domain.ZjsjTurnoverProfit;
import me.zhengjie.modules.zjsj.service.ZjsjTurnoverProfitService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjTurnoverProfitQueryCriteria;
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
import me.zhengjie.modules.zjsj.service.dto.ZjsjTurnoverProfitDto;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "资产管理：周转物资收益")
@RequestMapping("/api/zjsjTurnoverProfit")
public class ZjsjTurnoverProfitController {

    private final ZjsjTurnoverProfitService zjsjTurnoverProfitService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('zjsjTurnoverProfit:list')")
    public void exportZjsjTurnoverProfit(HttpServletResponse response, ZjsjTurnoverProfitQueryCriteria criteria) throws IOException {
        zjsjTurnoverProfitService.download(zjsjTurnoverProfitService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询周转物资收益")
    @PreAuthorize("@el.check('zjsjTurnoverProfit:list')")
    public ResponseEntity<PageResult<ZjsjTurnoverProfitDto>> queryZjsjTurnoverProfit(ZjsjTurnoverProfitQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(zjsjTurnoverProfitService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增周转物资收益")
    @ApiOperation("新增周转物资收益")
    @PreAuthorize("@el.check('zjsjTurnoverProfit:add')")
    public ResponseEntity<Object> createZjsjTurnoverProfit(@Validated @RequestBody ZjsjTurnoverProfit resources){
        zjsjTurnoverProfitService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改周转物资收益")
    @ApiOperation("修改周转物资收益")
    @PreAuthorize("@el.check('zjsjTurnoverProfit:edit')")
    public ResponseEntity<Object> updateZjsjTurnoverProfit(@Validated @RequestBody ZjsjTurnoverProfit resources){
        zjsjTurnoverProfitService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除周转物资收益")
    @ApiOperation("删除周转物资收益")
    @PreAuthorize("@el.check('zjsjTurnoverProfit:del')")
    public ResponseEntity<Object> deleteZjsjTurnoverProfit(@ApiParam(value = "传ID数组[]") @RequestBody Long[] ids) {
        zjsjTurnoverProfitService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}