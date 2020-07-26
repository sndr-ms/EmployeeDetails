var employeeName;
var employeeDesignation;
var employeeDob;
$(function()
{
	
	var $patt=new RegExp("^[A-Za-z ]+$");
	$("#add").click(function(){
		employeeName=$("#en").val();
		employeeDesignation=$("#dropdown").val();
		employeeDob=$("#dob").val();

		if(employeeName.length!==0 && employeeDesignation!=="Designation" && employeeDob.length !==0 ){
			if(employeeName.length<25 && $patt.test(employeeName))
				{
					addInfo();
				}
			else
			{
				alert("Enter Valid Name!");
				reset();
			}
				
		}
		else{
			warn();
		}
	});
});

function addInfo(){
	
		$.ajax({
			type: "GET",
			url: "/EmployeeDetails/AddEmployee",
			data: {
				ename :employeeName,
				designation : employeeDesignation,
				dateofbirth : employeeDob
			},
			success: function(res){
				if(parseInt(res)!==0)
				{
					$("#data").html("Last Added Record!<br><br>"+res);
				}
				reset();
				
			}
		});	
}

function warn(){
	alert("Enter valid data");
	reset();
}

function reset()
{
	$("#en").val("");
	$("#dropdown").val("Designation");
	$("#dob").val("");
}