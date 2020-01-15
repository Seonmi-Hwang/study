// http 통신을 위해 필요한 여러 기능들을 담고 있는 모듈 : http
var http = require('http'); // Node.js 설치 시 기본적으로 설치되는 모듈이기 때문에 ./ 안해줌.

// handler : 이벤트 핸들러 역할을 하는 콜백 함수
// 함수형 프로그래밍이라 함수 자체를 값처럼 매개변수로 주고 받을 수 있다!
// http.createServer().listen( , ) : 웹 서버 기능을 수행할 수 있는 서버를 만든 후(createServer) 동작시킴(listen).
http.createServer(function handler(req, res) { // handler : 동작해야할 기능을 작성.
    res.writeHead(200, {'Content-type': 'text/html'}); // 헤더 정보
    res.write('<h1>Server Test</h1>')
    res.end('Hello world!'); // 화면에 나오게 되는 부분
}).listen(1337, '127.0.0.1'); // listen하다가 입력이 들어오는 순간 createServer가 자동으로 호출하여 실행시켜줌. (시스템에서 실행. 콜백함수)

console.log('Server running at http://127.0.0.1:1337'); // http://localhost:1337