def max(*ints) :
    max_num = 0;
    for n in ints :
        if n > max_num :
            max_num = n
    return max_num

print(max(1, 4, 6))
print(max(10, 5, 87, 57, 38))
print(max(4, 3, 2, 1))
