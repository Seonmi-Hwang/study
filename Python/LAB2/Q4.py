for i in range(10) :
    for j in range(0, 10 - i - 1) :
        print(' ', end='')
    for j in range(0, i * 2 + 1) :
        print('*', end='')
    for j in range(0, 10 - i - 1) :
        print(' ', end='')
    print()        
