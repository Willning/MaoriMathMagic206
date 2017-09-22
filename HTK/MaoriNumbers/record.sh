#! bin/bash

if [ -e ./temp/foo.wav ]
then
rm ./temp/foo.wav

fi

arecord -d 2 -r 2200 -c 1 -i -t wav -f s16_LE ./temp/foo.wav

