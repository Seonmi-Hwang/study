import sys

inputfile = sys.argv[1]
outputfile = sys.argv[2]

genre = dict()
with open(inputfile, "rt") as f :
    for line in f :
        genres = line.strip('\n').split('::')
        list = genres[2].split('|')

        for l in list :
            if l not in genre :
                genre[l] = 1
            else :
                genre[l] += 1
                        
with open(outputfile, "wt") as f :
    for k in genre.keys():
        f.write(k + " " + str(genre[k]) + "\n")

