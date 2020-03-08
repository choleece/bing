<template>
  <div class="login-container">
    <div class="bg">
      <div class="bg-left"></div>
      <div class="bg-right">
        <el-form
          ref="loginForm"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
          auto-complete="on"
          label-position="left"
        >
          <img src="../static/img/welcome.png">
          <h3 class="title">{{title}}</h3>
          <el-form-item prop="userName" style="margin-top:50px;">
            <span class="svg-container">
              <svg-icon icon-class="user"/>
            </span>
            <el-input
              v-model="loginForm.userName"
              name="userName"
              type="text"
              auto-complete="on"
              placeholder="请输入账户"
            />
          </el-form-item>
          <el-form-item prop="password">
            <span class="svg-container">
              <svg-icon icon-class="password"/>
            </span>
            <el-input
              :type="pwdType"
              v-model="loginForm.password"
              name="password"
              auto-complete="on"
              placeholder="请输入密码"
              @keyup.enter.native="handleLogin"
            />
            <span class="show-pwd" @click="showPwd">
              <svg-icon icon-class="eye"/>
            </span>
          </el-form-item>
          <el-row>
            <el-col :span="10">
              <el-input
                v-model="loginForm.captcha"
                type="text"
                name="captcha"
                prop="authCode"
                placeholder="请输入验证码"
                class="auth-code-input"
              />
            </el-col>
            <el-col :span="6">
              <img id="authCode" class="auth-code-img" alt="验证码" :src="validImgUrl">
            </el-col>
            <el-col :span="4">
              <a  href="javascript:void(0);" class="refresh-img" @click="refreshImg();">换一个</a>
            </el-col>
          </el-row>
          <el-form-item>
            <el-button
              :loading="loading"
              type="primary"
              style="width:100%;height:50px;"
              @click.native.prevent="handleLogin"
            >登录</el-button>
          </el-form-item>
          <div class="tips">{{errorMsg}}</div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { setMenus } from "@/utils/menus";
import CryptoJS from "crypto-js";

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
          userName: "",
          password: "",
          captcha: ""
      },
      loginRules: {
          userName: [{ required: true, trigger: "blur", message: "账户不能为空" }],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        captcha: [
          { required: true, trigger: "blur", message: "验证码不能为空" }
        ]
      },
      loading: false,
      pwdType: "password",
      redirect: undefined,
      title: this.TITLE,
      serviceUrl: this.POSTS.commonSystemUrl,
      validImgUrl: this.POSTS.commonSystemUrl + "/index/captcha",
      errorMsg: ""
    };
  },
  mounted() {},
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.pwdType === "password") {
        this.pwdType = "";
      } else {
        this.pwdType = "password";
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          let key = this.getKey(this.loginForm.userName);
          this.loginForm.password = this.encrypt(this.loginForm.password, key);
          this.$store
            .dispatch("Login", this.loginForm)
            .then(res => {
              this.loading = false;
              if (res.code == 0) {
                  this.getUserMenus();
              } else {
                  this.refreshImg();
              }
            })
            .catch(err => {
              this.refreshImg();
              this.$message.warning(err.msg);
              this.loading = false;
              this.loginForm.password = "";
            });
        } else {
          return false;
        }
      });
    },
    refreshImg() {
      //刷新验证码
      this.validImgUrl = this.POSTS.commonSystemUrl + "/index/captcha?rnd=" + Math.random();
    },
    getUserInfo() {
      //获取用户信息
      this.$store.dispatch("GetUserInfo", {}).then(()=>{
        this.$router.push({ path: "/" });
      });
    },
    getUserMenus() {
      //获取用户菜单
      this.$store
        .dispatch("GetUserMenus", {})
        .then(res => {
          setMenus();
          this.getUserInfo();
        })
        .catch(error => {
          console.log(error);
        });
    },
    encrypt(word, keyTxt) {
      var key = CryptoJS.enc.Utf8.parse(keyTxt);
      var srcs = CryptoJS.enc.Utf8.parse(word);
      var encrypted = CryptoJS.AES.encrypt(srcs, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
      });
      return encrypted.toString();
    },
    getKey(userName) {
      return CryptoJS.MD5(userName)
        .toString()
        .substring(0, 16);
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
$bg: #2d3a4b;
$light_gray: #333333;

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 82%;
    input {
      background-color: #ffffff;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      height: 47px;
      &:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px #ffffff inset !important;
            -webkit-text-fill-color: #333333 !important;
        }
    }
  }
  .el-form-item {
    margin-top: 24px;
    border: solid 1px #c9c9c9;
    background: #ffffff;
    border-radius: 5px;
  }
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
.login-container {
  position: relative;
  height: 100%;
  width: 100%;
  background: url("../static/img/bg.jpg") no-repeat;
  background-size: 100% 100%;

  .bg {
    position: absolute;
    height: 720px;
    width: 1280px;
    top: 50%;
    left: 50%;
    margin-left: -640px;
    margin-top: -360px;

    .bg-left {
      width: 740px;
      height: 100%;
      float: left;
      background: url("../static/img/bg_left.jpg") no-repeat;
      background-size: 100%;
    }

    .bg-right {
      width: 540px;
      height: 100%;
      float: left;
      background-color: #ffffff;
      box-shadow: 0px 12px 28px 0px
		rgba(255, 81, 87, 0.23);
	  border-radius: 0 8px 8px 0;
    }
  }

  .login-form {
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    width: 340px;
    height: 405px;
    max-width: 100%;
    margin: auto;
    top: 151px;
    position: relative;
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 40px;
    display: inline-block;
  }
  .title {
    height: 20px;
    font-family: SourceHanSansCN-Bold;
    font-weight: normal;
    font-stretch: normal;
    line-height: 36px;
    letter-spacing: 0px;
    color: #333333;
    font-weight: bold;
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
  .auth-code-img {
    margin-top: 4px;
    width: 100px;
    height: 40px;
  }
  .refresh-img {
    position: absolute;
    right: 30px;
    line-height: 50px;
    color: #ff5157;
    text-decoration-line: underline;
  }
  .auth-code-input{
    height: 50px;
    border: 1px solid #c9c9c9;
    border-radius: 5px;
  }

}
</style>
