var fetch=false;
var id="";
var $enamePattern=new RegExp("^[A-za-z]+$");
$(function(){
	$("#updateanother").hide();
	changeVisibility();
	initialCheck();
	
	$("#fetch").click(function(){

		if($("#eid").val()!==undefined && $("#eid").val().length==7 && $("#eid").val().substring(0,3).match(/emp/i)
				&& $("#eid").val().substring(3,7).match(/\d/g))
		{
			fetchRecord();
		}
		else
		{
			warn();
		}
	});
	$("#enameCheck").click(function()
	{
		if($("#enameCheck").prop('checked')==false)
		{
			$("#ename").val("");
			$("#ename").prop('disabled',true);
		}
		else
			$("#ename").prop('disabled',false);
		changeStateOfUpdateButton();
	});	
	$("#edesignationCheck").click(function(){
		
		if($("#edesignationCheck").prop('checked')==false)
		{
			$("#edesignation").val("Designation");
			$("#edesignation").prop('disabled',true);
		}
		else
			$("#edesignation").prop('disabled',false);
		changeStateOfUpdateButton();
			
	});
	$("#edobCheck").click(function(){
	
		if($("#edobCheck").prop('checked')==false)
		{
			$("#edob").val("");
			$("#edob").prop('disabled',true);
		}
		else
			$("#edob").prop('disabled',false);
		changeStateOfUpdateButton();
	});
	
	$("#updaterecord").click(function(){
		if($enamePatter.test($("#ename").val()))
			updateRecord();
	});
	$("#updateanother").click(refreshUpdate);
});

function changeStateOfUpdateButton()
{
	if($("#enameCheck").prop('checked')==true || $("#edesignationCheck").prop('checked')==true || $("#edobCheck").prop('checked')==true)
		$("#updaterecord").show('fast');
	else
		$("#updaterecord").hide('fast');
}
function refreshUpdate()
{
	$.ajax({
		url:"/EmployeeDetails/login/operation/updateEmployee.html",
		success:function(res){
			if(parseInt(res)!==0)
				$("#menucontainer").html(res);
		}
	});
}

function initialCheck(){
	$("#ename").prop('disabled',true);
	$("#edesignation").prop('disabled',true);
	$("#edob").prop('disabled',true);
}

function changeVisibility()
{
	if(fetch)
		$("#updateContainer").show();
	else
		$("#updateContainer").hide();
	
}

function fetchRecord()
{
	$.ajax({
		type:"GET",
		url:"/EmployeeDetails/SearchRecord",
		data:{
			empId:$("#eid").val()
			},
		success:function(res){
			if(parseInt(res)!==0)
			{
				$("#fetchContain").html(res);
				if(res.substring(0,8)!=="<script>")
				{
					fetch=true;
					changeVisibility();
					$("#updaterecord").hide('fast');
					$("#updateanother").show('fast');
					id=$("#eid").val();
					changeVisibilityUpdateFetch();
					
				}
			}
		}	
	});
}

function warn()
{
	alert("Enter valid Employee Id");
	$("#eid").val("");
}

function changeVisibilityUpdateFetch()
{
	$("#eid").hide('fast');
	$("#fetch").hide('fast');
}

function updateRecord()
{
	$.ajax({
		type:"GET",
		url:"/EmployeeDetails/UpdateEmployee",
		data:{
			empId:id,
			empName:$("#ename").val(),
			empDesignation:$("#edesignation").val(),
			empDob:$("#edob").val()
		},
		success:function(res){
			if(parseInt(res)!==0)
			{
				$("#fetchContain").html("Updated Record!<br><br>"+res);
				fetch=false;
				changeVisibility();
			}
		}
	});
}