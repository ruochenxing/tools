public String uploadFile(MultipartHttpServletRequest request) throws IOException{
	int fileID = GlobalFunc.parseInt(request.getParameter("fileID"));
	StringBuilder sb=new StringBuilder("");
	List<MultipartFile> files = new LinkedList<MultipartFile>();
	for (int i = 1; i <= fileID; i++) {
		MultipartFile file = request.getFile("file" + i);
		if(file!=null&&!file.isEmpty()&&(!file.getOriginalFilename().endsWith(".exe")&&!file.getOriginalFilename().endsWith(".bat")))
			files.add(file);
	}
	ServletContext sc = request.getSession().getServletContext();
	String dir = sc.getRealPath("/upload/planfiles/");
	//如果文件夹不存在，则创建文件夹
	if (!new File(dir).exists()){
		new File(dir).mkdirs();
	}
	for (MultipartFile file:files) {
		String fileName = file.getOriginalFilename();//文件的真实名称
		//防止文件被覆盖，一纳秒生成文件名称
		Long _l = System.nanoTime();
		String _extName = fileName.substring(fileName.indexOf("."));
		fileName = _l+ _extName;
		FileUtils.writeByteArrayToFile(new File(dir, fileName), file.getBytes());
		String uploadPath =  request.getContextPath() + "/upload/planfiles/" + fileName;//存储到数据库的相对路径
		sb.append(uploadPath).append(",");
	}
	return sb.toString();
}



//增加上传附件
function addIndex(){
	var fileID = $("#fileID").val();
	var fileIDS = parseInt(fileID);
	fileIDS += 1;
	$("#fileID").val(fileIDS);
	$("#tool").before('<tr><td></td><td colspan="2"><input type="file" name="file'+ fileIDS +'"  id="file'+ fileIDS +'" size="40"/>&nbsp;<input type="button" onclick="getDel(this)" value="删除"/></td></tr>');
}
function getDel(k){
	$(k).parent().parent().remove();     
}


<tr>
	<td></td>
	<td><span class="STYLE4" style="weight:bold">标准附件：</span></td>
	<td colspan="2"></td>
</tr>
<tr>
	<td><input type="hidden" id="fileID" name="fileID" value="1"/></td>
	<td colspan="2">
		<input type="file"  id="file1" style=" border:1px solid #dabe83; width:230px; height:20px; padding-left:5px;  color:#606060; line-height:22px;" id="file1" onblur="doName(1);" name="file1" size="40"/>
		<img src="<c:url value="/images/add.gif"/>" width="45" height="13" style="cursor: pointer;" onclick="addIndex()" />
	</td>
	<td></td>
</tr>
<tr id="tool"></tr>
