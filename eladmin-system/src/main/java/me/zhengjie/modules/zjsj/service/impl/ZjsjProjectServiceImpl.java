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

import me.zhengjie.modules.zjsj.domain.ZjsjProject;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjProjectRepository;
import me.zhengjie.modules.zjsj.service.ZjsjProjectService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjProjectDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjProjectQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjProjectMapper;
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
* @date 2025-07-23
**/
@Service
@RequiredArgsConstructor
public class ZjsjProjectServiceImpl implements ZjsjProjectService {

    private final ZjsjProjectRepository zjsjProjectRepository;
    private final ZjsjProjectMapper zjsjProjectMapper;

    @Override
    public PageResult<ZjsjProjectDto> queryAll(ZjsjProjectQueryCriteria criteria, Pageable pageable){
        Page<ZjsjProject> page = zjsjProjectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjProjectMapper::toDto));
    }

    @Override
    public List<ZjsjProjectDto> queryAll(ZjsjProjectQueryCriteria criteria){
        return zjsjProjectMapper.toDto(zjsjProjectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjProjectDto findById(Long id) {
        ZjsjProject zjsjProject = zjsjProjectRepository.findById(id).orElseGet(ZjsjProject::new);
        ValidationUtil.isNull(zjsjProject.getId(),"ZjsjProject","id",id);
        return zjsjProjectMapper.toDto(zjsjProject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjProject resources) {
        if(zjsjProjectRepository.findByProjectNo(resources.getProjectNo()) != null){
            throw new EntityExistException(ZjsjProject.class,"project_no",resources.getProjectNo());
        }
        zjsjProjectRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjProject resources) {
        ZjsjProject zjsjProject = zjsjProjectRepository.findById(resources.getId()).orElseGet(ZjsjProject::new);
        ValidationUtil.isNull( zjsjProject.getId(),"ZjsjProject","id",resources.getId());
        ZjsjProject zjsjProject1 = null;
        zjsjProject1 = zjsjProjectRepository.findByProjectNo(resources.getProjectNo());
        if(zjsjProject1 != null && !zjsjProject1.getId().equals(zjsjProject.getId())){
            throw new EntityExistException(ZjsjProject.class,"project_no",resources.getProjectNo());
        }
        zjsjProject.copy(resources);
        zjsjProjectRepository.save(zjsjProject);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjProjectRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjProjectDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjProjectDto zjsjProject : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("项目编号", zjsjProject.getProjectNo());
            map.put("项目名称", zjsjProject.getName());
            map.put("项目类型", zjsjProject.getType());
            map.put("项目规模描述", zjsjProject.getScale());
            map.put("地理位置(GIS坐标)", zjsjProject.getLocation());
            map.put("责任主体单位", zjsjProject.getResponsibleOrg());
            map.put(" status",  zjsjProject.getStatus());
            map.put("创建者", zjsjProject.getCreateBy());
            map.put("更新者", zjsjProject.getUpdateBy());
            map.put("创建日期", zjsjProject.getCreateTime());
            map.put("更新时间", zjsjProject.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}