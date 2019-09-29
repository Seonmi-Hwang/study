scores = input('5개의 성적을 입력하세요(각 값은 공백으로 분리): ').split()

scorel = [int(score) for score in scores]

scorel.sort()
print(scorel)
