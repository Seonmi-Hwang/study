var http = require('http');
http.createServer(function handler(req, res) {
    res.writeHead(200, {'Content-type': 'text/plain'});
    res.end('Hello world!\n'); // 화면에 나오게 되는 부분
}).listen(1337, '127.0.0.1');
console.log('Server running at http://127.0.0.1:1337'); // http://localhost:1337
