// NodeClass를 선언한다. 여기서 NodeClass는 변수명이 아니라 class명 이므로 첫글자를 대문자로 한다. 
var	NodeClass =	require('./NodeClass'); // 다른 모듈(파일)에 있는 기능을 사용하기 위함 (import) // ./ => 현재클래스 아래

// new 연산자를 사용해서 NodeClass 클래스를 nodeClass 변수로 초기화한다.
var	nodeClass =	new	NodeClass();

// setMessage 함수로 값을 입력한다. 
nodeClass.setMessage('Good to See u!');

// 입력된 값을 출력한다. 
console.log(nodeClass.getMessage());
console.log(nodeClass.name);