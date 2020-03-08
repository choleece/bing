<template>
    <div>
    <el-menu class="navbar" mode="horizontal">
        <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
        <el-dropdown class="avatar-container" trigger="click">
            <div class="avatar-wrapper">
                <span class="user-name">{{user.userName}}</span>
                <span class="net-status" v-show="user.deptPointStatus"> | 网点状态：<span v-if="user.deptPointStatus == 1">授权</span><span v-else>未授权</span></span>
                <svg-icon icon-class="user"/>
                <i class="el-icon-caret-bottom"/>
            </div>
            <el-dropdown-menu slot="dropdown" class="user-dropdown">
                <router-link class="inlineBlock" to="/">
                    <el-dropdown-item>
                        首页
                    </el-dropdown-item>
                </router-link>
                <el-dropdown-item divided>
                    <span style="display:block;" @click="logout">退出</span>
                </el-dropdown-item>
                <el-dropdown-item divided>
                    <span style="display:block;" @click="resetPassword">修改密码</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </el-menu>

    <el-dialog
      title="修改密码"
      :visible.sync="dialogShow"
      width="400px">
      <el-form :model="psswordForm" :rules="psswordRules" ref="psswordForm" label-width="100px" class="demo-ruleForm">
         <el-form-item label="原始密码" prop="oldPwd">
           <el-input :type="oldPwdType" v-model="psswordForm.oldPwd" clearable placeholder="请输入原始密码">
            <i class="el-icon-view el-input__icon"
                slot="suffix"
                @click="showPwd(0)">
            </i>
           </el-input>
         </el-form-item>
         <el-form-item label="新的密码" prop="newPwd">
           <el-input :type="newPwdType" v-model="psswordForm.newPwd" clearable placeholder="请输入6-18位新密码">
               <i class="el-icon-view el-input__icon"
                slot="suffix"
                @click="showPwd(1)">
               </i>
           </el-input>
         </el-form-item>
         <el-form-item label="确认密码" prop="newPwdAgin">
           <el-input :type="newPwdAginType" v-model="psswordForm.newPwdAgin" clearable placeholder="请重新输入密码">
               <i class="el-icon-view el-input__icon"
                slot="suffix"
                @click="showPwd(2)">
               </i>
           </el-input>
         </el-form-item>

      </el-form>
      <div style="text-align: center;">
        <el-button @click="dialogShow = false" style="margin-right: 100px ">取 消</el-button>
        <el-button type="primary" @click="passwordSave">确 定</el-button>
      </div>
    </el-dialog>

    </div>
</template>

<script>
    import {mapGetters} from 'vuex'
    import Hamburger from '@/components/Hamburger'
    import auth from '@/utils/auth'

    export default {
        data(){
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                     callback(new Error('请输入新密码'));
                } else if(value.length < 6 || value.lenth > 18){
                    callback(new Error('长度在 6 到 18 个字符'));
                }else{
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入新密码'));
                } else if (value !== this.psswordForm.newPwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else if(value.length < 6 || value.lenth > 18){
                    callback(new Error('长度在 6 到 18 个字符'));
                }else{
                    callback();
                }
            };
            return {
                dialogShow: false,
                oldPwdType:'password',
                newPwdType:'password',
                newPwdAginType:'password',
                psswordForm: {
                    oldPwd:'',
                    newPwd:'',
                    newPwdAgin:''
                },
                psswordRules: {
                    oldPwd: [
                        { required: true, message: '请输入原始密码', trigger: 'blur' },
                        { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
                    ],
                    newPwd: [
                        { required: true, validator: validatePass, trigger: 'blur' },
                        { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
                    ],
                    newPwdAgin: [
                        { required: true, validator: validatePass2, trigger: 'blur' },
                        { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
                    ],
                }
            }
        },
        components: {
            Hamburger
        },
        computed: {
            ...mapGetters([
                'sidebar',
                'name',
            ]),
            user :function(){
                    let user=JSON.parse(auth.getUser());
                    let userName =  user.userName ? user.userName :user.jobNum;
                    let roles = user.roleList;
                    var roleName = "";
                    if(roles && roles.length > 0){
                        for(var i=0;i<roles.length; i++){
                             roleName += roles[i].roleName
                        }
                    }
                    if(roleName.length > 10){
                        roleName = roleName.substr(0, 10) + '...'
                    }
                    let userInfo = {
                        userName : userName,
                        deptPointStatus : user.deptPointStatus
                    };
                return userInfo;
            }
        },
        methods: {
            showPwd(type){
                switch(type){
                    case 0:
                        if (this.oldPwdType === 'password') {
                            this.oldPwdType = ''
                        } else {
                            this.oldPwdType = 'password'
                        }
                       break;
                    case 1:
                         if (this.newPwdType === 'password') {
                            this.newPwdType = ''
                         } else {
                            this.newPwdType = 'password'
                         }
                       break;
                    default:
                        if (this.newPwdAginType === 'password') {
                            this.newPwdAginType = ''
                         } else {
                            this.newPwdAginType = 'password'
                         }
                       break;
                }

            },
            toggleSideBar() {
                this.$store.dispatch('ToggleSideBar')
            },
            logout() {
                this.$store.dispatch('LogOut').then(() => {
                    location.reload() // 为了重新实例化vue-router对象 避免bug
                })
            },
            resetPassword(){
                this.dialogShow = true;
            },
            passwordDialogClose(){
                this.dialogShow = false;
            },
            passwordSave(){
                let _this = this;
                this.$refs["psswordForm"].validate(function(valid){
                    if(valid==true){
                        _this.$post(window.POSTS.commonSystemUrl + "/index/pwd/alter",_this.psswordForm).then(res =>{
                                if(res.success==true){
                                    _this.dialogShow = false;
                                    _this.$notify({
                                      title: '修改成功',
                                      message: res.msg,
                                      type: 'success',
                                      duration:1500,
                                      position:'bottom-right'
                                    });
                                }else{
                                    _this.$message.error(res.msg);
                                }
                            });
                    }else{
                        _this.$message.error('请按要求输入密码');
                    }
                });
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .navbar {
        height: 50px;
        line-height: 50px;
        border-radius: 0px !important;
        background-color: #ff5157;
        .hamburger-container {
            line-height: 58px;
            height: 50px;
            float: left;
            padding: 0 10px;
        }
        .screenfull {
            position: absolute;
            right: 90px;
            top: 16px;
            color: red;
        }
        .avatar-container {
            height: 50px;
            display: inline-block;
            position: absolute;
            right: 35px;
            color: #fff;
            .avatar-wrapper {
                cursor: pointer;
               line-height: 50px;
                position: relative;
                display: flex;
                flex-direction: row;
                align-items: center;
                .user-avatar {
                    width: 40px;
                    height: 40px;
                    border-radius: 10px;
                }
                .el-icon-caret-bottom {
                    /*<!--position: absolute;-->*/
                    /*<!--right: -20px;-->*/
                    /*<!--top: 15px;-->*/
                    font-size: 12px;
                }
                .svg-user {
                    font-size: 20px;
                    margin-top: 15px;
                }
                .user-name{
                    margin-right: 10px;
                }
                .bu-update-pwd{
                    margin-right: 20px;
                }
                .net-status{
                     margin-right: 20px;
                }
            }
            .avatar-user{


            }
        }
    }
</style>

