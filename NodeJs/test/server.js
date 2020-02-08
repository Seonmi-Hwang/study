// Controller 역할 (test_res.html 은 View 역할)
var http = require('http');
var fs = require('fs'); // 파일 입출력을 위한 모듈
var url = require('url');
var querystring = require('querystring'); // 문자열이 쿼리형태로 되어 있는 것을 잘 잘라주기 위한 모듈

http.createServer(function (req, res) {
    // 실습 1 : File System 모듈의 사용
    // // HTML 파일을 읽어 응답 만들기
    // fs.readFile('./html/test_res.html', function(error, data) { // fs.readFile('./html/test_res.html', ); // (파일명, 함수)
    //     if (error) {
    //         console.log(error.message);
    //     } else {
    //         res.writeHead(200, {'Content-Type': 'text/html'});
    //         res.end(data);
    //     }
    // });

    // 실습 2 : 이미지 파일 사용
    // fs.readFile('./img/patrick.jpg', function(error, data) {
    //     if (error) {
    //         console.log(error.message);
    //     } else {
    //         res.writeHead(200, {'Content-Type': 'image/jpeg'}); // mime type of multimedia data
    //         res.end(data);
    //     }
    // });

    // 실습 3 : 페이지 이동
    // res.writeHead(302, {'Location': 'https://cs.dongduk.ac.kr'}); 
    // res.end();

    // 실습 4 : 다양한 페이지 접근
    // var pathName = url.parse(req.url).pathname;
    // if (pathName == '/') {
    //     fs.readFile('./html/index.html', function(error, data) {
    //         res.writeHead(200, {'Content-Type': 'text/html'});
    //         res.end(data);
    //     });
    // } else if (pathName == '/second') {
    //     fs.readFile('./html/second.html', function(error, data) {
    //         res.writeHead(200, {'Content-Type': 'text/html'});
    //         res.end(data);
    //     });
    // } else if (pathName == '/cs') {
    //     res.writeHead(302, {'Location': 'https://cs.dongduk.ac.kr'});
    //     res.end();
    // }

    // 실습 5 : Method(GET/POST) 속성 구분
    // var query = url.parse(req.url, true).query;
    // res.writeHead(200, {'Content-Type': 'text/html'});
    // res.end('<h1>' + JSON.stringify(query) + '</h1>');

    // if (req.method == 'GET') { // 처음에 그냥 페이지를 요청했을 경우
    //     fs.readFile('./html/login.html', function(error, data) {
    //         res.writeHead(200, {'Content-Type': 'text/html'});
    //         res.end(data);
    //     });
    // } else if (req.method == 'POST') { // login.html의 form에서 POST방식으로 전달
    //     req.on('data', function(data) { // on : ~할 때, 사건이 발생했을 때 (사건이름, 동작) ('data' : data가 들어오는 사건이 발생)
    //         res.writeHead(200, {'Content-Type': 'text/html'});
    //         res.end('<h1>' + data + '</h1>');

    //         var text = "";
    //         text += data;

    //         var parsedStr = querystring.parse(text, '&', '=');
    //         console.log(parsedStr.id);
    //         console.log(parsedStr.pwd);
    //     });
    // }

    // 실습 6 : 같은 문자열일 경우 학과 홈페이지로 이동 or 로그인 실패 띄우기
    if (req.method == 'GET') { // 처음에 그냥 페이지를 요청했을 경우
        fs.readFile('./html/login.html', function(error, data) {
            res.writeHead(200, {'Content-Type': 'text/html'});
            res.end(data);
        });
    } else if (req.method == 'POST') { // login.html의 form에서 POST방식으로 전달
        req.on('data', function(data) { // on : ~할 때, 사건이 발생했을 때 (사건이름, 동작) ('data' : data가 들어오는 사건이 발생)
            var text = "";
            text += data;

            var parsedStr = querystring.parse(text, '&', '=');
            console.log(parsedStr.id);
            console.log(parsedStr.pwd);

            if (parsedStr.id == parsedStr.pwd) {
                res.writeHead(302, {'Location': 'https://cs.dongduk.ac.kr'});
                res.end();
            } else {
                fs.readFile('./html/login_failed.html', function(error, data) {
                    res.writeHead(200, {'Content-Type': 'text/html; charset=utf-8'});
                    res.end(data);
                });
            }
        });
    }

}).listen(1234, '127.0.0.1');

console.log('Server running at http://127.0.0.1:1234');