fname = input("Enter file name : ")

f = open(fname, "rt")

sender = dict()

for line in f :
    if line.startswith('From: ') :
        dictKey = line.split()[1]
        if dictKey in sender :
            sender[dictKey] += 1
        else :
            sender[dictKey] = 1
print(sender)

f.close()    
