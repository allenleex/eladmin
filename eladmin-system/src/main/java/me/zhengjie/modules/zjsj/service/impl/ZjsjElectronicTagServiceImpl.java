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

import me.zhengjie.modules.zjsj.domain.ZjsjElectronicTag;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjElectronicTagRepository;
import me.zhengjie.modules.zjsj.service.ZjsjElectronicTagService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjElectronicTagDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjElectronicTagQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjElectronicTagMapper;
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
public class ZjsjElectronicTagServiceImpl implements ZjsjElectronicTagService {

    private final ZjsjElectronicTagRepository zjsjElectronicTagRepository;
    private final ZjsjElectronicTagMapper zjsjElectronicTagMapper;

    @Override
    public PageResult<ZjsjElectronicTagDto> queryAll(ZjsjElectronicTagQueryCriteria criteria, Pageable pageable){
        Page<ZjsjElectronicTag> page = zjsjElectronicTagRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjElectronicTagMapper::toDto));
    }

    @Override
    public List<ZjsjElectronicTagDto> queryAll(ZjsjElectronicTagQueryCriteria criteria){
        return zjsjElectronicTagMapper.toDto(zjsjElectronicTagRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjElectronicTagDto findById(String id) {
        ZjsjElectronicTag zjsjElectronicTag = zjsjElectronicTagRepository.findById(id).orElseGet(ZjsjElectronicTag::new);
        ValidationUtil.isNull(zjsjElectronicTag.getId(),"ZjsjElectronicTag","id",id);
        return zjsjElectronicTagMapper.toDto(zjsjElectronicTag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjElectronicTag resources) {
        resources.setId(IdUtil.simpleUUID()); 
        zjsjElectronicTagRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjElectronicTag resources) {
        ZjsjElectronicTag zjsjElectronicTag = zjsjElectronicTagRepository.findById(resources.getId()).orElseGet(ZjsjElectronicTag::new);
        ValidationUtil.isNull( zjsjElectronicTag.getId(),"ZjsjElectronicTag","id",resources.getId());
        zjsjElectronicTag.copy(resources);
        zjsjElectronicTagRepository.save(zjsjElectronicTag);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            zjsjElectronicTagRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjElectronicTagDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjElectronicTagDto zjsjElectronicTag : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" type",  zjsjElectronicTag.getType());
            map.put("绑定资产ID", zjsjElectronicTag.getBindAssetId());
            map.put("绑定时间", zjsjElectronicTag.getBindTime());
            map.put(" healthStatus",  zjsjElectronicTag.getHealthStatus());
            map.put("最后检测时间", zjsjElectronicTag.getLastCheckTime());
            map.put("创建者", zjsjElectronicTag.getCreateBy());
            map.put("更新者", zjsjElectronicTag.getUpdateBy());
            map.put("创建日期", zjsjElectronicTag.getCreateTime());
            map.put("更新时间", zjsjElectronicTag.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}