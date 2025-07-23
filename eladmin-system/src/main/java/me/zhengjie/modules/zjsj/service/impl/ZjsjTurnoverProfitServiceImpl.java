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

import me.zhengjie.modules.zjsj.domain.ZjsjTurnoverProfit;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.zjsj.repository.ZjsjTurnoverProfitRepository;
import me.zhengjie.modules.zjsj.service.ZjsjTurnoverProfitService;
import me.zhengjie.modules.zjsj.service.dto.ZjsjTurnoverProfitDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjTurnoverProfitQueryCriteria;
import me.zhengjie.modules.zjsj.service.mapstruct.ZjsjTurnoverProfitMapper;
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
public class ZjsjTurnoverProfitServiceImpl implements ZjsjTurnoverProfitService {

    private final ZjsjTurnoverProfitRepository zjsjTurnoverProfitRepository;
    private final ZjsjTurnoverProfitMapper zjsjTurnoverProfitMapper;

    @Override
    public PageResult<ZjsjTurnoverProfitDto> queryAll(ZjsjTurnoverProfitQueryCriteria criteria, Pageable pageable){
        Page<ZjsjTurnoverProfit> page = zjsjTurnoverProfitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(zjsjTurnoverProfitMapper::toDto));
    }

    @Override
    public List<ZjsjTurnoverProfitDto> queryAll(ZjsjTurnoverProfitQueryCriteria criteria){
        return zjsjTurnoverProfitMapper.toDto(zjsjTurnoverProfitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ZjsjTurnoverProfitDto findById(Long id) {
        ZjsjTurnoverProfit zjsjTurnoverProfit = zjsjTurnoverProfitRepository.findById(id).orElseGet(ZjsjTurnoverProfit::new);
        ValidationUtil.isNull(zjsjTurnoverProfit.getId(),"ZjsjTurnoverProfit","id",id);
        return zjsjTurnoverProfitMapper.toDto(zjsjTurnoverProfit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ZjsjTurnoverProfit resources) {
        zjsjTurnoverProfitRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ZjsjTurnoverProfit resources) {
        ZjsjTurnoverProfit zjsjTurnoverProfit = zjsjTurnoverProfitRepository.findById(resources.getId()).orElseGet(ZjsjTurnoverProfit::new);
        ValidationUtil.isNull( zjsjTurnoverProfit.getId(),"ZjsjTurnoverProfit","id",resources.getId());
        zjsjTurnoverProfit.copy(resources);
        zjsjTurnoverProfitRepository.save(zjsjTurnoverProfit);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            zjsjTurnoverProfitRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ZjsjTurnoverProfitDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ZjsjTurnoverProfitDto zjsjTurnoverProfit : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("周转物资ID", zjsjTurnoverProfit.getAssetId());
            map.put("统计年份", zjsjTurnoverProfit.getYear());
            map.put("季度(1-4)", zjsjTurnoverProfit.getQuarter());
            map.put("租赁天数", zjsjTurnoverProfit.getLeaseDays());
            map.put("产生收益", zjsjTurnoverProfit.getIncome());
            map.put("使用率(%)", zjsjTurnoverProfit.getUtilizationRate());
            map.put("创建者", zjsjTurnoverProfit.getCreateBy());
            map.put("更新者", zjsjTurnoverProfit.getUpdateBy());
            map.put("创建日期", zjsjTurnoverProfit.getCreateTime());
            map.put("更新时间", zjsjTurnoverProfit.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}