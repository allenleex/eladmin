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

import me.zhengjie.modules.zjsj.domain.ZjsjMaintenanceRecord;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjMaintenanceRecordRepository;
import me.zhengjie.modules.zjsj.service.ZjsjMaintenanceRecordService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjMaintenanceRecordDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjMaintenanceRecordQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjMaintenanceRecordMapper;
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
public class ZjsjMaintenanceRecordServiceImpl implements ZjsjMaintenanceRecordService {

    private final ZjsjMaintenanceRecordRepository zjsjMaintenanceRecordRepository;
    private final ZjsjMaintenanceRecordMapper zjsjMaintenanceRecordMapper;

    @Override
    public PageResult<ZjsjMaintenanceRecordDto> queryAll(ZjsjMaintenanceRecordQueryCriteria criteria, Pageable pageable){
        Page<ZjsjMaintenanceRecord> page = zjsjMaintenanceRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjMaintenanceRecordMapper::toDto));
    }

    @Override
    public List<ZjsjMaintenanceRecordDto> queryAll(ZjsjMaintenanceRecordQueryCriteria criteria){
        return zjsjMaintenanceRecordMapper.toDto(zjsjMaintenanceRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjMaintenanceRecordDto findById(Long id) {
        ZjsjMaintenanceRecord zjsjMaintenanceRecord = zjsjMaintenanceRecordRepository.findById(id).orElseGet(ZjsjMaintenanceRecord::new);
        ValidationUtil.isNull(zjsjMaintenanceRecord.getId(),"ZjsjMaintenanceRecord","id",id);
        return zjsjMaintenanceRecordMapper.toDto(zjsjMaintenanceRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjMaintenanceRecord resources) {
        zjsjMaintenanceRecordRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjMaintenanceRecord resources) {
        ZjsjMaintenanceRecord zjsjMaintenanceRecord = zjsjMaintenanceRecordRepository.findById(resources.getId()).orElseGet(ZjsjMaintenanceRecord::new);
        ValidationUtil.isNull( zjsjMaintenanceRecord.getId(),"ZjsjMaintenanceRecord","id",resources.getId());
        zjsjMaintenanceRecord.copy(resources);
        zjsjMaintenanceRecordRepository.save(zjsjMaintenanceRecord);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjMaintenanceRecordRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjMaintenanceRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjMaintenanceRecordDto zjsjMaintenanceRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("资产ID", zjsjMaintenanceRecord.getAssetId());
            map.put(" type",  zjsjMaintenanceRecord.getType());
            map.put("维护费用", zjsjMaintenanceRecord.getCost());
            map.put("维护内容", zjsjMaintenanceRecord.getContent());
            map.put("处理结果", zjsjMaintenanceRecord.getResult());
            map.put(" maintenanceDate",  zjsjMaintenanceRecord.getMaintenanceDate());
            map.put("创建者", zjsjMaintenanceRecord.getCreateBy());
            map.put("更新者", zjsjMaintenanceRecord.getUpdateBy());
            map.put("创建日期", zjsjMaintenanceRecord.getCreateTime());
            map.put("更新时间", zjsjMaintenanceRecord.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}