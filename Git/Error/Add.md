# Git Add Error  
$ git add . 명령어 입력 시 발생하는 Error  

### **1. Whitespace Error**  
git add 뿐만 아니라 git 명령어 실행 시 발생 가능한 Error   

◼️ **Error message**  
**warning: LF will be replaced by CRLF in README.md.**  
**The file will have its original line endings in your working directory**  

이는 맥 또는 리눅스를 쓰는 개발자와 윈도우 쓰는 개발자가 Git으로 협업할 때 발생.  
유닉스 시스템에서는 한 줄의 끝이 LF(Line Feed)로 이루어지는 반면,  
윈도우에서는 줄 하나가 CR(Carriage Return)와 LF(Line Feed), 즉 CRLF로 이루어짐.  
따라서 어느 한 쪽을 선택할지 Git에게 혼란이 온 것.  

◼️ **Solution**  
Git에 있는 core.autocrlf 라는 기능(자동변환)을 켜줌  
* 윈도우 사용자의 경우  
**$ git config --global core.autocrlf true**  
* 맥 또는 리눅스 사용자의 경우  
**$ git config --global core.autocrlf true input**  

◼️ **Ref**  
https://blog.jaeyoon.io/2018/01/git-crlf.html  
