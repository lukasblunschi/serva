#!/bin/bash

# create drop tables SQL script (in order of dependency)
echo "\
drop table if exists payments;\
drop table if exists bookings;\
drop table if exists service;\
drop table if exists domains;\
drop table if exists users;\
" > /tmp/drop-all-tables.sql

# execute SQL script
mysql -u serva --password=123 serva < /tmp/drop-all-tables.sql

# rm SQL script
rm /tmp/drop-all-tables.sql

echo "All tables dropped."
