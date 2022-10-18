<template>
<!--  一个vue组件只能有一个-->
<div>
  <div style="margin-bottom: 30px;">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">个人信息管理</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  <template>
    <!--搜索表单-->
    <el-form :inline="true" :model="news1" class="demo-form-inline">
      <el-form-item label="文章标题">
        <el-input v-model="news1.newsTitle" placeholder="文章标题"></el-input>
      </el-form-item>
      <el-form-item label="发布日期">
        <el-date-picker
            v-model="news1.newsDate"
            type="date"
            placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="newssubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <!--文章管理板块按钮-->
    <el-row>
      <el-button type="danger" plain @click="deletenewsByids">批量删除</el-button>
    </el-row>
    <!--编辑文章对话框-->
    <el-dialog
        title="编辑文章"
        :visible.sync="newseditVisible"
        width="30%"
    >
      <el-form ref="form" :model="news3" label-width="80px">
        <el-form-item label="文章标题">
          <el-input v-model="news3.newsTitle"></el-input>
        </el-form-item>
        <el-form-item label="文章内容">
          <el-input
              type="textarea"
              autosize
              v-model="news3.newsContent">
          </el-input>
        </el-form-item>
        <el-form-item label="发布日期">
            <el-date-picker
                v-model="news3.newsDate"
                type="datetime"
                placeholder="选择日期时间">
            </el-date-picker>
        </el-form-item>

        <el-form-item label="文章封图">
          <el-upload
              class="upload-demo"
              multiple
              ref="upload"
              :file-list="newsimgfileList"
              :auto-upload="false"
              action="#"
              accept=".jpg,.jpeg,.gif,.bmp,.png"
              :on-remove="handleRemove"
          >
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
        </el-form-item>

        <el-form-item label="是否置顶">
          <el-select v-model="news3.newsIsTop" >
            <el-option label="否" value='0'></el-option>
            <el-option label="是" value='1'></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="点赞数">
          <el-input v-model="news3.newsZan"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="editnews">确认</el-button>
          <el-button @click="newseditVisible = false">取消</el-button>
        </el-form-item>
      </el-form>

    </el-dialog>
    <!--新闻展示面板-->
    <el-dialog title="文章内容" :visible.sync="newscontentVisible"
    >
      <el-input
          type="textarea"
          autosize
          v-model="newscontent">
      </el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="newscontentVisible = false">收起</el-button>
      </div>
    </el-dialog>
    <!--新闻封图展示面板-->
    <el-dialog title="文章封图" :visible.sync="newsimgVisible"
    >
      <el-image
          v-for="(item, index) in newsimgsurl"
          :key="index"
          style="width: 100px; height: 100px;margin-right: 10px;"
          :src="item"
          :preview-src-list="[item]"
      ></el-image>
      <div slot="footer" class="dialog-footer">
        <el-button @click="newsimgVisible = false">收起</el-button>
      </div>
    </el-dialog>
    <!--表格-->
    <template>
      <el-table
          :data="newstableData"
          style="width: 100%"
          :row-class-name="tableRowClassName"
          @selection-change="handleSelectionChange"
      >
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="newsID"
            label="ID"
            align="center"
            width="55">
        </el-table-column>

        <el-table-column
            prop="newsTitle"
            label="标题"
            align="center"
        >
        </el-table-column>
        <el-table-column
            label="内容"
            align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="newsxiangqing(scope.row.newsContent)">详情</el-button>
          </template>
        </el-table-column>
        <el-table-column
            prop="newsDate"
            align="center"
            type
            label="发布日期">
        </el-table-column>
        <el-table-column
            align="center"
            label="封图">
          <template slot-scope="scope">
            <el-button type="text" @click="newsimg(scope.row.newsImagePath)">预览</el-button>
          </template>
        </el-table-column>
        <el-table-column
            prop="newsIsTop"
            align="center"
            label="置顶">
          <template slot-scope="scope">
            <span v-if="scope.row.newsIsTop===0">否</span>
            <span v-else-if="scope.row.newsIsTop===1">是</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="newsZan"
            align="center"
            label="获赞数">
        </el-table-column>
        <el-table-column
            align="center"
            label="操作"
            width="200" >
          <template slot-scope="scope">
            <el-row>
              <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
              <el-button type="danger" plain @click="deletenewsByid(scope.row.newsID)">删除</el-button>
            </el-row>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <!--分页工具条-->
    <el-pagination
        @size-change="newshandleSizeChange"
        @current-change="newshandleCurrentChange"
        :current-page="newscurrentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="5"
        layout="total, sizes, prev, pager, next, jumper"
        :total="newstotalCount">
    </el-pagination>
  </template>
