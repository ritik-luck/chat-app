var socket = new SockJS('/chat'); // Connect to WebSocket endpoint
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log("Connected: " + frame);

    // Subscribe to /topic/messages to receive chat updates
    stompClient.subscribe('/topic/messages', function (response) {
        var message = response.body;
        document.getElementById("messages").innerHTML += "<p>" + message + "</p>";
    });
}, function (error) {
    console.error("WebSocket connection error:", error);
});

function sendMessage() {
    var message = document.getElementById("messageInput").value;

    // Ensure WebSocket is connected before sending message
    if (stompClient && stompClient.connected) {
        stompClient.send("/app/sendMessage", {}, message);
        document.getElementById("messageInput").value = "";
    } else {
        console.error("WebSocket is not connected yet.");
    }
}