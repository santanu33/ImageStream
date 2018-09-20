var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}
var i = 0;
function connect() {
	 var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);

        stompClient.subscribe('/topic/greetings', function (greeting) {
	        	if(JSON.parse(greeting.body).bytes!=null){
	        		//debugger;
	        		//document.getElementById("ItemPreview_0").src = "data:image/png;base64" + JSON.parse(greeting.body).bytes;
	        		//document.getElementById("ItemPreview_"+i).src = "/Users/santanu/Downloads/katy/takia_3 copy.jpeg"
	        		//i = i+1;
	        		
	        		
	        		document.getElementById("ItemPreview_"+i).src = "data:image/png;base64," + JSON.parse(greeting.body).bytes;
	        		var x = document.createElement("IMG");
	        		i = i+1;
	        		x.id = "ItemPreview_"+i;
	        		x.style.height = '50px';
	        	    x.style.width = '50px';
	        		document.body.appendChild(x);
	        		
	        	}
        })
        
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
	//debugger;
    stompClient.send("/app/hello");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
	connect();
    setInterval(sendName,4000);
    $( "#connect" ).click(function() { 
    
    });
    $( "#disconnect" ).click(function() { disconnect(); });
    
    $(".imgPreview").on('click', function (e) {
        e.preventDefault();
        console.log($(this).attr('src'));
        $( "#thumbnail" ).attr("src",$(this).attr('src'));
        
    });
    
});