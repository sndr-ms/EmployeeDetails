$(function(){
	$("#send").click(function(){
		if($("#id").val().length==7 && $("#id").val().substring(0,3).match(/emp/i) && 
				$("#id").val().substring(3,7).match(/\d/g))
		{ 
			search();
		}
		else
			warn();
	});
	$("#listall").click(function(){
		$("#id").val("");
		search();
	});
});

function search(){
			
	$.ajax({
		type:"GET",
		url:"/EmployeeDetails/SearchRecord",
		data:{
			empId:$("#id").val()
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
	alert("enter valid employee id");
	$("#id").val("");
}