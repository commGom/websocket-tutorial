# websocket-tutorial

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

## 3. 서비스 구현
- WSService
  - 메시지 보낸 것 보여주는 service
- WSController