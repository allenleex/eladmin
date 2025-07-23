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

import me.zhengjie.modules.zjsj.domain.ZjsjDroneInspection;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjDroneInspectionRepository;
import me.zhengjie.modules.zjsj.service.ZjsjDroneInspectionService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjDroneInspectionDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjDroneInspectionQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjDroneInspectionMapper;
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
public class ZjsjDroneInspectionServiceImpl implements ZjsjDroneInspectionService {

    private final ZjsjDroneInspectionRepository zjsjDroneInspectionRepository;
    private final ZjsjDroneInspectionMapper zjsjDroneInspectionMapper;

    @Override
    public PageResult<ZjsjDroneInspectionDto> queryAll(ZjsjDroneInspectionQueryCriteria criteria, Pageable pageable){
        Page<ZjsjDroneInspection> page = zjsjDroneInspectionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjDroneInspectionMapper::toDto));
    }

    @Override
    public List<ZjsjDroneInspectionDto> queryAll(ZjsjDroneInspectionQueryCriteria criteria){
        return zjsjDroneInspectionMapper.toDto(zjsjDroneInspectionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjDroneInspectionDto findById(Long id) {
        ZjsjDroneInspection zjsjDroneInspection = zjsjDroneInspectionRepository.findById(id).orElseGet(ZjsjDroneInspection::new);
        ValidationUtil.isNull(zjsjDroneInspection.getId(),"ZjsjDroneInspection","id",id);
        return zjsjDroneInspectionMapper.toDto(zjsjDroneInspection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjDroneInspection resources) {
        zjsjDroneInspectionRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjDroneInspection resources) {
        ZjsjDroneInspection zjsjDroneInspection = zjsjDroneInspectionRepository.findById(resources.getId()).orElseGet(ZjsjDroneInspection::new);
        ValidationUtil.isNull( zjsjDroneInspection.getId(),"ZjsjDroneInspection","id",resources.getId());
        zjsjDroneInspection.copy(resources);
        zjsjDroneInspectionRepository.save(zjsjDroneInspection);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjDroneInspectionRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjDroneInspectionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjDroneInspectionDto zjsjDroneInspection : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("无人机编号", zjsjDroneInspection.getDroneId());
            map.put("巡检场地ID", zjsjDroneInspection.getStorageAreaId());
            map.put("巡检路径坐标集", zjsjDroneInspection.getPathData());
            map.put("异常汇总", zjsjDroneInspection.getResultSummary());
            map.put(" inspectionTime",  zjsjDroneInspection.getInspectionTime());
            map.put("创建者", zjsjDroneInspection.getCreateBy());
            map.put("更新者", zjsjDroneInspection.getUpdateBy());
            map.put("创建日期", zjsjDroneInspection.getCreateTime());
            map.put("更新时间", zjsjDroneInspection.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}