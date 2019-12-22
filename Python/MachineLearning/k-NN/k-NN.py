import numpy as np
import operator

def createDataSet():
    group = np.array([[1.0, 1.1], [1.0, 1.0], [0, 0], [0, 0.1]]) # 배열 생성
    labels = ['A', 'A', 'B', 'B'] # 각 데이터와 매칭되는 라벨 리스트
    return group, labels

def classify0(inX, dataSet, labels, k):
    # 거리 계산 (Euclidian distance)
    dataSetSize = dataSet.shape[0]
    diffMat = np.tile(inX, (dataSetSize, 1)) - dataSet
    sqDiffMat = diffMat ** 2 # 제곱
    sqDistances = sqDiffMat.sum(axis = 1) # 주어진 axis로 배열 요소들의 합계 반환
    distances = sqDistances ** 0.5 # 루트 씌우기
    sortedDistIndicies = distances.argsort() # 어떤 순서로 접근해야 정렬되는지 index list 반환
    classCount = {} # Dictionary 선언

    for i in range(k): # 가장 짧은 거리를 투표
        # labels[sortedDisIndicies[i]] 에서 i가 dataSetSize만큼 돌면 B B A A 가 들어감!
        voteIlabel = labels[sortedDistIndicies[i]] # label을 구분하여 넣어줌 (A or B) // 여기선 B B A 임 
        # Dictionary의 메소드 get(key값, 0) : key값이 있으면 key값을 가져오고, 없으면 0을 가져와라!
        classCount[voteIlabel] = classCount.get(voteIlabel, 0) + 1 # A와 B의 개수를 count하는 Dictionary
    sortedClassCount = sorted(classCount.items(), key= operator.itemgetter(1), reverse=True) # Dictionary를 개수로 정렬 (array반환)
    return sortedClassCount[0][0]

def autoNorm(dataSet):
    minVals = dataSet.min(0) # 집합에서의 최솟값. param 0은 column의 최솟값을 얻게 함(뭔소리야)
    maxVals = dataSet.max(0) # 집합에서의 최댓값
    ranges = maxVals - minVals # 범위
    normDataSet = np.zeros(np.shape(dataSet)) # 왜 있는지 모르겠지만 그냥 초기화인 듯?
    m = dataSet.shape[0]
    normDataSet = dataSet - np.tile(minVals, (m, 1)) # 행렬 크기 맞춰주기 (최솟값 빼기)
    normDataSet = normDataSet / np.tile(ranges, (m, 1)) # 구성요소 나누기 (range로 나누기)
    return normDataSet, ranges, minVals

group, labels = createDataSet()
print(classify0([0, 0], group, labels, 3))    

# print("group\n", group)
# print("labels = ", labels)
