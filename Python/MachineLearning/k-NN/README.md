## k-Nearest Neighbors Algorithm  
머신러닝의 지도 학습(Supervised Learning) 방법 중 **분류, 클래스 판정을 산출하는 알고리즘** 중 하나이다.  

#### 📘 알고리즘에 대한 간단한 설명    
기존에 훈련 집합이었던 예제 데이터 집합이 존재   
모든 데이터는 label이 붙어 있어서 각각의 데이터가 어떤 label로 구분되는지 알 수 있음  

이후 label이 붙어있지 않은 새 데이터가 주어졌을 때 기존의 모든 데이터와 새 데이터 비교  
가장 유사한 데이터(가장 근접한 이웃)의 label을 살펴봄  
&nbsp;&nbsp;&nbsp;&nbsp;\-> 이미 알고 있는 데이터 집합에서 **상위 k개의 가장 유사한 데이터**를 살펴봄 
k개의 가장 유사한 데이터들 중 **다수결**을 통해 새 데이터의 label을 결정

❓ **지도 학습?**  
&nbsp;&nbsp;&nbsp;&nbsp; 문제의 답이 데이터에 주어져 있는 상태에서 그 데이터를 가지고 일반적인 판정 규칙을 만들어 내는 수법  

#### 📘 분류하는 방법
```python
def classify0(inX, dataSet, labels, k): # 거리 계산 (Euclidian distance)
    dataSetSize = dataSet.shape[0]
    diffMat = np.tile(inX, (dataSetSize, 1)) - dataSet
    sqDiffMat = diffMat ** 2 # 제곱
    sqDistances = sqDiffMat.sum(axis = 1) # 주어진 axis로 배열 요소들의 합계 반환
    distances = sqDistances ** 0.5 # 루트 씌우기
    sortedDistIndicies = distances.argsort() # 정렬가능한 index list 반환
    classCount = {} # Dictionary 선언

    for i in range(k): # 가장 짧은 거리를 투표
        voteIlabel = labels[sortedDistIndicies[i]] # label을 구분하여 넣어줌
        # Dictionary의 메소드 get(key값, 0) : key값이 있으면 key값을 가져오고, 없으면 0을 가져와라!
        classCount[voteIlabel] = classCount.get(voteIlabel, 0) + 1 # 값의 개수를 count하는 Dictionary
    sortedClassCount = sorted(classCount.items(), key= operator.itemgetter(1), reverse=True) # Dictionary를 개수로 정렬 (array반환)
    return sortedClassCount[0][0]
```


#### 📘 장점  
간단하며 데이터를 분류하는데 효과적  

#### 📘 단점  
데이터 구조에 대한 어떠한 정보도 주지 못함 (평균 or 각 클래스가 어떻게 생겼는지)  
데이터 집합 전체를 다루므로 큰 데이터 집합을 처리하기 위해 큰 저장소가 필요  
데이터 집합 내의 모든 데이터에 대해 거리를 측정해야 함  
&nbsp;&nbsp;&nbsp;&nbsp; => 데이터 크기가 커지면 사용하기 힘듦

## Cursive Recognition System  
k-NN을 통한 필기체 인식 시스템    
예제 데이터가 주어지며, 주어진 데이터를 통해 학습하여 결과를 예측하고 돌려준다.    

실행 시 아래와 같이 실행한다.  
``` 
python3 <실행파일 이름> <트레이닝할 폴더 이름> <테스트할 폴더 이름>  
```
출력은 k가 1~20까지의 홀수일 경우마다의 에러율을 세로로 출력한다.   

데이터는 txt 파일로 주어지므로 txt 파일을 vector로 변환해야 함.  

📘 Vector 변환 방법  
```python
def getVector(filename): # txt file을 1행 1024열 vector(list)로 변환 
    vector = np.zeros((1, 1024)) # 1024 = 32 x 32
    with open(filename) as f:
        for i in range(32):
            line = f.readline()
            for j in range(32):
                vector[0, 32 * i + j] = int(line[j])
        return vector       
```

📘 Data 학습 방법  
```python
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
```

📘 에러율 계산하여 출력 (main)  
```python
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
```

📘 REF  
https://codeapp.tistory.com/6
