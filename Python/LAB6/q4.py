n1, n2 = input("숫자 두 개를 입력하세요 : ").split()

try :
    div = int(n1) / int(n2)
    print(div)
except :
    print("division by zero")

