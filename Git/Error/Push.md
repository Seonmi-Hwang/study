# Git Push Error  
$ git push 명령어를 입력 시 발생하는 Error  

### **1. github repository에서 변경된 사항을 local repository에 반영하지 않고 push하는 경우**  
◼️ **Error message**  
&nbsp;&nbsp;&nbsp;&nbsp;**! [rejected] master -> master (fetch first)**  
&nbsp;&nbsp;&nbsp;&nbsp;**error: failed to push some refs to 'git@github.com:Seonmi-Hwang/study.git'**  
&nbsp;&nbsp;&nbsp;&nbsp;hint: Updates were rejected because the remote contains work that you do  
&nbsp;&nbsp;&nbsp;&nbsp;hint: not have locally. This is usually caused by another repository pushing  
&nbsp;&nbsp;&nbsp;&nbsp;hint: to the same ref. You may want to first integrate the remote changes  
&nbsp;&nbsp;&nbsp;&nbsp;hint: (e.g., 'git pull ...') before pushing again.  
&nbsp;&nbsp;&nbsp;&nbsp;hint: See the 'Note about fast-forwards' in 'git push --help' for details.  

새로운 github repository를 README 파일과 함께 생성했을 경우 발생.  

내가 발생한 에러는 repository 생성 시가 아니라  
Algorithm directory와 그 안에 README.md와 java파일을 같이 생성했고,  
동시에 push하려고 했을 때 발생했다.  

+) github repository에서 README.md와 같은 파일들을 직접 생성했을 경우,  
&nbsp;&nbsp;&nbsp;&nbsp; local repository에 이와 같은 내용이 update 되지 않았을 때 발생.  

◼️ **Solution**  
* 첫 번째 방법  
**$ git pull origin master**  
Github의 저장 내용을 불러와 합침  
pull은 fetch와 merge를 동시에 해줌  
* 두 번째 방법  
**$ git push -f origin master**  
강제로(force) push하는 것이므로 주의    

◼️ **Ref**  
https://waaan.tistory.com/13  
https://blog.jaeyoon.io/2018/01/git-crlf.html  
