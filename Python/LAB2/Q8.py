
def fib_opt(n, memo) :

    if n == 1 or n == 2 :

        return 1


    if memo[n] == 0 :

        memo[n] = fib_opt(n-1, memo) + fib_opt(n-2, memo)

    
    return memo[n]



n = 5

F = [0 for i in range(n + 1)]
print(fib_opt(n, F))



n = 10

F = [0 for i in range(n + 1)]

print(fib_opt(n, F))



n = 35

F = [0 for i in range(n + 1)]

print(fib_opt(n, F))
