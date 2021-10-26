# EnrichData

Java project that contains static methods that can be called from Snowpark for enriching web logs to decode HTTP request URL, extract product name from request URL, get country and city info from client IP using [GeoLite](https://dev.maxmind.com/geoip/geolite2-free-geolocation-data?lang=en).


## Sample Web Logs

```
request,auth,ident,response,bytes,clientip,verb,httpversion,rawrequest,timestamp,is_product_page_view,product_name
/home,-,-,200,1671,79.133.215.123,GET,1.1,,Sat Jun 14 07:30:13 PDT 2014,0,
/department/apparel/category/featured shops/product/adidas Kids' RG III Mid Football Cleat,-,-,200,1175,162.235.161.200,GET,1.1,,Sat Jun 14 07:30:13 PDT 2014,1,adidas Kids' RG III Mid Football Cleat
/department/fitness,-,-,200,1435,39.244.91.133,GET,1.1,,Sat Jun 14 07:30:14 PDT 2014,0,
/department/fan shop/category/water sports/product/Pelican Sunstream 100 Kayak/add_to_cart,-,-,200,1932,150.47.54.136,GET,1.1,,Sat Jun 14 07:30:14 PDT 2014,1,Pelican Sunstream 100 Kayak
/view_cart,-,-,200,1401,217.89.36.129,GET,1.1,,Sat Jun 14 07:30:14 PDT 2014,0,
/department/footwear/category/cardio equipment,-,-,200,386,36.44.59.115,GET,1.1,,Sat Jun 14 07:30:15 PDT 2014,0,
/view_cart,-,-,200,1726,11.252.83.179,GET,1.1,,Sat Jun 14 07:30:15 PDT 2014,0,
/department/footwear/category/fitness accessories,-,-,200,2076,56.251.19.230,GET,1.1,,Sat Jun 14 07:30:15 PDT 2014,0,
/department/fan shop/category/fishing/product/Field & Stream Sportsman 16 Gun Fire Safe,-,-,200,1413,137.95.229.186,GET,1.1,,Sat Jun 14 07:30:16 PDT 2014,1,Field & Stream Sportsman 16 Gun Fire Safe
/department/fan shop/category/water sports/product/Pelican Sunstream 100 Kayak,-,-,200,396,100.98.159.99,GET,1.1,,Sat Jun 14 07:30:16 PDT 2014,1,Pelican Sunstream 100 Kayak
/home,-,-,200,1473,137.95.229.186,GET,1.1,,Sat Jun 14 07:30:16 PDT 2014,0,
/department/fan shop,-,-,200,771,86.214.1.70,GET,1.1,,Sat Jun 14 07:30:17 PDT 2014,0,
/support,-,-,200,1321,18.168.113.227,GET,1.1,,Sat Jun 14 07:30:17 PDT 2014,0,
/home,-,-,200,1238,133.20.136.170,GET,1.1,,Sat Jun 14 07:30:18 PDT 2014,0,
/home,-,-,200,1797,137.95.229.186,GET,1.1,,Sat Jun 14 07:30:18 PDT 2014,0,
/home,-,-,200,878,100.158.186.50,GET,1.1,,Sat Jun 14 07:30:18 PDT 2014,0,
/checkout,-,-,200,1551,36.44.59.115,GET,1.1,,Sat Jun 14 07:30:19 PDT 2014,0,
/department/outdoors/category/kids' golf clubs/product/Polar Loop Activity Tracker,-,-,200,1026,150.47.54.136,GET,1.1,,Sat Jun 14 07:30:19 PDT 2014,1,Polar Loop Activity Tracker
/department/fitness,-,-,200,2079,69.109.16.108,GET,1.1,,Sat Jun 14 07:30:19 PDT 2014,0,
/department/outdoors,-,-,200,2125,103.9.12.197,GET,1.1,,Sat Jun 14 07:30:20 PDT 2014,0,
/department/golf,-,-,200,760,64.246.220.203,GET,1.1,,Sat Jun 14 07:30:20 PDT 2014,0,
/home,-,-,200,1740,85.55.142.159,GET,1.1,,Sat Jun 14 07:30:20 PDT 2014,0,
/department/outdoors/category/golf shoes,-,-,200,741,150.47.54.136,GET,1.1,,Sat Jun 14 07:30:21 PDT 2014,0,
/department/footwear,-,-,200,604,192.77.121.142,GET,1.1,,Sat Jun 14 07:30:21 PDT 2014,0,
/department/fan shop/category/hunting & shooting,-,-,200,1872,133.20.136.170,GET,1.1,,Sat Jun 14 07:30:21 PDT 2014,0,
/department/fitness/category/baseball & softball/product/adidas Brazuca 2014 Official Match Ball,-,-,200,1777,113.143.229.102,GET,1.1,,Sat Jun 14 07:30:22 PDT 2014,1,adidas Brazuca 2014 Official Match Ball
/home,-,-,200,918,118.155.115.238,GET,1.1,,Sat Jun 14 07:30:22 PDT 2014,0,
/department/outdoors/category/golf apparel,-,-,200,1459,111.203.179.87,GET,1.1,,Sat Jun 14 07:30:23 PDT 2014,0,
/department/fitness/category/baseball & softball/product/adidas Kids' F5 Messi FG Soccer Cleat/add_to_cart,-,-,200,728,56.251.19.230,GET,1.1,,Sat Jun 14 07:30:23 PDT 2014,1,adidas Kids' F5 Messi FG Soccer Cleat
/department/apparel/category/featured shops/product/adidas Kids' RG III Mid Football Cleat,-,-,200,1901,36.44.59.115,GET,1.1,,Sat Jun 14 07:30:23 PDT 2014,1,adidas Kids' RG III Mid Football Cleat
/home,-,-,200,523,220.242.192.122,GET,1.1,,Sat Jun 14 07:30:24 PDT 2014,0,
/view_cart,-,-,503,512,79.133.215.123,GET,1.1,,Sat Jun 14 07:30:24 PDT 2014,0,
/department/fan shop/category/indoor/outdoor games/product/O'Brien Men's Neoprene Life Vest,-,-,200,2001,18.168.113.227,GET,1.1,,Sat Jun 14 07:30:24 PDT 2014,1,O'Brien Men's Neoprene Life Vest
/department/outdoors/category/kids' golf clubs/product/Mio ALPHA Heart Rate Monitor/Sport Watch,-,-,404,2084,18.168.113.227,GET,1.1,,Sat Jun 14 07:30:25 PDT 2014,1,Mio ALPHA Heart Rate Monitor/Sport Watch
/view_cart,-,-,200,2054,35.115.21.150,GET,1.1,,Sat Jun 14 07:30:25 PDT 2014,0,
/department/fan shop/category/water sports,-,-,200,2109,150.47.54.136,GET,1.1,,Sat Jun 14 07:30:25 PDT 2014,0,
/home,-,-,200,647,220.237.115.28,GET,1.1,,Sat Jun 14 07:30:26 PDT 2014,0,
/department/fan shop/category/water sports,-,-,200,1842,135.102.251.22,GET,1.1,,Sat Jun 14 07:30:26 PDT 2014,0,
/support,-,-,200,705,158.201.225.172,GET,1.1,,Sat Jun 14 07:30:26 PDT 2014,0,
/department/fan shop/category/fishing/product/Field & Stream Sportsman 16 Gun Fire Safe,-,-,200,297,175.139.143.20,GET,1.1,,Sat Jun 14 07:30:27 PDT 2014,1,Field & Stream Sportsman 16 Gun Fire Safe
/home,-,-,200,1495,220.242.192.122,GET,1.1,,Sat Jun 14 07:30:27 PDT 2014,0,
/department/outdoors/category/golf gloves,-,-,200,1687,137.95.229.186,GET,1.1,,Sat Jun 14 07:30:28 PDT 2014,0,
/home,-,-,200,1659,27.160.201.42,GET,1.1,,Sat Jun 14 07:30:28 PDT 2014,0,
/support,-,-,200,1937,138.168.77.138,GET,1.1,,Sat Jun 14 07:30:28 PDT 2014,0,
/department/fan shop,-,-,200,2001,203.236.44.20,GET,1.1,,Sat Jun 14 07:30:29 PDT 2014,0,
/home,-,-,200,1627,172.94.201.230,GET,1.1,,Sat Jun 14 07:30:29 PDT 2014,0,
/department/golf/category/girls' apparel/product/TYR Boys' Team Digi Jammer,-,-,200,1231,148.65.179.239,GET,1.1,,Sat Jun 14 07:30:29 PDT 2014,1,TYR Boys' Team Digi Jammer
/department/footwear/category/strength training,-,-,200,931,14.220.19.89,GET,1.1,,Sat Jun 14 07:30:30 PDT 2014,0,
/department/fan shop,-,-,200,1020,73.153.158.240,GET,1.1,,Sat Jun 14 07:30:30 PDT 2014,0,

```


## Usage

### Step 1

After cloning the project, run `mvn package`. If all goes well, this will create **EnrichData-1.0-SNAPSHOT-jar-with-dependencies.jar** in the target folder.


### Step 2

Use the [Snowflake CLI](https://docs.snowflake.com/en/user-guide/snowsql.html) to upload the jar once it is compiled locally. Follow the instructions here to install the [Snowflake CLI](https://docs.snowflake.com/en/user-guide/snowsql-install-config.html).


### Step 3

3.a Start a SnowSQL session at the command line by running `snowsql -a streamsets -u <YOUR_USER_NAME>`


3.b Set the database, schema, and warehouse by running the following command.

```
use database <YOUR_DATABASE>;
use schema <YOUR_SCHEMA>;
use warehouse <YOUR_WAREHOUSE>;
```

3.c Upload the jar to Snowflake by running the following command.


```
put file:///<full-path>/target/EnrichData-1.0-SNAPSHOT-jar-with-dependencies.jar
@~/<YOUR_SNOWFLAKE_STAGE_NAME>/
auto_compress = false
overwrite = true;
```

3.d Create the UDFs in Snowflake by running the following command.


```
create or replace function get_country_name(ip varchar)
returns varchar
language java
imports = ('@~/<YOUR_SNOWFLAKE_STAGE_NAME>/EnrichData-1.0-SNAPSHOT-jar-with-dependencies.jar')
handler = 'com.dash.enrichdata.EnrichData.getCountryName';
```

```
create or replace function get_city_name(ip varchar)
returns varchar
language java
imports = ('@~/<YOUR_SNOWFLAKE_STAGE_NAME>/EnrichData-1.0-SNAPSHOT-jar-with-dependencies.jar')
handler = 'com.dash.enrichdata.EnrichData.getCityName';
```

```
create or replace function get_decoded_url(ip varchar)
returns varchar
language java
imports = ('@~/<YOUR_SNOWFLAKE_STAGE_NAME>/EnrichData-1.0-SNAPSHOT-jar-with-dependencies.jar')
handler = 'com.dash.enrichdata.EnrichData.decodeURL';
```

```
create or replace function extract_product_name(ip varchar)
returns varchar
language java
imports = ('@~/<YOUR_SNOWFLAKE_STAGE_NAME>/EnrichData-1.0-SNAPSHOT-jar-with-dependencies.jar')
handler = 'com.dash.enrichdata.EnrichData.extractProductName';
```

### Step 4

Use the UDFs in your SQL queries in Snowflake. For example:

```sql
SELECT *, get_country_name(CLIENTIP) as COUNTRY_NAME, get_city_name(CLIENTIP) as CITY_NAME, get_decoded_url(REQUEST) as DECODED_URL, extract_product_name(REQUEST) as PRODUCT_NAME from WEB_LOGS;

```

