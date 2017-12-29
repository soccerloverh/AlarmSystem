<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">

a,div{
	font-size: 12px;
	font-family: Verdana;
	font-weight: bold;
} 

#userTable thead th {
	background-color: rgb(81, 130, 187);
	color: #fff;
	border-bottom-width: 0;
}

/* Column Style */
#userTable td {
	color: #000;
}
/* Heading and Column Style */
#userTable tr, #userTable th {
	border-width: 1px;
	border-style: solid;
	border-color: rgb(81, 130, 187);
}

/* Padding and font style */
#userTable td, #userTable th {
	padding: 5px 10px;
	font-size: 12px;
	font-family: Verdana;
	font-weight: bold;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="scripts/jquery-3.2.1.min.js"></script>
<title>User Configeration</title>
</head>

<body> 
	<div align="center">
		<h2>User Configeration</h2> 
		<div align="right"><a href="toMainPage"><< Back</a></div>
		<hr>
		<br>
	</div>
	<div align="center">
		<div>查询条件</div>
		<input id="search_name" type="text"/>:用户名   
		<select id='search_level'>
			<option value="-1"> </option>
			<option value="1">Admin</option>
			<option value="0">User</option>
		</select>:等级
		<button onclick="return searchUser()">查询</button>
	</div>
	<br>
	<div align="center">
		<table id="userTable">
		</table>
	</div>
	<br>
	<div id="split" align="center"></div>	
	<br>
	<div align="center">
		<a href="#" onclick="addUser()">Add new user</a>
	</div>
	<br>
	<form id="editUser" action="">
		<div id = "edit" align="center"></div>
	</form>
	
	
	<div align="center">
		<font size=2 color='red' id="erro"></font>
	</div>
	
	<script type="text/javascript">
		window.onload = function() {
			applyTable(1);
		}
		function searchUser(now){
			var name = $('#search_name').val();
			var level = $("#search_level option:selected").val();
			if(now == null|| now == undefined){
				now = 1;
			}
			var json = {"user_name":name,"user_level":level};
			$.ajax({
				type:"POST",
				data:JSON.stringify(json),
				contentType :"application/json",
				url:"getSearchUser/"+now,
				success:function(data){
					tab(data);
					splitSearchPage(json,now);
				}
			}); 
			return false;
		}
		function splitSearchPage(json,now){
			if(now == null|| now == undefined){
				now = 1;
			}
			$.ajax({
				type:"POST",
				data:JSON.stringify(json),
				contentType :"application/json",
				url:"getSearchSplit",
				success:function(data){
					var split_str = split("searchUser",data,now);
					document.getElementById("split").innerHTML = split_str;
				}
			});
		}
		function addUser(){
			edit_str = "<input name='user_id' type='hidden' readonly='true' /><br>";
			edit_str+= "Username:<input name='user_name' type='text' /><br>";
			edit_str+= "Password:<input name='user_password' type='password'/><br>";
			edit_str+= "Level:<input type='radio' name='user_level' value='1'>Admin</input><input type='radio' name='user_level' value='0'>User</input><br>";
			edit_str+= "<input type='submit' onclick='return subAdd()' value='Submit'/>";
			
			document.getElementById("edit").innerHTML = edit_str;
		}
		
		function subAdd(){
			var jsonUserInfo = $('#editUser').serializeObject()
			var json = JSON.stringify(jsonUserInfo)
			$.ajax({
				type : "post",
				url : "addUser",
				contentType : "application/json;charset=utf-8",
				data : json,
				success : function(data){
					if(data.status=="ok"){
						alert("Add Success!");
						location.reload();
					}else{
						document.getElementById("erro").innerHTML = data.info;
					}
				}
			});
			return false;
		}
		
		function tab(data){
			var tablestr = "<thead><th>ID</th><th>Name</th><th>Level</th><th>Edit</th><th>Delete</th></thead><tbody>";
			var json = JSON.stringify(data);
			for (x in data) {
				tablestr = tablestr + "<tr>";
				for (y in data[x]) {
					if (data[x][y] == null) {
						continue;
					}
					tablestr += "<td>" + data[x][y] + "</td>";
				}
				tablestr += "<td><a href='#' onclick='editUser("+JSON.stringify(data[x])+")'>Edit</td>";
				tablestr += "<td><a href='#' onclick='deleteUser("+data[x].user_id+")'>Delete</td>";
				tablestr = tablestr + "</tr>";
			}
			tablestr += "</tbody>";
			document.getElementById("userTable").innerHTML = tablestr;
		}
		
		function applyTable(pageNow){
			$.ajax({ 
				type : "GET",
				url : "getUsersListByPage/"+pageNow,
				success : function(data) {
					tab(data);
				}
			}); 
			splitPage(pageNow);
			return false;
		}
		
		 $.fn.serializeObject = function() {    
		   		var o = {};    
		   		var a = this.serializeArray();    
		   		$.each(a, function() {    
		       	if (o[this.name]) {    
		           if (!o[this.name].push) {    
		               o[this.name] = [o[this.name]];    
		           }    
		          o[this.name].push(this.value || '');    
		       } else {    
		           o[this.name] = this.value || '';    
		       }    
		   });    
		   return o;    
		}
		function split(method,data,now){
			var pageNow = parseInt(now); 
			var x = ((data.rowCount/data.pageSize)-parseInt((data.rowCount/data.pageSize)))>0?(parseInt((data.rowCount/data.pageSize))+1):(data.rowCount / data.pageSize);
			if (x < 0)
				return;
			var split_str = "";
			if (pageNow <=1) {
				split_str += "<a onclick='return "+method+"(" + 1 
						+ ")' href='#'>Pre</a> ";
			} else { 
				split_str += "<a onclick='return "+method+"(" + (pageNow - 1)
						+ ")' href='#'>Pre</a> ";
			} 
			var offset = 1;
			var count =0;
			if((pageNow-2)>0) {
				offset = parseInt(pageNow-2 > 0 ? (pageNow-2):1);
			} 
			if((pageNow+2) > x) {
				if(x-4 > 0){
					offset = x-4;
				}else{
					offset = 1;
				}
			}
			if(x > 5){ count += offset+5;}
			else{count+=x;} 
			for (var i = offset; i <= count; i++) {
				if((i) != pageNow){
					split_str += "<a onclick='return "+method+"(" +i
					+ ")' href='#'> (" + i + ") </a>";
				}else{
					split_str +="<a>("+i+")</a>";
				}
			}

			if (pageNow+1 > x) {
				split_str += "<a onclick='return "+method+"(" + x
						+ ")' href='#'>Next</a> ";
			} else {
				split_str += "<a onclick='return "+method+"(" + (pageNow + 1) 
						+ ")' href='#'>Next</a> ";
			}
			split_str +="跳转:<input id='jump' size='2'/> 页 <button value='跳' onclick='jump()'>跳</button>共"+x+"页";
			
			return split_str;
		}
		
		function splitPage(now){
			$.ajax({
				type : "GET",
				url : "getSplitInfo",
				success : function(data) {
					var split_str = split("applyTable",data,now);
					document.getElementById("split").innerHTML = split_str;
				}
			});
		}
		
		function editUser(user){
			edit_str = "<input name='user_id' type='hidden' readonly='true' value='"+user.user_id+"'/><br>";
			edit_str+= "Username:<input name='user_name' type='text' value='"+user.user_name+"'/><br>";
			edit_str+= "Password:<input name='user_password' type='password'/><br>";
			edit_str+= "Level:<input type='radio' name='user_level' value='1'>Admin</input><input type='radio' name='user_level' value='0'>User</input><br>";
		
			edit_str+= "<input type='submit' onclick='return subUpdate()' value='Submit'/>";
			
			document.getElementById("edit").innerHTML = edit_str;
		}
		 
		function subUpdate(){
			var jsonUserInfo = $('#editUser').serializeObject()
			var json = JSON.stringify(jsonUserInfo)
			alert(json);
			$.ajax({
				type : "post",
				url : "updateUser",
				contentType : "application/json;charset=utf-8",
				data : json,
				success : function(data){
					if(data.status=="ok"){
						alert("Update Success!");
						location.reload();
					}else{
						document.getElementById("erro").innerHTML = data.info;
					}
				}
			});
			return false;
		}
		
		
		function deleteUser(id){
			if(confirm("MAKE SURE DELETE!")){
				$.ajax({
					type : "get",
					url : "deleteUser/"+id,
					success : function(data){
						if(data.status=="ok"){
							alert("Delete Success!");
							location.reload();
						}else{
							document.getElementById("erro").innerHTML = data.info;
						}
					}
				});
				
			}else{
				return false;
			}
		}
	</script>
</body>
</html>