<template>
<div id="userContainer" class="page-container">
<el-row class="main-search">
   <el-form :inline="true" :model="qryForm" onsubmit="return false">
      <el-form-item>
          <el-button type="text" icon="plus" @click="openAddUserPage(1,$event)">新增</el-button>
      </el-form-item>
      <el-form-item label="关键字：">
        <el-input v-model="qryForm.keyword" placeholder="请输入姓名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <button class="query-button mybutton" @click="qryUser" :loading="loading">查询</button>
      </el-form-item>
</el-form>
</el-row>
<el-row class="main-table">
  <el-table :data="tableData.data" 
        style="width: 100%"  
        :loading="loading" 
        :element-loading-text="loadingTxt" 
        height="100%"
        stripe
        :header-cell-style="{
            'background-color': '#eeeeee',
            'color': '#333333',
        }">
    <el-table-column align="center" prop="userName" label="账号" min-width="100"/>
      <el-table-column align="center" prop="nickName" label="昵称" min-width="100"/>
    <el-table-column align="center" prop="usable" label="启用状态" min-width="80">
        <template scope="scope">
            {{userTypeText[scope.row.usable]}}
        </template>
    </el-table-column>
    <el-table-column align="center" prop="roleList" label="角色" min-width="120" :formatter="roleFormatter"/>
    <el-table-column  align="center" label="操作" min-width="200" >
        <template scope="scope">
        <button class="table-edit-button" type="button" @click="openAddUserPage(2,$event,scope.row.id)" :userId="scope.row.id" >修改</button>
        <!-- <el-button type="info" icon="view" size="small" @click="openAddUserPage(3,$event,scope.row.id)" :userId="scope.row.id">详情</el-button> -->
         <el-button type="success" icon="setting" size="small" :userId="scope.row.id" @click="openUserRoleWin(scope.row.id)">关联角色</el-button>

       <button style="margin: 0 10px 0 10px" class="table-delet-button" type="button" @click="delUser(scope.row.id,scope.row.userName)"  :userId="scope.row.id" :userName="scope.row.userName">删除</button>
         <el-button type="warning" icon="view" size="small" @click="updatePwd(scope.row.id)" :userId="scope.row.id" >重置密码</el-button>
        </template>
    </el-table-column>
  </el-table>
</el-row>
<el-row class="main-page">
   <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="tableData.pageIndex"
      :page-sizes="[tableData.pageSize, 15, 20, 30]"
      :page-size="tableData.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableData.total">
    </el-pagination>
</el-row>

    <!--新增用户 -->
    <el-dialog :title="editDialog.title"  width="1000px" :visible.sync="editDialog.show" @close="onCloseUserDialog" :close-on-click-modal="false">
        <el-form :model="editDialog.formUser" :rules="editDialog.rules" ref="userDialogForm" onsubmit="return false;">
            <el-row>
                <el-col :md="11">
                    <el-form-item label="姓名：" :label-width="editDialog.formLabelWidth" prop="userName">
                        <el-input v-model="editDialog.formUser.userName" auto-complete="off"  placeholder="请输入姓名"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :md="11">
                    <el-form-item label="电话号码" :label-width="editDialog.formLabelWidth">
                        <el-input v-model="editDialog.formUser.phone"  placeholder="请输入电话号码"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :md="11">
                    <el-form-item label="性别：" :label-width="editDialog.formLabelWidth"  prop="sex" >
                        <el-radio v-model="editDialog.formUser.sex" label="1">男</el-radio>
                        <el-radio v-model="editDialog.formUser.sex" label="2">女</el-radio>
                    </el-form-item>
                </el-col>
                <el-col :md="11">
                    <el-form-item label="启用状态：" :label-width="editDialog.formLabelWidth"  prop="usable" >
                        <el-radio v-model="editDialog.formUser.usable" label="1">启用</el-radio>
                        <el-radio v-model="editDialog.formUser.usable" label="0">禁用</el-radio>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :md="22">
                    <el-form-item label="备注：" :label-width="editDialog.formLabelWidth"  prop="remark">
                        <el-input type="textarea" v-model="editDialog.formUser.remark" auto-complete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer">
            <el-button type="warning" icon="close" @click="editDialog.show= false">{{ editDialog.closeBuName }}</el-button>
            <el-button type="primary" icon="check" @click="saveUser" v-if="editDialog.type!=3">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 用户关联角色-->
    <el-dialog :title="userRoleDialog.title" :visible.sync="userRoleDialog.show" @close="onCloseUserRoleDialog" top="5%" :close-on-click-modal="false">
        <el-row>
            <el-col :md="20" :offset="2">
                <el-transfer v-model="userRoleDialog.selectRoles" :data="userRoleDialog.roles" :filterable="true" :titles="['角色列表', '拥有角色']"
                             :props="userRoleDialog.props">
                </el-transfer>
            </el-col>
        </el-row>
        <div slot="footer">
            <el-button @click="userRoleDialog.show= false">取 消</el-button>
            <el-button type="primary" @click="saveUserRole">确 定</el-button>
        </div>
    </el-dialog>


