<template>
  <el-container class="iot-layout">
    <el-container class="iot-layout__container">
      <el-aside class="iot-layout__aside" :style="asideWidth">
        <div class="iot-layout__scrollbar">
          <el-scrollbar>
            <el-menu
              class="iot-layout__menu"
              router
              :default-active="activePath"
              :collapse="isCollapse"
              background-color="#29314d"
              text-color="#f5f6fa"
              active-text-color="#f5f6fa"
            >
              <template v-for="route in routes">
                <el-submenu
                  v-if="route.meta && route.meta.title"
                  :index="route.path"
                  :key="route.path"
                >
                  <template slot="title">
                    <i :class="route.meta.icon"></i>
                    <span slot="title">{{$t('route.' +route.meta.title)}}</span>
                  </template>
                  <template v-for="child in route.children">
                    <el-menu-item
                      v-if="!child.hidden"
                      :index="child.path"
                      :key="child.path"
                    >{{$t('route.' + child.meta.title)}}</el-menu-item>
                  </template>
                </el-submenu>
                <el-menu-item v-else :index="route.children[0].path" :key="route.children[0].path">
                  <i :class="route.meta.icon"></i>
                  <span slot="title">{{$t('route.' +route.children[0].meta.title)}}</span>
                </el-menu-item>
              </template>
            </el-menu>
          </el-scrollbar>
        </div>
      </el-aside>
      <el-container>
        <el-header class="iot-layout__header">
          <div class="iot-layout__collapse" :style="collapseStyle" @click="changeCollapse"></div>
          <div class="layout__top-center">
            <el-badge :value="12" class="custom-badge">
              <i class="el-icon-bell" style="cursor: pointer;"  @click="goToAlram"></i>
            </el-badge>
          </div>
          <el-select
            @change="changeSelect"
            v-model="lang"
            :style="{'width':lang == 'en' ? '75px':'60px'}"
            class="iot-select-home"
          >
            <el-option
              v-for="item in languageList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :disabled="item.disabled"
            ></el-option>
          </el-select>
          <div class="header-admin">
            <el-dropdown class="layout__dropdown" trigger="click">
              <span class="el-dropdown-link">
                {{$t('navbar.administrator')}}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <span @click="handlechangePwd">{{ $t('navbar.changePwd') }}</span>
                </el-dropdown-item>
                <el-dropdown-item>
                  <span @click="handleExit">{{ $t('navbar.logOut') }}</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
        <el-main class="iot-layout__main">
          <div class="layout__watch_record">
            <div class="layout__watch_record-left" @click="handleLeftMove"></div>
            <div
              id="recordBoxParent"
              class="layout__watch_record-center"
              :style="{overflow:'hidden'}"
            >
              <ul id="recordBox" class="record__list" :style="{width:recordWidth+'px'}">
                <li
                  class="record__list_item"
                  :class="{'active' : item.name === $route.name}"
                  v-for="item in route_record"
                  :key="item.path"
                  @click="openRecordRoute(item)"
                >
                  <span>{{$t('route.' + item.meta.title)}}</span>
                  <i
                    class="el-icon-close"
                    v-if="item.name !=='home'"
                    @click.stop="closeRecordRoute(item)"
                  ></i>
                </li>
              </ul>
            </div>

            <div class="layout__watch_record-btn">
              <div class="layout__watch_record-right" @click="handleRightMove"></div>
              <el-dropdown class="layout__dropdown" trigger="click">
                <span class="el-dropdown-link">
                  {{ $t('navbar.operation') }}
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>
                    <span @click="handleCloseAll">{{ $t('tagsView.closeAll') }}</span>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <span @click="handleCloseOther">{{ $t('tagsView.closeOthers') }}</span>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
          <div class="iot-layout__scrollbar">
            <el-scrollbar>
              <div class="iot-layout__view">
                <router-view :lang="lang" />
              </div>
            </el-scrollbar>
            <el-backtop
              class="iot-layout__backtop"
              target=".iot-layout__main .iot-layout__scrollbar .el-scrollbar__wrap"
            >
              <div class="home_back_top">
                <img src="@/assets/icon/home_back_top.png" />
                <div class="text">顶部</div>
              </div>
            </el-backtop>
          </div>
        </el-main>
      </el-container>
    </el-container>
    <el-dialog :title="$t('navbar.changePwd')" :visible.sync="dialogFormVisible" width="500px">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="right"
        :label-width="lang=='en' ? '140px' :'100px' "
        style="width: 100%;"
      >
        <el-form-item :label="$t('navbar.oldpsd')" prop="oldPassword" class="iot-loginFor-item">
          <el-input
            :type="showodlPass ? 'text' : 'password'"
            class="iot-loginForm-input"
            v-model="temp.oldPassword"
            :placeholder="$t('login.password')"
          ></el-input>
          <i
            :class="showodlPass? icon1: icon2"
            style="position: absolute; right: 1rem; top: 0.2rem;color: #1F47A4"
            @click="showodlPass=!showodlPass"
          ></i>
        </el-form-item>
        <el-form-item :label="$t('navbar.newpsd')" prop="newPassword" class="iot-loginFor-item">
          <el-input
            :type="shownewPass ? 'text' : 'password'"
            class="iot-loginForm-input"
            v-model="temp.newPassword"
            :placeholder="$t('login.password')"
          ></el-input>
          <i
            :class="shownewPass? icon1: icon2"
            style="position: absolute; right: 1rem; top: 0.2rem;color: #1F47A4"
            @click="shownewPass=!shownewPass"
          ></i>
        </el-form-item>
        <el-form-item
          :label="$t('navbar.confirmpsd')"
          prop="confirmPassword"
          class="iot-loginFor-item"
        >
          <el-input
            :type="showconfirmPass ? 'text' : 'password'"
            class="iot-loginForm-input"
            v-model="temp.confirmPassword"
            :placeholder="$t('login.password')"
          ></el-input>
          <i
            :class="showconfirmPass? icon1: icon2"
            style="position: absolute; right: 1rem; top: 0.2rem;color: #1F47A4"
            @click="showconfirmPass=!showconfirmPass"
          ></i>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <!--                <el-button @click="dialogFormVisible = false">{{ $t('table.empty') }}</el-button>-->
        <el-button @click="reset()">{{ $t('permission.empty') }}</el-button>
        <el-button type="primary" @click="updatePassword()">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
