#include <stdio.h> // ǥ�� ���̺귯��  
#include "my.h" // ��ǥ�� ���̺귯��  

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