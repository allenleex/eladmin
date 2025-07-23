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

import me.zhengjie.modules.zjsj.domain.ZjsjStorageArea;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjStorageAreaRepository;
import me.zhengjie.modules.zjsj.service.ZjsjStorageAreaService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjStorageAreaDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjStorageAreaQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjStorageAreaMapper;
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
public class ZjsjStorageAreaServiceImpl implements ZjsjStorageAreaService {

    private final ZjsjStorageAreaRepository zjsjStorageAreaRepository;
    private final ZjsjStorageAreaMapper zjsjStorageAreaMapper;

    @Override
    public PageResult<ZjsjStorageAreaDto> queryAll(ZjsjStorageAreaQueryCriteria criteria, Pageable pageable){
        Page<ZjsjStorageArea> page = zjsjStorageAreaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjStorageAreaMapper::toDto));
    }

    @Override
    public List<ZjsjStorageAreaDto> queryAll(ZjsjStorageAreaQueryCriteria criteria){
        return zjsjStorageAreaMapper.toDto(zjsjStorageAreaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjStorageAreaDto findById(Long id) {
        ZjsjStorageArea zjsjStorageArea = zjsjStorageAreaRepository.findById(id).orElseGet(ZjsjStorageArea::new);
        ValidationUtil.isNull(zjsjStorageArea.getId(),"ZjsjStorageArea","id",id);
        return zjsjStorageAreaMapper.toDto(zjsjStorageArea);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjStorageArea resources) {
        zjsjStorageAreaRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjStorageArea resources) {
        ZjsjStorageArea zjsjStorageArea = zjsjStorageAreaRepository.findById(resources.getId()).orElseGet(ZjsjStorageArea::new);
        ValidationUtil.isNull( zjsjStorageArea.getId(),"ZjsjStorageArea","id",resources.getId());
        zjsjStorageArea.copy(resources);
        zjsjStorageAreaRepository.save(zjsjStorageArea);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjStorageAreaRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjStorageAreaDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjStorageAreaDto zjsjStorageArea : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("场地名称", zjsjStorageArea.getName());
            map.put(" type",  zjsjStorageArea.getType());
            map.put("地理坐标", zjsjStorageArea.getLocation());
            map.put("场地容量描述", zjsjStorageArea.getCapacity());
            map.put("负责人ID", zjsjStorageArea.getManagerEmpId());
            map.put("创建者", zjsjStorageArea.getCreateBy());
            map.put("更新者", zjsjStorageArea.getUpdateBy());
            map.put("创建日期", zjsjStorageArea.getCreateTime());
            map.put("更新时间", zjsjStorageArea.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}