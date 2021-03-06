var stompClient = null;
var notificationCount = 0;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
    });

    $("#send-private").click(function() {
        sendPrivateMessage();
    });
    //알림창 클릭하면 알림 0으로 reset
    $("#notifications").click(function() {
        resetNotificationCount();
    });
});

function connect() {
    // our-websocket에 연결
    var socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        //연결이 되면 알림창이 있는지 확인
        updateNotificationDisplay();
        // /topic/messages 구독 중 (메시지오면 응답?)
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });
        // /user/topic/private-messages 구독 중 (메시지오면 응답?)
        stompClient.subscribe('/user/topic/private-messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });
        //메시지가 오면 알림 수 1씩 증가하고 내용 추가하는 updateNotification 호출

        // /topic/global-notifications 구독 중 (메시지오면 응답?)
        stompClient.subscribe('/topic/global-notifications', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });
        // /user/topic/private-notifications 구독 중 (메시지오면 응답?)
        stompClient.subscribe('/user/topic/private-notifications', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });
    });
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}
// 메시지 보내는 url /ws/message
function sendMessage() {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': $("#message").val()}));
}
// 메시지 보내는 url /ws/private-message
function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/ws/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
}

function updateNotificationDisplay() {
    if (notificationCount == 0) {
        $('#notifications').hide();
    } else {
        $('#notifications').show();
        $('#notifications').text(notificationCount);
    }
}

function resetNotificationCount() {
    notificationCount = 0;
    updateNotificationDisplay();
}