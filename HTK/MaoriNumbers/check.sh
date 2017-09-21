#!/bin/bash

HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0 user/dictionaryD user/tiedList foo.wav

more recout.mlf
