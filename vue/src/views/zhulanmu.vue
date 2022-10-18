<template>
  <div>
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
      <el-form ref="form" :model="lanmu2" label-width="120px">
        <el-form-item label="主菜单名字">
          <el-input v-model="lanmu2.lanmuli"></el-input>
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
      <el-form ref="form" :model="lanmu1" label-width="120px">
        <el-form-item label="主菜单名字">
          <el-input
              type="text"
              autosize
              v-model="lanmu1.lanmuli">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="update">确认</el-button>
          <el-button @click="editdialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>

    </el-dialog>
    <!--表格-->
    <template>
      <el-table
          :data="lanmuData"
          style="width: 100%"
          :row-class-name="tableRowClassName"
          @selection-change="handleSelectionChange"
      >
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="lanmuli"
            label="主菜单名字"
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
  </div>
</template>

<script>
export default {
  name: "lanmu",
  mounted() {
    this.submitlanmu();
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
              url:"http://localhost:9090/web-demo/LanmuServlet/deleteByid?id="+val
            }).then(function (resp) {
              if(resp.data == "success"){
                //删除成功
                // 重新查询数据
                _this.submitlanmu();
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
              url:"http://localhost:9090/web-demo/LanmuServlet/deleteByids",
              data:_this.selectedIds
            }).then(function (resp) {
              if(resp.data == "success"){
                //删除成功
                // 重新查询数据
                _this.submitlanmu();
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
    submitlanmu(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/LanmuServlet/selectAll"
      }).then(function (resp){
        _this.lanmuData=resp.data;
      })
    },
    edit(val){
      this.lanmu1=Object.assign({},val);
      this.editdialogVisible=true;
    },
    update(){
      var _this = this;
      // 发送ajax请求，添加数据
      this.$axios({
        method:"post",
        url:"http://localhost:9090/web-demo/LanmuServlet/update",
        data:_this.lanmu1
      }).then(function (resp) {
        if(resp.data == "success"){
          //添加成功
          //关闭窗
          _this.editdialogVisible = false;
          // 重新查询数据
          _this.submitlanmu();
          // 弹出消息提示
          _this.$message({
            message: '恭喜你，修改成功',
            type: 'success'
          });
        }
      })
    },
    xinzeng(){
      console.log(this.lanmu2)
          this.adddialogVisible=true
    },
    add(){
      var _this = this;
      console.log(_this.lanmu2)
      // 发送ajax请求，添加数据
      this.$axios({
        method:"post",
        url:"http://localhost:9090/web-demo/LanmuServlet/add",
        data:_this.lanmu2
      }).then(function (resp) {
        if(resp.data == "success"){
          //添加成功
          //关闭窗
          _this.adddialogVisible = false;
          // 重新查询数据
          _this.submitlanmu();
          // 弹出消息提示
          _this.$message({
            message: '恭喜你，新增成功',
            type: 'success'
          });
        }
      })
    }
  },
  data(){
    return{
      lanmuData:[],
      lanmu:{
        id:'',
        lanmuli:""
      },
      lanmu1:{
        id:'',
        lanmuli:""
      },
      lanmu2:{
        id:'',
        lanmuli:""
      },
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

