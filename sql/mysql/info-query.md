
## 테이블 정보
~~~
SELECT *
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'schema_name'
~~~

## 칼럼 정보
~~~
SHOW FULL COLUMNS FROM table_name;
~~~

## 인덱스 정보
~~~
SHOW INDEX FROM table_name
~~~
