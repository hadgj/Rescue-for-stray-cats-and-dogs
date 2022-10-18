<template>
<div>
  <!--新增-->
    <el-form ref="form" :model="pet" label-width="80px">
      <el-form-item label="宠物名字">
        <el-input v-model="pet.petName"></el-input>
      </el-form-item>
      <el-form-item label="宠物类型">
        <el-select v-model="pet.petType" placeholder="请选择">
          <el-option value="猫猫">猫猫</el-option>
          <el-option value="狗狗">狗狗</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="宠物性别">
        <el-select v-model="pet.petSex" placeholder="请选择">
          <el-option value="小哥哥">小哥哥</el-option>
          <el-option value="小姐姐">小姐姐</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="宠物年龄">
        <el-input v-model="pet.petAge"></el-input>
      </el-form-item>
      <el-form-item label="宠物介绍">
        <el-input
            type="textarea"
            autosize
            v-model="pet.petDsc">
        </el-input>
      </el-form-item>
      <el-form-item label="领养状态">
        <el-select v-model="pet.petIsApply" placeholder="请选择">
          <el-option value="未领养">未领养</el-option>
          <el-option value="已领养">已领养</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="宠物地址">
        <el-input v-model="pet.petAddress"></el-input>
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input v-model="pet.otele"></el-input>
      </el-form-item>
      <el-form-item label="宠物图片">
        <el-upload
            class="upload-demo"
            multiple
            ref="upload"
            :file-list="fileList"
            :auto-upload="false"
            action="#"
            accept=".jpg,.jpeg,.gif,.bmp,.png"
        >
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addpet">确认</el-button>
      </el-form-item>
    </el-form>
</div>
</template>

