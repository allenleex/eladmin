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

import me.zhengjie.modules.zjsj.domain.ZjsjLeaseOrder;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjLeaseOrderRepository;
import me.zhengjie.modules.zjsj.service.ZjsjLeaseOrderService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjLeaseOrderDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjLeaseOrderQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjLeaseOrderMapper;
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
public class ZjsjLeaseOrderServiceImpl implements ZjsjLeaseOrderService {

    private final ZjsjLeaseOrderRepository zjsjLeaseOrderRepository;
    private final ZjsjLeaseOrderMapper zjsjLeaseOrderMapper;

    @Override
    public PageResult<ZjsjLeaseOrderDto> queryAll(ZjsjLeaseOrderQueryCriteria criteria, Pageable pageable){
        Page<ZjsjLeaseOrder> page = zjsjLeaseOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjLeaseOrderMapper::toDto));
    }

    @Override
    public List<ZjsjLeaseOrderDto> queryAll(ZjsjLeaseOrderQueryCriteria criteria){
        return zjsjLeaseOrderMapper.toDto(zjsjLeaseOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjLeaseOrderDto findById(Long id) {
        ZjsjLeaseOrder zjsjLeaseOrder = zjsjLeaseOrderRepository.findById(id).orElseGet(ZjsjLeaseOrder::new);
        ValidationUtil.isNull(zjsjLeaseOrder.getId(),"ZjsjLeaseOrder","id",id);
        return zjsjLeaseOrderMapper.toDto(zjsjLeaseOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjLeaseOrder resources) {
        if(zjsjLeaseOrderRepository.findByOrderNo(resources.getOrderNo()) != null){
            throw new EntityExistException(ZjsjLeaseOrder.class,"order_no",resources.getOrderNo());
        }
        zjsjLeaseOrderRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjLeaseOrder resources) {
        ZjsjLeaseOrder zjsjLeaseOrder = zjsjLeaseOrderRepository.findById(resources.getId()).orElseGet(ZjsjLeaseOrder::new);
        ValidationUtil.isNull( zjsjLeaseOrder.getId(),"ZjsjLeaseOrder","id",resources.getId());
        ZjsjLeaseOrder zjsjLeaseOrder1 = null;
        zjsjLeaseOrder1 = zjsjLeaseOrderRepository.findByOrderNo(resources.getOrderNo());
        if(zjsjLeaseOrder1 != null && !zjsjLeaseOrder1.getId().equals(zjsjLeaseOrder.getId())){
            throw new EntityExistException(ZjsjLeaseOrder.class,"order_no",resources.getOrderNo());
        }
        zjsjLeaseOrder.copy(resources);
        zjsjLeaseOrderRepository.save(zjsjLeaseOrder);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjLeaseOrderRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjLeaseOrderDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjLeaseOrderDto zjsjLeaseOrder : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("租赁单号", zjsjLeaseOrder.getOrderNo());
            map.put("工程项目ID", zjsjLeaseOrder.getProjectId());
            map.put("租赁开始日", zjsjLeaseOrder.getStartDate());
            map.put("租赁结束日", zjsjLeaseOrder.getEndDate());
            map.put("租赁总费用", zjsjLeaseOrder.getTotalFee());
            map.put(" status",  zjsjLeaseOrder.getStatus());
            map.put("创建者", zjsjLeaseOrder.getCreateBy());
            map.put("更新者", zjsjLeaseOrder.getUpdateBy());
            map.put("创建日期", zjsjLeaseOrder.getCreateTime());
            map.put("更新时间", zjsjLeaseOrder.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}