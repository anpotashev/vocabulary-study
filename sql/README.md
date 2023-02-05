**SQL to create db in Postgresql**
```sql
create database vocabulary;
create user vocabulary password 'vocabulary';
grant all on database vocabulary to vocabulary;
```

**Shell script to create new SQL**
```shell
./create_sql.sh ddl create_new_table_my "New table my"
```
