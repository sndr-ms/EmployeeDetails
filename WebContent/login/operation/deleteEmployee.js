var employeeId;
$(function(){
	$("#delete").click(function(){
		employeeId=$("#id").val();
		if(employeeId.length==7 && employeeId.substring(0,3).match(/emp/i) && 
				employeeId.substring(3,7).match(/\d/g))
		{ 
			deleteRecord();
		}
		else
			alert("Enter valid Employee Id");
	});
});

function deleteRecord(){
	
	$.ajax({
		url:"/EmployeeDetails/DeleteRecord",
		data:{
			empId:employeeId
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
								empId: employeeId
							},
							cache:false,
							success:function(res){
								if(parseInt(res)!==0)
								{
									alert(employeeId+" "+res);
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
					alert(employeeId+" record doesn't exist");
			}
		}
	});
}
