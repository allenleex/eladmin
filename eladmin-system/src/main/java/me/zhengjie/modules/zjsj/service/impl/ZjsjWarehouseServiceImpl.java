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

import me.zhengjie.modules.zjsj.domain.ZjsjWarehouse;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjWarehouseRepository;
import me.zhengjie.modules.zjsj.service.ZjsjWarehouseService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjWarehouseDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjWarehouseQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjWarehouseMapper;
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
public class ZjsjWarehouseServiceImpl implements ZjsjWarehouseService {

    private final ZjsjWarehouseRepository zjsjWarehouseRepository;
    private final ZjsjWarehouseMapper zjsjWarehouseMapper;

    @Override
    public PageResult<ZjsjWarehouseDto> queryAll(ZjsjWarehouseQueryCriteria criteria, Pageable pageable){
        Page<ZjsjWarehouse> page = zjsjWarehouseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjWarehouseMapper::toDto));
    }

    @Override
    public List<ZjsjWarehouseDto> queryAll(ZjsjWarehouseQueryCriteria criteria){
        return zjsjWarehouseMapper.toDto(zjsjWarehouseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjWarehouseDto findById(Long id) {
        ZjsjWarehouse zjsjWarehouse = zjsjWarehouseRepository.findById(id).orElseGet(ZjsjWarehouse::new);
        ValidationUtil.isNull(zjsjWarehouse.getId(),"ZjsjWarehouse","id",id);
        return zjsjWarehouseMapper.toDto(zjsjWarehouse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjWarehouse resources) {
        if(zjsjWarehouseRepository.findByCode(resources.getCode()) != null){
            throw new EntityExistException(ZjsjWarehouse.class,"code",resources.getCode());
        }
        zjsjWarehouseRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjWarehouse resources) {
        ZjsjWarehouse zjsjWarehouse = zjsjWarehouseRepository.findById(resources.getId()).orElseGet(ZjsjWarehouse::new);
        ValidationUtil.isNull( zjsjWarehouse.getId(),"ZjsjWarehouse","id",resources.getId());
        zjsjWarehouse1 = zjsjWarehouseRepository.findByCode(resources.getCode());
        if(zjsjWarehouse1 != null && !zjsjWarehouse1.getId().equals(zjsjWarehouse.getId())){
            throw new EntityExistException(ZjsjWarehouse.class,"code",resources.getCode());
        }
        zjsjWarehouse.copy(resources);
        zjsjWarehouseRepository.save(zjsjWarehouse);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjWarehouseRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjWarehouseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjWarehouseDto zjsjWarehouse : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("仓库名称", zjsjWarehouse.getName());
            map.put("仓库编码", zjsjWarehouse.getCode());
            map.put("所属场地ID", zjsjWarehouse.getStorageAreaId());
            map.put("仓库容量(平方米)", zjsjWarehouse.getCapacity());
            map.put("创建者", zjsjWarehouse.getCreateBy());
            map.put("更新者", zjsjWarehouse.getUpdateBy());
            map.put("创建日期", zjsjWarehouse.getCreateTime());
            map.put("更新时间", zjsjWarehouse.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}