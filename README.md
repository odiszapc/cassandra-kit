cassandra-kit
=============

For a long time I worked with Cassandra. I started with this repo to save your time, Cassandra brothers. I do it just because I like it.

####How many data in bytes are involved in streaming process
##### 1. For a specific column family

    # <CF_NAME> - name of your column family
    nodetool netstats | grep <CF_NAME> | sed --unbuffered -r 's/ - [0-9]+%//g' | sed --unbuffered -r 's/.+?[0-9]+\///g' | awk '{total = total + $1}END{print total}'
    
##### 2. Overall streaming estimate
nodetool netstats | grep % | sed --unbuffered -r 's/ - [0-9]+%//g' | sed --unbuffered -r 's/.+?[0-9]+\///g' | awk '{total = total + $1}END{print total}'
    
