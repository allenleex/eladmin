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
package me.zhengjie.modules.zjsj.repository;

import me.zhengjie.modules.zjsj.domain.ZjsjProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @website https://eladmin.vip
* @author allenleex
* @date 2025-07-23
**/
public interface ZjsjProjectRepository extends JpaRepository<ZjsjProject, Long>, JpaSpecificationExecutor<ZjsjProject> {
    /**
    * 根据 ProjectNo 查询
    * @param project_no /
    * @return /
    */
    ZjsjProject findByProjectNo(String project_no);
}