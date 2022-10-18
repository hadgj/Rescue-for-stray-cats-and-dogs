<template>
  <div>
    <!--搜索表单-->
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item label="评论者身份">
        <el-select v-model="comment.status" clearable placeholder="请选择">
          <el-option label="普通用户" value='2'></el-option>
          <el-option label="救助机构" value='3'></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="评论者电话">
        <el-input v-model="comment.tele" placeholder="被投诉者电话"></el-input>
      </el-form-item>

      <el-form-item label="文章标题">
        <el-input v-model="comment.ntitle" placeholder="文章标题"></el-input>
      </el-form-item>

      <el-form-item label="评论时间">
        <el-date-picker
            v-model="comment.shijian"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd" >
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onsubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <!--按钮-->
    <el-row>
      <el-button type="danger" plain @click="deleteByids">批量删除</el-button>
    </el-row>
    <!--评论内容-->
    <el-dialog title="评论内容" :visible.sync="commentVisible"
    >
      <span>{{message}}</span>
      <div slot="footer" class="dialog-footer">
        <el-button @click="commentVisible = false">收起</el-button>
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
            prop="status"
            label="评论者身份"
            align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.status===2">普通用户</span>
            <span v-else-if="scope.row.status===3">救助机构</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="tele"
            label="评论者电话"
            align="center"
        >
        </el-table-column>
        <el-table-column
            prop="username"
            label="评论者昵称"
            align="center"
        >
        </el-table-column>
        <el-table-column
            prop="message"
            label="评论内容"
            align="center"
        >
          <template slot-scope="scope">
            <el-button type="text" @click="chakan(scope.row.message)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column
            prop="ntitle"
            align="center"
            label="新闻标题"
        >
        </el-table-column>
        <el-table-column
            prop="shijian"
            align="center"
            label="评论时间"
        >
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
  name: "comment",
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
        url:"http://localhost:9090/web-demo/comment/selectByPageAndCondition?currentPage="+1+"&pageSize="+_this.pageSize,
        data:_this.comment
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
        url:"http://localhost:9090/web-demo/comment/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
        data:_this.comment
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
              url:"http://localhost:9090/web-demo/comment/deleteByids",
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
              url:"http://localhost:9090/web-demo/comment/deleteByid?id="+val
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
    chakan(val){
      this.commentVisible=true;
      this.message=val;
    }
    //==================！！！宠物管理方法========================
  },
  data() {
    return {
      //===============宠物管理部分需要的=============================
      //每页显示的条数
      pageSize:5,
      //总记录数
      totalCount:100,
      // 当前页码
      currentPage: 1,
      comment:{
        id:'',
        nid:'',
        status:'',
        tele:"",
        message:"",
        shijian:"",
        ntitle:"",
        username:""
      },
      //被选中的id数组
      selectedIds:[],
      // 复选框选中数据集合
      multipleSelection: [],
      tableData: [],
      message:"",
      commentVisible:false
      //===============！宠物管理部分需要的！=============================
    }
  }
}
</script>
