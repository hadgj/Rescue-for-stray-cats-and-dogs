<template>
<div>
  <template>
    <el-form ref="form" :model="admin" label-width="80px">
      <el-form-item label="联系电话">
        <el-col :span="5">
          <el-input v-model="admin.tele"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="年龄">
        <el-col :span="5">
          <el-input v-model="admin.age"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="名字">
        <el-col :span="5">
          <el-input v-model="admin.username"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="admin.sex" placeholder="请选择">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="头像">
        <el-upload
            action="#"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            ref="upload"
            :file-list="fileList"
            :auto-upload="false"
            accept=".jpg,.jpeg,.gif,.bmp,.png" >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="update">立即修改</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </template>
</div>
</template>

<script>
export default {
  name: "admin",
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