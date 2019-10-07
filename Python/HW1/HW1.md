```python
first = ['egg', 'salad', 'bread', 'soup', 'canafe']
second = ['fish', 'lamb', 'pork', 'beef', 'chicken']
third = ['apple', 'banana', 'orange', 'grape', 'mango']

order = [first, second, third]
john = [order[0][:-2], second[1::3], third[0]]
del john[2]
john.extend([order[2][0:1]])
print(john)
```
~
~
~
