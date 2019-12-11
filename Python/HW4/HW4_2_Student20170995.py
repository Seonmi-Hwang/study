import sys
import json
import configuration
from socket import *
import sqlite3
import time

def get_connection(configProps):
    try:
        conn = sqlite3.connect( configProps['DB.db'] )
        return conn
    finally:
        pass

def getRows(list) :
    rows = ''
    for l in list :
        rows += l[0] + ' ' + str(l[1]) + '<br>'
    return rows    

def findPrice(cryptoCur, targetCur, conn):
    now = time.localtime()
    nowtime = str(now.tm_year) + '-' + str(now.tm_mon) + '-' + str(now.tm_mday)
    return __findPrice(cryptoCur, targetCur, nowtime, conn.cursor())

def __findPrice(cryptoCur, targetCur, nowtime, cursor):
    cursor.execute("SELECT CurrentTime, Price FROM PRICE_TBL WHERE CryptoCurrencyName = \'%s\' AND TargetCurrencyName = \'%s\' AND CurrentTime LIKE \'%s\'" % (cryptoCur, targetCur, nowtime + '%'))
    list = cursor.fetchall()
    cursor.close()
     
    return getRows(list)

def parserequest(msg,conn): 
    msg = str.strip(msg) 
    if msg == '': return None; 
    msg = msg.split("\n")[0] 
    url = msg.split()[1] 
    print(url)
    val = url.split('/') 
    
    if len(val) < 3:
        return None; 

    return findPrice(val[1], val[2], conn)
   
if __name__ == "__main__":
    filename = sys.argv[1]
    c = configuration.get_web_configuration(filename)
    db_c = configuration.get_db_configuration(filename)
    PORT = int(c['web.port'])
    BUFSIZE = int(c['web.bufsize'])

    db_conn = get_connection(db_c)

    listen_sock = socket(AF_INET, SOCK_STREAM) 
    listen_sock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    listen_sock.bind(('', PORT)) 
    listen_sock.listen(1) 

    while 1: 
        conn, addr = listen_sock.accept() 
        data = conn.recv(BUFSIZE).decode("UTF-8") 

        rslt = parserequest(data,db_conn) 
        if (rslt == None): 
            rslt = "Wrong request"

        msg = """HTTP/1.1 200 OK 

            
            <html><body>
            Price Table:<br>
            %s
            </body></html>""" % rslt 
        conn.sendall(msg.encode("UTF-8")) 
        conn.close()