import { mapState } from "vuex";
import Utils from "@/utils/index";
import { changePwd, changeLanguage } from "./api";

export default {
  name: "container",
  data() {
    return {
      icon1: "iconfont icon-icon-eye-open",
      icon2: "iconfont icon-biyan",
      showodlPass: false,
      shownewPass: false,
      showconfirmPass: false,
      isCollapse: false,
      breadcrumb: [],
      activePath: "/",
      moveLength: 0,
      languageList: [],
      asideWidth: {
        width: "auto"
      },
      dialogFormVisible: false,
      passwordTypeNew: "password",
      passwordTypeConfirm: "password",
      passwordTypeOld: "password",
      temp: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: ""
      }
    };
  },
  mounted() {
    let lang = this.$store.state.AppMoudule.language;
    this.$store.dispatch("SetLanguage", lang);
    this.$i18n.locale = lang;
    this.initLang();
    //this.changeLanguage(lang);
  },
  computed: {
    rules() {
      //此处的rules 是你声明的名字，参考下图
      var validate_Types = (rule, value, callback) => {
        //兑换类型规则
        if (this.dataForm.types == "") {
          callback(new Error(this.$t("navbar.systembusy")));
        }
        callback();
      };
      return {
        //注意此处的return，别忘记了
        ConverTypes: [{ validator: validate_Types, trigger: "change" }],
        newPassword: [
          {
            required: true,
            message: this.$t("navbar.oldpsdnull"),
            trigger: "change"
          }
        ],
        oldPassword: [
          {
            required: true,
            message: this.$t("navbar.newpsdnull"),
            trigger: "change"
          }
        ],
        confirmPassword: [
          {
            required: true,
            message: this.$t("navbar.confirmpsdnull"),
            trigger: "change"
          }
        ]
      };
    },

    ...mapState(["route_record"]),
    routes() {
      let routes = this.$router.options.routes;
      let list = [];
      routes.forEach(item => {
        if (item.children) {
          list.push(item);
        }
      });
      return list;
    },
    collapseStyle() {
      if (this.isCollapse) {
        this.asideWidth.width = "80px";
        return {
          backgroundImage: `url(${require("@/assets/icon/expand.png")})`
        };
      } else {
        this.asideWidth.width = "auto";
        return {
          backgroundImage: `url(${require("@/assets/icon/pack.png")})`
        };
      }
    },
    recordWidth() {
      if (this.$i18n.locale === "en") {
        return this.route_record.length * 185;
      }
      return this.route_record.length * 125;
    },
    lang: {
      get() {
        return this.$store.state.AppMoudule.language;
      },
      set(lang) {
        console.log('-----' + lang)
        this.$store.dispatch("SetLanguage", lang);
        this.$i18n.locale = lang;
        this.initLang();
      }
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.activePath = route.path;
        let list = [];
        route.matched.forEach(item => {
          if (item.meta.title) {
            list.push({
              title: item.meta.title,
              path: item.path
            });
          }
        });
        this.breadcrumb = list;
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    reset() {
      this.$refs.dataForm.resetFields();
    },
    //修改语言
    changeSelect(val) {
      //this.changeLanguage(val);
    },
    async changeLanguage(val) {
      let params = {
        LanguageName: val
      };
      let res = await changeLanguage(params);
      console.log(res)
    },
    updatePassword() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          if (this.temp.newPassword != this.temp.confirmPassword) {
            this.$message.error(this.$t("navbar.psdnotmatch"));
            return;
          }
          this.$confirm(
            this.$t("navbar.changepsdtip"),
            this.$t("navbar.tiptitle"),
            {
              confirmButtonText: this.$t("table.confirm"),
              cancelButtonText: this.$t("table.cancel"),
              type: "warning"
            }
          ).then(async action => {
            let params = {
              CurrentPassword: this.temp.oldPassword,
              NewPassword: this.temp.newPassword
            };

            let res = await changePwd(params);
            if (res) {
              this.$message({
                type: "success",
                message: this.$t("navbar.changesucc")
              });
              this.dialogFormVisible = false;
            }
          });
        }
      });
    },
    /*显示密码*/
    showPwd(type) {
      if (type == "new") {
        if (this.passwordTypeNew === "password") {
          this.passwordTypeNew = "";
        } else {
          this.passwordTypeNew = "password";
        }
      } else if (type == "confirm") {
        if (this.passwordTypeConfirm === "password") {
          this.passwordTypeConfirm = "";
        } else {
          this.passwordTypeConfirm = "password";
        }
      } else if (type == "old") {
        if (this.passwordTypeOld === "password") {
          this.passwordTypeOld = "";
        } else {
          this.passwordTypeOld = "password";
        }
      }
    },
    changeCollapse() {
      this.isCollapse = this.isCollapse ? false : true;
    },
    openRecordRoute(item) {
      this.$router.push({
        name: item.name
      });
    },
    closeRecordRoute(item) {
      this.$store.commit("closeOneRouteRecord", item);
      if (this.$route.name === item.name) {
        this.$router.push({
          name: this.route_record[this.route_record.length - 1]["name"]
        });
      }
    },
    handleExit() {
      this.$confirm(this.$t("navbar.exitsys"), this.$t("navbar.tiptitle"), {
        confirmButtonText: this.$t("table.confirm"),
        cancelButtonText: this.$t("table.cancel"),
        type: "warning"
      })
        .then(() => {
          this.handleCloseAll();
          Utils.setStorage("userInfo", null);
          this.$router.replace("/Login");
        })
        .catch(() => {});
    },
    handlechangePwd() {
      //this.$refs['dataForm'].resetFields();
      this.temp = {
        oldPassword: "",
        newPassword: "",
        confirmPassword: ""
      };
      this.dialogFormVisible = true;
      this.reset();
    },
    handleLeftMove() {
      if (this.moveLength === 0) {
        return;
      }
      this.moveLength = this.moveLength + 250;
      let box = document.getElementById("recordBox");
      box.style.transform = `translateX(${this.moveLength}px)`;
    },
    handleRightMove() {
      let parentWidth = document.getElementById("recordBoxParent").offsetWidth;
      if (this.moveLength + this.recordWidth < parentWidth) {
        return;
      }
      this.moveLength = this.moveLength - 250;

      let box = document.getElementById("recordBox");
      box.style.transform = `translateX(${this.moveLength}px)`;
    },
    handleCloseAll() {
      this.$store.commit("closeAllRouteRecord");
      this.$router.push({ name: "home" });
    },
    handleCloseOther() {
      let obj;
      this.route_record.forEach(el => {
        if (this.$route.name == el.name) {
          obj = el;
        }
      });
      if (obj) {
        this.$store.commit("closeOtherRouteRecord", obj);
        this.$router.push({ name: this.$route.name });
      }
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
    },
    //跳到报警页面
    goToAlram(){
      this.$router.push({name:'warehouseWarning-threshold'})
    }
  }
};
</script>

