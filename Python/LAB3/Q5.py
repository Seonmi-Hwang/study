s = '901231-1914983'

year = s[:2]
idx = s.find('-')
gender = s[idx + 1 : idx + 2]

if gender == '1' :
    print('%s년생 남자' % year)
else :
    print('%s년생 여자' % year)
