<template>
  <div>
    <!--搜索表单-->
    <el-form :inline="true" :model="mingan1" class="demo-form-inline">
      <el-form-item label="文章标题">
        <el-input v-model="mingan1.str" placeholder="敏感词"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="mingansubmit">查询</el-button>
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
    <el-form ref="form"  label-width="120px">
      <el-form-item label="新增敏感词">
        <el-input type="textarea"
                  autosize
                  v-model="str" placeholder="多个敏感词用#号隔开"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="add">确认</el-button>
        <el-button @click="adddialogVisible = false">取消</el-button>
      </el-form-item>
    </el-form>

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
          prop="str"
          label="敏感词"
          align="center"
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
  name: "mingan",
  mounted() {
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
    submit(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/mingan/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
        data:_this.mingan
      }).then(function (resp){
        _this.tableData=resp.data.rows;
        _this.totalCount=resp.data.totalCount;
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
              url:"http://localhost:9090/web-demo/mingan/deleteByid?id="+val
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
              url:"http://localhost:9090/web-demo/mingan/deleteByids",
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
    xinzeng(){
      this.adddialogVisible=true;
      this.str=""
    },
    add(){
     var _this=this;
     this.str=this.str+"#";
     this.data=this.str.split("#");
     this.data.pop();

       this.$axios({
         method:"post",
         url:"http://localhost:9090/web-demo/mingan/add",
         data:_this.data
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
           _this.adddialogVisible=false;
         }

       })


    },
    mingansubmit(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/mingan/selectByPageAndCondition?currentPage="+1+"&pageSize="+_this.pageSize,
        data:_this.mingan1
      }).then(function (resp){
        _this.tableData=resp.data.rows;
        _this.totalCount=resp.data.totalCount;
      })
    }
  },
  data(){
    return{
      tableData:[],
      currentPage: 1,
      pageSize:5,
      totalCount:100,
      mingan:{
        id:'',
        str:""
      },
      mingan1:{
        id:'',
        str:""
      },
      str:"",
      data:[],
      adddialogVisible:false,
      //被选中的id数组
      selectedIds:[],
      // 复选框选中数据集合
      multipleSelection: []
    }
  }
}
</script>

<style scoped>

</style>