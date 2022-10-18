<template>
  <div>
    <!--搜索表单-->
    <el-form :inline="true" :model="ro" class="demo-form-inline">

      <el-form-item label="被投诉者身份">
        <el-select v-model="ro.bstatus" clearable placeholder="请选择">
          <el-option label="普通用户" value='2'></el-option>
          <el-option label="救助机构" value='3'></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="被投诉者电话">
        <el-input v-model="ro.btele" placeholder="被投诉者电话"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onsubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <!--按钮-->
    <el-row>
      <el-button type="danger" plain @click="deleteByids">批量删除</el-button>
    </el-row>
    <!--宠物图片展示-->
    <el-dialog title="投诉情况图片" :visible.sync="imgdialogVisible"
    >
      <el-image
          v-for="(item, index) in imgsurl"
          :key="index"
          style="width: 100px; height: 100px;margin-right: 10px;"
          :src="item"
          :preview-src-list="[item]"
      ></el-image>
      <div slot="footer" class="dialog-footer">
        <el-button @click="imgdialogVisible= false">收起</el-button>
      </div>
    </el-dialog>
    <!--新闻展示面板-->
    <el-dialog title="投诉内容" :visible.sync="contentVisible"
    >
      <span>{{tousumessage}}</span>
      <div slot="footer" class="dialog-footer">
        <el-button @click="contentVisible = false">收起</el-button>
      </div>
    </el-dialog>
    <!--审核面板-->
    <el-dialog title="审核投诉" :visible.sync="shenheVisible"
    >
      <el-select v-model="jstate" placeholder="请选择">
        <el-option label="待核实" value='1'></el-option>
        <el-option label="警告处理" value='2'></el-option>
        <el-option label="封禁帐号" value='3'></el-option>
        <el-option label="核实无效" value='4'></el-option>
      </el-select>
      <div slot="footer" class="dialog-footer">
        <el-button @click="qveshenhe">确认审核</el-button>
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
            prop="bstatus"
            label="被举报者身份"
            align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.bstatus===2">普通用户</span>
            <span v-else-if="scope.row.bstatus===3">救助机构</span>
          </template>
        </el-table-column>

        <el-table-column
            prop="btele"
            label="被举报者电话"
            align="center"
        >
        </el-table-column>
        <el-table-column
            prop="message"
            label="举报理由"
            align="center"
        >
          <template slot-scope="scope">
            <el-button type="text" @click="chakan(scope.row.message)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column
            prop="jtele"
            align="center"
            label="举报者电话"
           >
        </el-table-column>
        <el-table-column
            prop="jstatus"
            align="center"
            label="举报者身份"
           >
          <template slot-scope="scope">
            <span v-if="scope.row.jstatus===2">普通用户</span>
            <span v-else-if="scope.row.jstatus===3">救助机构</span>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            label="图片">
          <template slot-scope="scope">
            <el-button type="text" @click="preview(scope.row.pic)">预览</el-button>
          </template>
        </el-table-column>
        <el-table-column
            prop="jstate"
            align="center"
            label="举报信息状态"
            width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.jstate===1">待核实</span>
            <span v-else-if="scope.row.jstate===2">警告账号</span>
            <span v-else-if="scope.row.jstate===3">封禁账号</span>
            <span v-else-if="scope.row.jstate===4">核实无效</span>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            label="操作"
            width="200" >
          <template slot-scope="scope">
            <el-row>
              <el-button type="primary" plain @click="shenhe(scope.row)">审核</el-button>
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
  name: "tousu",
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
        url:"http://localhost:9090/web-demo/ro/selectByPageAndCondition?currentPage="+1+"&pageSize="+_this.pageSize,
        data:_this.ro
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
        url:"http://localhost:9090/web-demo/ro/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
        data:_this.ro
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
              url:"http://localhost:9090/web-demo/ro/deleteByids",
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
              url:"http://localhost:9090/web-demo/ro/deleteByid?id="+val
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
      var baseurl="http://localhost:9090";
      this.imgnames.forEach((item)=>{
        item=baseurl+item;
        this.imgsurl.push(item);
      })
    },
    chakan(val){
      this.contentVisible=true;
      this.tousumessage=val;
    },
    shenhe(val){
      this.shenheVisible=true;
      this.ro1=val;
    },
    qveshenhe(){
      this.$confirm(
          '您确定要改变审核结果吗，是否继续？',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            this.ro1.jstate=this.jstate;
            if(this.ro1.jstate==4||this.ro1.jstate==1)
            {
              //只是修改，对帐号不做操作
              var _this =this;
              //页面加载完成之后，发送异步数据请求，查询数据
              this.$axios({method:"post",
                url:"http://localhost:9090/web-demo/ro/update",
                data:_this.ro1
              }).then(function (resp){
                if(resp.data=="success"){
                  _this.shenheVisible=false;
                  // 弹出消息提示
                  _this.$message({
                    message: '修改成功',
                    type: 'success'
                  });
                  _this.submit();
                }else {
                  // 弹出消息提示
                  _this.$message({
                    message: '修改失败',
                    type: 'error'
                  });
                }
              })
            }
            else if(this.ro1.jstate==2)
            {
              if(this.ro1.bstatus==2)
              {
                var _this = this;
                //页面加载完成之后，发送异步数据请求，查询数据
                this.$axios({
                  method: "post",
                  url: "http://localhost:9090/web-demo/Regular/jinggao?tele=" + this.ro1.btele,
                  data:_this.ro1
                }).then(function (resp) {
                  if (resp.data == "need") {
                    _this.shenheVisible=false;
                    // 弹出消息提示
                    _this.$message({
                      message: '修改成功',
                      type: 'success'
                    });
                    _this.submit();
                  } else {
                    _this.shenheVisible=false;
                    // 弹出消息提示
                    _this.$message({
                      message: '修改成功',
                      type: 'success'
                    });
                    _this.submit();
                  }
                })
              }
              else {
                var _this = this;
                //页面加载完成之后，发送异步数据请求，查询数据
                this.$axios({
                  method: "post",
                  url: "http://localhost:9090/web-demo/organization/jinggao?tele=" + this.ro1.btele,
                  data:_this.ro1
                }).then(function (resp) {
                  if (resp.data == "need") {
                    _this.shenheVisible=false;
                    // 弹出消息提示
                    _this.$message({
                      message: '修改成功',
                      type: 'success'
                    });
                    _this.submit();
                  } else {
                    _this.shenheVisible=false;
                    // 弹出消息提示
                    _this.$message({
                      message: '修改成功',
                      type: 'success'
                    });
                    _this.submit();
                  }
                })
              }
            }
            else
            {
              var _this = this;
              //页面加载完成之后，发送异步数据请求，查询数据
              this.$axios({
                method: "post",
                url: "http://localhost:9090/web-demo/ro/update",
                data: _this.ro1
              }).then(function (resp) {
                if (resp.data == "success") {
                  _this.shenheVisible = false;
                  // 弹出消息提示
                  _this.$message({
                    message: '修改成功',
                    type: 'success'
                  });
                  _this.submit();
                } else {
                  // 弹出消息提示
                  _this.$message({
                    message: '修改失败',
                    type: 'error'
                  });
                }
              })
              if(this.ro1.bstatus==2)
              {
                var _this = this;
                //页面加载完成之后，发送异步数据请求，查询数据
                this.$axios({
                  method: "post",
                  url: "http://localhost:9090/web-demo/Regular/jinyong?tele=" + this.ro1.btele
                }).then(function (resp) {
                  if (resp.data == "success") {
                    console.log("封禁成功")
                  }
                })
              }
              else
              {
                var _this = this;
                //页面加载完成之后，发送异步数据请求，查询数据
                this.$axios({
                  method: "post",
                  url: "http://localhost:9090/web-demo/organization/jinyong?tele=" + this.ro1.btele
                }).then(function (resp) {
                  if (resp.data == "success") {
                    console.log("封禁成功")
                  }
                })
              }
            }
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消修改！',
            })
          })
    }
    //==================！！！宠物管理方法========================
  },
  data() {
    return {
      //===============宠物管理部分需要的=============================
      //添加图片是用来存放图片文件的数组
      fileList:[],
      //投诉图片展示时用的图片名字数组
      imgnames:[],
      //投诉图片展示展示时用的路径数组
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
      ro:{
        id:'',
        bstatus:'',
        btele:"",
        message:"",
        pic:"",
        jtele:"",
        jstatus:'',
        jstate:''
      },
      ro1:{
        id:'',
        bstatus:'',
        btele:"",
        message:"",
        pic:"",
        jtele:"",
        jstatus:'',
        jstate:''
      },
      ro2:{
        id:'',
        bstatus:'',
        btele:"",
        message:"",
        pic:"",
        jtele:"",
        jstatus:'',
        jstate:''
      },
      //被选中的id数组
      selectedIds:[],
      // 复选框选中数据集合
      multipleSelection: [],
      tableData: [],
      contentVisible:false,
      tousumessage:"",
      shenheVisible:false,
      jstate:''
      //===============！宠物管理部分需要的！=============================
    }
  }
}
</script>
