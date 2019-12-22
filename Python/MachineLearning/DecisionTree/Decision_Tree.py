import numpy as np
import operator
from math import log 

def createDataSet():
    dataSet = [[1, 1, 'y'],
            [1, 1, 'y'],
            [1, 0, 'n'],
            [0, 1, 'n'],
            [0, 1, 'n']]
    labels = ['no surfaceing', 'flippers']
    return dataSet, labels

def calcShannonEnt(dataSet): # dataSet의 Entropy를 구하는 function
    numEntries = len(dataSet) # 데이터 집합에 있는 사례의 개수를 셈
    labelCounts = {} # 딕셔너리 생성
    for featVec in dataSet:
        # key는 마지막 column에 있는 값을 가리킴. (물고기/not물고기)
        currentLabel = featVec[-1]
        if currentLabel not in labelCounts.keys(): # 이전에 key가 생성되지 않았다면
            labelCounts[currentLabel] = 0 # 새롭게 생성
        labelCounts[currentLabel] += 1 # 각각 key에 대해 라벨이 얼마나 발생하는지 추적
    shannonEnt = 0.0
    for key in labelCounts: # 라벨의 확률을 계산
        prob = float(labelCounts[key] / numEntries)
        shannonEnt -= prob * log(prob, 2)
    return shannonEnt

def splitDataSet(dataSet, axis, value):
    retDataSet = [] # 분할 리스트 생성
    for featVec in dataSet: # 분할한 속성 잘라내기
        if featVec[axis] == value: # axis번 속성이 value와 같으면
            reducedFeatVec = featVec[:axis] # axis만 뺀 리스트 생성. axis 미만 부분
            reducedFeatVec.extend(featVec[axis + 1:]) # axis 초과 부분 추가
            retDataSet.append(reducedFeatVec) # 분할 리스트에 붙임
    return retDataSet

def chooseBestFeatureToSplit(dataSet): # 핵심 함수!!
    numFeatures = len(dataSet[0]) - 1
    baseEntropy = calcShannonEnt(dataSet)
    bestInfoGain = 0.0; bestFeature = -1
    for i in range(numFeatures):
        featList = [example[i] for example in dataSet] # 라벨의 중복이 없는 리스트 ex.1 1 0 1 1
        uniqueVals = set(featList) # 집합(중복허용X) (리스트에 있던 중복된 값들을 제거) ex. 1 0
        newEntropy = 0.0
        for value in uniqueVals: # 각각의 분할을 위해 엔트로피 계산
            subDataSet = splitDataSet(dataSet, i, value) # 
            prob = len(subDataSet) / float(len(dataSet))
            newEntropy += prob * calcShannonEnt(subDataSet)
        infoGain = baseEntropy - newEntropy # Information gain 계산
        if infoGain > bestInfoGain: # 가장 큰 Information gain 찾기
            bestInfoGain = infoGain
            beatFeature = i
    return beatFeature

def majorityCnt(classList):
    classCount = {}
    for vote in classList:
        if vote not in classCount.keys(): # key가 없으면
            classCount[vote] = 0 # 새로 생성
        classCount[vote] += 1
    sortedClassCount = sorted(classCount.items(),
            key = operator.itemgetter(1), reverse = True)
    return sortedClassCount[0][0]

def createTree(dataSet, labels):
    classList = [example[-1] for example in dataSet]
    if classList.count(classList[0]) == len(classList): # 모든 분류 항목이 같으면
        return classList[0] # 멈추고 그 분류 항목 표시를 반환
    if len(dataSet[0]) == 1: # 분류할 속성이 더 이상 없으면
        return majorityCnt(classList) # 가장 많은 수를 반환함
    bestFeat = chooseBestFeatureToSplit(dataSet)
    bestFeatLabel = labels[bestFeat]
    myTree = {bestFeatLabel:{}} # 유일한 값의 리스트를 구함
    del(labels[bestFeat])
    featValues = [example[bestFeat] for example in dataSet]
    uniqueVals = set(featValues)
    for value in uniqueVals:
        subLabels = labels[:]
        # 데이터 집합을 분할할 때마다 recursive call
        myTree[bestFeatLabel][value] = createTree(splitDataSet(dataSet, bestFeat, value), subLabels)
    return myTree        
        

myDat, labels = createDataSet()
print(createTree(myDat, labels))
#print(splitDataSet(myDat, 0, 1))
#print(splitDataSet(myDat, 0, 0))
#print(chooseBestFeatureToSplit(myDat))


