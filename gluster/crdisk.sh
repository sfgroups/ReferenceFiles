#!/bin/bash

export FILE=/home/disk1.dat
[ -f $FILE ] ||fallocate -l 10G $FILE
if ! $( file /home/disk1.dat 2>&1 |grep -q ext4) ; then
	yes |mkfs.ext4  $FILE
fi
export NUM=1
export LOOPDEV="/dev/loop${NUM}"
losetup ${LOOPDEV}   $FILE
lsblk

#losetup -d /dev/loop0

