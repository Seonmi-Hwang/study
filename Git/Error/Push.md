# Git Push Error  
git push 명령어를 입력했을 때 발생하는 Error  

 **1. 새로운 github repository를 README 파일과 함께 생성했을 경우**
* Error message  
**! [rejected] master -> master (fetch first)**  
**error: failed to push some refs to 'git@github.com:Seonmi-Hwang/study.git'**  
hint: Updates were rejected because the remote contains work that you do  
hint: not have locally. This is usually caused by another repository pushing  
hint: to the same ref. You may want to first integrate the remote changes  
hint: (e.g., 'git pull ...') before pushing again.  
hint: See the 'Note about fast-forwards' in 'git push --help' for details.  

* Solution  
1️⃣ **$ git pull origin master**  
Github의 저장 내용을 불러와 합침  
git pull -> fetch & merge  <br>
2️⃣ **$ git push -f origin master**  
강제로(force) push하는 것이므로 주의    

* Ref  
https://waaan.tistory.com/13  
https://blog.jaeyoon.io/2018/01/git-crlf.html  
