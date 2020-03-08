<template>
    <div id="roleContainer" class="page-container">
        <el-row class="role-container">
            <el-col :md="17" style="height: 100%; border-right: 1px solid #efefef">
                <el-row class="main-search">
                    <el-form :inline="true" :model="qryForm" onsubmit="return false">
                        <el-form-item>
                            <el-button type="text" icon="plus" @click="openAddPage(1)">新增</el-button>
                        </el-form-item>
                        <el-form-item label="角色名称：">
                            <el-input v-model="qryForm.name" placeholder="请输入角色名称"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <button class="query-button mybutton" @click="qry" :loading="loading">查询</button>
                        </el-form-item>
                    </el-form>
                </el-row>
                <el-row class="main-table role-table">
                    <el-table ref="roleTable" :data="tableData"  :stripe="true"  style="width: 100%" height="100%"
                                :loading="loading" :element-loading-text="loadingTxt" :highlight-current-row="true" @current-change="tableSelectChange"
                                :header-cell-style="{
                                    'background-color': '#eeeeee',
                                    'color': '#333333',
                                }">
                        <el-table-column prop="roleName" align="center" label="角色名称" width="150"></el-table-column>
                        <el-table-column prop="sysFlag" align="center" label="添加类型" width="100" :formatter="roleAddFormat"></el-table-column>
                        <el-table-column prop="remark" align="center" label="备注" min-width="250"></el-table-column>
                        <el-table-column label="操作" align="center" min-width="180">
                            <template scope="scope">
                                <el-button type="primary" icon="edit" size="small" :disabled="scope.row.sysFlag == 1 ? true : false" @click="openAddPage(2, scope.row)">修改</el-button>
                                <el-button type="warning" icon="delete" size="small" :disabled="scope.row.sysFlag == 1 ? true : false" @click="del(scope.row)"  :roleId="scope.row.id">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-row>
                <el-row class="main-page">
                    <el-pagination  @size-change="handleSizeChange" @current-change="handleCurrentChange"
                                    :current-page="currentPage" :page-size="pageSize" :total="totalCount" layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </el-row>
            </el-col>
            <el-col :md="6" :offset="1" style="height: 100%">
                <el-row class="main-search edit-permission">
                    <el-button type="primary" @click="updateRoleResource">修改权限</el-button>
                </el-row>
                <el-row class="role-table">
                    <el-tree ref="resourceTree" :data="treeData" :props="treeProps"  :show-checkbox="true" :default-expand-all="true"
                             node-key="id" style="width: 100%;" :style="treeStyle"></el-tree>
                </el-row>
            </el-col>
        </el-row>

        <!-- 角色-->
        <el-dialog :title="editDialog.title" :visible.sync="editDialog.show" @close="onCloseEditDialog" :close-on-click-modal="false">
            <el-form :model="editDialog.form" :rules="editDialog.rules" ref="roleDialogForm" onsubmit="return false;">
                <el-form-item label="名称：" :label-width="editDialog.formLabelWidth" prop="roleName">
                    <el-input v-model="editDialog.form.roleName" auto-complete="off" ></el-input>
                </el-form-item>
                <el-form-item label="备注：" :label-width="editDialog.formLabelWidth">
                    <el-input type="textarea" v-model="editDialog.form.remark" auto-complete="off" rows="3"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialog.show=false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import { listToTree } from "../../../utils";
    import ElCol from "element-ui/packages/col/src/col";

    export default {
        components: {ElCol},
        name: "RoleManager",
        data: function() {
            return {
                defaultProps: {
                    parent: 'parentId',   // 父级唯一标识
                    value: 'id',          // 唯一标识
                    label: 'orgName',       // 标签显示
                    children: 'children', // 子级
                },
                treeOptions:[],
                loading: false,
                treeData: [],
                treeProps: {
                    children: 'children',
                    label: 'name'
                },
                treeStyle: {height: '600px', overflow: "auto"},
                currentPage: 1,
                defaultPageSize: 1,
                pageSize: 10,
                totalCount: 0,
                qryForm: {
                    name: ""
                },
                tableData: [],
                tableSelectRow: null,
                editDialog: {
                    title: "新增角色",
                    type: 1,
                    form: {
                        id: "",
                        roleName: "",
                        remark: ""
                    },
                    show:false,
                    formLabelWidth: "100px",
                    rules:{
                        roleName:[{ required: true, message: '角色名不能为空', trigger: 'blur' }]
                    }
                }
            }
        },
        methods: {
            // 加载资源数据
            loadResources: function() {
                this.$get(`${window.POSTS.commonSystemUrl}/sys/resource/all`, {}).then(res => {
                    if (res.code === 0) {
                        this.treeData = listToTree(res.data, "id", "parentId");
                    }
                });
            },
            // 查询
            qry: function() {
                this.loading = true;
                const $this = this;
                this.$get(`${window.POSTS.commonSystemUrl}/sys/role`, {roleName: this.qryForm.name, pageIndex: this.currentPage, pageSize: this.pageSize}).then(res => {
                    $this.loading = false;
                    if (res.code === 0) {
                        $this.totalCount = Number(res.data.total);
                        $this.tableData = res.data.records;
                    }
                }).catch(() => {
                    this.loading = false;
                });
            },
            //打开新增/修改页面
            openAddPage: function(type, row) {
                this.editDialog.type = type;
                this.editDialog.show = true;
                if(1 === type) {
                    this.editDialog.title = "新增角色";
                    this.editDialog.form.id = "";
                    this.editDialog.form.remark= "";
                } else if(2 === type) {
                    this.editDialog.title = "修改角色";
                    const $this = this;
                    this.$get(`${window.POSTS.commonSystemUrl}/sys/role/${row.id}`, {}).then(res => {
                        if (res.code === 0) {
                            $this.editDialog.form.id = res.data.id;
                            $this.editDialog.form.remark=res.data.remark;
                            $this.editDialog.form.roleName=res.data.roleName;
                        }
                    });
                }
            },
            //保存
            save: function() {
                const $this = this;
                this.$refs["roleDialogForm"].validate(function(valid){
                    if(valid) {
                        $this.$post(`${window.POSTS.commonSystemUrl}/sys/role/save`, $this.editDialog.form).then(res => {
                            if (res.code === 0) {
                                $this.editDialog.show = false;
                                $this.$message.success('操作成功');
                                $this.resetTable();
                            } else {
                                $this.$message.error(res.msg);
                            }
                        });
                    }
                });
            },
            //删除角色
            del: function(row) {
                const $this = this;
                this.$confirm('确定要删除?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function() {
                    $this.$post(`${window.POSTS.commonSystemUrl}/sys/role/${row.id}/del`, {}).then(res => {
                        if (res.code === 0) {
                            $this.resetTable();
                            $this.$message.success('删除成功');
                        } else {
                            $this.$message.error(res.msg);
                        }
                    });
                }).catch(function() {
                    $this.$message.info('已取消删除');
                });
            },
            // 关闭弹出页面
            onCloseEditDialog: function() {
                this.clearForm();
            },
            //清理新增表单数据
            clearForm: function() {
                const $roleDialogForm = this.$refs["roleDialogForm"];
                if($roleDialogForm) {
                    $roleDialogForm.resetFields();
                }
            },
            //重置表格
            resetTable: function() {
                this.qry();
                //清空树型选择
                this.$refs["resourceTree"].setCheckedKeys([]);
            },
            // 表格选择改变事件
            tableSelectChange: function(currentRow) {
                if(currentRow) {
                    this.tableSelectRow = currentRow;
                    this.selectRoleResourceTree();
                }
            },
            selectRoleResourceTree: function() {
                const $this = this;
                const roleId = this.tableSelectRow.id;
                this.$get(`${window.POSTS.commonSystemUrl}/sys/role/${roleId}/resource`).then(res => {
                    const data = res.data;
                    const resourceIds = [];
                    $this.$refs["resourceTree"].setCheckedKeys([]);
                    for(let i = 0; i < data.length; i++) {
                        let resourceId = data[i].resourceId;
                        resourceIds.push(resourceId);
                        $this.$refs["resourceTree"].setChecked(resourceId, true);
                    }
                });
            },
            //更新角色资源
            updateRoleResource: function() {
                if(this.tableSelectRow != null) {
                    const $this = this;
                    const $resourceTree = this.$refs["resourceTree"];
                    let keys = this.getCheckedResource($resourceTree.getCheckedNodes());
                    const roleId = this.tableSelectRow.id;
                    this.$post(`${window.POSTS.commonSystemUrl}/sys/role/${roleId}/resource`, {resourceId: keys.toString()}).then(res => {
                        if (res.code === 0) {
                            $this.$message.success('修改角色成功');
                        }
                    });
                } else {
                    this.$message.warning('请选择角色');
                }

            },
            // 获取选择的资源，包括父级
            getCheckedResource: function(nodes) {
                let keys = [];
                let objKeys = {};
                for(let i = 0; i < nodes.length; i++) {
                    let node = nodes[i];
                    objKeys[node.id] = node;
                    if(node.parent && node.parent.id != 'root') {
                        objKeys[node.parent.id]=node.parent;
                    }
                }
                for(let key in objKeys) {
                    keys.push(key);
                }
                return keys;
            },
            //分页条改变显示条数
            handleSizeChange: function(v) {
                this.pageSize = v;
                this.qry();
            },
            //分页条分页发生改变
            handleCurrentChange: function(val) {
                this.currentPage = val;
                this.qry();
            },
            roleAddFormat(row, col, value){
                if(value == 1){
                    return "系统";
                }else{
                    return "用户";
                }
            }
        },
        mounted: function() {
            this.$nextTick(() => {
                this.qry(1);
                this.loadResources();
            });
        }
    }
</script>

<style scoped>
    .role-container {
        height: 100%;
    }
    .edit-permission {
        margin-bottom: 15px;
    }
    .role-table {
        height: calc(100% - 134px);
    }
</style>
