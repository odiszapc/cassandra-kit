cassandra-kit
=============

To save your time, Cassandra brothers

How many data in bytes are involved in streaming

    # <CF_NAME> - name of your column family
    nodetool netstats | grep <CF_NAME> | sed --unbuffered -r 's/ - [0-9]+%//g' | sed --unbuffered -r 's/.+?[0-9]+\///g' | awk '{total = total + $1}END{print total}'
    
