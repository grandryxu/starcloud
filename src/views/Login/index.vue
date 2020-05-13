<template>
  <div class="iot-login">
    <div class="iot-login-bg">
      <div class="iot-loginForm" :style="{'margin-top':compatibility}">
        <el-select v-model="lang" :style="{'width':lang == 'en' ? '75px':'60px'}" class="iot-select">
          <el-option v-for="item in languageList" :key="item.value" :label="item.label" :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
        <div class="iot-loginForm_title">
          <!-- <span class="xmx">新美星</span> -->
          <span class="ws">{{$t('settings.WarehousingSystem')}}</span>
        </div>
        <div class="iot-loginForm_body">
          <el-form label-position="left" :model="loginForm" :rules="rules" ref="loginForm" class="custom__login">
            <el-form-item prop="name" class="iot-loginFor-item">
              <el-input class="iot-loginForm-input" v-model="loginForm.name" :placeholder="$t('login.username')"></el-input>
            </el-form-item>
              <el-form-item prop="region" class="iot-loginFor-item">
                <el-input  :type="showPass ? 'text' : 'password'" class="iot-loginForm-input" v-model="loginForm.region" :placeholder="$t('login.password')"   ></el-input>


                <i :class="showPass? icon1: icon2" style="position: absolute; right: 1rem; top: 0.2rem;color: #1F47A4" @click="showPass=!showPass"></i>
              </el-form-item>
            <el-form-item class="iot-loginFor-item">
              <el-button class="iot-loginForm-login" type="primary" @click="submit('loginForm')">{{$t('login.logIn')}}</el-button>
            </el-form-item>
            <!-- <div class="iot-loginForm_checkbox">
              <div class="through-line">{{$t('login.forgotPassword')}}</div>
              <div class="through-line">{{$t('login.register')}}</div>
            </div> -->
          </el-form>
        </div>
      </div>
    </div>
    <div class="login-foot-copy">Copyright ©2019-2020 {{$t('settings.company')}}</div>

<!--      <span @click='clickFullscreen'>全屏</span>-->
  </div>

</template>