<style lang="scss">
.iot-layout {
  height: 100%;
  overflow: hidden;

  .el-scrollbar {
    height: 100%;

    .el-scrollbar__wrap {
      overflow-x: hidden;
    }
  }
}

.iot-layout__header {
  height: 48px !important;
  background-color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .header-logo {
    font-size: 18px;
    width: 204px;
    height: 25px;
    line-height: 25px;
    padding-left: 140px;
    color: #585860;
    background-image: url("~@/assets/icon/home_logo.png");
    background-repeat: no-repeat;
    background-position: 0 center;
    cursor: pointer;
  }

  .iot-select-home {
    input {
      border: none !important;
      font-size: 14px;
      background: none;
      color: #005cdc;
      padding: 0 !important;
      background: none !important;
    }
  }
}

.iot-layout__container {
  overflow: hidden;
}

.iot-layout__aside {
  width: initial !important;
  border-right: solid 1px #e5e5e5;
  box-shadow: 1px 0px 8px 0px rgba(145, 145, 145, 0.2);
  background-color: #29314d;
  display: flex !important;
  flex-direction: column;

  .iot-layout__menu {
    border: 0;
    width: 200px;
    text-align: left;

    &.el-menu--collapse {
      width: initial;
    }

    .el-menu-item.is-active {
      background-color: #005cdc !important;
    }
  }

  .iot-layout__scrollbar {
    flex: 1;
    overflow: hidden;
  }
}

