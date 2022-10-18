<template>
  <div style="font-size: 12px;line-height: 60px;display:flex;">
    <div style="flex: 1;font-size: 18px;">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
    </div>
    <el-dropdown>
      <img :src="url" alt="" style="display: inline-block;width: 24px;height: 24px;vertical-align: middle;margin-right: 5px;border-radius: 12px;">
      <span>{{ admin.username }}</span>
      <i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item><a href="javascript:location.href='http://localhost:9090/web-demo/exitServlet';">退出</a></el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse:Function
  },
  mounted(){
    this.submit();
  },
  methods:{
    update() {
      this.admin.pic=this.$refs.upload.uploadFiles[0].name;
      var _this = this;
      // 发送ajax请求，添加数据
      this.$axios({
        method:"post",
        url:"http://localhost:9090/web-demo/admin/update",
        data:_this.admin
      }).then(function (resp) {
        if(resp.data == "success"){
          //添加成功
          //关闭窗
          // 重新查询数据
          _this.onsubmit();
          // 弹出消息提示
          _this.$message({
            message: '恭喜你，修改成功',
            type: 'success'
          });
        }
      })
    },
    handleRemove(file, fileList) {
      console.log(fileList)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    submit(){
      var _this=this;
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/admin/getUsername"
      }).then(function (resp){
        _this.admin=Object.assign({},resp.data);
        _this.fileList=[];
        _this.url="";
        var baseurl="http://localhost:9090/file/";
        _this.url=baseurl+_this.admin.pic;
        let obj=new Object();
        obj.name=_this.admin.pic;
        obj.url=_this.url;
        _this.fileList.push(obj);
      })

    },
    onsubmit(){
      var _this=this;
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/admin/selectbytele?tele="+_this.admin.tele
      }).then(function (resp){
        _this.admin=Object.assign({},resp.data);
        _this.fileList=[];
        _this.url="";
        var baseurl="http://localhost:9090/file/";
        _this.url=baseurl+_this.admin.pic;
        let obj=new Object();
        console.log(_this.admin.pic)
        obj.name=_this.admin.pic;
        obj.url=_this.url;
        _this.fileList.push(obj);
      })
    }
  },
  data(){
    return{
      admin:{
        tele:"",
        password:"",
        sex:"",
        age:'',
        pic:"",
        status:'',
        username:""
      },
      dialogImageUrl: '',
      dialogVisible: false,
      fileList:[],
      url:""
    }}
}
</script>

<style scoped>

</style>