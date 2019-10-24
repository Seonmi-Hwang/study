def a(listA) :
    listB =[]
    for n in range(len(listA)) :
        temp = listA[:]
        mult = 1
        sum = 0
        for i in temp :
            mult *= i
        mult = str(mult)
        for l in mult :
            sum += int(l)
        listB.append(sum)
    return listB

listA = [1, 2, 3]
print(a(listA))
