<template>
  <div>
    <Modal
      id="new-board-modal"
      v-model="isVisible"
      @on-visible-change="visibleChange"
      :mask-closable="false"
      :footer-hide="true"
      width="420"
      :closable="true"
      @on-cancel="closeApp"
      :transfer="false"
      :styles="{top: '120px'}"
    >
      <p slot="header" style="text-align:center;">
        <span style="font-size:15px;">{{appname}}</span>
      </p>
      <Form ref="formInline" :model="formInline" :rules="ruleInline" :label-width="80">
        <FormItem prop="user" label>
          <i-input
            type="text"
            v-model="formInline.user"
            placeholder="用户名"
            clearable
            style="width: 240px"
          >
            <Icon type="ios-person" slot="prefix"/>
          </i-input>
        </FormItem>
        <FormItem prop="password" label>
          <i-input
            type="password"
            v-model="formInline.password"
            placeholder="密码"
            clearable
            style="width: 240px"
            @on-enter="handleSubmit('formInline')"
          >
            <Icon type="md-lock" slot="prefix"></Icon>
          </i-input>
        </FormItem>
        <FormItem prop="remember" label>
          <i-switch v-model="formInline.remember" size="large" true-value="1" false-value="0">
            <span slot="open">记住</span>
            <span slot="close">忽略</span>
          </i-switch>
          <a @click="open" style="margin-left:110px;">点击进入官网</a>
        </FormItem>
        <FormItem>
          <Button
            @click="handleSubmit('formInline')"
            shape="circle"
            style="width:240px;font-size:14px;margin-right:0px;"
            type="primary"
            :loading="loading">系统登录</Button>
            <!--
          <Poptip placement="right" width="210">
            <Button type="info" shape="circle" style="width:110px;font-size:14px;">扫码登录</Button>
            <div class="api" slot="content">
              <qr-code data="http://www.baidu.com"></qr-code><br/>
              <Button type="text" icon="md-refresh" size="small">重新获取</Button>
            </div>
          </Poptip>
          -->
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>
<script>
import 'webcomponent-qr-code';
import settingsRepository from "@/repositories/settingsRepository";
const remote = require("electron").remote;
export default {
  computed: {
    isVisible: {
      get() {
        return this.$store.state.modals.login.isVisible;
      },
      set(value) {
        this.$store.state.modals.login.isVisible = value;
      }
    },
    baseURL: {
      get() {
        return this.$store.state.modals.settings.baseURL;
      },
      set() {}
    },
    appname() {
      return this.$store.state.modals.settings.appName;
    },
    site() {
      return this.$store.state.modals.settings.site;
    }
  },
  data() {
    return {
      loading: false,
      formInline: {
        user: "",
        password: "",
        modal: "1",
        remember: "1"
      },
      ruleInline: {
        user: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur"
          }
        ],
        password: [
          {
            required: true,
            message: "请输入密码",
            trigger: "blur"
          },
          {
            type: "string",
            min: 3,
            message: "密码至少3位",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {
    var log = settingsRepository.getUserlog();
    this.formInline.user = log.username;
    this.formInline.password = log.password;
    this.formInline.modal = log.mode;
  },
  methods: {
    open() {
      this.$electron.shell.openExternal(this.site);
    },
    closeApp() {
      this.$Modal.confirm({
        title: "确认",
        content: "<p>确认关闭并退出本系统？</p>",
        onOk: () => {
          remote.app.quit();
        }
      });
    },
    handleSubmit(name) {
      this.$refs[name].validate(valid => {
        var that = this;
        if (valid) {
          that.$Spin.show();
          that.loading = true;
          var md5 = require("js-md5");
          console.log(md5(that.formInline.password));
          that
            .$http({
              baseURL: that.baseURL,
              url: "loginPost",
              data: {
                account: that.formInline.user,
                password: md5(that.formInline.password),
                userType: 1
              },
              method: "post",
              headers: {
                "Content-Type": "application/json"
              }
            })
            .then(function(data) {
              console.log(data);
              if (data.data.code === 200) {
                if (typeof data.data.data.workerTypeList !== "undefined") {
                  that.workerTypeRepo(data.data.data.workerTypeList);
                }
                that.$store.dispatch("setToken", data.data.token);
                that.$store.dispatch(
                  "setOrgcode",
                  data.data.data.userInfo.organizationCode
                );
                that.$store.dispatch(
                  "setLoginName",
                  data.data.data.userInfo.accountName
                );
                that.$store.dispatch(
                  "setLoginUserName",
                  data.data.data.userInfo.name
                );
                if (that.formInline.remember === "1") {
                  that.$store.dispatch(
                    "setUserLoginName",
                    that.formInline.user
                  );
                  that.$store.dispatch(
                    "setUserLoginPassword",
                    that.formInline.password
                  );
                  that.$store.dispatch(
                    "setUserLoginMode",
                    that.formInline.modal
                  );
                } else {
                  that.$store.dispatch("setUserLoginName", "");
                  that.$store.dispatch("setUserLoginPassword", "");
                  that.$store.dispatch("setUserLoginMode", "1");
                }
                that.closeModal();
              } else {
                that.$Message.error(data.data.message);
              }
            })
            .catch(function(error) {
              that.$Message.error("登录异常，请联系管理员");
              console.log(error);
            })
            .finally(() => {
              that.$Spin.hide();
              that.loading = false;
            });
        } else {
          that.$Message.error("输入数据不符合要求");
        }
      });
    },
    visibleChange(isVisible) {
      if (!isVisible) {
        this.closeModal();
      }
    },
    closeModal() {
      if (this.$store.state.modals.login.token !== "") {
        this.$store.dispatch("hideLoginModal");
        this.$router.replace({
          name: "project"
        });
      } else {
        this.$store.dispatch("showLoginModal");
      }
      this.resetInput();
    },
    resetInput() {
      this.$refs["formInline"].resetFields();
    },
    workerTypeRepo(list) {
      this.$store.dispatch("setWorkerType", list);
    }
  }
};
</script>

<style>
.center {
  text-align: center;
}

.demo-spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}

#new-board-modal .ivu-modal-content {
  background: -webkit-linear-gradient(top, #f8f8f9, white);
}

</style>