#!/bin/bash

# get filename
filename=$1
if [ -z $filename ]; then
  echo "Error: no filename given."
  exit 1
fi

# create backup
mysqldump -u serva --password=123 --opt serva > $filename

echo "Backup created in $filename."
