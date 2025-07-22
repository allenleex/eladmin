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
package me.zhengjie.modules.zjsj.service.impl;

import me.zhengjie.modules.zjsj.domain.ZjsjEmployee;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjEmployeeRepository;
import me.zhengjie.modules.zjsj.service.ZjsjEmployeeService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjEmployeeDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjEmployeeQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjEmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author allenleex
* @date 2025-07-22
**/
@Service
@RequiredArgsConstructor
public class ZjsjEmployeeServiceImpl implements ZjsjEmployeeService {

    private final ZjsjEmployeeRepository zjsjEmployeeRepository;
    private final ZjsjEmployeeMapper zjsjEmployeeMapper;

    @Override
    public PageResult<ZjsjEmployeeDto> queryAll(ZjsjEmployeeQueryCriteria criteria, Pageable pageable){
        Page<ZjsjEmployee> page = zjsjEmployeeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjEmployeeMapper::toDto));
    }

    @Override
    public List<ZjsjEmployeeDto> queryAll(ZjsjEmployeeQueryCriteria criteria){
        return zjsjEmployeeMapper.toDto(zjsjEmployeeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjEmployeeDto findById(Long id) {
        ZjsjEmployee zjsjEmployee = zjsjEmployeeRepository.findById(id).orElseGet(ZjsjEmployee::new);
        ValidationUtil.isNull(zjsjEmployee.getId(),"ZjsjEmployee","id",id);
        return zjsjEmployeeMapper.toDto(zjsjEmployee);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjEmployee resources) {
        if(zjsjEmployeeRepository.findByEmpNo(resources.getEmpNo()) != null){
            throw new EntityExistException(ZjsjEmployee.class,"emp_no",resources.getEmpNo());
        }
        zjsjEmployeeRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjEmployee resources) {
        ZjsjEmployee zjsjEmployee = zjsjEmployeeRepository.findById(resources.getId()).orElseGet(ZjsjEmployee::new);
        ValidationUtil.isNull( zjsjEmployee.getId(),"ZjsjEmployee","id",resources.getId());
        ZjsjEmployee zjsjEmployee1 = null;
        zjsjEmployee1 = zjsjEmployeeRepository.findByEmpNo(resources.getEmpNo());
        if(zjsjEmployee1 != null && !zjsjEmployee1.getId().equals(zjsjEmployee.getId())){
            throw new EntityExistException(ZjsjEmployee.class,"emp_no",resources.getEmpNo());
        }
        zjsjEmployee.copy(resources);
        zjsjEmployeeRepository.save(zjsjEmployee);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjEmployeeRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjEmployeeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjEmployeeDto zjsjEmployee : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("工号", zjsjEmployee.getEmpNo());
            map.put(" name",  zjsjEmployee.getName());
            map.put("所属机构", zjsjEmployee.getOrgCode());
            map.put("职位", zjsjEmployee.getPosition());
            map.put("联系方式", zjsjEmployee.getContact());
            map.put("创建者", zjsjEmployee.getCreateBy());
            map.put("更新者", zjsjEmployee.getUpdateBy());
            map.put("创建日期", zjsjEmployee.getCreateTime());
            map.put("更新时间", zjsjEmployee.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}