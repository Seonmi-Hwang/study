OBJECT = test1.o test2.o test3.o  
CC = gcc  

all : $(OBJECT)  
&nbsp&nbsp&nbsp&nbsp gcc $(OBJECT) -o all  
test1.o : test1.c my.h  
&nbsp&nbsp&nbsp&nbsp gcc -c test1.c -Ddebug  
test2.o : test2.c my.h  
&nbsp&nbsp&nbsp&nbsp gcc -c test2.c  
test3.o : test3.c my.h  
&nbsp&nbsp&nbsp&nbsp gcc -c test3.c  
clean :   
&nbsp&nbsp&nbsp&nbsp rm *.o  
