import sys
from os import listdir
import numpy as np
import operator

def createDataSet(dirname):
    labels = []
    trainingFileList = listdir(dirname)
    m = len(trainingFileList)
    matrix = np.zeros((m, 1024)) # 반환된 vector를 담을 곳

    for i in range(m): # 파일의 개수(1934개)만큼 반복
        fileNameStr = trainingFileList[i]
        answer = int(fileNameStr.split('_')[0]) # 정답 저장 
        labels.append(answer)
        matrix[i, :] = getVector(dirname + '/' + fileNameStr)
    return matrix, labels 

def classify0(inX, dataSet, labels, k): # 거리 계산 (Euclidian distance)
    dataSetSize = dataSet.shape[0]
    diffMat = np.tile(inX, (dataSetSize, 1)) - dataSet
    sqDiffMat = diffMat ** 2 # 제곱
    sqDistances = sqDiffMat.sum(axis = 1) # 주어진 axis로 배열 요소들의 합계 반환
    distances = sqDistances ** 0.5 # 루트 씌우기
    sortedDistIndicies = distances.argsort() # 정렬가능한 index list 반환
    classCount = {} # Dictionary 선언

    for i in range(k): # 가장 짧은 거리를 투표
        voteIlabel = labels[sortedDistIndicies[i]] # label을 구분하여 넣어줌 (0 or 1 or 2 ..) 
        # Dictionary의 메소드 get(key값, 0) : key값이 있으면 key값을 가져오고, 없으면 0을 가져와라!
        classCount[voteIlabel] = classCount.get(voteIlabel, 0) + 1 # 값의 개수를 count하는 Dictionary
    sortedClassCount = sorted(classCount.items(), key= operator.itemgetter(1), reverse=True) # Dictionary를 개수로 정렬 (array반환)
    return sortedClassCount[0][0]

def getVector(filename): # txt file을 1행 1024열 vector(list)로 변환 
    vector = np.zeros((1, 1024)) # 1024 = 32 x 32
    with open(filename) as f:
        for i in range(32):
            line = f.readline()
            for j in range(32):
                vector[0, 32 * i + j] = int(line[j])
        return vector        

# main
trainingFileDirName = sys.argv[1]
testFileDirName = sys.argv[2]

testFileList = listdir(testFileDirName)
length = len(testFileList)

matrix, labels = createDataSet(trainingFileDirName)

for k in range(1, 20, 2): # 1부터 20까지의 홀수만
    count = 0 # 전체 데이터 개수
    errorCount = 0 # 에러가 발생한 데이터 개수
    
    for i in range(length): # 테스트 데이터 개수만큼 반복
        answer = int(testFileList[i].split('_')[0])
        testData = getVector(testFileDirName + '/' + testFileList[i])
        classifiedResult = classify0(testData, matrix, labels, k)
        
        count += 1
        if answer != classifiedResult :
            errorCount += 1
    
    #print(str(errorCount) + "/" + str(count))
    print(int(errorCount / count * 100))

