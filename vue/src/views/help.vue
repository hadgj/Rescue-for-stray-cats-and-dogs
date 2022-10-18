<template>
<div>
  <!--搜索表单-->
  <el-form :inline="true" :model="help" class="demo-form-inline">
    <el-form-item label="地点">
      <el-input v-model="help.address" placeholder="地点"></el-input>
    </el-form-item>
    <el-form-item label="申请日期">
      <el-date-picker
          v-model="help.hdate"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd" >
      </el-date-picker>
    </el-form-item>
    <el-form-item label="联系电话">
      <el-input v-model="help.rid" placeholder="联系电话"></el-input>
    </el-form-item>
    <el-form-item>
    <el-button type="primary" @click="onsubmit">查询</el-button>
    </el-form-item>
  </el-form>
  <!--按钮-->
  <el-row>

    <el-button type="danger" plain @click="deleteByids">批量删除</el-button>
  </el-row>
  <!--新闻封图展示面板-->
  <el-dialog title="情况图片" :visible.sync="imgVisible"
  >
    <el-image
        v-for="(item, index) in url"
        :key="index"
        style="width: 100px; height: 100px;margin-right: 10px;"
        :src="item"
        :preview-src-list="[item]"
    ></el-image>
    <div slot="footer" class="dialog-footer">
      <el-button @click="imgVisible = false">收起</el-button>
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
          prop="rid"
          label="救助者电话"
          align="center"
      >
      </el-table-column>

      <el-table-column
          prop="address"
          label="地点"
          align="center"
      >
      </el-table-column>

      <el-table-column
          prop="hdate"
          align="center"
          label="时间">
      </el-table-column>

      <el-table-column
          prop="hdesc"
          align="center"
          label="情况描述">
      </el-table-column>

      <el-table-column
          prop="hstate"
          align="center"
          label="状态">
        <template slot-scope="scope">
          <span v-if="scope.row.hstate===1">未处理</span>
          <span v-else-if="scope.row.hstate===2">处理中</span>
          <span v-else-if="scope.row.hstate===3">已完成</span>
        </template>
      </el-table-column>

      <el-table-column
          prop="pic"
          align="center"
          label="情况图片">
        <template slot-scope="scope">
          <el-button type="text" @click="img(scope.row.pic)">预览</el-button>
        </template>
      </el-table-column>


      <el-table-column
          align="center"
          label="操作"
          width="200" >
        <template slot-scope="scope">
          <el-row>
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
export default {
  name: "help",
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
       url:"http://localhost:9090/web-demo/help/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
        data:_this.help
      }).then(function (resp){
        _this.tableData=resp.data.rows;
        _this.totalCount=resp.data.totalCount;
      })
    },
    submit(){
      var _this=this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/help/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
        data:_this.help
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
              this.selectedIds[i]=selectionElement.id;
            }
            //2.发送ajax请求
            var _this = this;
            // 发送ajax请求，添加数据
            this.$axios({
              method:"post",
              url:"http://localhost:9090/web-demo/help/deleteByids",
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
              url:"http://localhost:9090/web-demo/help/deleteByid?id="+val
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
    img(val){
      this.url=[];
      this.imgVisible=true;
      this.imgnames=val.split("#");
      this.imgnames.pop();
      var baseurl="http://localhost:9090/file/";
      this.imgnames.forEach((item)=>{
        item=baseurl+item;
        this.url.push(item);
      })
    }
  },
  data(){return{
    imgnames:[],
    url:[],
    imgVisible:false,
    help:{
      id:'',
      address:"",
      hdate:"",
      hdesc:"",
      pic:"",
      rid:"",
      hstate:''
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
    multipleSelection: []
  }}
}
</script>