.iot-layout__main {
  padding: 0 !important;
  overflow: hidden;
  display: flex !important;
  flex-direction: column;
  background-color: #eff2f4;

  .iot-layout__breadcrumb {
    padding: 15px;

    .el-breadcrumb__inner {
      color: #666;
    }

    .el-breadcrumb__item:last-child .el-breadcrumb__inner {
      color: #666;
      font-weight: bold;
    }

    .el-breadcrumb__separator {
      color: #4b4c4d;
    }
  }

  .iot-layout__scrollbar {
    flex: 1;
    overflow: hidden;
    background-color: #eff2f4;
  }

  .iot-layout__view {
    padding: 0 15px 15px;
    background-color: #eff2f4;

    > div {
      background-color: #fff;
    }
  }

  .iot-layout__backtop {
    width: 46px;
    height: 46px;
    border-radius: 4px;
    background-color: #409eff;

    &:hover {
      background-color: #409eff;
    }

    .home_back_top {
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      font-size: 12px;
      color: #fff;

      img {
        width: 18px;
        height: 16px;
      }

      .text {
        height: 13px;
      }
    }
  }
}

.header-logo {
  font-size: 18px;
  width: 204px;
  height: 25px;
  line-height: 25px;
  padding-left: 140px;
  color: #ffffff;
  background-image: url("~@/assets/icon/home_logo.png");
  background-repeat: no-repeat;
  background-position: 0 center;
  cursor: pointer;
}

.header-admin {
  font-size: 14px;
  height: 28px;
  line-height: 28px !important;
  padding-left: 35px;
  color: #585860;
  background-image: url("~@/assets/icon/home_admin.png");
  background-repeat: no-repeat;
  background-position: 0 center;
  background-size: 28px 28px;

  .layout__dropdown {
    line-height: 28px;
    padding: 0;
  }
}

.iot-layout__collapse {
  display: inline-block;
  width: 24px;
  height: 48px;
  padding: 12px 0;
  box-sizing: border-box;
  cursor: pointer;
  font-size: 16px;
  background: url("~@/assets/icon/pack.png") no-repeat left center;
  background-size: 18px 18px;
}

.layout__top-center {
  flex: 1;
  display: flex;
  justify-content: flex-end;
  padding-right: 44px;
  font-size: 24px;

  .custom-badge {
    .el-badge__content.is-fixed {
      top: 4px;
    }
  }
}

.layout__watch_record {
  height: 40px;
  margin-bottom: 16px;
  background-color: #fff;
  box-shadow: 0 1px 1px 0 rgba(239, 242, 244, 0.8) inset;
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  justify-content: space-between;

  &-btn {
    height: 100%;
    display: flex;
    flex-flow: row nowrap;
    align-items: center;
  }

  &-center {
    flex: 1;
    height: 100%;
  }

  &-left,
  &-right {
    height: 100%;
    width: 40px;
    border-left: 1px solid #eff2f4;
    border-right: 1px solid #eff2f4;
    background: url("~@/assets/icon/left-tab.png") no-repeat center center;
    background-size: 8px;
    cursor: pointer;
  }

  &-right {
    background: url("~@/assets/icon/right-tab.png") no-repeat center center;
  }
}

.record__list {
  height: 100%;
  font-size: 12px;
  color: #585860;

  &_item {
    height: 100%;
    line-height: 40px;
    padding: 0 16px;
    border-right: 1px solid #eff2f4;
    display: inline-block;
    cursor: pointer;
    transition: all 0.2s;

    > i {
      display: inline-block;
      margin-left: 8px;
    }
  }

  .record__list_item.active {
    background-color: #005cdc;
    color: #fff;
  }
}

.layout__dropdown {
  height: 100%;
  line-height: 40px;
  padding: 0 16px;
  font-size: 12px;
  color: #878a99;
  cursor: pointer;
  border: 0;
  outline: none;

  .el-dropdown-link {
    height: 100%;
    width: 100%;
    display: inline-block;
    border: 0;
    outline: none;
  }
}
</style>