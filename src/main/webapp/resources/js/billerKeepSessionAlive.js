var SERVER_POLL_TIME = 1000 * 60 * 25 // 25 minutes 

function keepAlive(){
	$.ajax({
		url: 'keepAlive.do',
		type: 'GET',
		data: false,
		success: function(result){
			// do nothing, result will be void. This is just a dummy call to keep session alive.
			// however, this would accomplish our goal to poll server.
		},
		error: function(result){
 			location.href = 'error.do';
 		}
	});
}


window.setInterval(keepAlive, SERVER_POLL_TIME);