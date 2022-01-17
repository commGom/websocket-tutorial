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

