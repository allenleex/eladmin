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
package me.zhengjie.modules.zjsj.demo.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.zjsj.demo.domain.TestModel;
import me.zhengjie.modules.zjsj.demo.service.TestService;
import me.zhengjie.modules.zjsj.demo.service.dto.TestDto;
import me.zhengjie.modules.zjsj.demo.service.dto.TestQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
* @author allenleex
* @date 2025-07-22
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "测试：测试模型")
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @ApiOperation("导出")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('test:list')")
    public void exportTestModel(HttpServletResponse response, TestQueryCriteria criteria) throws IOException {
        testService.download(testService.queryAll(criteria), response);
    }

    @ApiOperation(value = "查询")
    @GetMapping
    @PreAuthorize("@el.check('test:list')")
    public ResponseEntity<PageResult<TestDto>> queryTestModel(TestQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(testService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增")
    @ApiOperation(value = "新增")
    @PostMapping
    @PreAuthorize("@el.check('test:add')")
    public ResponseEntity<Object> createTestModel(@Validated @RequestBody TestModel resources){
        testService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改")
    @ApiOperation(value = "修改")
    @PutMapping
    @PreAuthorize("@el.check('test:edit')")
    public ResponseEntity<Object> updateTestModel(@Validated @RequestBody TestModel resources){
        testService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation(value = "删除")
    @DeleteMapping
    @PreAuthorize("@el.check('test:del')")
    public ResponseEntity<Object> deleteTestModel(@RequestBody Set<Long> ids){
        testService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
