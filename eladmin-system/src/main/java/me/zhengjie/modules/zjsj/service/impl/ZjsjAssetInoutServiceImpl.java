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

import me.zhengjie.modules.zjsj.domain.ZjsjAssetInout;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjAssetInoutRepository;
import me.zhengjie.modules.zjsj.service.ZjsjAssetInoutService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetInoutDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetInoutQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjAssetInoutMapper;
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
public class ZjsjAssetInoutServiceImpl implements ZjsjAssetInoutService {

    private final ZjsjAssetInoutRepository zjsjAssetInoutRepository;
    private final ZjsjAssetInoutMapper zjsjAssetInoutMapper;

    @Override
    public PageResult<ZjsjAssetInoutDto> queryAll(ZjsjAssetInoutQueryCriteria criteria, Pageable pageable){
        Page<ZjsjAssetInout> page = zjsjAssetInoutRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjAssetInoutMapper::toDto));
    }

    @Override
    public List<ZjsjAssetInoutDto> queryAll(ZjsjAssetInoutQueryCriteria criteria){
        return zjsjAssetInoutMapper.toDto(zjsjAssetInoutRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjAssetInoutDto findById(Long id) {
        ZjsjAssetInout zjsjAssetInout = zjsjAssetInoutRepository.findById(id).orElseGet(ZjsjAssetInout::new);
        ValidationUtil.isNull(zjsjAssetInout.getId(),"ZjsjAssetInout","id",id);
        return zjsjAssetInoutMapper.toDto(zjsjAssetInout);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjAssetInout resources) {
        zjsjAssetInoutRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjAssetInout resources) {
        ZjsjAssetInout zjsjAssetInout = zjsjAssetInoutRepository.findById(resources.getId()).orElseGet(ZjsjAssetInout::new);
        ValidationUtil.isNull( zjsjAssetInout.getId(),"ZjsjAssetInout","id",resources.getId());
        zjsjAssetInout.copy(resources);
        zjsjAssetInoutRepository.save(zjsjAssetInout);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjAssetInoutRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjAssetInoutDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjAssetInoutDto zjsjAssetInout : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("资产ID", zjsjAssetInout.getAssetId());
            map.put(" type",  zjsjAssetInout.getType());
            map.put("关联单号", zjsjAssetInout.getRefOrderId());
            map.put("仓库ID", zjsjAssetInout.getWarehouseId());
            map.put("移库前货位", zjsjAssetInout.getBeforeLocation());
            map.put("移库后货位", zjsjAssetInout.getAfterLocation());
            map.put("操作人", zjsjAssetInout.getOperatorId());
            map.put("创建者", zjsjAssetInout.getCreateBy());
            map.put("更新者", zjsjAssetInout.getUpdateBy());
            map.put("创建日期", zjsjAssetInout.getCreateTime());
            map.put("更新时间", zjsjAssetInout.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}