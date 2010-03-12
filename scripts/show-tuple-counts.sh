#!/bin/bash

# create count SQL script (in lexicographical order)
echo "\
select 'bookings',count(*)          from bookings;\
select 'domains',count(*)           from domains;\
select 'payments',count(*)          from payments;\
select 'services',count(*)          from services;\
select 'users',count(*)             from users;\
" > /tmp/show-tuple-counts.sql

# execute SQL script
mysql -u serva --password=123 serva < /tmp/show-tuple-counts.sql | grep -v count

# rm SQL script
rm /tmp/show-tuple-counts.sql
