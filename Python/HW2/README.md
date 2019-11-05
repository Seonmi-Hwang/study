# PYTHON HW2
엑셀로 학점을 매기는 프로그램을 개발한다.  

제공되는 파일 'student.xlsx'은 아래와 같다.  
![image](https://user-images.githubusercontent.com/50273050/67959785-9801c180-fc3c-11e9-8b3c-d6639efc1bb7.png)  

total 점수는 midterm 30%, final 35%, hw 34%, attendance 1%로 구성된다.  
(단, attendance는 1점 만점)  

아래 코드를 통해 total은 계산되었다고 가정한다.  
```python
#!/usr/bin/python3
from openpyxl import *

wb = load_workbook(filename = 'student.xlsx')
sheet = wb['Sheet1']

i = 2
while sheet.cell(row = i, column = 1).value is not None:
    midterm = sheet.cell(row = i, column = 3).value
    final = sheet.cell(row = i, column = 4).value
    hw = sheet.cell(row = i, column = 5).value
    attendence = sheet.cell(row = i, column = 6).value
    total = midterm * 0.3 + final * 0.35 + hw * 0.34 + attendence
    sheet.cell(row = i, column = 7, value = "{}".format(total))
    i += 1
```

계산된 total 값을 토대로 학생들에게 학점을 부여한다.  
학점 비율은 아래의 규칙을 따른다.  
![image](https://user-images.githubusercontent.com/50273050/67960394-879e1680-fc3d-11e9-991d-22a9315fa096.png)  

아래는 코드의 일부분(학점을 구분짓는 코드)이다.  
```python
    if total < 40 :
        sheet.cell(row = idx, column = 8, value = "F")
    elif rank <= students * 0.7 :
        if rank <= students * 0.3 :
            if rank <= students * 0.15 :
                sheet.cell(row = idx, column = 8, value = "A+")
            else :
                sheet.cell(row = idx, column = 8, value = "A0")
        else :
            if rank <= students * 0.5 :
                sheet.cell(row = idx, column = 8, value = "B+")
            else :
                sheet.cell(row = idx, column = 8, value = "B0")
    else :
        if rank <= students * 0.85 :
            sheet.cell(row = idx, column = 8, value = "C+")
        else :
            sheet.cell(row = idx, column = 8, value = "C0")
```

동점자도 고려하여 생각해보자.  
아래는 코드의 일부분(동점자를 고려한 코드)이다.  
```python
    j = i + 1
    if j < len(scores) :
        while total == scores[j][1] :
            j += 1

        if (j - i) >= 2:
            rank = j
```
