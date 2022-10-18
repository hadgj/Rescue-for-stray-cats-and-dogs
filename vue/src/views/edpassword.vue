<template>
  <div id="app">
      <button @click="upload">上传</button>
    <input type="file" id="file">
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      fileList:[]
    };
  },
  methods: {
    upload()
    {
      let param = new FormData(); // 创建form对象
      let fileip=document.getElementById("file");
      let file=fileip.files[0];
       param.append("file",file);
      let config = {
      headers: { "Content-Type": "multipart/form-data" }
    };
      axios.post("http://localhost:9090/web-demo/UploadServlet/upload", param, config).then(function(resp){
      console.log(resp.data);
   })
    }
  }
};
</script>