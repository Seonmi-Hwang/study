function	Clazz(msg){
    this.name =	'I	am	Class';
    this.message =	msg;
    message2 = "find	me!"; 
} 

// Clazz 객체의 prototype	을	가져와서 message값을 리턴하는 함수를 하나 추가한다. 
Clazz.prototype.getMessage = function(){				
    return this.message; 
}
Clazz.prototype.getMessage2 = function(){				
    return this.message2; 
}

//	객체를	생성 
var	myClazz	= new Clazz('good to see u!'); 
console.log(myClazz.getMessage()); 
// 내부에 선언된 함수와는 다르게 prototype으로 선언한 함수는	값을 사용할	수 없다. 
console.log(myClazz.getMessage2());