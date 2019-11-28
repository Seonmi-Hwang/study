import configuration
import sqlite3
from socket import *

def get_connection(configProps):
    try:
        conn = sqlite3.connect(configProps['DB.db'])
        return conn
    finally:
        pass

def initdb(con):
    cursor = con.cursor()
    cursor.execute("DROP TABLE IF EXISTS USER")
    cursor.execute("CREATE TABLE USER (id CHAR(16) PRIMARY KEY, password CHAR(16), nickname CHAR(16)")
    cursor.close()

def finduser(uid, conn):
    return __finduser(uid, conn.cursor())

def __finduser(uid, cursor):
    cursor.execute("SELECT id, password, nickname FROM USER WHERE id = '%s'" % uid)
    userlist = cursor.fetchall()
    cursor.close()
    return userlist

def 

if __name__ == "__main__":
    db_c = configuration.get_db_configuration('config.props')
    c = configuration.get_web_configuration('config.props')
    PORT = int(c['web.port')
    BUFSIZE = int(c['web.bufsize'])
    initdb(db_conn)
    db_conn.commit()

    listen_sock = socket(AF_INET, SOCK_STREAM)
    listen_sock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    listen_sock.bind(('', PORT))
    listen_sock.listen(2)

    while 1:
        conn, addr = listen_sock.accept() # 커넥션을 기다림
        data = conn.recv(BUFSIZE).decode('utf-8')
        
        rslt = "Please Sign in or Sign up"
        msg = """HTTP/1.1 200 OK
        
        
            <html><body>%s</body></html>""" % rslt
        conn.sendall(msg.encode('utf-8')
        conn.close()
        
