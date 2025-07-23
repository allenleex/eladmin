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

import me.zhengjie.modules.zjsj.domain.ZjsjAssetCategory;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjAssetCategoryRepository;
import me.zhengjie.modules.zjsj.service.ZjsjAssetCategoryService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetCategoryDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetCategoryQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjAssetCategoryMapper;
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
public class ZjsjAssetCategoryServiceImpl implements ZjsjAssetCategoryService {

    private final ZjsjAssetCategoryRepository zjsjAssetCategoryRepository;
    private final ZjsjAssetCategoryMapper zjsjAssetCategoryMapper;

    @Override
    public PageResult<ZjsjAssetCategoryDto> queryAll(ZjsjAssetCategoryQueryCriteria criteria, Pageable pageable){
        Page<ZjsjAssetCategory> page = zjsjAssetCategoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjAssetCategoryMapper::toDto));
    }

    @Override
    public List<ZjsjAssetCategoryDto> queryAll(ZjsjAssetCategoryQueryCriteria criteria){
        return zjsjAssetCategoryMapper.toDto(zjsjAssetCategoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjAssetCategoryDto findById(String categoryCode) {
        ZjsjAssetCategory zjsjAssetCategory = zjsjAssetCategoryRepository.findById(categoryCode).orElseGet(ZjsjAssetCategory::new);
        ValidationUtil.isNull(zjsjAssetCategory.getCategoryCode(),"ZjsjAssetCategory","categoryCode",categoryCode);
        return zjsjAssetCategoryMapper.toDto(zjsjAssetCategory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjAssetCategory resources) {
        resources.setCategoryCode(IdUtil.simpleUUID()); 
        zjsjAssetCategoryRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjAssetCategory resources) {
        ZjsjAssetCategory zjsjAssetCategory = zjsjAssetCategoryRepository.findById(resources.getCategoryCode()).orElseGet(ZjsjAssetCategory::new);
        ValidationUtil.isNull( zjsjAssetCategory.getCategoryCode(),"ZjsjAssetCategory","id",resources.getCategoryCode());
        zjsjAssetCategory.copy(resources);
        zjsjAssetCategoryRepository.save(zjsjAssetCategory);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String categoryCode : ids) {
            zjsjAssetCategoryRepository.deleteById(categoryCode);
        }
    }

    @Override
    public void download(List<ZjsjAssetCategoryDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjAssetCategoryDto zjsjAssetCategory : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("分类名称", zjsjAssetCategory.getCategoryName());
            map.put("上级分类编码", zjsjAssetCategory.getParentCode());
            map.put("是否设备类", zjsjAssetCategory.getIsEquipment());
            map.put("创建者", zjsjAssetCategory.getCreateBy());
            map.put("更新者", zjsjAssetCategory.getUpdateBy());
            map.put("创建日期", zjsjAssetCategory.getCreateTime());
            map.put("更新时间", zjsjAssetCategory.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}