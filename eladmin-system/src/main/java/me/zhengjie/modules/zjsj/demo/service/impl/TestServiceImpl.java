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
package me.zhengjie.modules.zjsj.demo.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.demo.domain.TestModel;
import me.zhengjie.modules.zjsj.demo.repository.TestRepository;
import me.zhengjie.modules.zjsj.demo.service.TestService;
import me.zhengjie.modules.zjsj.demo.service.dto.TestDto;
import me.zhengjie.modules.zjsj.demo.service.dto.TestQueryCriteria;
import me.zhengjie.modules.zjsj.demo.service.mapstruct.TestMapper;
import me.zhengjie.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* @author allenleex
* @date 2025-07-22
*/
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;

    @Override
    public PageResult<TestDto> queryAll(TestQueryCriteria criteria, Pageable pageable){
        Page<TestModel> page = testRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(testMapper::toDto));
    }

    @Override
    public List<TestDto> queryAll(TestQueryCriteria criteria){
        return testMapper.toDto(testRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public TestDto findById(Long id) {
        TestModel testModel = testRepository.findById(id).orElseGet(TestModel::new);
        ValidationUtil.isNull(testModel.getId(),"TestModel","id",id);
        return testMapper.toDto(testModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(TestModel resources) {
        // 验证应用名称是否存在恶意攻击payload，https://github.com/elunez/eladmin/issues/873
        String name = resources.getName();
        if (name.contains(";") || name.contains("|") || name.contains("&")) {
            throw new IllegalArgumentException("非法的名称，请勿包含[; | &]等特殊字符");
        }
        verification(resources);
        testRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TestModel resources) {
        // 验证应用名称是否存在恶意攻击payload，https://github.com/elunez/eladmin/issues/873
        String name = resources.getName();
        if (name.contains(";") || name.contains("|") || name.contains("&")) {
            throw new IllegalArgumentException("非法的应用名称，请勿包含[; | &]等特殊字符");
        }
        verification(resources);
        TestModel testModel = testRepository.findById(resources.getId()).orElseGet(TestModel::new);
        ValidationUtil.isNull(testModel.getId(),"TestModel","id",resources.getId());
        testModel.copy(resources);
        testRepository.save(testModel);
    }

    private void verification(TestModel resources){
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            testRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TestDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TestDto testDto : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", testDto.getName());
            map.put("创建日期", testDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
