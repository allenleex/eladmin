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
package me.zhengjie.modules.zjsj.demo.service;

import me.zhengjie.modules.zjsj.demo.domain.TestModel;
import me.zhengjie.modules.zjsj.demo.service.dto.TestDto;
import me.zhengjie.modules.zjsj.demo.service.dto.TestQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author allenleex
* @date 2025-07-22
*/
public interface TestService {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    PageResult<TestDto> queryAll(TestQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param criteria 条件
     * @return /
     */
    List<TestDto> queryAll(TestQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    TestDto findById(Long id);

    /**
     * 创建
     * @param resources /
     */
    void create(TestModel resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(TestModel resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 导出数据
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void download(List<TestDto> queryAll, HttpServletResponse response) throws IOException;
}
