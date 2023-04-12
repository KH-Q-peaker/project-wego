
$(() => {
	
	$(".confirm").on({
        click:function (e) {
			
			$.ajax({
				type: "POST",
				url: "confirm",

				error: function () {
					console.log("err")
				},

				success: function () {
					console.log("good")

					
				}
			});

		}
	})




});

