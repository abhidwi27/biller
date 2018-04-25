var SERVER_POLL_TIME = 1000 * 60 * 25 // 25 minutes 

function keepAlive(){
	$.ajax({
		url: 'keepAlive.do',
		type: 'GET',
		data: false,
		success: function(result){
			// do nothing, this is just a dummy call to keep session alive.
			// calling this would always return 404 as request would never keepAlive method
			// however, this would accomplish our goal to poll server.
		}		
	});
}


window.setInterval(keepAlive, SERVER_POLL_TIME);