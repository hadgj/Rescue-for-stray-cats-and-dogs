<template>
<div>
  <el-form ref="form" :model="news2" label-width="80px">
    <el-form-item label="文章标题">
      <el-input v-model="news2.newsTitle"></el-input>
    </el-form-item>
    <el-form-item label="文章内容">
      <el-input
          type="textarea"
          autosize
          v-model="news2.newsContent">
      </el-input>
    </el-form-item>
    <el-form-item label="发布日期">
      <el-date-picker
          v-model="news2.newsDate"
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
      >
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      </el-upload>
    </el-form-item>

    <el-form-item label="是否置顶">
      <el-select v-model="news2.newsIsTop" >
        <el-option label="否" value='0'></el-option>
        <el-option label="是" value='1'></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="点赞数">
      <el-input v-model="news2.newsZan"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="xinzengnews">确认</el-button>
    </el-form-item>
  </el-form>

</div>
</template>

<script>
export default {
  name: "adnews",
  methods: {
    //==================文章管理方法========================
    // 格式化日期
    fomartDate(time) {
      // 将需要格式化的数据传入
      time = this.dayjs(time).format('YYYY-MM-DD HH:mm:ss') //这个时间已经加了8小时哦,跟你
                                                            //如果是添加事件,时间就是当下时间
      return time
    },
    add(){
      this.news2={
        newsID:'',
        newsTitle:"",
        newsContent:"",
        newsDate:"",
        newsImagePath:"",
        newsIsTop:'',
        newsZan:''},
          this.newsimgfileList=[]
    },
    xinzengnews(){
      this.news2.newsDate=this.fomartDate(this.news2.newsDate);
      this.newsimgnames=[];
      var basestr="#";
      this.$refs.upload.uploadFiles.forEach((item)=>{
        item.name=item.name+basestr;
        this.newsimgnames.push(item.name);
        item.name = item.name.substr(0, item.name.length - 1);
      })
      var newsImagePath="";
      newsImagePath=this.newsimgnames.join("");
      this.news2.newsImagePath=newsImagePath;
      var _this=this;
      this.$axios({
        method:"post",
        url:"http://localhost:9090/web-demo/news/add",
        data:_this.news2
      }).then(function (resp) {
        if(resp.data == "success"){
          //添加成功
          var num=_this.newstotalCount+1;
          var num1=Math.ceil(num/_this.newspageSize)
          _this.newscurrentPage=num1;
          // 弹出消息提示
          _this.$message({
            message: '恭喜你，添加成功',
            type: 'success'
          });
          _this.newsonsubmit();
        }
      })
    },
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

<style scoped>

</style>