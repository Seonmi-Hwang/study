## NoSQL Midterm - 3. Text Indexing and Text Search SQL

### text database 구축 & 질의문 실행  
(1) 기사번호, 기사제목, 기사 요약문, 신문사, 날짜의 속성을 갖는 뉴스기사 10개를 MongoDB로 구축  
``` json
db.articleinfo.insert([
{
	_id : "a1",
	title : "TURKEY PHOTO SET PANDEMIC COVID19 CORONA?VIRUS",
	summary : "A morgue workers wearing personal protective equipment (PPE) waits for the start of the funeral ceremony of a person who died from COVID-19-related illness at the Cekmekoy morgue in Istanbul, Turkey, 16 May 2020 (issued 20 May 2020).",
	newspaper_office : "Yonhap News Agency",
	date : "2020/05/20" 
},
{	_id : "a2",
	title : "Korean drug firms in early stage of coronavirus vaccine development",
	summary : "SEOUL, May 20 (Yonhap) -- South Korean pharmaceutical firms are in the beginning stages of coronavirus vaccine development, lagging behind U.S. rivals, industry sources said Wednesday.",
	newspaper_office : "Yonhap News Agency",
	date : "2020/05/20" 
},
{	_id : "a3",
	title : "Beware of fake food, health advices related to COVID19",
	summary : "With 3.0 version of national lockdown because of COVID19, the consumption of social media content has surely gone all time high.",
	newspaper_office : "Entertainment Times",
	date : "2020/05/13" 
},
{	_id : "a4",
	title : "The new budget iPhone SE is the right phone for an uncertain time",
	summary : "In a normal year, the process of reviewing a new iPhone might start with a flashy press event followed some days later by a carefully-staged unboxing while a professional camera rolls on a perfectly composed reviewer.",
	newspaper_office : "CNN Business",
	date : "2020/04/25" 
},
{	_id : "a5",
	title : "Corona crisis: Connectivity, collaboration must for bridging digital divide",
	summary : "The world around us is facing one of its biggest crisis, which has altered the way we go about our daily lives. However, this crisis poses a bigger threat to developing and underdeveloped world, as the gap between hyper-digitized and under-connected countries continues to widen.",
	newspaper_office : "Geospatial World",
	date : "2020/05/15" 
},
{	_id : "a6",
	title : "An unwanted booster dose for vaccine hesitancy",
	summary : "In January this year, the World Health Organization (WHO) listed vaccine hesitancy as among the top 10 threats to global health this year; it is defined as a reluctance or refusal to vaccinate despite the availability of vaccines.",
	newspaper_office : "The Hindu",
	date : "2019/11/07" 
},
{	_id : "a7",
	title : "What's behind the Trumps' Covid quackery",
	summary : "President Donald Trump said at a White House roundtable event on Monday.",
	newspaper_office : "CNN Opinion",
	date : "2019/05/20" 
},
{	_id : "a8",
	title : "He joked about killing endangered species. Trump gave him a top environment job",
	summary : "Under the leadership of William Perry Pendley, the Bureau of Land Management is failing to fulfill its most basic duties of safeguarding America’s public lands, his critics say.",
	newspaper_office : "The Guardian",
	date : "2019/05/02" 
},
{	_id : "a9",
	title : "Comic book collection found during Drake Hotel demolition returned to owner",
	summary : "Steve Preston escaped the Christmas Day fire with only his cat and thought his treasured comic book collection was gone forever.",
	newspaper_office : "KARE",
	date : "2019/05/19" 
},
{	_id : "a10",
	title : "Corona virus: Why a vaccine is still far away",
	summary : "For Covid-19, there are over 100 vaccines being developed across the world now, some from scratch, some from existing molecules developed for other diseases.",
	newspaper_office : "The Indian Express",
	date : "2019/05/20" 
}
])
```

* 실행결과  
![image](https://user-images.githubusercontent.com/50273050/82806946-ca695980-9ec1-11ea-8cca-10e2c5271d0d.png)  
![image](https://user-images.githubusercontent.com/50273050/82807000-e7059180-9ec1-11ea-827c-8023686d2ff7.png)  
![image](https://user-images.githubusercontent.com/50273050/82807041-f7b60780-9ec1-11ea-8bfc-41b636a1279d.png)  
<hr>

(2) 기사제목과 기사 요약문에 text index 생성, 단 기사 제목에 가중치 3, 요약문에 가중치 1   
```json
db.articleinfo.createIndex(
	{ title : "text", summary : "text" },
	{weights : {title : 3, summary : 1}, name : "TextIndex"})
```

* 실행결과  
![image](https://user-images.githubusercontent.com/50273050/82806919-be7d9780-9ec1-11ea-9d16-b13698cbf5ba.png)  

 <hr>
 
(3) "corona" 혹은 "virus" 혹은 "covid19" 단어가 들어간 문서 검색  
```json
db.articleinfo.find({$text : {$search : "corona virus coivd19"}})
```

* 실행결과   
![image](https://user-images.githubusercontent.com/50273050/82807179-3fd52a00-9ec2-11ea-9d57-4deff4506579.png)  

<hr>

(4) "corona virus"가 들어간 문서 검색  
```json
db.articleinfo.find({$text : {$search : "\"corona virus\""}})
```

* 실행결과  
![image](https://user-images.githubusercontent.com/50273050/82807211-48c5fb80-9ec2-11ea-957a-d04c695a0622.png)  

<hr>

(5) "vaccine"이 들어간 문서를 찾되, 검색 결과를 매칭 점수가 높은 순위로 정렬하여 출력
```json
db.articleinfo.find({$text : {$search : "vaccine"}}, {score : {$meta : "textScore"}}).sort({score : {$meta : "textScore"}})
```

* 실행결과  
![image](https://user-images.githubusercontent.com/50273050/82807228-4fed0980-9ec2-11ea-874f-a4eb4468c333.png)  

<hr>
