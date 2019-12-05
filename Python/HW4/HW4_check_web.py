

import configuration

import urllib.request, urllib.parse, urllib.error

import sys

   

if __name__ == "__main__": 

        web_config = configuration.get_web_configuration(sys.argv[1])

        port_num = int( web_config['WEB.port'] ) #8080

        src_objs = ['BTC', 'ETH' ]

        dest_objs = ['USD', 'JPY', 'EUR'] 

        for src in src_objs: 

            for dest in dest_objs:

                try: 

                    fhand = urllib.request.urlopen('http://localhost:%d/%s/%s' % (port_num,src,dest)) 
                    for line in fhand:
                        print(line)

                except:

                    print("ERROR")


