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
package me.zhengjie.modules.zjsj.service;

import me.zhengjie.modules.zjsj.domain.ZjsjElectronicTag;
import me.zhengjie.modules.zjsj.service.dto.ZjsjElectronicTagDto;
import me.zhengjie.modules.zjsj.service.dto.ZjsjElectronicTagQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description 服务接口
* @author allenleex
* @date 2025-07-23
**/
public interface ZjsjElectronicTagService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<ZjsjElectronicTagDto> queryAll(ZjsjElectronicTagQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ZjsjElectronicTagDto>
    */
    List<ZjsjElectronicTagDto> queryAll(ZjsjElectronicTagQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return ZjsjElectronicTagDto
     */
    ZjsjElectronicTagDto findById(String id);

    /**
    * 创建
    * @param resources /
    */
    void create(ZjsjElectronicTag resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(ZjsjElectronicTag resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(String[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<ZjsjElectronicTagDto> all, HttpServletResponse response) throws IOException;
}