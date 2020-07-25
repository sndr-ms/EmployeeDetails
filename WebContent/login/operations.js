$(function()
{
	$(location).attr('href',"#menu");
	$("#adde").click(getAddEmployee);
	$("#search").click(getSearchEmployee);
	$("#del").click(getDeleteEmployee);
	$("#update").click(getUpdateEmployee);
});

function getAddEmployee(){
	$("ul li a").css('background-color','tomato');
	$("#adde").css('background-color','black');
	$.ajax({
		url:"/EmployeeDetails/login/operation/addEmployee.html",
		success:function(res){
			$("#menucontainer").html(res);
		}
	});
}

function getSearchEmployee(){
	
	$("ul li a").css('background-color','tomato');
	$("#search").css('background-color','black');
	$.ajax({
		url:"/EmployeeDetails/login/operation/searchEmployee.html",
		success:function(res){
			$("#menucontainer").html(res);
		}
	});
}

function getDeleteEmployee(){
	
	$("ul li a").css('background-color','tomato');
	$("#del").css('background-color','black');
	$.ajax({
		url:"/EmployeeDetails/login/operation/deleteEmployee.html",
		success:function(res){
			$("#menucontainer").html(res);
		}
	});
}

function getUpdateEmployee(){
	$("ul li a").css('background-color','tomato');
	$("#update").css('background-color','black');
	$.ajax({
		url:"/EmployeeDetails/login/operation/updateEmployee.html",
		success:function(res){
			if(parseInt(res)!==0)
				$("#menucontainer").html(res);
		}
	});
}