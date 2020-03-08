<template>
    <div id="resourceContainer" class="page-container">
        <el-row style="height: 100%;">
            <el-col :md="6" style="height: 100%; border-right: 1px solid #efefef">
                <el-tree ref="resourceTree" :data="treeResources" :props="treeProps"
                         :default-expanded-keys="treeDefaultKeys" :lazy="true" :load="loadChildren"
                         :highlight-current="true" :current-node-key="treeCurrentKey" :expand-on-click-node="true"
                         :node-key="treeKeyField" style="width: 98%" @node-click="treeNodeClick" :show-checkbox="false"
                         :default-expand-all="false"></el-tree>
            </el-col>
            <el-col :md="18" style="height: 100%">
                <el-row class="main-search">
                    <el-form :inline="true" onsubmit="return false" >
                        <el-form-item>
                            <button class="add-button mybutton" type="button" @click="openAddPage(1)">新增</button>
                        </el-form-item>
                    </el-form>
                </el-row>
                <el-row class="main-table" style="height: 100%;">
                    <el-table :data="tableResource"
                        :element-loading-text="loadingTxt"
                        height="100%" style="width: 100%"
                        stripe
                        :header-cell-style="{
                            'background-color': '#eeeeee',
                            'color': '#333333'
                        }"
                    >
                        <el-table-column align="center" prop="name" label="资源名称" width="150"/>
                        <el-table-column prop="type" label="资源类型" width="100">
                            <template scope="scope">
                                {{
                                    resourceTypeText[scope.row.type]
                                }}
                            </template>
                        </el-table-column>
                        <el-table-column align="center" prop="identify" label="资源标识" width="150"/>
                        <el-table-column align="center" prop="url" label="资源地址" width="200"/>
                        <el-table-column align="center" prop="useStatusName" label="停用/启用" width="100">
                            <template scope="scope">
                                 <el-switch
                                    style="display: block"
                                    v-model="scope.row.useStatus"
                                    active-color="#13ce66"
                                    inactive-color="#e2dede"
                                    :active-value="1"
                                    :inactive-value="0"
                                    @change="onUsableResource(scope.row.useStatus,scope.row.id)">
                                </el-switch>
                            </template>
                        </el-table-column>
                        <el-table-column align="center" label="操作" width="200">
                            <template scope="scope">
                                <button class="table-edit-button" type="button" @click="openAddPage(2,scope.row)"
                                           :resourceId="scope.row.id">修改
                                </button>
                                <button class="table-delet-button" type="button" @click="del(scope.row.id)"
                                           :resourceId="scope.row.id">删除
                                </button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-row>
            </el-col>
        </el-row>
        <el-dialog :title="editDialog.title" :visible.sync="editDialog.show" @close="onCloseEditDialog" top="5%" width="800px"
                   :close-on-click-modal="false">
            <el-form :model="editDialog.form" :rules="editDialog.rules" ref="resourceDialogForm"
                     onsubmit="return false;">
                <el-form-item label="资源名称：" :label-width="editDialog.formLabelWidth" prop="name">
                    <el-input v-model="editDialog.form.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="资源类型：" :label-width="editDialog.formLabelWidth" prop="type">
                    <el-radio v-model="editDialog.form.type" label="1">目录</el-radio>
                    <el-radio v-model="editDialog.form.type" label="2">菜单</el-radio>
                    <el-radio v-model="editDialog.form.type" label="3">功能</el-radio>
                </el-form-item>
                <el-form-item label="资源标示：" :label-width="editDialog.formLabelWidth" prop="identify">
                    <el-input v-model="editDialog.form.identify" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址URL：" :label-width="editDialog.formLabelWidth" prop="url"
                    :rules=" editDialog.form.type != '1' ? [{ required: true, message: '请填写地址URL', trigger: 'blur' }] : null "
                    >
                    <el-input v-model="editDialog.form.url" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="图标：" :label-width="editDialog.formLabelWidth" prop="icon">
                    <el-input v-model="editDialog.form.icon" auto-complete="off" @blur="onIconInputBlur"
                              placeholder="table">
                        <template slot="append">
                            <svg-icon :icon-class="editDialog.icon"></svg-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item label="序号：" :label-width="editDialog.formLabelWidth" prop="snum">
                    <el-input-number v-model="editDialog.form.snum" :min="1"></el-input-number>
                </el-form-item>
                <el-form-item label="备注：" :label-width="editDialog.formLabelWidth" prop="remark">
                    <el-input type="textarea" v-model="editDialog.form.remark" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialog.show= false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { listToTree } from "../../../utils";

let menuRoot = process.env.VUE_APP_MENU_ROOT_ID;

