var employeeId;
$(function(){
	$("#send").click(function(){
		employeeId=$("#id").val();
		if(employeeId.length==7 && employeeId.substring(0,3).match(/emp/i) && 
				employeeId.substring(3,7).match(/\d/g))
		{ 
			search();
		}
		else
			warn();
	});
	$("#listall").click(function(){
		$("#id").val("");
		employeeId=$("#id").val();
		search();
	});
});

function search(){
			
	$.ajax({
		type:"GET",
		url:"/EmployeeDetails/SearchRecord",
		data:{
			empId:employeeId
		},
		cache:false,
		success:function(res){
			if(parseInt(res)!==0){
				$("#result").html(res);
			}
		}
	});
	
}

function warn(){
	alert("Enter valid employee id");
	$("#id").val("");
}