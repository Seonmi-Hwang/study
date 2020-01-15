// class의 선언 - 낙타표기법으로 첫번째	글자를 대문자로	함수를 하나 선언한다. 
function Clazz(msg) {				
    // 변수를 객체의 멤버로 사용하기 위해 this 예약어를 사용해서 정의한다.				
    this.name = 'I am Class';				
    this.message = msg;    // this를 사용하지 않은 변수				
     
    message2 = "find me!";   //	멤버함수 선언				
    this.print = function(){				
        console.log(message2);		
    }; 
}

// 객체를 생성
var	myClazz	=	new	Clazz('good	to	see	u!'); 
console.log(myClazz.message); // this를	사용하지 않은 message2 변수는 외부에서 참조할 수 없다. 
console.log(myClazz.message2); // this로 선언된	함수를 통해 사용할 수 있다. 
myClazz.print();