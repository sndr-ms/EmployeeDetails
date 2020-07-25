$(function(){
	
	$(location).attr('href',"#home");
	$("#log").click(function(){
		getLoginElements();
	});
	$("#companyname").click(function(){
		if(confirm("are you sure want to go home page?"))
			$(location).attr('href',"home.html");
	});
	
});

function getLoginElements(){
	
	$.ajax({
		url:"/EmployeeDetails/login/login.html",
		success:function(res){
			if(parseInt(res)!==0)
				$("#container").html(res); 	
		}
	});
	$(location).attr('href',"#login");
}

