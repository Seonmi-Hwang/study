#include <stdio.h> // 표준 라이브러리  
#include "my.h" // 비표준 라이브러리  

extern int two_time(int input);
extern int four_time(int input);

int main(void) {
	int a;

	printf("Start !!\n");

	a = (int)My_input;

	a = two_time(a);
	printf("Two Times : %d\n", a);

	a = four_time(a);
	printf("Four Times : %d\n", a);

	return 0;
}