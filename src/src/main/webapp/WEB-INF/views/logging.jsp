<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logging view</title>
<style type="text/css">
a, div {
	font-size: 12px;
	font-family: Verdana;
	font-weight: bold;
}

#log thead th {
	background-color: rgb(81, 130, 187);
	color: #fff;
	border-bottom-width: 0;
}

/* Column Style */
#log td {
	color: #000;
}
/* Heading and Column Style */
#log tr, #log th {
	border-width: 1px;
	border-style: solid;
	border-color: rgb(81, 130, 187);
}

/* Padding and font style */
#log td, #log th {
	padding: 5px 10px;
	font-size: 12px;
	font-family: Verdana;
	font-weight: bold;
}
</style>
</head>
<script type="text/javascript" src="scripts/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	window.onload = function() {
		getLog(1);
	}
	function splitPage(now) {
		$.ajax({
			type : "GET",
			url : "getLogPageInfo",
			success : function(data) {
				var pageNow = parseInt(now); 
				var x = (data.rowCount / data.pageSize)-parseInt((data.rowCount / data.pageSize))>0?parseInt((data.rowCount / data.pageSize))+1:(data.rowCount / data.pageSize);
				if (x < 0)
					return;
				var split_str = "";
				if (pageNow <=1) {
					split_str += "<a onclick='return getLog(" + 1 
							+ ")' href='#'>Pre</a> ";
				} else { 
					split_str += "<a onclick='return getLog(" + (pageNow - 1)
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
				for (var i = offset; i < count; i++) {
					if((i) != pageNow){
						split_str += "<a onclick='return getLog(" +i
						+ ")' href='#'> (" + i + ") </a>";
					}else{
						split_str +="<a>("+i+")</a>";
					}
				}

				if (pageNow+1 > x) {
					split_str += "<a onclick='return getLog(" + x
							+ ")' href='#'>Next</a> ";
				} else {
					split_str += "<a onclick='return getLog(" + (pageNow + 1) 
							+ ")' href='#'>Next</a> ";
				}
				split_str +="跳转:<input id='jump' size='2'/> 页 <button value='跳' onclick='jump()'>跳</button>共"+x+"页";
				document.getElementById("split").innerHTML = split_str;

			}
		});
	}

	var format = function(time, format) {
		var t = new Date(time);
		var tf = function(i) {
			return (i < 10 ? '0' : '') + i
		};
		return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
			switch (a) {
			case 'yyyy':
				return tf(t.getFullYear());
				break;
			case 'MM':
				return tf(t.getMonth() + 1);
				break;
			case 'mm':
				return tf(t.getMinutes());
				break;
			case 'dd':
				return tf(t.getDate());
				break;
			case 'HH':
				return tf(t.getHours());
				break;
			case 'ss':
				return tf(t.getSeconds());
				break;
			}
		});
	}
	
	function getLog(pageNow) {
		$.ajax({
			type : "GET",
			url : "getLogByPage/" + pageNow,
			dataType : "json",
			success : function(data) {
				var str = "";
				for (x in data) {
					str += "<tr>";
					for (y in data[x]) {
						if (y == "log_time") {
							str += "<td>";
							str += format(data[x][y], 'yyyy-MM-dd HH:mm:ss'); 
							str += "</td>";
							continue;
						} 
						str += "<td>";
						str += data[x][y];
						str += "</td>";
					}
					str += "</tr>";
				} 
				document.getElementById("tb").innerHTML = str;
				splitPage(pageNow);
			}
		});
	}
	
	function jump(){ 
		var x = $("#jump").val();
		return getLog(x);
	}
</script>
<body>
	<div align="center">
		<h2>Log Review</h2>
		<hr>
		<div align="right">
			<a href="toMainPage"><< back</a> 
		</div>
		<table id="log">
			<thead>
				<tr>
					<th>ID</th>
					<th>User</th>
					<th>IP</th>
					<th>Operation</th>
					<th>Time</th>
				</tr>
			</thead>
			<tbody id="tb">
			</tbody>
		</table>
	</div>
	<div id="split" align="center"></div>
	
</body>
</html>