export default {
    name: "resourceManager",
    data: function () {
        return {
            loading: false,
            resourceTypeText: {
                '1': '目录',
                '2': '菜单',
                '3': '功能'
            },
            treeResources: [],
            treeProps: {
                children: 'children',
                label: 'name'
            },
            treeDefaultKeys: [menuRoot],
            treeCurrentKey: "",
            treeKeyField: "id",
            selectTreeData: {},
            tableResource: [],
            editDialog: {
                title: "新增资源",
                icon: "table",
                type: 1,
                form: {
                    id: "",
                    name: "",
                    type: "1",
                    identify: "",
                    url: "",
                    icon: "table",
                    remark: "",
                    snum: 1
                },
                show: false,
                formLabelWidth: "100px",
                rules: {
                    name: [{required: true, message: '资源名不能为空', trigger: 'blur'}],
                    type: [{required: true, message: '资源类型不能为空', trigger: 'blur'}]
                }
            }
        }
    },
    methods: {
        treeNodeClick: function (data) {
            this.tableResource = data.children;
            this.selectTreeData = data;
        },
        loadChildren: function (node, resolve) {
            if (node.level == 0) {
                this.loadResourceRoot(resolve)
            } else if (node.level == 1) {
                return resolve(this.treeResources[0].children)
            } else {
                let parentId = node.data.id;
                let $this = this;
                $this.$get(`${window.POSTS.commonSystemUrl}/sys/resource/menus/parent/${parentId}`, {}).then(res => {
                    if (res.code == 0) {
                        $this.tableResource = res.data;
                    }
                    resolve(res.data);
                    $this.selectTreeData.children = res.data;
                });
            }
        },
        updateSelectTreeData: function () {
            let $tree = this.$refs.resourceTree;
            //更新子节点
            if (this.selectTreeData) {
                const $this = this;
                this.loading = true;
                let parentId = this.selectTreeData.id;
                this.$get(`${window.POSTS.commonSystemUrl}/sys/resource/menus/parent/${parentId}`, {}).then(res => {
                    //移出节点
                    let len = $this.selectTreeData.children.length;
                    for (let i = 0; i < len; i++) {
                        let item = $this.selectTreeData.children.shift();
                        $tree.remove(item);
                    }
                    if (res.code == 0) {
                        for (let i = 0; i < res.data.length; i++) {
                            let item = res.data[i];
                            $tree.append(item, $this.selectTreeData);
                        }
                    }
                    $this.loading = false;
                    $this.tableResource = res.data;
                }).catch(() => {
                    $this.loading = false;
                });
            }
        },
        //加载根节点数据
        loadResourceRoot: function (resolve) {
            this.$get(`${window.POSTS.commonSystemUrl}/sys/resource/menus/parent`, {resourceId: process.env.VUE_APP_MENU_ROOT_ID}).then(res => {
                if (res.data && res.data.length > 0) {
                    let data = res.data;
                    this.treeResources = listToTree(res.data, "id", "parentId");
                    if (resolve) {
                        resolve(this.treeResources[0].children);
                    }
                }
            });
        },
        // 打开新增/修改页面
        openAddPage: function (type, row) {
            const $this = this;
            this.editDialog.type = type;
            this.editDialog.show = true;
            this.editDialog.icon = "fa fa-leaf";
            if (1 === type) {
                this.editDialog.title = "新增资源";
                this.editDialog.form.id = "";
            } else if (2 == type) {
                this.editDialog.title = "修改资源";
                this.$get(`${window.POSTS.commonSystemUrl}/sys/resource/${row.id}`, {}).then(res => {
                    if (res.code == 0) {
                        let data = res.data;
                        data.type = '' + data.type;
                        $this.editDialog.form = data;
                        if (data.icon) {
                            $this.editDialog.icon = data.icon;
                        }
                        this.loadResourceRoot();
                    }
                });
            }
        },
        onIconInputBlur: function () {
            if (this.editDialog.form.icon == null) {
                this.editDialog.icon = "fa fa-leaf";
            } else {
                this.editDialog.icon = this.editDialog.form.icon;
            }
        },
        //保存
        save: function () {
            const $this = this;
            this.$refs["resourceDialogForm"].validate(function (valid) {
                if (valid) {
                    let params = $this.editDialog.form;
                    params.parentId = $this.selectTreeData.id;
                    params.useStatus = '1';
                    if (!params.icon) {
                        params.icon = "fa fa-leaf";
                    }
                    $this.$post(`${window.POSTS.commonSystemUrl}/sys/resource`, params).then(res => {
                        if (res.code == 0) {
                            $this.editDialog.show = false;
                            $this.$message.success('操作成功');
                            $this.updateSelectTreeData();
                        } else {
                            $this.$message.warning(res.msg);
                        }
                    });
                }
            });
        },
        //删除资源
        del: function (id) {
            const $this = this;
            this.$confirm('确定要删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                $this.$post(`${window.POSTS.commonSystemUrl}/sys/resource/${id}/del`, {}).then(res => {
                    if (res.code == 0) {
                        $this.updateSelectTreeData();
                        $this.$message.success('删除成功');
                    } else {
                        $this.$message.error(res.msg);
                    }
                });
            }).catch(function () {
                $this.$message.info('已取消删除');
            });
        },
        // 关闭弹出页面
        onCloseEditDialog: function () {
            this.clearForm();
        },
        //清理新增表单数据
        clearForm: function () {
            let $resourceDialogForm = this.$refs["resourceDialogForm"];
            if ($resourceDialogForm) {
                $resourceDialogForm.resetFields();
            }
        },
        //启用/禁用资源
        onUsableResource: function (useStatus, resourceId) {
            const $this = this;
            $this.$post(`${window.POSTS.commonSystemUrl}/sys/resource`, {id: resourceId, useStatus: useStatus}).then(res => {
                if (res.code == 0) {
                    $this.$message.success('操作成功');
                    $this.updateSelectTreeData();
                } else {
                    $this.$message.warning(res.msg);
                }
            });
        }
    },
    created() {
        this.loadResourceRoot();
    }
}
</script>

<style scoped>

</style>
