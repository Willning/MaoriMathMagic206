#!/bin/bash

HVite -H ./HTK/MaoriNumbers/HMMs/hmm15/macros -H ./HTK/MaoriNumbers/HMMs/hmm15/hmmdefs -C ./HTK/MaoriNumbers/user/configLR -w ./HTK/MaoriNumbers/user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0 ./HTK/MaoriNumbers/user/dictionaryD ./HTK/MaoriNumbers/user/tiedList ./temp/foo.wav

more recout.mlf
