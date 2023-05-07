var sanInfo = document.querySelector("#sanInfo").value;

window.onload = function(){
	console.log("loadload");

		
		$.ajax({
        type: 'get',
        url: '/info/'+sanInfo+'/information',
        success: function(data){
            $("#module").load("/info/"+sanInfo+"/information");
	            
        }
	   });
	
};
$('#in').click(function(){
		
		var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="cotents"></div>';
		$.ajax({
        type: 'get',
        url: '/info/'+sanInfo+'/Sandeail',
        success: function(data){
            $("#module").load("/info/"+sanInfo+"/Sandeail");
        }
	   });
	   
});

$('#wea').click(function(){
		
		var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="cotents"></div>';
		$.ajax({
        type: 'get',
        url: '/info/'+sanInfo+'/SanWeather',
        success: function(data){
            $("#module").load("/info/"+sanInfo+"/SanWeather");
        }
	   });
	   
});

$('#food').click(function(){
		
		var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="cotents"></div>';
		$.ajax({
        type: 'get',
        url: '/info/'+sanInfo+'/SanKakaoMap',
        success: function(data){
            $("#module").load("/info/"+sanInfo+"/SanKakaoMap");
        }
	   });
	   
});
$('#item-point').click(function(){
		
		var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="cotents"></div>';
		$.ajax({
        type: 'get',
        url: '/info/'+sanInfo+'/information',
        success: function(data){
            $("#module").load("/info/"+sanInfo+"/information");
        }
	   });
	   
});
