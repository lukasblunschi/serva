#!/bin/bash

# get filename
filename=$1
if [ -z $filename ]; then
  echo "Error: no filename given."
  exit 1
fi

# restore database
mysql -u serva --password=123 serva < $filename

echo "Database from $filename restored."
