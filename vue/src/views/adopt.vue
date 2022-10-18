<template>
<div>
  <el-dialog title="领养理由" :visible.sync="contentVisible"
  >
    <span>{{content}}</span>
    <div slot="footer" class="dialog-footer">
      <el-button @click="contentVisible = false">收起</el-button>
    </div>
  </el-dialog>
  <!--搜索表单-->
  <el-form :inline="true" :model="apply1" class="demo-form-inline">
    <el-form-item label="用户电话">
      <el-input v-model="apply1.rtele" placeholder="用户电话"></el-input>
    </el-form-item>
    <el-form-item label="申请日期">
      <el-date-picker
          v-model="apply1.applytime"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd" >
      </el-date-picker>
    </el-form-item>
    <el-form-item label="动物名字">
      <el-input v-model="apply1.pname" placeholder="动物名字"></el-input>
    </el-form-item>
    <el-form-item label="当前状态">
      <el-select v-model="apply1.apstate" clearable placeholder="请选择">
        <el-option label="已拒绝" value='1'></el-option>
        <el-option label="待审核" value='2'></el-option>
        <el-option label="已同意" value='3'></el-option>
      </el-select>
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
    <el-radio v-model="apstate" label='1'>已拒绝</el-radio>
    <el-radio v-model="apstate" label='2'>待审核</el-radio>
    <el-radio v-model="apstate" label='3'>已同意</el-radio>
    <div slot="footer" class="dialog-footer">
      <el-button @click="shenheVisible = false">取 消</el-button>
      <el-button type="primary" @click="shenhe">确 定</el-button>
    </div>
  </el-dialog>
  <!--表格-->
  <template>
    <el-table
        :data="applytableData"
        style="width: 100%"
        :row-class-name="tableRowClassName"
        @selection-change="handleSelectionChange"
    >
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column
          prop="rtele"
          label="电话"
          align="center"
      >
      </el-table-column>

      <el-table-column
          prop="rusername"
          label="用户昵称"
          align="center"
      >
      </el-table-column>

      <el-table-column
          align="center"
          label="领养理由">
        <template slot-scope="scope">
          <el-button type="text" @click="xiangqing(scope.row.apmessage)">详情</el-button>
        </template>
      </el-table-column>

      <el-table-column
          prop="applytime"
          align="center"
          label="申请时间">
      </el-table-column>

      <el-table-column
          prop="pname"
          align="center"
          label="动物名字">
      </el-table-column>

      <el-table-column
          prop="ptype"
          align="center"
          label="动物类型">
      </el-table-column>

      <el-table-column
          prop="apstate"
          align="center"
          label="状态">
        <template slot-scope="scope">
          <span v-if="scope.row.apstate===1">已拒绝</span>
          <span v-else-if="scope.row.apstate===2">待审核</span>
          <span v-else-if="scope.row.apstate===3">已通过</span>
        </template>
      </el-table-column>

      <el-table-column
          align="center"
          label="操作"
          width="200" >
        <template slot-scope="scope">
          <el-row>
            <el-button type="primary" @click="sheheo(scope.row.pid)">审核</el-button>
            <el-button type="danger" plain @click="deleteByid(scope.row.apid)">删除</el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
  </template>
  <!--分页工具条-->
  <el-pagination
      @size-change="applyhandleSizeChange"
      @current-change="applyhandleCurrentChange"
      :current-page="applycurrentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="5"
      layout="total, sizes, prev, pager, next, jumper"
      :total="applytotalCount">
  </el-pagination>
</div>
</template>

<script>
export default {
  name: "adopt",
  mounted(){
    this.submit();
  },
  methods: {
    //==================领养管理方法========================
    xiangqing(val){
      this.content=val;
      this.contentVisible=true;
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
    sheheo(val){
      this.pid=val;
      this.shenheVisible=true;
    },
    shenhe(){
      var petIsApply="";
      if(this.apstate==1){
        petIsApply="未领养";
      }else if(this.apstate==2){
        petIsApply="未领养";
      }else{
        petIsApply="已领养";
      }
      var _this=this;
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/apply/update?petIsApply="+petIsApply+"&apstate="+_this.apstate+"&pid="+_this.pid,
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
    },
    onsubmit(){
      var _this=this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/apply/selectByPageAndCondition?currentPage="+1+"&pageSize="+_this.applypageSize,
        data:_this.apply1
      }).then(function (resp){
        _this.applytableData=resp.data.rows;
        _this.applytotalCount=resp.data.totalCount;
      })
    },
    submit(){
      var _this=this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/apply/selectByPageAndCondition?currentPage="+_this.applycurrentPage+"&pageSize="+_this.applypageSize,
        data:_this.apply1
      }).then(function (resp){
        _this.applytableData=resp.data.rows;
        _this.applytotalCount=resp.data.totalCount;
      })
    },
    applyhandleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      // 重新设置每页显示的条数
      this.applypageSize  = val;
      this.submit();
    },
    applyhandleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      // 重新设置当前页码
      this.applycurrentPage  = val;
      this.submit();
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
              this.selectedIds[i]=selectionElement.apid;
            }
            //2.发送ajax请求
            var _this = this;
            // 发送ajax请求，添加数据
            this.$axios({
              method:"post",
              url:"http://localhost:9090/web-demo/apply/deleteByids",
              data:_this.selectedIds
            }).then(function (resp) {
              if(resp.data == "success"){
                //删除成功
                // 重新查询数据
                var num=_this.applytotalCount-_this.selectedIds.length;
                var num1=Math.ceil(num/_this.applypageSize)
                _this.applycurrentPage=num1;
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
              url:"http://localhost:9090/web-demo/apply/deleteByid?apid="+val
            }).then(function (resp) {
              if(resp.data == "success"){
                //删除成功
                // 重新查询数据
                if((_this.applycurrentPage-1)*_this.applypageSize==(_this.applytotalCount-1)){
                  _this.applycurrentPage=_this.applycurrentPage-1;
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
    }
    //==================!!!领养管理方法========================
  },
  data() {
    return {
      //===============领养管理部分需要的=============================
      content:"",
      contentVisible:false,
      //这条信息的宠物id
      pid:'',
      //审核面板控制是否可见
      shenheVisible:false,
      //审核面板领养信息状态值
      apstate:'',
      //领养信息数据对象
      apply1:{
        apid:'',
        rtele:"",
        pid:'',
        apmessage:"",
        applytime:"",
        rusername:"",
        pname:"",
        apstate:'',
        ptype:""
      },
      apply2:{
        apid:'',
        rtele:"",
        pid:'',
        apmessage:"",
        applytime:"",
        rusername:"",
        pname:"",
        apstate:'',
        ptype:""
      },
      apply3:{
        apid:'',
        rtele:"",
        pid:'',
        apmessage:"",
        applytime:"",
        rusername:"",
        pname:"",
        apstate:'',
        ptype:""
      },
      //领养信息表格的每一页的数据
      applypageSize:5,
      //领养信息表格的当前页
      applycurrentPage:1,
      //领养信息展示表需要的数据总数
      applytotalCount:100,
      //领养信息展示的表格所绑定的数组
      applytableData:[],
      //被选中的id数组
      selectedIds:[],
      // 复选框选中数据集合
      multipleSelection: []
      //===============!!!领养管理部分需要的=============================
    }
  }
}
</script>

<style scoped>

</style>