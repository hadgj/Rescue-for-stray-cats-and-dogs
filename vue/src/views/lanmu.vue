<template>
<div>
  <!--搜索表单-->
  <el-form :inline="true"  class="demo-form-inline">

    <el-form-item label="所属主菜单">
      <el-select v-model="lanmuliitem1.parentid" clearable placeholder="请选择">
        <el-option
            v-for="item in lanmuData"
            :label="item.lanmuli"
            :value="item.id">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onsubmit">查询</el-button>
    </el-form-item>
  </el-form>
  <!--按钮-->
  <el-row>
    <el-button type="danger" plain @click="deleteByids">批量删除</el-button>
    <el-button type="danger" plain @click="xinzeng">增加</el-button>
  </el-row>
  <!--新增-->
  <el-dialog
      title="新增"
      :visible.sync="adddialogVisible"
      width="60%"
  >
    <el-form ref="form" :model="lanmuliitem3" label-width="120px">
      <el-form-item label="所属主菜单">
        <el-select v-model="lanmuliitem3.parentid" clearable placeholder="请选择">
          <el-option
              v-for="item in lanmuData"
              :label="item.lanmuli"
              :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="栏目名字">
        <el-input
            type="textarea"
            autosize
            v-model="lanmuliitem3.itemname">
        </el-input>
      </el-form-item>
      <el-form-item label="栏目问题">
        <el-input
            type="textarea"
            autosize
            v-model="lanmuliitem3.itemques">
        </el-input>
      </el-form-item>
      <el-form-item label="栏目答案">
        <el-input
            type="textarea"
            autosize
            v-model="lanmuliitem3.itemansw">
        </el-input>
      </el-form-item>
      <el-form-item label="资源文件">
      <el-upload
          class="avatar-uploader el-upload--tex"
          multiple
          ref="upload"
          :auto-upload="false"
          action="#"
      >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="add">确认</el-button>
        <el-button @click="adddialogVisible = false">取消</el-button>
      </el-form-item>
    </el-form>

  </el-dialog>
  <!--编辑-->
  <el-dialog
      title="编辑"
      :visible.sync="editdialogVisible"
      width="60%"
  >
    <el-form ref="form" :model="lanmuliitem2" label-width="120px">
        <el-form-item label="所属主菜单">
          <el-select v-model="lanmuliitem2.parentid" clearable placeholder="请选择">
            <el-option
                v-for="item in lanmuData"
                :label="item.lanmuli"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      <el-form-item label="栏目名字">
        <el-input
            type="textarea"
            autosize
            v-model="lanmuliitem2.itemname">
        </el-input>
      </el-form-item>
      <el-form-item label="栏目问题">
        <el-input
            type="textarea"
            autosize
            v-model="lanmuliitem2.itemques">
        </el-input>
      </el-form-item>
      <el-form-item label="栏目答案">
        <el-input
            type="textarea"
            autosize
            v-model="lanmuliitem2.itemansw">
        </el-input>
      </el-form-item>
      <el-form-item label="资源文件">
        <el-upload
            class="avatar-uploader el-upload--tex"
            multiple
            :file-list="fileList"
            ref="upload1"
            :auto-upload="false"
            action="#"
        >
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="update">确认</el-button>
        <el-button @click="editdialogVisible = false">取消</el-button>
      </el-form-item>
    </el-form>

  </el-dialog>
  <!--新闻展示面板-->
  <el-dialog title="内容" :visible.sync="contentVisible"
  >
    <span>{{message}}</span>
    <div slot="footer" class="dialog-footer">
      <el-button @click="contentVisible = false">收起</el-button>
    </div>
  </el-dialog>
  <!--表格-->
  <template>
    <el-table
        :data="lanmuliitemData"
        style="width: 100%"
        :row-class-name="tableRowClassName"
        @selection-change="handleSelectionChange"
    >
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="parentid"
          label="所属主菜单"
          align="center">
        <template slot-scope="scope">
          <div v-for="(item) in lanmuData">
            <span v-if="scope.row.parentid===item.id">{{item.lanmuli}}</span>
          </div>

        </template>
      </el-table-column>

      <el-table-column
          prop="itemname"
          label="子栏目名字"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="itemques"
          label="问题"
          align="center"
      >
        <template slot-scope="scope">
          <el-button type="text" @click="chakan(scope.row.itemques)">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column
          prop="itemansw"
          label="答案或者内容"
          align="center"
      >
        <template slot-scope="scope">
          <el-button type="text" @click="chakan(scope.row.itemansw)">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column
          prop="itempic"
          label="资源名"
          align="center"
      >
      </el-table-column>
      <el-table-column
          align="center"
          label="操作"
          width="200" >
        <template slot-scope="scope">
          <el-row>
            <el-button type="primary" plain @click="edit(scope.row)">编辑</el-button>
            <el-button type="danger" plain @click="deleteByid(scope.row.id)">删除</el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
  </template>
  <!--分页工具条-->
  <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="5"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalCount">
  </el-pagination>
</div>
</template>

<script>
import axios from "axios";

