<template>
    <el-menu style="overflow-x: hidden;min-height: 100%;"   background-color="rgb(48,65,86)"
             text-color="#ffffff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="isCollapse"
             router>

      <div style="height: 60px;line-height: 60px;text-align: center;">
        <img src="../assets/logo.png" alt="" style="width: 15px;vertical-align: middle;">
        <b style="color: azure" v-show="logoTextShow">救助平台后台管理系统</b>
      </div>
      <el-submenu index="1">
        <template slot="title">
          <img src="../assets/个人信息.png" alt="" style="width: 25px;vertical-align: middle;margin-right: 5px;" >
          <span slot="title" >个人信息管理</span></template>
        <el-menu-item-group>
          <template slot="title">操作</template>
          <el-menu-item index="/admin">修改基本信息</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="2">
        <template slot="title">
          <img src="../assets/猫.png" alt="" style="width: 25px;vertical-align: middle;margin-right: 5px;" >
          <span slot="title" >领养管理</span></template>
        <el-menu-item-group>
          <template slot="title">操作</template>
          <el-menu-item index="/pet">未领养宠物</el-menu-item>
          <el-menu-item index="/adopt">审核领养</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
          <img src="../assets/文章.png" alt="" style="width: 25px;vertical-align: middle;margin-right: 5px;" >
          <span slot="title" >前台功能管理</span></template>
        <el-menu-item-group>
          <template slot="title">操作</template>
          <el-menu-item index="/adpet">新增宠物</el-menu-item>
          <el-menu-item index="/help">求助信息管理</el-menu-item>
          <el-menu-item index="/news">文章管理</el-menu-item>
          <el-menu-item index="/adnews">新增文章</el-menu-item>
          <el-menu-item index="/comment">文章评论管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <img src="../assets/维保内容管理.png" alt="" style="width: 25px;vertical-align: middle;margin-right: 5px;" >
          <span slot="title" >网站内容管理</span></template>
        <el-menu-item-group>
          <template slot="title">操作</template>
          <el-menu-item index="/zhulanmu">主菜单管理</el-menu-item>
          <el-menu-item index="/lanmu">栏目管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="5">
        <template slot="title">
          <img src="../assets/用户.png" alt="" style="width: 25px;vertical-align: middle;margin-right: 5px;" >
          <span slot="title" >用户信息管理</span></template>
        <el-menu-item-group>
          <template slot="title">操作</template>
          <el-menu-item index="/regular">普通用户管理</el-menu-item>
          <el-menu-item index="/organization">救助机构管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="6">
        <template slot="title">
          <img src="../assets/投诉.png" alt="" style="width: 25px;vertical-align: middle;margin-right: 5px;" >
          <span slot="title" >投诉管理</span></template>
        <el-menu-item-group>
          <template slot="title">操作</template>
          <el-menu-item index="/tousu">查看所有</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="7">
        <template slot="title">
          <img src="../assets/信箱.png" alt="" style="width: 25px;vertical-align: middle;margin-right: 5px;" >
          <span slot="title" >留言箱</span></template>
        <el-menu-item-group>
          <template slot="title">操作</template>
          <el-menu-item index="/chatbox">查看所有</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="8">
        <template slot="title">
          <img src="../assets/敏感词词库.png" alt="" style="width: 25px;vertical-align: middle;margin-right: 5px;" >
          <span slot="title" >敏感词管理</span></template>
        <el-menu-item-group>
          <template slot="title">操作</template>
          <el-menu-item index="/mingan">敏感词管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

    </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse:Boolean,
    logoTextShow:Boolean
  }
}
</script>

<style scoped>

</style>