<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="工号" prop="empNo">
            <el-input v-model="form.empNo" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="name" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属机构" prop="orgCode">
            <el-input v-model="form.orgCode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="职位">
            <el-input v-model="form.position" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="form.contact" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建者">
            <el-input v-model="form.createBy" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新者">
            <el-input v-model="form.updateBy" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建日期">
            <el-input v-model="form.createTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新时间">
            <el-input v-model="form.updateTime" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="id" />
        <el-table-column prop="empNo" label="工号" />
        <el-table-column prop="name" label="name" />
        <el-table-column prop="orgCode" label="所属机构" />
        <el-table-column prop="position" label="职位" />
        <el-table-column prop="contact" label="联系方式" />
        <el-table-column prop="createBy" label="创建者" />
        <el-table-column prop="updateBy" label="更新者" />
        <el-table-column prop="createTime" label="创建日期" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column v-if="checkPer(['admin','zjsjEmployee:edit','zjsjEmployee:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudZjsjEmployee from '@/api/zjsjEmployee'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, empNo: null, name: null, orgCode: null, position: null, contact: null, createBy: null, updateBy: null, createTime: null, updateTime: null }
export default {
  name: 'ZjsjEmployee',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '员工档案', url: 'api/zjsjEmployee', idField: 'id', sort: 'id,desc', crudMethod: { ...crudZjsjEmployee }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'zjsjEmployee:add'],
        edit: ['admin', 'zjsjEmployee:edit'],
        del: ['admin', 'zjsjEmployee:del']
      },
      rules: {
        empNo: [
          { required: true, message: '工号不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        orgCode: [
          { required: true, message: '所属机构不能为空', trigger: 'blur' }
        ]
      }    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