export default {
  name: "lanmu",
  mounted() {
    this.submitlanmu();
    this.submit();
  },
  methods:{
    tableRowClassName({row, rowIndex}) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
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
    chakan(val){
      this.contentVisible=true;
      this.message=val;
    },
    preview(val){
      console.log(val);
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
              url:"http://localhost:9090/web-demo/LanmuliitemServlet/deleteByid?id="+val
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
              this.selectedIds[i]=selectionElement.id;
            }
            //2.发送ajax请求
            var _this = this;
            // 发送ajax请求，添加数据
            this.$axios({
              method:"post",
              url:"http://localhost:9090/web-demo/LanmuliitemServlet/deleteByids",
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
    submit(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/LanmuliitemServlet/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
        data:_this.lanmuliitem1
      }).then(function (resp){
        _this.lanmuliitemData=resp.data.rows;
        _this.totalCount=resp.data.totalCount;
      })
  },
    submitlanmu(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/LanmuServlet/selectAll"
      }).then(function (resp){
        _this.lanmuData=resp.data;
      })
    },
    onsubmit(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/LanmuliitemServlet/selectByPageAndCondition?currentPage="+1+"&pageSize="+_this.pageSize,
        data:_this.lanmuliitem1
      }).then(function (resp){
        _this.lanmuliitemData=resp.data.rows;
        _this.totalCount=resp.data.totalCount;
      })
    },
    edit(val){
      this.fileList=[];
      //使用深拷贝，不改变拷贝前的值！！！！！！！
      this.lanmuliitem2=Object.assign({},val);
      var ziyuanname=this.lanmuliitem2.itempic;
      this.yuanwenjian=ziyuanname;
      var url="http://localhost:9090/web-demo/upload/"+ziyuanname
      let obj=new Object();
      obj.url=url;
      obj.name=ziyuanname;
      this.fileList.push(obj);
      this.editdialogVisible=true;
    },
    update(){
      var _this=this;
      if(this.$refs.upload1.uploadFiles[0]!=undefined)
      {
        console.log("选取了文件！");
        console.log(this.$refs.upload1.uploadFiles[0].name);
        console.log(this.lanmuliitem2.itempic)
        if(this.$refs.upload1.uploadFiles[0].name==this.lanmuliitem2.itempic){
          console.log("文件名重复不可以写入！");
        }
        else
        {
          console.log("文件名不重复可以写入！");
          this.lanmuliitem2.itempic=this.$refs.upload1.uploadFiles[0].name;
          //资源名不同可以传文件
          let param = new FormData(); // 创建form对象
          this.$refs.upload1.uploadFiles.forEach((item)=>{
            param.append("file",item.raw);
            console.log(param); // FormData私有类对象，访问不到，可以通过get判断值是否传进去
          })
          let config = {
            headers: { "Content-Type": "multipart/form-data" }
          };
          axios.post("http://localhost:9090/web-demo/UploadServlet/upload", param, config).then(function(resp){
            if(resp.data!="000")
            {
              _this.$message({
                message: '恭喜你，修改资源成功',
                type: 'success'
              });
            }
            else {
              _this.$message({
                message: '资源已存在',
                type: 'error'
              });
            }
          })
        }
      }
      else
      {
        console.log("并未选取文件！");
        this.lanmuliitem2.itempic="";
      }
          // 发送ajax请求，添加数据
          _this.$axios({
            method:"post",
            url:"http://localhost:9090/web-demo/LanmuliitemServlet/update",
            data:_this.lanmuliitem2
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
    xinzeng(){
      this.fileList=[];
      this.lanmuliitem3={
        id:'',
        parentid:'',
        itemname:"",
        itemques:"",
        itemansw:"",
        itempic:""
      },
      this.adddialogVisible=true
    },
    add(){
      var _this = this;
      let param = new FormData(); // 创建form对象
      this.$refs.upload.uploadFiles.forEach((item)=>{
        param.append("file",item.raw);
        console.log(param); // FormData私有类对象，访问不到，可以通过get判断值是否传进去
      })
      let config = {
        headers: { "Content-Type": "multipart/form-data" }
      };
      axios.post("http://localhost:9090/web-demo/UploadServlet/upload", param, config).then(function(resp){
        if(resp.data!="000")
        {
          _this.lanmuliitem3.itempic=resp.data;
          // 发送ajax请求，添加数据
          _this.$axios({
            method:"post",
            url:"http://localhost:9090/web-demo/LanmuliitemServlet/add",
            data:_this.lanmuliitem3
          }).then(function (resp) {
            if(resp.data == "success"){
              //添加成功
              //关闭窗
              _this.adddialogVisible = false;
              // 重新查询数据
              _this.submit();
              // 弹出消息提示
              _this.$message({
                message: '恭喜你，新增成功',
                type: 'success'
              });
            }
          })
        }
        else{
          _this.$message({
            message: '文件名重复！',
            type: 'error'
          });
        }
      })

    }
  },
  data(){
    return{
      yuanwenjian:"",
      fileList:[],
      totalCount:100,
      currentPage: 1,
      lanmuData:[],
      lanmuliitemData:[],
      contentVisible:false,
      message:"",
      lanmuliitem:{
        id:'',
        parentid:'',
        itemname:"",
        itemques:"",
        itemansw:"",
        itempic:""
      },
      lanmuliitem1:{
        id:'',
        parentid:'',
        itemname:"",
        itemques:"",
        itemansw:"",
        itempic:""
      },
      lanmuliitem2:{
        id:'',
        parentid:'',
        itemname:"",
        itemques:"",
        itemansw:"",
        itempic:""
      },
      lanmuliitem3:{
        id:'',
        parentid:'',
        itemname:"",
        itemques:"",
        itemansw:"",
        itempic:""
      },
      pageSize:5,
      //被选中的id数组
      selectedIds:[],
      // 复选框选中数据集合
      multipleSelection: [],
      adddialogVisible:false,
      editdialogVisible: false
    }
  }
}
</script>

