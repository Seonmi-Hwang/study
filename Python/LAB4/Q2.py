filename = input()

f = open(filename, "rt")

sum = 0
cnt = 0

while True :
    row = f.readline()
    if not row : break
    
    if 'X-DSPAM-Confidence' in row :
        sum += float(row[-7:-1])
        cnt += 1

print(sum / cnt)

f.close()