<script>
// import {
//   baseUrl2
// } from "@/api/config.js";
import Utils from "@/utils/index";
import qs from "qs";
import axios from "axios";
import { Loading } from "element-ui";
import { mapState, mapMutations, mapActions } from "vuex";
import screenfull from 'screenfull'
import "@/assets/iconfont/iconfont.css";
export default {
  data() {
    return {
      loginForm: {
        name: "admin",
        region: "new_wms"
      },
      rules: {},
      checked: false,
      languageList: [],
      isFullscreen: false,
      icon1: 'iconfont icon-icon-eye-open',
      icon2: 'iconfont icon-biyan',
      showPass:false,
    };
  },
  created() {
    let lang = this.$store.state.AppMoudule.language;
    this.$store.dispatch("SetLanguage", lang);
    this.$i18n.locale = lang;
    this.initLang();
    //this.clickFullscreen();
  },
  mounted() {

  },
  methods: {
    clickFullscreen(){
      // this.isFullscreen=true;
      screenfull.toggle()
    },
    submit(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          let data = {
            // grant_type: "password",
            // client_id: 'SSO',
            // client_secret: '52454724-e1f0-45dd-9167-38b40b46a935',
            // scope: 'SSOAPI offline_access',
            userNameOrEmailAddress: this.loginForm.name,
            password: this.loginForm.region,
            rememberClient: true,
            language: this.$store.state.AppMoudule.language
            // clientKey: '725E09D6F4FC8C96'
          };
          // let data = {
          //   name: this.loginForm.name,
          //   pwd: this.loginForm.region
          // }
          // data = qs.stringify(data);
          let loadingInstance = this.$loading({
            lock: true,
            text: this.$t('login.loading'),
            spinner: "el-icon-loading",
            background: "rgba(0, 0, 0, 0.7)"
          });
          axios.post(this.$store.state.api_BaseURL + "/api/TokenAuth/Authenticate", data).then(res => {
            console.log(res, "login--------------");
            Utils.setStorage("userInfo", res);
            loadingInstance.close();
            this.$router.replace({ name: "home" });
            this.$store.commit("removeLoginRecord");
          }).catch(error => {
            if (
              error.response.data.error.details ===
              "Invalid user name or password"
            ) {
              window.rootApp.$message({
                type: "error",
                message: this.$t('login.error')
              });
            } else {
              window.rootApp.$message({
                type: "error",
                message: this.$t('login.fail')
              });
            }
            loadingInstance.close();
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    initLang() {
      this.languageList = [
        {
          label: this.$t("lang.zh"),
          value: "zh"
        },
        {
          label: this.$t("lang.en"),
          value: "en"
        },
        {
          label: this.$t("lang.ja"),
          value: "ja"
        }
      ];
      this.rules = {
        name: [
          {
            required: true,
            message: this.$t("login.username"),
            trigger: "blur"
          }
        ],
        region: [
          {
            required: true,
            message: this.$t("login.password"),
            trigger: "blur"
          }
        ]
      };
    }
    // ...mapActions('SetLanguage')
  },
  computed: {
    lang: {
      get() {
        return this.$store.state.AppMoudule.language;
      },
      set(lang) {
        this.$store.dispatch("SetLanguage", lang);
        this.$i18n.locale = lang;
        this.initLang();
        this.$store.commit('STORECURRENTLANG',lang)
      }
    },
    //为了兼容IE11中flex容器align-items:center
    compatibility() {
      return window.innerHeight / 2 - 450 / 2 + "px";
    }
  }
};
</script>
<style lang="scss">
.iot-login {
  width: 100%;
  height: 100%;
  background-image: url("~@/assets/icon/login-back.png");
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;
  // display: flex;
  justify-content: center;
  align-items: center;

  .iot-login-bg {
    // height: 416px;
    // width: 1200px;
    display: flex;
    align-items: center;
    justify-content: center;
    // position: absolute;
    z-index: 2;
  }

  .iot-loginForm {
    position: relative;
    width: 400px;
    height: 450px;
    border-radius: 4px;
    background-color: rgba(130, 168, 255, 0.3);
    input {
      background-color: rgba(130, 168, 255, 0.3);
      color: #c3d5ff;
      border: none;
    }
    .iot-select {
      position: absolute;
      top: 40px;
      right: 60px;
      input {
        border: none !important;
        font-size: 14px;
        background: none;
        color: #c3d5ff;
        padding: 0 !important;
        background: none !important;
      }
    }
    .iot-loginFor-item {
      text-align: center;
      margin: 0;
      margin-bottom: 28px;
    }
    .iot-loginForm_title {
      margin-top: 72px;
      height: 70px;
      line-height: 70px;
      padding-left: 60px;
      display: flex;
      color: #c3d5ff;
      .xmx {
        font-size: 30px;
      }
      .ws {
        margin-left: 10px;
        font-size: 18px;
      }
    }
    .iot-loginForm-input {
      width: 282px;
      // height:42px;
      background-color: rgba(161, 191, 255, 0.33);
      border-radius: 2px;
      border: solid 1px rgba(255, 255, 255, 0.06);
    }
    .iot-loginForm-login {
      width: 282px !important;
      // height:42px;
      background-image: linear-gradient(
        90deg,
        #1e439a 0%,
        #2658cb 100%
      ) !important;
      box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.1);
      border-radius: 2px;
    }

    .iot-loginForm_body {
      // padding-top: 40px;

      .el-form {
        width: 282px;
        margin: 0 auto;
      }

      .el-form-item__label {
        color: #333333;
        font-weight: bold;
        text-align-last: justify;
      }

      .iot-loginForm_checkbox {
        display: flex;
        justify-content: space-between;
        color: #c3d5ff;
        .through-line {
          text-decoration: underline;
        }
      }

      .el-button {
        width: 100%;
      }
    }
  }
}
.login-foot-copy {
  text-align: center;
  position: absolute;
  bottom: 20px;
  color: rgba(255, 255, 255, 0.6);
  width: 100%;
}

.custom__login {
  &
    /deep/
    .el-form-item.is-required:not(.is-no-asterisk)
    .el-form-item__label-wrap
    > .el-form-item__label:before,
  .el-form-item.is-required:not(.is-no-asterisk) > .el-form-item__label:before {
    display: none;
  }
}


.show-pwd {
  position: absolute;
  right: 10px;
  top: 7px;
  font-size: 16px;
  color: red;
  cursor: pointer;
  user-select: none;
}
</style>