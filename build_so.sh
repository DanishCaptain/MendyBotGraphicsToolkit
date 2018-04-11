#!/bin/sh

jniResult=libMBFrameBuffer.so
jniJdkHeader=$JAVA_HOME/include
jniSysHeader=$JAVA_HOME/include/linux

javah -d src/main/c -classpath bin org.mendybot.gtk.FrameBuffer
gcc -Wall -O2 -fPIC -o "$jniResult" -shared -I "$jniJdkHeader"  -I "$jniSysHeader" -I src/main/c src/main/c/FrameBuffer.c
