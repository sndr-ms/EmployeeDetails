$(function(){
	
	$("#log").click(function(){
		$(location).attr('href',"#login");
		$.ajax({
			url:"/EmployeeDetails/login/login.html",
			async:false,
			success:function(res){
				if(parseInt(res)!==0)
					$("#container").html(res);
				else
					alert(res.status+" : "+res.statusText);
			}
		});
	});
	
});

$(window).bind('popstate',function(){
	alert("pop called");
	$(location).attr('href',"#home");
});