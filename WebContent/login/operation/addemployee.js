$(function()
{
	var $patt=new RegExp("^[A-Za-z]+$");
	$("#add").click(function(){
		if($("#en").val().length!==0 && $("#dropdown").val()!=="Designation" && $("#dob").val().length !==0 ){
			if($("#en").val().length<25 && $patt.test($("#en").val()))
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
				ename :$("#en").val(),
				designation : $("#dropdown").val(),
				dateofbirth : $("#dob").val()
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