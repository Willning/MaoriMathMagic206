#! /bin/bash

if [ ! -d ./temp ]
then 
mkdir temp
fi

if [ -e ./temp/foo.wav ]
then
rm ./temp/foo.wav

fi

arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE ./temp/foo.wav

