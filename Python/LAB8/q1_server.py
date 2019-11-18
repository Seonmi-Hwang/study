from socket import *

BUFSIZE = 1024

s = socket(AF_INET, SOCK_STREAM)
s.bind(("", 9090))
s.listen(5)
print("Listen")
conn, addr = s.accept()
print("Accept")
data = "tmp"

while data != "exit":
    data = conn.recv(BUFSIZE).decode("UTF-8")
    if len(data) == 0 : break
    print(addr, ":", data)
    conn.send(data.encode("UTF-8"))
conn.close()    
        
