cassandra-kit
=============

To save your time, Cassandra brothers

How many bytes are streamed

    nodetool netstats | grep <CF_NAME> | sed --unbuffered -r 's/ - [0-9]+%//g' | sed --unbuffered -r 's/.+?[0-9]+\///g' | awk '{total = total + $1}END{print total}'
