# MyHelper

Help developers on the JetBrains platform to be more efficient and fast (mainly Java at present).

## Features:

### Convert Domain to SQL that create table

When using DDD for Java development, many Domain (including more fields) are generally created as needed first, when we focus on the repository layer, we often need to create SQL for domains and fields, which is painful because it takes double time.

If you want to complete this job more efficient, this plugin can help you.

"Copy Table SQL (ctrl shift C)" will appear in the right-click menu of editor and project View.

Minimize configuration，you just need to add the @table tag (tag value is not required) to your domain header comment, and it is recommended to configure @length tags for String Type Fields. MyHelper will infer all other relevant information intelligently.


**Type conversion inference:**

- Java Type -> MySQL Type
- String -> varchar, varchar(20) if @length tag is not specified
- BigDecimal -> decimal, decimal(20, 4) if @length tag is not specified
- int -> int
- Integer -> int
- long -> bigint
- Long -> bigint
- Boolean -> tinyint(1)
- enum -> tinyint(2)
- Date -> datetime
- LocalDate -> date
- LocalDateTime -> datetime


**Tags:**

- @table [table_name]
- @primaryKey [true] => Please configure @primaryKey tag when your primary key name is not called id.
- @unique [true] => Create a unique index based on the name field
- @index [true] => Will create an index based on the name field
- @length 50 => default 20, This will become the length for name filed, means varchar(50)
- @dbType varchar(100) => specify data type directly
- @defaultVal 'tom' => Indicates that the default value is Tom
- @nullAble [true] => Indicates that the name field can be null


**[Demo](https://github.com/iisimpler/MyHelper/blob/master/src/test/java/Demo.java)**