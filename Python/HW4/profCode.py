import sys
import json
import configuration
from socket import *
import sqlite3

def get_connection(configProps):
    try:
        conn = sqlite3.connect( configProps['DB.db'] )
        return conn
    finally:
        pass

def initdb(con):
    cursor = con.cursor() 
    cursor.execute("DROP TABLE IF EXISTS USER")
    cursor.execute("CREATE TABLE USER (id CHAR(16) PRIMARY KEY, password CHAR(16), nickname CHAR(16))" )
    cursor.close()

def finduser(uid,conn):
    return __finduser(uid,conn.cursor())


def __finduser(uid,cursor): 
    cursor.execute("SELECT id, password, nickname FROM USER WHERE id = '%s'" % uid) 
    userlist = cursor.fetchall() 
    return userlist 

def insertuser(uid, passwd, nickname,conn): 
    cursor = conn.cursor() 
    ulist = __finduser(uid,cursor) 
    if len(ulist)>0: 
        return False 
    cursor.execute("INSERT INTO USER VALUES (\'%s\', \'%s\', \'%s\')" % (uid, passwd, nickname)) 
    cursor.close()
    return True


def signup(val,conn): 
    if len(val) < 5: 
        return "Please enter your ID, password and nickname in url" 

    if insertuser(val[2], val[3], val[4],conn): 
        return "Thank you for signing up!" 
    else: 
        return "Duplicated ID" 
    
    
def signin(val,conn): 
    userlist = finduser(val[2],conn) 
    if len(userlist) < 1: 
        return "This ID does not exist" 

    record = list(userlist[0]) 
    if val[3] != record[1]: 
        return "The password is incorrect" 

    userjson = dict(); 
    userjson['id'] = record[0] 
    userjson['password'] = record[1] 
    userjson['nickname'] = record[2] 
    return json.JSONEncoder().encode(userjson) 

def parserequest(msg,conn): 
    msg = str.strip(msg) 
    if msg == '': return None; 
    msg = msg.split("\n")[0] 
    url = msg.split()[1] 
    print(url)
    val = url.split('/') 
    if len(val) < 4:
        return None; 
    
    if val[1] == 'signup': 
        return signup(val,conn) 
    elif val[1] == 'signin': 
        return signin(val,conn) 
    else:
        return None; 
   
if __name__ == "__main__": 
    filename = sys.argv[1]
    c = configuration.get_web_configuration(filename)
    db_c = configuration.get_db_configuration(filename)
    PORT = int(c['web.port'])
    BUFSIZE = int(c['web.bufsize'])

    db_conn = get_connection(db_c)
    initdb(db_conn)
    db_conn.commit()

    listen_sock = socket(AF_INET, SOCK_STREAM) 
    listen_sock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    listen_sock.bind(('', PORT)) 
    listen_sock.listen(1) 

    while 1: 
        conn, addr = listen_sock.accept() 
        data = conn.recv(BUFSIZE).decode("UTF-8") 

        rslt = parserequest(data,db_conn) 
        if (rslt == None): 
            rslt = "Please Sign in or Sign up"

        msg = """HTTP/1.1 200 OK 

            
            <html><body>%s</body></html>""" % rslt 
        conn.sendall(msg.encode("UTF-8")) 
        conn.close()
