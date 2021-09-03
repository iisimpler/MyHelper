create database if not exists ${databaseName} default character set utf8mb4 collate utf8mb4_unicode_ci;

use ${databaseName};

<#list tableList as table>
create table ${table.tableName}
(
    <#list table.tableFiledList as filed>
    ${filed.name} ${filed.type} <#if filed.defaultVal??>default ${filed.defaultVal?no_esc}</#if> ${filed.nullAble ? then('', 'not null')} comment '${filed.comment}'<#sep>,
    </#list>

    <#if table.primaryKeySet??>
    ,primary key (<#list table.primaryKeySet as primaryKey>${primaryKey}<#sep>,</#list>)
    </#if>
    <#if table.uniqueKeySet??>
        <#list table.uniqueKeySet as uniqueKey>
    ,unique key uniq_${uniqueKey} (${uniqueKey})
        </#list>
    </#if>
    <#if table.indexSet??>
        <#list table.indexSet as index>
    ,key idx_${index} (${index})
        </#list>
    </#if>
) engine = ${table.engine} default charset = ${table.charset} collate ${table.collate}
comment ='${table.comment}';

</#list>