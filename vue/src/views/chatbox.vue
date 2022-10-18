<template>
  <div>
  <el-dialog
      title="回复内容"
      :visible.sync="dialogVisible"
      width="30%"
     >
    <el-input
        type="textarea"
        :autosize="{ minRows: 2, maxRows: 20}"
        placeholder="请输入回复内容"
        v-model="chat.message">
    </el-input>
    <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="sendchat">确 定</el-button>
  </span>
  </el-dialog>
  <template>
  <el-table
      :data="tableData"
      style="width: 100%">
    <el-table-column
        prop="cstatus"
        label="留言者身份"
        width="90">
      <template slot-scope="scope">
        <span v-if="scope.row.cstatus===1">管理员</span>
        <span v-else-if="scope.row.cstatus===2">普通用户</span>
        <span v-else-if="scope.row.cstatus===3">救助机构</span>
      </template>
    </el-table-column>
    <el-table-column
        prop="ctele"
        label="留言者电话"
        width="100">
    </el-table-column>
    <el-table-column
        prop="message"
        label="留言内容">
    </el-table-column>
    <el-table-column
        width="100"
        label="操作">
      <template slot-scope="scope">
        <el-row>
          <el-button type="primary" plain @click="huifu(scope.row)">回复</el-button>
        </el-row>
      </template>
    </el-table-column>
  </el-table>
  </template>
  </div>
</template>

<script>
export default {
  name: "chatbox",
  mounted(){
   this.mychat();
 },
  methods:{
    mychat(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({
        method: "post",
        url: "http://localhost:9090/web-demo/admin/getUsername"
      }).then(function (resp) {
        _this.chat.cstatus=resp.data.status;
        _this.chat.ctele=resp.data.tele;
        //==================================
        _this.$axios({
          method: "post",
          url: "http://localhost:9090/web-demo/chat/selectchats?btele="+resp.data.tele+"&bstatus="+resp.data.status
        }).then(function (resp) {
          console.log(resp.data)
          _this.tableData=resp.data;
        })
      })
    },
    huifu(val){
      this.dialogVisible=true;
      this.chat.bstatus=val.cstatus;
      this.chat.btele=val.ctele;
      this.chat.cstatus=val.bstatus;
      this.chat.ctele=val.btele;
    },
    sendchat(){
      this.$confirm('确认回复消息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var _this=this;
        this.$axios({
          method: "post",
          url: "http://localhost:9090/web-demo/chat/add",
          data: _this.chat
        }).then(function (resp) {
          if (resp.data == "success") {
            _this.$message({
              type: 'success',
              message: '回复成功!'
            });
            _this.dialogVisible=false;
          } else {
            _this.$message({
              type: 'error',
              message: '回复失败!'
            });
            _this.dialogVisible=false;
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消回复'
        });
      });
    }
  },
  data(){
    return{
      tableData:[],
      chat:{
        id:'',
        bstatus:'',
        btele:"",
        ctele:"",
        cstatus:'',
        message:""
      },
      dialogVisible: false
    }
  }
}
</script>

<style scoped>

</style>