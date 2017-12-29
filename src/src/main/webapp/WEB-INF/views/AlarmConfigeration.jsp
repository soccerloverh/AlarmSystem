<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="scripts/jquery-3.2.1.min.js"></script>
<title>Alarm Configeration</title>
<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #333;
}
li {
	float: left;
}
li a {
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}
li a:hover {
	background-color: #111;
}
a,form{
	font-size: 12px;
	font-family: Verdana;
	font-weight: bold;
}

#alarm thead th {
	background-color: rgb(81, 130, 187);
	color: #fff;
	border-bottom-width: 0;
}

/* Column Style */
#alarm td {
	color: #000;
}
/* Heading and Column Style */
#alarm tr, #alarm th {
	border-width: 1px;
	border-style: solid;
	border-color: rgb(81, 130, 187);
}

/* Padding and font style */
#alarm td, #alarm th {
	padding: 5px 10px;
	font-size: 12px;
	font-family: Verdana;
	font-weight: bold;
}
</style>
</head>
<body>
	<div align="center">
		<h2 >Alarm Configeration System</h2>
		<hr>
		<div align="right">
			<a href="toMainPage"><< back</a>
		</div>
	</div>
	<br>
	<div align="center">
		<ul>
			<li><a class="active" href="#" onclick="return Email(1)">邮件</a></li>
			<li><a href="#" onclick="return IP(1)">黑IP</a></li>
			<li><a href="#" onclick="return DNS(1)">黑DNS</a></li>
			<li><a href="#" onclick="return Eigen(1)">特征值</a></li>
		</ul>
	</div>
	<br>
	<br>
	<div align="center">
		<table id="alarm">
			<thead id="head"></thead>
			<tbody id="body"></tbody>
		</table>
	</div>
	<br>
	<div id="split" align="center"></div>	
	<br>
	<div align="center">
		
	</div>
	
	<br>
	<div align="center">
		<form id="edit">
		</form>
	</div>
	<br>
	<div align="center">
		<font size='2' color="red" id="erro"></font>
	</div>
	<script type="text/javascript">
	var type;
	var level = new Map();
	var type_str="";
	var level_str="";
	//初始化全局变量
	window.onload = function() {
		level.set(1,"Low");
		level.set(2,"Medium");
		level.set(3,"High");
		$.ajax({
			type : "get",
			url : "getType",
			success : function(data){
				for(x in data){
					type=data;
					type_str+="<option value='"+(x)+"'>";
					type_str+=data[x];
					type_str+="</option>";
				}
			} 
		});
		for(var y = 1;y<=level.size; y++){
			level_str+="<option value='"+y+"'>";  
			level_str+=level.get(y);
			level_str+="</option>";
		}  
		Email(1);
	}
	function jump(type){ 
		var x = $("#jump").val();
		type(x);
	} 
	
	//分页
	function split(now,type){
		$.ajax({
			type : "GET",
			url : "get"+type+"PageInfo",
			success : function(data) {
				var pageNow = parseInt(now); 
				var x = (data.rowCount/data.pageSize)-parseInt((data.rowCount/data.pageSize))>0?parseInt((data.rowCount / data.pageSize))+1:(data.rowCount / data.pageSize);
				if (x < 0)
					return;
				var split_str = "";
				if (pageNow <=1) {
					split_str+="<a onclick='return "+type+"("+1+")' href='#'>Pre</a> ";
				} else { 
					split_str+="<a onclick='return  "+type+"("+(pageNow-1)+")' href='#'>Pre</a> ";
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
						split_str+="<a onclick='return  "+type+"("+i+")' href='#'> ("+i+") </a>";
					}else{
						split_str +="<a>("+i+")</a>";
					}
				}

				if (pageNow+1 > x) {
					split_str+="<a onclick='return  "+type+"("+(pageNow)+")' href='#'>Next</a> ";
				} else {
					split_str+="<a onclick='return  "+type+"("+(pageNow+1)+")' href='#'>Next</a> ";
				}
				split_str +="跳转:<input id='jump' size='2'/> 页 <button value='跳' onclick='jump("+type+""+")'>跳</button>共"+x+"页";
				document.getElementById("split").innerHTML = split_str;

			}
		});
		return false;
	}
	function toEditEigen(data){ 
		var form_str="<input type='hidden' name='info_id' value='"+data.info_id+"'/>"+
			"Alarm Name:<input name='alarm_name' value='"+data.alarm_name+"'/><br>"+
			"Alarm Type:<select name='alarm_type_id' >"+type_str+"</select><br>"+
			"Alarm Level:<select name='alarm_level' >"+level_str+"</select><br>"+
			"Alarm Note:<input name='alarm_note' value='"+data.alarm_note+"'/><br>"+
			"<input type='hidden' name='eigen_id' value='"+data.eigen_id+"'/>"+     
			"Source IP:<input name='eigen_sourceIP' value='"+data.eigen_sourceIP+"'/><br>"+
			"Destination IP:<input name='eigen_destinationIP' value='"+data.eigen_destinationIP+"'/><br>"+
			"Protocol:<input name='eigen_protocol' value='"+data.eigen_protocol+"'/><br>"+
			"<input type='submit' value='Submit' onclick='return subEdit(\"eigen\")'/>";
		document.getElementById("edit").innerHTML = form_str;
	}
	//eigen表格
	function Eigen(pageNow){
		if(pageNow==NaN){ 
			pageNow=1;
		}
		$.ajax({
			type : "get",
			url : "getEigenListByPage/"+pageNow,
			success: function(data){
				var table_str = "<tr><th>AlarmID</th><th>AlarmName</th><th>AlarmType</th><th>AlarmLevel</th><th>AlarmNote</th><th>SourceIP</th><th>DesitinationIP</th><th>Protocol</th><th>Edit</th><th>Delete</th></tr>";		
				for(alarm in data){
					table_str+="<tr>";
					for(col in data[alarm]){
						if(col == "alarm_type_id"){
							table_str+="<td>"; 
							table_str+=type[data[alarm][col]];
							table_str+="</td>";
						}else if(col == "alarm_level"){
							table_str+="<td>"; 
							table_str+=level.get(data[alarm][col]);
							table_str+="</td>";
						}else if(col == "eigen_id"){
							continue;
						}else{
							table_str+="<td>";
							table_str+=data[alarm][col];
							table_str+="</td>";
						}
					}
					table_str+="<td>"; 
					table_str+="<a href='#' onclick='toEditEigen("+JSON.stringify(data[alarm])+")' >Edit</a>";
					table_str+="</td>";
					table_str+="<td>"; 
					table_str+="<a href='#' onclick='deleteAlarm("+JSON.stringify(data[alarm].info_id)+",\"eigen\")' >Delete</a>";
					table_str+="</td>";
					table_str+="</tr>";
				}
				var newAlarm = {"info_id":null,"alarm_name":"","alarm_type_id":1,"alarm_level":1,"alarm_note":"","eigen_id":0,"eigen_sourceIP":"","eigen_destinationIP":"","eigen_protocol":""};
				table_str+="<br><a href='#' onclick='return toEditEigen("+JSON.stringify(newAlarm)+")'> >>Add new eigen alarm</a>";
				document.getElementById("head").innerHTML = table_str;
			}
		});
		split(pageNow,"Eigen");
		return false;
	}
	function toEditDNS(data){ 
		var form_str="<input type='hidden' name='info_id' value='"+data.info_id+"'/>"+
			"Alarm Name:<input name='alarm_name' value='"+data.alarm_name+"'/><br>"+
			"Alarm Type:<select name='alarm_type_id' >"+type_str+"</select><br>"+
			"Alarm Level:<select name='alarm_level' >"+level_str+"</select><br>"+
			"Alarm Note:<input name='alarm_note' value='"+data.alarm_note+"'/><br>"+
			"<input type='hidden' name='blackDNS_id' value='"+data.blackDNS_id+"'/>"+     
			"Black DNS:<input name='blackDNS_DNS' value='"+data.blackDNS_DNS+"'/><br>"+
			"<input type='submit' value='Submit' onclick='return subEdit(\"dns\")'/>";
		document.getElementById("edit").innerHTML = form_str;
	}
	
	//DNS表格
	function DNS(pageNow){
		if(pageNow==NaN){
			pageNow=1;
		}
		$.ajax({
			type : "get",
			url : "getDNSListByPage/"+pageNow,
			success: function(data){
				var table_str = "<tr><th>AlarmID</th><th>AlarmName</th><th>AlarmType</th><th>AlarmLevel</th><th>AlarmNote</th><th>BlackDNS</th><th>Edit</th><th>Delete</th></tr>";		
				for(alarm in data){
					table_str+="<tr>";
					for(col in data[alarm]){
						if(col == "alarm_type_id"){
							table_str+="<td>"; 
							table_str+=type[data[alarm][col]];
							table_str+="</td>";
						}else if(col == "alarm_level"){
							table_str+="<td>";
							table_str+=level.get(data[alarm][col]);
							table_str+="</td>";
						}else if(col == "blackDNS_id"){
							continue;
						}else{
							table_str+="<td>";
							table_str+=data[alarm][col];
							table_str+="</td>";
						}
					}
					table_str+="<td>"; 
					table_str+="<a href='#' onclick='toEditDNS("+JSON.stringify(data[alarm])+")' >Edit</a>";
					table_str+="</td>";
					table_str+="<td>"; 
					table_str+="<a href='#' onclick='deleteAlarm("+JSON.stringify(data[alarm].info_id)+",\"dns\")' >Delete</a>";
					table_str+="</td>";
					table_str+="</tr>";
				}
				var newAlarm = {"info_id":null,"alarm_name":"","alarm_type_id":1,"alarm_level":1,"alarm_note":"","blackDNS_id":0,"blackDNS_DNS":""};
				table_str+="<br><a href='#' onclick='return toEditDNS("+JSON.stringify(newAlarm)+")'> >>Add new black DNS alarm</a>";
				document.getElementById("head").innerHTML = table_str;
			}
		});
		split(pageNow,"DNS");
		return false;
	}	
	
	function toEditIP(data){ 
		var form_str="<input type='hidden' name='info_id' value='"+data.info_id+"'/>"+
			"Alarm Name:<input name='alarm_name' value='"+data.alarm_name+"'/><br>"+
			"Alarm Type:<select name='alarm_type_id' >"+type_str+"</select><br>"+
			"Alarm Level:<select name='alarm_level' >"+level_str+"</select><br>"+
			"Alarm Note:<input name='alarm_note' value='"+data.alarm_note+"'/><br>"+
			"<input type='hidden' name='blackIP_id' value='"+data.blackIP_id+"'/>"+     
			"Black IP:<input name='blackIP_ip' value='"+data.blackIP_ip+"'/><br>"+
			"<input type='submit' value='Submit' onclick='return subEdit(\"ip\")'/>";
		document.getElementById("edit").innerHTML = form_str;
	}
	
	//IP页面
	function IP(pageNow){
		if(pageNow==NaN){
			pageNow=1;
		}
		$.ajax({
			type : "get",
			url : "getIPListByPage/"+pageNow,
			success: function(data){
				var table_str = "<tr><th>AlarmID</th><th>AlarmName</th><th>AlarmType</th><th>AlarmLevel</th><th>AlarmNote</th><th>BlackIP</th><th>Edit</th><th>Delete</th></tr>";		
				for(alarm in data){
					table_str+="<tr>";
					for(col in data[alarm]){
						if(col == "alarm_type_id"){
							table_str+="<td>"; 
							table_str+=type[data[alarm][col]];
							table_str+="</td>";
						}else if(col == "alarm_level"){
							table_str+="<td>";
							table_str+=level.get(data[alarm][col]);
							table_str+="</td>";
						}else if(col == "blackIP_id"){
							continue;
						}else{
							table_str+="<td>";
							table_str+=data[alarm][col];
							table_str+="</td>";
						}
					}
					table_str+="<td>"; 
					table_str+="<a href='#' onclick='toEditIP("+JSON.stringify(data[alarm])+")' >Edit</a>";
					table_str+="</td>";
					table_str+="<td>"; 
					table_str+="<a href='#' onclick='deleteAlarm("+JSON.stringify(data[alarm].info_id)+",\"ip\")' >Delete</a>";
					table_str+="</td>";
					table_str+="</tr>";
				}
				var newAlarm = {"info_id":null,"alarm_name":"","alarm_type_id":1,"alarm_level":1,"alarm_note":"","blackIP_id":0,"blackIP_ip":""};
				table_str+="<br><a href='#' onclick='return toEditIP("+JSON.stringify(newAlarm)+")'> >>Add new black IP alarm</a>";
				document.getElementById("head").innerHTML = table_str;
			}
		});
		split(pageNow,"IP");
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
	
	function subEdit(uri){
		var obj = $('#edit').serializeObject()
		var json = JSON.stringify(obj);
		$.ajax({
			type : "POST",
			url : "updateAlarm/"+uri,
			data : json,
			contentType : "application/json;charset=utf-8",
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
	
	function toEditMail(data){ 
		 
		var form_str="<input type='hidden' name='info_id' value='"+data.info_id+"'/>"+
			"Alarm Name:<input name='alarm_name' value='"+data.alarm_name+"'/><br>"+
			"Alarm Type:<select name='alarm_type_id' >"+type_str+"</select><br>"+
			"Alarm Level:<select name='alarm_level' >"+level_str+"</select><br>"+
			"Alarm Note:<input name='alarm_note' value='"+data.alarm_note+"'/><br>"+
			"<input type='hidden' name='email_id' value='"+data.email_id+"'/>"+
			"Block Location:<select name='email_location'><option value='title'>Title</option><option value='content'>Content</option><option value='title&content'>Title&Content</option></select><br>"+     
			"Block Words:<input name='email_blockwords' value='"+data.email_blockwords+"'/><br>"+
			"<input type='submit' value='Submit' onclick='return subEdit(\"email\")'/>";

		document.getElementById("edit").innerHTML = form_str;
	}
	//Email页面 
	function Email(pageNow){
			if(pageNow==NaN||pageNow==undefined){
				pageNow=1;
			}
			$.ajax({
				type : "get",
				url : "getEmailListByPage/"+pageNow,
				success: function(data){
					var table_str = "";
					table_str = "<tr><th>AlarmID</th><th>AlarmName</th><th>AlarmType</th><th>AlarmLevel</th><th>AlarmNote</th><th>Location</th><th>BlockWords</th><th>Edit</th><th>Delete</th></tr>";	
					for(alarm in data){
						table_str+="<tr>";
						for(col in data[alarm]){ 
							if(col == "alarm_type_id"){
								table_str+="<td>";
								table_str+=type[data[alarm][col]];
								table_str+="</td>";
							}else if(col == "alarm_level"){ 
								table_str+="<td>";
								table_str+=level.get(data[alarm][col]);
								table_str+="</td>";
							}else if(col == "email_id"){
								continue;
							}else{
								table_str+="<td>";
								table_str+=data[alarm][col];
								table_str+="</td>"; 
							} 
						}
						table_str+="<td>"; 
						table_str+="<a href='#' onclick='toEditMail("+JSON.stringify(data[alarm])+")' >Edit</a>";
						table_str+="</td>";
						table_str+="<td>"; 
						table_str+="<a href='#' onclick='deleteAlarm("+JSON.stringify(data[alarm].info_id)+",\"email\")' >Delete</a>";
						table_str+="</td>";
						table_str+="</tr>";
					}
					var newAlarm = {"info_id":null,"alarm_name":"","alarm_type_id":1,"alarm_level":1,"alarm_note":"","email_id":0,"email_location":"","email_blockwords":""};
					table_str+="<br><a href='#' onclick='return toEditMail("+JSON.stringify(newAlarm)+")'> >>Add new email alarm</a>";
					document.getElementById("head").innerHTML = table_str;
				}
			});
			split(pageNow,"Email");
			return false;
		}
		
	
	function deleteAlarm(id,type){
		if(!confirm("Are you sure delete this Alarm?")){
			return false;
		}else{
			$.ajax({
				url : "delete/"+type+"&"+id,
				success : function(data){
					if(data.status=="ok"){
						alert("Delete Success!");
						location.reload();
					}else{
						document.getElementById("erro").innerHTML = data.info;
					}
				}
			});
			return false;
		}
	}
	</script>
</body>
</html>