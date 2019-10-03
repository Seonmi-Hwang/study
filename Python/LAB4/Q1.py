inputfile = input()

f1 = open(inputfile, "rt")
f2 = open("output.txt", "wt")

for line in f1 :
    f2.write(line.upper())

f1.close()
f2.close()
