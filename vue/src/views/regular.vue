<template>
<div>
  <!--搜索表单-->
  <el-form :inline="true" :model="regular1" class="demo-form-inline">
    <el-form-item label="用户电话">
      <el-input v-model="regular1.tele" placeholder="用户电话"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onsubmit">查询</el-button>
    </el-form-item>
  </el-form>
  <!--按钮-->
  <el-row>
    <el-button type="danger" plain @click="deleteByids">批量删除</el-button>
  </el-row>
  <!--头像展示面板-->
  <el-dialog title="头像" :visible.sync="picVisible"
  >
    <el-image
        style="width: 100px; height: 100px;margin-right: 10px;"
        :src="pic"
        :preview-src-list="[pic]"
    ></el-image>
    <div slot="footer" class="dialog-footer">
      <el-button @click="picVisible = false">收起</el-button>
    </div>
  </el-dialog>
  <!--审核面板-->
  <el-dialog title="审核用户" :visible.sync="shenheVisible"
  >
    <el-select v-model="status" placeholder="请选择">
      <el-option label="启用账号" value='2'></el-option>
      <el-option label="禁用账号" value='4'></el-option>
    </el-select>
    <div slot="footer" class="dialog-footer">
      <el-button @click="qverenshenhe">确认审核</el-button>
      <el-button @click="shenheVisible = false">取消</el-button>
    </div>
  </el-dialog>
  <!--表格-->
  <template>
    <el-table
        :data="tableData"
        style="width: 100%"
        :row-class-name="tableRowClassName"
        @selection-change="handleSelectionChange"
    >
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column
          prop="tele"
          label="电话"
          align="center"
      >
      </el-table-column>

      <el-table-column
          prop="password"
          label="密码"
          align="center"
      >
      </el-table-column>

      <el-table-column
          prop="sex"
          align="center"
          label="性别">
      </el-table-column>

      <el-table-column
          prop="age"
          align="center"
          label="年龄">
      </el-table-column>

      <el-table-column
          prop="address"
          align="center"
          label="住址">
      </el-table-column>

      <el-table-column
          prop="username"
          align="center"
          label="昵称">
      </el-table-column>

      <el-table-column
          prop="status"
          align="center"
          label="身份">
        <template slot-scope="scope">
          <span v-if="scope.row.status===2">用户</span>
          <span v-else-if="scope.row.status===4">禁用</span>
        </template>
      </el-table-column>

      <el-table-column
          align="center"
          label="头像">
        <template slot-scope="scope">
          <el-button type="text" @click="picshow(scope.row.pic)">预览</el-button>
        </template>
      </el-table-column>

      <el-table-column
          align="center"
          label="操作"
          width="200" >
        <template slot-scope="scope">
          <el-row>
            <el-button type="danger" plain @click="shenhe(scope.row)">审核</el-button>
            <el-button type="danger" plain @click="deleteByid(scope.row.tele)">删除</el-button>
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
export default {
  name: "regular",
  mounted(){
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
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      // 重新设置每页显示的条数
      this.pageSize = val;
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      // 重新设置当前页码
      this.currentPage= val;
    },
    onsubmit(){
      var _this=this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/Regular/selectByPageAndCondition?currentPage="+1+"&pageSize="+_this.pageSize,
        data:_this.regular1
      }).then(function (resp){
        _this.tableData=resp.data.rows;
        _this.totalCount=resp.data.totalCount;
      })
    },
    submit(){
      var _this=this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/Regular/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
        data:_this.regular1
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
              this.selectedIds[i]=selectionElement.tele;
            }
            //2.发送ajax请求
            var _this = this;
            // 发送ajax请求，添加数据
            this.$axios({
              method:"post",
              url:"http://localhost:9090/web-demo/Regular/deleteByids",
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
              url:"http://localhost:9090/web-demo/Regular/deleteByid?tele="+val
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
    picshow(val){
      var baseurl="http://localhost:9090/file/";
      this.pic=baseurl+val;
      this.picVisible=true;
    },
    shenhe(val){
    this.shenheVisible=true;
    this.regular=val;
    },
    qverenshenhe(){
   this.regular.status=this.status;
      this.$confirm(
          '确定审核结果？',
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
              url:"http://localhost:9090/web-demo/Regular/update",
              data:_this.regular
            }).then(function (resp) {
              if(resp.data == "success"){
                _this.submit();
                // 弹出消息提示
                _this.$message({
                  message: '审核成功',
                  type: 'success'
                });
                _this.shenheVisible=false;
              }
            })
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除！',
            })
          })
    }
  },
  data(){
    return {
      //用户头像文件名
      pic:"",
      //用户头像面板
      picVisible:false,
      //用户对象
      regular1:{
        tele:"",
        password:"",
        sex:"",
        age:'',
        address:"",
        pic:"",
        num:'',
        status:'',
        username:""
      },
      regular:{
        tele:"",
        password:"",
        sex:"",
        age:'',
        address:"",
        pic:"",
        num:'',
        status:'',
        username:""
      },
      //用户信息表格的每一页的数据
      pageSize: 5,
      //用户信息表格的当前页
      currentPage: 1,
      //领养信息展示表需要的数据总数
      totalCount: 100,
      //领养信息展示的表格所绑定的数组
      tableData: [],
      //被选中的id数组
      selectedIds: [],
      // 复选框选中数据集合
      multipleSelection: [],
      shenheVisible:false,
      status:''
    }
  }
}
</script>

<style scoped>

</style>