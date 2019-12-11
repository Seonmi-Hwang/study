import sys
import json
import configuration
import requests
import urllib
import sqlite3
import time

def get_connection(configProps):
    try:
        conn = sqlite3.connect(configProps['DB.db'])
        return conn
    finally:
        pass

def initdb(con):
    cursor = con.cursor()
    cursor.execute("DROP TABLE IF EXISTS PRICE_TBL")
    cursor.execute("CREATE TABLE PRICE_TBL (CurrentTime VARCHAR(20) not null, CryptoCurrencyName VARCHAR(10) not null, TargetCurrencyName VARCHAR(10) not null, Price decimal(20,5) not null)")
    cursor.close()

def insertprice(curtime, cryptoCur, targetCur, price, conn):
    cursor = conn.cursor()
    cursor.execute("INSERT INTO PRICE_TBL VALUES (\'%s\', \'%s\', \'%s\', '%s')" % (curtime, cryptoCur, targetCur, price))
    cursor.close()

def parserequest(data, cryptoCur, now, conn):
    msg = json.loads(data)
    print(msg)
    
    insertprice(now, cryptoCur, 'USD', msg['USD'], conn)
    insertprice(now, cryptoCur, 'JPY', msg['JPY'], conn)
    insertprice(now, cryptoCur, 'EUR', msg['EUR'], conn)
    insertprice(now, cryptoCur, 'KRW', msg['KRW'], conn)
    conn.commit()

def selectfunc(cryptoCur, targetCur, conn):
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM PRICE_TBL")
    list = cursor.fetchall()
    return list


if __name__ == "__main__":
    filename = sys.argv[1]
    db_c = configuration.get_db_configuration(filename)
    btcUrl = 'https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,JPY,EUR,KRW'
    ethUrl = 'https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD,JPY,EUR,KRW'
    
    db_conn = get_connection(db_c)
    initdb(db_conn)
    db_conn.commit()

    while 1 :
        btcWebpage = urllib.request.urlopen(btcUrl).read().decode('utf-8')
        ethWebpage = urllib.request.urlopen(ethUrl).read().decode('utf-8')
        
        now = time.localtime()
        nowtime = str(now.tm_year) + '-' + str(now.tm_mon) + '-' + str(now.tm_mday) + ' ' + str(now.tm_hour) + ':' + str(now.tm_min) + ':' + str(now.tm_sec)  

        parserequest(btcWebpage,'BTC', nowtime, db_conn)
        parserequest(ethWebpage,'ETH', nowtime, db_conn)

        print(selectfunc('BTC', 'USD', db_conn))
        time.sleep(600) # 제출 시 600으로 변경

