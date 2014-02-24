cassandra-kit
=============

I have been working with Cassandra for a long time. I started this repository to save your time, Cassandra brothers. This project will represent my Cassandra expirience. Snippets ands tools I'll push here helped me many times. All of them are from practice, not theory.

I do it just because I like it.

## Visual token calculator
Sources are located `./token-calculator-ui/`. Open `index.html` to start token calculation.

Features:
* Built on Twitter Bootstrap
* Unbalanced rings support

![](https://pbs.twimg.com/media/BhNd2IKCcAA4yyx.png:large)

## Useful BASH commands
####How many data in bytes are involved in streaming process
##### 1. For a specific column family

    # <CF_NAME> - name of your column family
    nodetool netstats | grep <CF_NAME> | sed --unbuffered -r 's/ - [0-9]+%//g' | sed --unbuffered -r 's/.+?[0-9]+\///g' | awk '{total = total + $1}END{print total}'
    
##### 2. Overall streaming estimate
     
    nodetool netstats | grep % | sed --unbuffered -r 's/ - [0-9]+%//g' | sed --unbuffered -r 's/.+?[0-9]+\///g' | awk '{total = total + $1}END{print total}'
    
##### 3. See the data transfer estimate in GB

    echo "$(bc -l <<< "scale=2; $(nodetool netstats | grep % | sed --unbuffered -r 's/ - [0-9]+%//g' | sed --unbuffered -r 's/.+?[0-9]+\///g' | awk '{total = total + $1}END{print total}') / 1024 / 1024 / 1024") GB"
    
