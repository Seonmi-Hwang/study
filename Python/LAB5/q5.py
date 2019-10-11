import json

with open('movies.json') as datafile :
    jsondata = json.load(datafile)

sales = list(jsondata['boxOfficeResult']['dailyBoxOfficeList'])

total = 0
for sale in sales :
    total += int(sale['salesAmt'])
print("오늘 매출액은 총 ", total, "원")
