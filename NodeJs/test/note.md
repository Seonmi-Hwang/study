프로토콜://서버주소:포트번호/폴더*/파일명?쿼리*

- 모든 데이터 연산 구성
쓰기 Create     POST
읽기 Read       GET
수정 Update     PUT
삭제 Delete     DELETE
 
// 일반적인 언어의 경우
function calc(num1, num2, type) {
    if (type == '+') return num1 + num2;
    else if (type == '-') return num1 - num2;
    else if (type == '*') return num1 * num2;
    else if (type == '/') return num1 / num2;
}

// Javascript의 경우 - 함수를 매개변수로 사용
function calc(num1, num2, func) {
    retrun func(num1, num2);
}

function add(num1, num2) {
    return num1 + num2;
}

calc(1, 2, add);