<script>
export default {
  name: "adpet",
  mounted(){
    this.submit();
  },
  methods: {
    //==================宠物管理方法========================
    //按条件查询
    onsubmit(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/pet/selectByPageAndCondition?currentPage="+1+"&pageSize="+_this.pageSize,
        data:_this.pet1
      }).then(function (resp){
        _this.tableData=resp.data.rows;
        _this.totalCount=resp.data.totalCount;
      })
    },
    //刷新时查询
    submit(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/pet/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
        data:_this.pet1
      }).then(function (resp){
        _this.tableData=resp.data.rows;
        _this.totalCount=resp.data.totalCount;
      })
    },
    deleteByids(){
      this.$confirm(
          '此操作将永久删除数据，是否继续？',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            //1.创建id数组[1,2,3]，从this.multipleSelection获取即可
            for(let i=0;i<this.multipleSelection.length;i++){
              let selectionElement=this.multipleSelection[i];
              this.selectedIds[i]=selectionElement.petID;
            }
            //2.发送ajax请求
            var _this = this;
            // 发送ajax请求，添加数据
            this.$axios({
              method:"post",
              url:"http://localhost:9090/web-demo/pet/deleteByids",
              data:_this.selectedIds
            }).then(function (resp) {
              if(resp.data == "success"){
                //删除成功
                // 重新查询数据
                var num=_this.totalCount-_this.selectedIds.length;
                var num1=Math.ceil(num/_this.pageSize)
                _this.currentPage=num1;
                _this.submit();
                // 弹出消息提示
                _this.$message({
                  message: '删除成功',
                  type: 'success'
                });
              }
            })
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除！',
            })
          })
    },
    tableRowClassName({row, rowIndex}) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
    // 复选框选中后执行的方法
    handleSelectionChange(val) {
      this.multipleSelection = val;

      console.log(this.multipleSelection)
    },
    handleSizeChange(val){
      console.log(`每页 ${val} 条`);
      // 重新设置每页显示的条数
      this.pageSize=val;
      this.submit();
    },
    handleCurrentChange(val){
      console.log(`每页 ${val} 条`);
      // 重新设置每页显示的条数
      this.currentPage=val;
      this.submit();
    },
    deleteByid(val){
      this.$confirm(
          '此操作将永久删除数据，是否继续？',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            //2.发送ajax请求
            var _this = this;
            // 发送ajax请求，添加数据
            this.$axios({
              method:"post",
              url:"http://localhost:9090/web-demo/pet/deleteByid?petID="+val
            }).then(function (resp) {
              if(resp.data == "success"){
                //删除成功
                // 重新查询数据
                if((_this.currentPage-1)*_this.pageSize==(_this.totalCount-1)){
                  _this.currentPage=_this.currentPage-1;
                }
                _this.submit();
                // 弹出消息提示
                _this.$message({
                  message: '删除成功',
                  type: 'success'
                });
              }
            })
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除！',
            })
          })
    },
    preview(val){
      this.imgsurl=[];
      this.imgdialogVisible=true;
      this.imgnames=val.split("#");
      this.imgnames.pop();
      var baseurl="/file/";
      this.imgnames.forEach((item)=>{
        item=baseurl+item;
        this.imgsurl.push(item);
      })
    },
    editpet(){
      this.imgnames=[];
      var basestr="#";
      this.$refs.upload.uploadFiles.forEach((item)=>{
        item.name=item.name+basestr;
        this.imgnames.push(item.name);
        item.name = item.name.substr(0, item.name.length - 1);
      })
      var pic="";
      pic=this.imgnames.join("");
      this.pet2.pic=pic;
      console.log(this.pet2)
      var _this = this;
      // 发送ajax请求，添加数据
      this.$axios({
        method:"post",
        url:"http://localhost:9090/web-demo/pet/update",
        data:_this.pet2
      }).then(function (resp) {
        if(resp.data == "success"){
          //添加成功
          //关闭窗
          _this.editdialogVisible = false;
          // 重新查询数据
          _this.submit();
          // 弹出消息提示
          _this.$message({
            message: '恭喜你，修改成功',
            type: 'success'
          });
        }
      })
    },
    edit(val){
      this.fileList=[];
      this.imgsurl=[];
      //使用深拷贝，不改变拷贝前的值！！！！！！！
      this.pet2=Object.assign({},val);
      this.editdialogVisible=true;
      this.imgnames=this.pet2.pic.split("#");
      this.imgnames.pop();
      var baseurl="/file/";
      this.imgnames.forEach((item)=>{
        item=baseurl+item;
        this.imgsurl.push(item);
      })
      this.imgsurl.forEach(item=>{
        let obj=new Object();
        obj.url=item;
        obj.name=item.slice(6);
        this.fileList.push(obj);
      });
    },
    add(){
      this.dialogVisible=true;
      this.pet={
        petID:"",
        petName:"",
        petType:"",
        petSex:"",
        petAge:"",
        petDsc:"",
        petIsApply:"",
        petAddress:"",
        pic:""
      };
      this.fileList=[];
    },
    addpet(){
      this.imgnames=[];
      var basestr="#";
      this.$refs.upload.uploadFiles.forEach((item)=>{
        item.name=item.name+basestr;
        this.imgnames.push(item.name);
        item.name = item.name.substr(0, item.name.length - 1);
      })
      var pic="";
      pic=this.imgnames.join("");
      this.pet.pic=pic;
      var _this=this;
      this.$axios({
        method:"post",
        url:"http://localhost:9090/web-demo/pet/add",
        data:_this.pet
      }).then(function (resp) {
        if(resp.data == "success"){
          //添加成功
          var num=_this.totalCount+1;
          var num1=Math.ceil(num/_this.pageSize)
          _this.currentPage=num1;
          // 弹出消息提示
          _this.$message({
            message: '恭喜你，添加成功',
            type: 'success'
          });
          _this.submit();
          _this.dialogVisible=false;
        }
      })
    }
    //==================！！！宠物管理方法========================
  },
  data() {
    return {
      //===============宠物管理部分需要的=============================
      //添加图片是用来存放图片文件的数组
      fileList:[],
      //宠物图片展示时用的图片名字数组
      imgnames:[],
      //宠物图片展示展示时用的路径数组
      imgsurl:[],
      //每页显示的条数
      pageSize:5,
      //总记录数
      totalCount:100,
      // 当前页码
      currentPage: 1,
      // 添加数据对话框是否展示的标记
      dialogVisible: false,
      //编辑对话框是否展示标记
      editdialogVisible: false,
      //图片预览框是否展示
      imgdialogVisible: false,
      // 品牌模型数据
      pet: {
        petID:"",
        petName:"",
        petType:"",
        petSex:"",
        petAge:"",
        petDsc:"",
        petIsApply:"",
        petAddress:"",
        pic:"",
        otele:""
      },
      pet1: {
        petID:"",
        petName:"",
        petType:"",
        petSex:"",
        petAge:"",
        petDsc:"",
        petIsApply:"",
        petAddress:"",
        pic:"",
        otele:""
      },
      pet2: {
        petID:"",
        petName:"",
        petType:"",
        petSex:"",
        petAge:"",
        petDsc:"",
        petIsApply:"",
        petAddress:"",
        pic:"",
        otele:""
      },
      //被选中的id数组
      selectedIds:[],
      // 复选框选中数据集合
      multipleSelection: [],
      tableData: [],
      //===============！宠物管理部分需要的！=============================
    }
  }
}
</script>

<style scoped>

</style>