</div>
</template>
<script>
export default {
  name: "news",
  mounted(){
    this.newsonsubmit();
  },
  methods: {
    //==================文章管理方法========================
    // 格式化日期
    fomartDate(time) {
      // 将需要格式化的数据传入
      time = this.dayjs(time).format('YYYY-MM-DD HH:mm:ss') //这个时间已经加了8小时哦,跟你
                                                            //如果是添加事件,时间就是当下时间
      return time
    },
    fomartDate1(time) {
      // 将需要格式化的数据传入
      time = this.dayjs(time).format('YYYY-MM-DD') //这个时间已经加了8小时哦,跟你
                                                            //如果是添加事件,时间就是当下时间
      return time
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
    newssubmit(){
       if(this.news1.newsDate!=null) {
  this.news1.newsDate = this.fomartDate1(this.news1.newsDate);
}
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/news/selectByPageAndCondition?currentPage="+1+"&pageSize="+_this.newspageSize,
        data:_this.news1
      }).then(function (resp){
        _this.newstableData=resp.data.rows;
        _this.newstotalCount=resp.data.totalCount;
      })
    },
    editnews(){
      this.news3.newsDate=this.fomartDate(this.news3.newsDate);
      this.newsimgnames=[];
      var basestr="#";
      this.$refs.upload.uploadFiles.forEach((item)=>{
        item.name=item.name+basestr;
        this.newsimgnames.push(item.name);
        item.name = item.name.substr(0, item.name.length - 1);
      })
      var newsImagePath="";
      newsImagePath=this.newsimgnames.join("");
      this.news3.newsImagePath=newsImagePath;
      var _this = this;
      // 发送ajax请求，添加数据
      this.$axios({
        method:"post",
        url:"http://localhost:9090/web-demo/news/update",
        data:_this.news3
      }).then(function (resp) {
        if(resp.data == "success"){
          //添加成功
          //关闭窗
          _this.newseditVisible = false;
          // 重新查询数据
          _this.newsonsubmit();
          // 弹出消息提示
          _this.$message({
            message: '恭喜你，修改成功',
            type: 'success'
          });
        }
      })
    },
    edit(val){
      this.newsimgfileList=[];
      this.newsimgsurl=[];
      //使用深拷贝，不改变拷贝前的值！！！！！！！
      this.news3=Object.assign({},val);
      this.newseditVisible=true;
      this.newsimgnames=this.news3.newsImagePath.split("#");
      this.newsimgnames.pop();
      var baseurl="http://localhost:9090/file/";
      this.newsimgnames.forEach((item)=>{
        item=baseurl+item;
        this.newsimgsurl.push(item);
      })
      this.newsimgsurl.forEach(item=>{
        let obj=new Object();
        obj.url=item;
        obj.name=item.slice(6);
        this.newsimgfileList.push(obj);
      });
    },
    deletenewsByid(val){
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
              url:"http://localhost:9090/web-demo/news/deleteByid?newsID="+val
            }).then(function (resp) {
              if(resp.data == "success"){
                //删除成功
                // 重新查询数据
                if((_this.newscurrentPage-1)*_this.newspageSize==(_this.newstotalCount-1)){
                  _this.newscurrentPage=_this.newscurrentPage-1;
                }
                _this.newsonsubmit();
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
    deletenewsByids(){
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
              this.selectedIds[i]=selectionElement.newsID;
            }
            //2.发送ajax请求
            var _this = this;
            // 发送ajax请求，添加数据
            this.$axios({
              method:"post",
              url:"http://localhost:9090/web-demo/news/deleteByids",
              data:_this.selectedIds
            }).then(function (resp) {
              if(resp.data == "success"){
                //删除成功
                // 重新查询数据
                var num=_this.newstotalCount-_this.selectedIds.length;
                var num1=Math.ceil(num/_this.newspageSize)
                _this.newscurrentPage=num1;
                _this.newsonsubmit();
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
    newsimg(val){
      this.newsimgsurl=[];
      this.newsimgVisible=true;
      this.newsimgnames=val.split("#");
      this.newsimgnames.pop();
      var baseurl="http://localhost:9090/file/";
      this.newsimgnames.forEach((item)=>{
        item=baseurl+item;
        this.newsimgsurl.push(item);
      })
    },
    newsxiangqing(val){
      this.newscontentVisible=true;
      this.newscontent=val;
    },
    newshandleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      // 重新设置每页显示的条数
      this.newspageSize  = val;
      this.newsonsubmit();
    },
    newshandleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      // 重新设置当前页码
      this.newscurrentPage  = val;
      this.newsonsubmit();
    },
    newsonsubmit(){
      var _this =this;
      //页面加载完成之后，发送异步数据请求，查询数据
      this.$axios({method:"post",
        url:"http://localhost:9090/web-demo/news/selectByPageAndCondition?currentPage="+_this.newscurrentPage+"&pageSize="+_this.newspageSize,
        data:_this.news1
      }).then(function (resp){
        _this.newstableData=resp.data.rows;
        _this.newstotalCount=resp.data.totalCount;
      })
    },
    handleRemove(file, fileList) {
      console.log(fileList)
    }
    //==================!!!文章管理方法========================
  },
  data() {
    return {
      //===============文章管理部分需要的=============================
      //添加图片是用来存放图片文件的数组
      newsimgfileList:[],
      //新闻封图展示时用的图片名字数组
      newsimgnames:[],
      //新闻封图展示时用的路径数组
      newsimgsurl:[],
      //文章内容展示时所用变量
      newscontent:"",
      //文章编辑面板是否展示
      newseditVisible:false,
      //添加文章面板是否展示
      newsaddVisible:false,
      //文章封图是否展示
      newsimgVisible:false,
      //文章内容是否展示
      newscontentVisible:false,
      //表格显示新闻数据
      newstableData:[],
      //每页显示的条数
      newspageSize:5,
      //总记录数
      newstotalCount:100,
      // 当前页码
      newscurrentPage: 1,
      news1:{
        newsID:'',
        newsTitle:"",
        newsContent:"",
        newsDate:"",
        newsImagePath:"",
        newsIsTop:'',
        newsZan:'',
        newsRedu:''
      },
      news2:{
        newsID:'',
        newsTitle:"",
        newsContent:"",
        newsDate:"",
        newsImagePath:"",
        newsIsTop:'',
        newsZan:'',
        newsRedu:''
      },
      news3:{
        newsID:'',
        newsTitle:"",
        newsContent:"",
        newsDate:"",
        newsImagePath:"",
        newsIsTop:'',
        newsZan:'',
        newsRedu:''
      },
      //被选中的id数组
      selectedIds:[],
      // 复选框选中数据集合
      multipleSelection: []
      //===============!!!文章管理部分需要的=============================
    }
  }
}
</script>

