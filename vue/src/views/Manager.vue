<template>
<el-container style="min-height:100vh;">
  <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px rgba(0,21,41,0.35); ">
<Aside :is-collapse="isCollapse" :logo-text-show="logoTextShow" />
  </el-aside>

<el-container>

  <el-header style="border-bottom: 1px solid #cccccc;">
   <Header :collapse-btn-class="collapseBtnClass" :collapse="collapse" />
  </el-header>

  <el-main>
<!--    表示当前页面的子路由会在router-view里面展示-->
<router-view/>
  </el-main>

</el-container>
</el-container>
</template>


<script>
import Aside from "@/views/Aside";
import Header from "@/views/Header";
export default {
  components: {Header, Aside},
  data() {
    return {
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWidth:200,
      logoTextShow:true,
      input: ''
    }
  },
  comments:{
    Aside,
    Header
  },
  methods:{
    collapse(){
      this.isCollapse=!this.isCollapse
      if(this.isCollapse){
        this.sideWidth=64;
        this.collapseBtnClass="el-icon-s-unfold";
        this.logoTextShow=false;
      }else {
        this.sideWidth=200;
        this.collapseBtnClass="el-icon-s-fold";
        this.logoTextShow=true;
      }
    }
  }
};
</script>
