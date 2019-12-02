import numpy as np
import operator

def createDataSet():
    filename = 'datingTestSet.txt'
    with open(filename) as f:
        numberOfLines = len(f.readlines())
    group = np.zeros((numberOfLines, 3))
    labels = []
    index = 0
    with open(filename) as f:
        for line in f.readlines() :
            list = line.strip()
            listFromLine = line.split()
            group[index][0] = float(listFromLine[0])
            group[index][1] = float(listFromLine[1])
            group[index][2] = float(listFromLine[2])
            labels.append(listFromLine[-1])
            index += 1
    return group, labels

def autoNorm(dataSet):
    minVals = dataSet.min(0)
    maxVals = dataSet.max(0)
    ranges = maxVals - minVals
    normDataSet = np.zeros(np.shape(dataSet))
    m = dataSet.shape[0]
    normDataSet = dataSet - np.tile(minVals, (m, 1))
    normDataSet = normDataSet / np.tile(ranges, (m, 1))
    return normDataSet, ranges, minVals

def classify0(inX, dataSet, labels, k):
    dataSetSize = dataSet.shape[0]
    diffMat = np.tile(inX, (dataSetSize, 1)) - dataSet
    sqDiffMat = diffMat ** 2
    sqDistances = sqDiffMat.sum(axis = 1)
    distances = sqDistances ** 0.5
    sortedDistIndicies = distances.argsort()
    classCount = {}
    for i in range(k):
        votellabel = labels[sortedDistIndicies[i]]
        classCount[votellabel] = classCount.get(votellabel, 0) + 1

    sortedClassCount = sorted(classCount.items(), key = operator.itemgetter(1), reverse = True)
    return sortedClassCount[0][0]

group, labels = createDataSet()
normDataSet, ranges, minVals = autoNorm(group)
print(40900, 8.3, 0.9)
print(classify0(([40900, 8.3, 0.9] - minVals) / ranges, normDataSet, labels, 9))
print(5570, 4.9, 0.7)
print(classify0(([5570, 4.9, 0.7] - minVals) / ranges, normDataSet, labels, 9))
print(68086, 11.4, 0.8)
print(classify0(([68086, 11.4, 0.8] - minVals) / ranges, normDataSet, labels, 9))
