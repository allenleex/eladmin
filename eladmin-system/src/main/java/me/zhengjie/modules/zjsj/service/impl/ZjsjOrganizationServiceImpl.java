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

import me.zhengjie.modules.zjsj.domain.ZjsjOrganization;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjOrganizationRepository;
import me.zhengjie.modules.zjsj.service.ZjsjOrganizationService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjOrganizationDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjOrganizationQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjOrganizationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
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
public class ZjsjOrganizationServiceImpl implements ZjsjOrganizationService {

    private final ZjsjOrganizationRepository zjsjOrganizationRepository;
    private final ZjsjOrganizationMapper zjsjOrganizationMapper;

    @Override
    public PageResult<ZjsjOrganizationDto> queryAll(ZjsjOrganizationQueryCriteria criteria, Pageable pageable){
        Page<ZjsjOrganization> page = zjsjOrganizationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjOrganizationMapper::toDto));
    }

    @Override
    public List<ZjsjOrganizationDto> queryAll(ZjsjOrganizationQueryCriteria criteria){
        return zjsjOrganizationMapper.toDto(zjsjOrganizationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjOrganizationDto findById(String orgCode) {
        ZjsjOrganization zjsjOrganization = zjsjOrganizationRepository.findById(orgCode).orElseGet(ZjsjOrganization::new);
        ValidationUtil.isNull(zjsjOrganization.getOrgCode(),"ZjsjOrganization","orgCode",orgCode);
        return zjsjOrganizationMapper.toDto(zjsjOrganization);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjOrganization resources) {
        resources.setOrgCode(IdUtil.simpleUUID()); 
        zjsjOrganizationRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjOrganization resources) {
        ZjsjOrganization zjsjOrganization = zjsjOrganizationRepository.findById(resources.getOrgCode()).orElseGet(ZjsjOrganization::new);
        ValidationUtil.isNull( zjsjOrganization.getOrgCode(),"ZjsjOrganization","id",resources.getOrgCode());
        zjsjOrganization.copy(resources);
        zjsjOrganizationRepository.save(zjsjOrganization);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String orgCode : ids) {
            zjsjOrganizationRepository.deleteById(orgCode);
        }
    }

    @Override
    public void download(List<ZjsjOrganizationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjOrganizationDto zjsjOrganization : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("机构名称", zjsjOrganization.getOrgName());
            map.put(" orgType",  zjsjOrganization.getOrgType());
            map.put("上级机构编码", zjsjOrganization.getParentOrgCode());
            map.put("创建者", zjsjOrganization.getCreateBy());
            map.put("更新者", zjsjOrganization.getUpdateBy());
            map.put("创建日期", zjsjOrganization.getCreateTime());
            map.put("更新时间", zjsjOrganization.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}