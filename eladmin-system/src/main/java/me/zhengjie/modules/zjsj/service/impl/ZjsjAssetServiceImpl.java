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

import me.zhengjie.modules.zjsj.domain.ZjsjAsset;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjAssetRepository;
import me.zhengjie.modules.zjsj.service.ZjsjAssetService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjAssetQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjAssetMapper;
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
public class ZjsjAssetServiceImpl implements ZjsjAssetService {

    private final ZjsjAssetRepository zjsjAssetRepository;
    private final ZjsjAssetMapper zjsjAssetMapper;

    @Override
    public PageResult<ZjsjAssetDto> queryAll(ZjsjAssetQueryCriteria criteria, Pageable pageable){
        Page<ZjsjAsset> page = zjsjAssetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjAssetMapper::toDto));
    }

    @Override
    public List<ZjsjAssetDto> queryAll(ZjsjAssetQueryCriteria criteria){
        return zjsjAssetMapper.toDto(zjsjAssetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjAssetDto findById(Long id) {
        ZjsjAsset zjsjAsset = zjsjAssetRepository.findById(id).orElseGet(ZjsjAsset::new);
        ValidationUtil.isNull(zjsjAsset.getId(),"ZjsjAsset","id",id);
        return zjsjAssetMapper.toDto(zjsjAsset);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjAsset resources) {
        if(zjsjAssetRepository.findByAssetNo(resources.getAssetNo()) != null){
            throw new EntityExistException(ZjsjAsset.class,"asset_no",resources.getAssetNo());
        }
        zjsjAssetRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjAsset resources) {
        ZjsjAsset zjsjAsset = zjsjAssetRepository.findById(resources.getId()).orElseGet(ZjsjAsset::new);
        ValidationUtil.isNull( zjsjAsset.getId(),"ZjsjAsset","id",resources.getId());
        ZjsjAsset zjsjAsset1 = null;
        zjsjAsset1 = zjsjAssetRepository.findByAssetNo(resources.getAssetNo());
        if(zjsjAsset1 != null && !zjsjAsset1.getId().equals(zjsjAsset.getId())){
            throw new EntityExistException(ZjsjAsset.class,"asset_no",resources.getAssetNo());
        }
        zjsjAsset.copy(resources);
        zjsjAssetRepository.save(zjsjAsset);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjAssetRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjAssetDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjAssetDto zjsjAsset : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("固定资产编号(RFID/UUID)", zjsjAsset.getAssetNo());
            map.put("资产名称", zjsjAsset.getName());
            map.put("分类编码", zjsjAsset.getCategoryCode());
            map.put("规格型号", zjsjAsset.getModel());
            map.put("计量单位", zjsjAsset.getUnit());
            map.put("资产原值", zjsjAsset.getOriginalValue());
            map.put(" status",  zjsjAsset.getStatus());
            map.put("是否周转物资", zjsjAsset.getIsTurnover());
            map.put("存放场地ID", zjsjAsset.getStorageAreaId());
            map.put("仓库ID", zjsjAsset.getWarehouseId());
            map.put("货位号", zjsjAsset.getStorageLocation());
            map.put("购置日期", zjsjAsset.getPurchaseDate());
            map.put("预计使用年限", zjsjAsset.getEstimatedLife());
            map.put("创建者", zjsjAsset.getCreateBy());
            map.put("更新者", zjsjAsset.getUpdateBy());
            map.put("创建日期", zjsjAsset.getCreateTime());
            map.put("更新时间", zjsjAsset.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}