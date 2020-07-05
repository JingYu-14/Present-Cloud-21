<template>
	<div class="content">
		<div class="detail">
			<p>任务详情</p>
			<span>所有的任务完成后都要提交到GitHub中仓库中（包括原型、需求文档等）。每个组员编写周工作日志，可以使用teambition截图。整合成一份文档，文档中列出项目在GitHub中的链接，如果有多个仓库分别列出。文档命名规范：小组序号_第N周周报.docx 例如：01_第5周周报.docx。</span>
		</div>
		<div class="upload">
			<el-upload
			  class="upload-demo"
			  action="https://jsonplaceholder.typicode.com/posts/"
			  :on-preview="handlePreview"
			  :on-remove="handleRemove"
			  :before-remove="beforeRemove"
			  multiple
			  :limit="3"
			  :on-exceed="handleExceed"
			  :file-list="fileList">
			  <el-button size="small" type="primary">点击上传</el-button>
			  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
			</el-upload>
		</div>
		<div class="btn">
			<el-button type="primary">提交</el-button>
			<el-button type="success">离开</el-button>
		</div>
	</div>
</template>

<script>
export default {
  name:'TeacherDetail',
	data() {
      return {
        fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
      };
    },
    methods: {
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->

<style scoped>
.content{
	background: white;
    padding-top: 1px;
}
.content .detail{
padding: 0 20px;
}
.content .detail p{
font-size: 14px;
    margin: 30px 20px 20px 20px;
    line-height: 14px;
    color: #333;
    border-left: 5px solid #0bd;
    padding-left: 10px;
}
.content .detail span{
    margin: 20px 0px 0px;
    height: auto;
    font-size: 13px;
    line-height: 1.8;
    word-wrap: break-word;
    white-space: pre-wrap;
    padding: 0 20px;
    color: #333;
}
.content .upload{
padding: 20px;
}
.content .btn{
	text-align: center;
    padding: 20px;
}
</style>
