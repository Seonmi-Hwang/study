for i in range(10) :
    print(' ' * (9 - i), end='')
    for j in range(i + 1) :
        print('*', end='')
    print()