</div>
</template>
<script type="text/javascript">
export  default {
    data:function(){
        return {
            loading: false,
            roles: [],
            tableData: {
                pageSize: 10,
                pageIndex: 1,
                data: [],
                totalCount: 0
            },
            qryForm: {
                keyword: ''
            },
            userTypeText: {
                '0': '停用',
                '1': '启用'
            },
            editDialog: {//用户弹出框
                org: {
                   show:false
                },
                show: false,
                title: "新增用户",
                type: 1,
                userNameProp: "userName",
                jobNumDisable: true,
                formLabelWidth: "100px",
                closeBuName: "取消",
                formDisable: true,
                employDisable: true,
                formUser: {
                    id: "",
                    userName: "",
                    phone: "",
                    sex: "1",
                    usable: "1",
                    email: "",
                    remark: ""
                },
                defaultUser: {
                    sex: "1",
                    usable: "1",
                    userType: "2"
                },
                rules:{
                    userName:[
                        { required: true, message: '姓名不能为空', trigger: 'blur' }
                    ],
                    sex:[{ required: true, message: '性别不能为空', trigger: 'blur' }],
                    phone:[
                        {pattern : /^1[34578]\d{9}$/, message: '手机号码格式错误',trigger: 'change' }
                    ],
                    email:[
                        { type: 'email', message: '请输入正确的邮箱地址', trigger: 'change' },
                    ],
                    relCode: [{ required: true, message: '关联编码不能为空', trigger: 'blur' }]
                }
            },
            //用户关联角色
            userRoleDialog: {
                title: "关联角色",
                show: false,
                selectRoles: [],
                props: {key: "id", label: "roleName"},
                roles: [],
                userId: ""
            }
        };
    },
    methods:{
        // 查询
        qryUser: function() {
            this.loading = true;
            const $this = this;
            this.$get(`${window.POSTS.commonSystemUrl}/sys/user/page`, {keyword: this.qryForm.keyword, pageIndex: this.tableData.pageIndex, pageSize: this.tableData.pageSize}).then(res => {
                $this.loading = false;
                if (res.code === 0) {
                    $this.tableData.total = Number(res.data.total);
                    $this.tableData.data = res.data.records;
                }
            }).catch(() => {
                this.loading = false;
            });
        },
        //打开新增/修改用户窗口
        openAddUserPage:function(type,event,userId) {
            this.editDialog.show = true;
            this.editDialog.type = type;
            this.editDialog.closeBuName = "取消";
            if(type==1) {
                this.editDialog.formUser = this.editDialog.defaultUser;
                this.editDialog.formUser.id = "";
                this.editDialog.title = "新增用户";
                this.editDialog.userNameProp = "userName";
                this.editDialog.jobNumDisable = false;
            } else if(type==2) {
                this.editDialog.userNameProp = '';
                this.editDialog.jobNumDisable = true;
                this.editDialog.title = "修改用户";
                this.bindUser(userId);
            } else if(type==3) {
                this.editDialog.title = "用户详情";
                this.editDialog.closeBuName = "关闭";
                this.bindUser(userId);
            }
        },
        //绑定弹出框用户信息
        bindUser: function(userId) {
            this.$get(`${window.POSTS.commonSystemUrl}/sys/user/${userId}`, {}).then(res => {
                if (res.code === 0) {
                    let data = res.data;
                    data.sex = data.sex + '';
                    data.usable = data.usable + '';
                    this.editDialog.formUser.id = data.id;
                    this.editDialog.formUser.userName = data.userName;
                    this.editDialog.formUser.phone = data.phone;
                    this.editDialog.formUser.sex = data.sex;
                    this.editDialog.formUser.usable = data.usable;
                    this.editDialog.formUser.remark = data.remark;
                }
            });
        },
        saveUser: function() {
            let $this = this;
            this.$refs["userDialogForm"].validate(function(valid){
                if(valid) {
                    let msg = "修改用户成功";
                    if($this.editDialog.type == 1) {
                        msg = "保存用户成功，默认密码为123456";
                    }
                    let params = $this.editDialog.formUser;
                    $this.$post(`${window.POSTS.commonSystemUrl}/sys/user`, params).then(res => {
                        if(res.code === 0) {
                            $this.editDialog.show=false;
                            $this.$message.success(msg);
                            $this.qryUser();
                        } else {
                            $this.$message.error(res.msg);
                        }
                    });
                }
            });
        },
        onCloseUserDialog: function() {
            this.$refs["userDialogForm"].resetFields();
        },
        delUser: function(userId, userName) {
            let $this = this;
            this.$confirm('删除后用户将无法登录系统，确定要删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                    this.$post(`${window.POSTS.commonSystemUrl}/sys/user/${userId}/del`, {}).then(res => {
                        if(res.code === 0) {
                            $this.qryUser();
                            $this.$message.success('删除成功!');
                        }
                    });
                
            }).catch(() => {
                $this.$message.info('已取消删除');
            });
        },
        //重置密码
        updatePwd:function(userId) {
            this.$post(`${window.POSTS.commonSystemUrl}/sys/user/${userId}/reset/pwd`, {}).then(res => {
                if (res.code === 0) {
                    this.$message.success('重置密码成功!');
                }
            });
        },
        handleSizeChange:function(val) {
            this.tableData.pageSize = val;
            this.qryUser();
        },
        handleCurrentChange:function(val) {
            this.tableData.pageIndex = val;
            this.qryUser();
        },
        //打开用户关联角色窗口
        openUserRoleWin:function(userId){
            this.userRoleDialog.show=true;
            this.userRoleDialog.userId=userId;
            this.qryUserRole();
        },
        onCloseUserRoleDialog:function() {
            this.userRoleDialog.roles= [];
            this.userRoleDialog.selectRoles = [];
        },
        saveUserRole: function() {
            this.userRoleDialog.show = true;
            this.$post(`${window.POSTS.commonSystemUrl}/sys/user/${this.userRoleDialog.userId}/role`,
                {roleIds: this.userRoleDialog.selectRoles.toString()}).then(res => {
                this.userRoleDialog.show = false;
                if(res.code === 0) {
                    this.$message.success('操作成功');
                    this.qryUser();
                } else {
                    this.$message.warning(res.msg);
                }
            }).catch(() => {
                this.userRoleDialog.show = false;
            });
        },
        //查询用户关联角色
        qryUserRole: function() {
            this.$get(`${window.POSTS.commonSystemUrl}/sys/user/${this.userRoleDialog.userId}/role/all`, {}).then(res => {
                if (res.code === 0) {
                    this.userRoleDialog.roles = res.data.roles;
                    this.userRoleDialog.selectRoles = res.data.selectRoles;
                }
            });
        },
        listRoles() {
           let params = {roleName: '', pageSize: 10000, pageIndex: 1};
            this.$get(`${window.POSTS.commonSystemUrl}/sys/role`, params).then(res => {
                this.roles = res.data.records;
            }).catch(() => {
                this.loading = false;
            });
        },
        roleFormatter(row, column, cellValue){
            if(cellValue && cellValue.length > 0){
                var re = "";
                for(var j = 0,len = cellValue.length; j < len; j++){
                    re += (cellValue[j].roleName + "、")
                }
                return re.substr(0, re.length-1);
            }
            return '';
        }
    },
    mounted: function () {
        this.$nextTick(() => {
            this.qryUser();
        });
        this.listRoles();
    }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>

</style>
