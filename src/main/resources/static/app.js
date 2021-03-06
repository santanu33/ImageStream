var stompClient = null;
var i = 1;
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

function connect() {
	 var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);

        stompClient.subscribe('/topic/greetings', function (greeting) {
        	
        		var jsonObj = JSON.parse(greeting.body);
        		console.log(jsonObj.isFirstImage);
	        	if(jsonObj.bytes!=null){
	        		
	        		if(jsonObj.isFirstImage == "1"){
	        			debugger;
	        			i = 1;
	        		}
	        		var para = document.createElement("p");
	        		var node = document.createTextNode(jsonObj.content);
	        		para.appendChild(node);
	        		document.body.appendChild(para);
	        		
	        		if(i==1){
	        			document.getElementById("thumbnail").src = "data:image/png;base64," + jsonObj.bytes;
	        		}
	        		document.getElementById("ItemPreview_"+i).src = "data:image/png;base64," + jsonObj.bytes;
	        		//var x = document.createElement("IMG");
	        		i = i+1;
	        		
	        		
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
    setInterval(sendName,500);
    $( "#connect" ).click(function() { 
    
    });
    $( "#clean" ).click(function() { 
    		stompClient.send("/app/clean");
    });
    $( "#move" ).click(function() { 
			stompClient.send("/app/move");
	});
    $( "#disconnect" ).click(function() { disconnect(); });
    
    $(".imgPreview").on('click', function (e) {
        e.preventDefault();
        console.log($(this).attr('src'));
        $( "#thumbnail" ).attr("src",$(this).attr('src'));
        
    });
    
});