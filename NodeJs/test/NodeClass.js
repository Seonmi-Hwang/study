function Clazz(){				
    this.name = 'Hello there!';
    this.message; 
}

//	message	변수에	값을	입력하는	함수 
Clazz.prototype.setMessage = function(msg){				
    this.message = msg; 
} 

//	message	변수의	값을	가져오는	함수 
Clazz.prototype.getMessage = function(){				
    return this.message; 
}

//	exports	명령어를 사용함으로써 다른파일에서 require 예약어를 이용해 Clazz 객체를 사용할 수 있게된다. 
//	exports	명령어의 위치는	파일의	어떤곳에 위치해도 동일하게 동작한다. 
module.exports = Clazz; // 다른파일에서 new 해서 객체를 만들게될 때 Clazz가 만들어지도록 연결되어 있음.