$(function(){
	$("#delete").click(function(){
		if($("#id").val().length==7 && $("#id").val().substring(0,3).match(/emp/i) && 
				$("#id").val().substring(3,7).match(/\d/g))
		{ 
			deleteRecord();
		}
		else
			warn();
	});
});

function deleteRecord(){
	
	$.ajax({
		url:"/EmployeeDetails/DeleteRecord",
		data:{
			empId:$("#id").val()
		},
		cache:false,
		success:function(res){
			if(parseInt(res)!==0){
				if(res!==null && res.length!==0){
					
					if(confirm("Are you sure want to delete "+res+" record?"))
					{
						$.ajax({
							url:"/EmployeeDetails/DeleteRecordMain",
							data:{
								empId:$("#id").val()
							},
							cache:false,
							success:function(res){
								if(parseInt(res)!==0)
								{
									alert($("#id").val()+" "+res);
									$("#id").val("");
								}
							}
						});
						
					}
					else
					{
						alert("Record deletion aborted");
					}
				}
				else
					alert($("#id").val()+" record doesn't exist");
			}
		}
	});
}
