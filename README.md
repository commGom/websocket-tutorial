# websocket-tutorial
[Youtube Video(4)](https://www.youtube.com/watch?v=XY5CUuE6VOk&list=PLXy8DQl3058PNFvxOgb5k52Det1DGLWBW&index=1&ab_channel=LiliumCode, "google link")

## 1. 환경설정 
- 라이브러리 의존성 주입
    - Spring Web
    - WebSocket
    - 추가적인 dependencies  
      ```
      // 추가적인 dependencies (manually input dependencies)
      implementation 'org.webjars:webjars-locator-core'
      implementation 'org.webjars:sockjs-client:1.0.2'
      implementation 'org.webjars:stomp-websocket:2.3.3'
      implementation 'org.webjars:bootstrap:3.3.7'
      implementation 'org.webjars:jquery:3.1.1-1'
      ```

- WebSocketConfig 환경설정 클래스
```java
    registry.addEndpoint("/our-websocket").withSockJS();
```
```
        implements WebSocketMessageBrokerConfigurer
        - websocket destination prefix 지정
        - application destination prefix 지정
        - registry.addEndpoint("/our-websocket").withSockJS();
```

- dto
    - Message
    - ResponseMessage
    
- controller
    - MessageController (MessageMapping, SendTo Annotation)
  
## 2. 화면 구현
- index.html
- scripts.js
### SockJS
```js
function connect() {
    var socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        updateNotificationDisplay();
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });

        stompClient.subscribe('/user/topic/private-messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });

        stompClient.subscribe('/topic/global-notifications', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });

        stompClient.subscribe('/user/topic/private-notifications', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });
    });
}
```

## 3. 서비스 구현 (1.메시지 보여주기, 2.특정 사용자에게 메시지 보내기, 3, 메시지 알림)
- WSService
  - 메시지 보낸 것 보여주는 service
- WSController

- 특정 사용자에게 메시지 보내기
  - 아이디 임의로 만들어 User 생성
  
```java
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String randomId = UUID.randomUUID().toString();
        LOG.info("User with ID '{}' opened the page",randomId);
        return new UserPrincipal(randomId);
    }
```

  - private message 입력해서 화면에 띄우기
  
```java
    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public ResponseMessage getPrivateMessage(final Message message,
                                             final Principal principal) throws InterruptedException {
        Thread.sleep(1000);

        return new ResponseMessage(HtmlUtils.htmlEscape(
                "Sending private Message to user "+principal.getName()+": "+message.getMessageContent()));
    }
```
  - 특정사용자의 id로 private message 보내기
```java
    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final String id, @RequestBody final Message message){
        wsService.notifyUser(id,message.getMessageContent());
    }
```

- 메시지 알림
  - NotificationService