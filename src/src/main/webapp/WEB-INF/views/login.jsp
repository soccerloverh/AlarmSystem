<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login Page</title>
<style type="text/css">
	form{
		font-size: 12px;
		font-family: Verdana;
		font-weight: bold;
	}
</style>
</head>
<script type="text/javascript" src="scripts/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	function isEmpty() {
		var name = document.getElementById("name");
		var password = document.getElementById("password");

		if (name.value == "") {
			alert("UserName can't be empty");
			return false;
		}
		if (password.value == "") {
			alert(" Password can't be empty");
			return false;
		}
		return true;
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
	
	function sub(){
		if(!isEmpty()){
			return false;
		}else{
			var jsonUserInfo = $('#userForm').serializeObject()
			var json = JSON.stringify(jsonUserInfo)
			var href = $("#userForm").attr("action"); 
			$.ajax({
				type: "POST",
				url : href,
				contentType : "application/json;charset=utf-8",
				data : json,
				success:function (data){
					if(data.status=="ok"){
						window.location.href = data.href;
					}else{
						$("#erroInfo").html("<font size=2 color='red'>"+data.info+"<font>");
						return false;
					}
				}
			}
			);	
			return false;
		}
	}
	
</script>

<body>
	<h2 align="center">AlarmConfigeration System</h2>
	<hr> 
	<br>
	<div align="center">
		<form id="userForm" action="login" method="post">
			name:<input type="text" id="name" name="user_name" /><br /> <br />
			password:<input type="password" id="password" name="user_password" /><br />
			<br /> <input type="submit" value="submit" id="submit"
				onclick="return sub()" /><br/>
		</form>
		<p >
			<font size=2 color='red' id="erroInfo">
			<% 
			if(session.getAttribute("erro")!=null){
				out.print(session.getAttribute("erro"));
			}
			%>
			</font>
		</p>
	</div>
	<br>
	<br>

</body>