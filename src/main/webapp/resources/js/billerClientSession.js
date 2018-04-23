var IDLE_TIMEOUT =  30; // 30 min
var SESSION_EXPIRY_ALERT_MESSAGE =  25  //  25 min
var SESSION_IDLE_TIME_CHECKER = 1000 * 60 // 1 min
var _idleSecondsCounter = 0;
document.onclick = function() {
    _idleSecondsCounter = 0;
};
document.onmousemove = function() {
    _idleSecondsCounter = 0;
};
document.onkeypress = function() {
    _idleSecondsCounter = 0;
};
window.setInterval(CheckIdleTime, SESSION_IDLE_TIME_CHECKER);

function CheckIdleTime() {
    _idleSecondsCounter++;
    
    if (_idleSecondsCounter >= SESSION_EXPIRY_ALERT_MESSAGE && !$('#biller-session-expiry-alert').is(':visible')) {
        $('#biller-session-expiry-alert').modal('toggle');        
    }
    if (_idleSecondsCounter >= IDLE_TIMEOUT) {
    	location.href= 'logout.do';        
    }
}

$(document).ready(function(){	
	$('#billerSessionSubmit').click(function(){
		location.href= 'logout.do';
	});	
});