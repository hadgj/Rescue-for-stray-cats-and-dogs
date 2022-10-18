<template>
<div>
  <!--搜索表单-->
  <el-form :inline="true" :model="organization" class="demo-form-inline">
    <el-form-item label="机构电话">
      <el-input v-model="organization.tele" placeholder="电话"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onsubmit">查询</el-button>
    </el-form-item>
  </el-form>
  <!--按钮-->
  <el-row>

    <el-button type="danger" plain @click="deleteByids">批量删除</el-button>
  </el-row>
  <!--审核面板-->
  <el-dialog title="审核" :visible.sync="shenheVisible">
    <el-radio v-model="status" label='4'>禁用</el-radio>
    <el-radio v-model="status" label='3'>启用</el-radio>
    <div slot="footer" class="dialog-footer">
      <el-button @click="shenheVisible = false">取 消</el-button>
      <el-button type="primary" @click="shenhe">确 定</el-button>
    </div>
  </el-dialog>
  <!--新闻封图展示面板-->
  <el-dialog title="资质图片" :visible.sync="picVisible"
  >
    <el-image
        v-for="(item, index) in picurl"
        :key="index"
        style="width: 100px; height: 100px;margin-right: 10px;"
        :src="item"
        :preview-src-list="[item]"
    ></el-image>
    <div slot="footer" class="dialog-footer">
      <el-button @click="picVisible = false">收起</el-button>
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
          prop="address"
          align="center"
          label="住址">
      </el-table-column>

      <el-table-column
          prop="username"
          align="center"
          label="机构名称">
      </el-table-column>

      <el-table-column
          align="center"
          label="资质图">
        <template slot-scope="scope">
          <el-button type="text" @click="picshow(scope.row.pic)">查看</el-button>
        </template>
      </el-table-column>

      <el-table-column
          prop="status"
          align="center"
          label="帐号状态">
        <template slot-scope="scope">
          <span v-if="scope.row.status===4">禁用</span>
          <span v-else-if="scope.row.status===3">启用</span>
        </template>
      </el-table-column>



      <el-table-column
          align="center"
          label="操作"
          width="200" >
        <template slot-scope="scope">
          <el-row>
            <el-button type="primary" @click="sheheo(scope.row.tele)">审核</el-button>
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
  name: "organization",
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
        url:"http://localhost:9090/web-demo/organization/selectByPageAndCondition?currentPage="+1+"&pageSize="+_this.pageSize,
        data:_this.organization
      }).then(function (resp){
        _this.tableData=resp.data.rows;
        _this.totalCount=resp.data.totalCount;
      })
    },
    submit(){
      var _this=this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/organization/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
        data:_this.organization
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
              url:"http://localhost:9090/web-demo/organization/deleteByids",
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
              url:"http://localhost:9090/web-demo/organization/deleteByid?tele="+val
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
      this.picVisible=true;
      this.picurl=[];
      this.picurl=val.split("#");
      this.picurl.pop();
      var url=[];
      this.picurl.forEach((item)=>{
        item="http://localhost:9090/"+item;
        url.push(item);
      });
      this.picurl=url;
    },
    sheheo(val){
      this.tele=val;
      this.shenheVisible=true;
    },
    shenhe(){
      var _this=this;
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/organization/update?status="+_this.status+"&tele="+_this.tele,
      }).then(function (resp){
        if(resp.data=="success"){
          _this.$message({
            message: '恭喜你，审核成功！',
            type: 'success'
          });
          _this.shenheVisible=false;
          _this.submit();
        }
      })
    }
  },
  data(){
    return{
      tele:"",
      shenheVisible:false,
      status:'',
      picurl:[],
      //资质图片面板
      picVisible:false,
      //机构对象
      organization:{
        tele:"",
        password:"",
        address:"",
        username:"",
        status:'',
        pic:"",
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
    }
  }
}
</script>

<style scoped>

</style>