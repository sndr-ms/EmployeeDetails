$(function(){

	$("#login").click(checkUser);
	$("#clearfields").click(reset);

});


function checkUser(){

	var username=$("#user").val();
	var password=$("#pwd").val();
	if( username.length !==0 && password.length !==0){
		$.ajax({
			type: "POST",
			url:"/EmployeeDetails/CheckUser",
			data:{
				uname:username,
				pswd:password
			},
			dataType:'html',
			success:function(res){
				if(res=="true"){
					
					getMenu();
				}
				else{
					warn();
				}
			}
		});
	}
	else{
		warn();
	}
	
}

function getMenu(){
	
	$.ajax({
		url:"/EmployeeDetails/login/menu.html",
		success:function(res){
			if(parseInt(res)!==0)
				$("#container").html(res);
			else
				alert(res.status+" : "+res.statusText);
		}
	});
}

function warn(){
	
	alert("invalid username or password");
	reset();
}

function reset(){
	
	$("#user").val("");
	$("#pwd").val("");
}
