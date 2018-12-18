<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <#assign address = "上海">
    <#-- 注释部分 -->
    <#-- 下面使用插值 -->
    <#assign foo = true/>
    ${foo?string("yes","no")}

    This is home!<br>
    我家住在 ${address} <br>

    <#--遍历数组，每个数组元素的下标用 ?index 访问，计数用 ?counter 访问-->
    <#list colors as color>
        this color is ${color} , index is ${color?index}, count is ${color?counter} <br>
    </#list>

    <#--遍历 map-->
    <#assign keys = maps?keys>
    <#list keys as key>
        ${key} = ${maps[key]} <br>
    </#list>

    ${value1}<br>

    <#--可以直接访问传入对象的属性-->
    ${user.getName()}<br>
    ${user.description}<BR>
    ${user.getDescription()}<br>
    ${user.getClass()}<br>
    ${user.setName("HELLO")}<BR>
    ${user.getName()}<br>
    ${user.class}<br>

    <#--FreeMarker 定义参数的方法-->
    <#assign title = "nowcode" >
    Title : ${title}<br>

    <#--导入其他 .ftl 文件，parse = false 表示如果导入文件中有 FreeMarker 语法，不进行解析，默认为 true-->
    <#include "header.ftl" parse = false>
</body>
</html>