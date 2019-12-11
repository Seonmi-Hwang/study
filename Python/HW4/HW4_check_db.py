import configuration

import sqlite3

import sys


def get_connection(configProps):

    try:

        conn = sqlite3.connect( configProps['DB.db'] )

        return conn

    finally:

        pass


def print_all_price_data(conn): 

    cursor = conn.cursor() 

    cursor.execute("SELECT CurrentTime, CryptoCurrencyName, TargetCurrencyName, Price FROM price_tbl")

    price_list = cursor.fetchall() 

    cursor.close()

    for price_obj in price_list:

        print(price_obj)


if __name__ == "__main__": 

    db_c = configuration.get_db_configuration(sys.argv[1])

    db_conn = get_connection(db_c)

    print_all_price_data(db_conn)

