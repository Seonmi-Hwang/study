address = input('웹 주소를 입력하세요 : ')

if address[-3::1] == '.kr' :
    print('한국 도메인입니다')
else :
    print('한국 도메인이 아닙니다')
