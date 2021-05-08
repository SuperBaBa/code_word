<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>图书列表</title>
</head>
<style>
    html {
        font-size: 14px;
        font-weight: 400;
    }

    .exp {
        font-size: 12px;
        color: lightgray;
    }
</style>

<body>
welcome ${name} to freemarker!
<h1>Hello boy</h1><br>
<p>当前时间：${.now?string("yyyy-MM-dd HH:mm:ss.sss")}</p>
<table border="1">
    <tr>
        <td>books no</td>
        <td>books name</td>
        <td>books author</td>
    </tr>
            <tr>
                <td>${resource.name}</td>
                <td>${resource.website}</td>
                <td>${resource.language}</td>
            </tr>
</table>
<dl>
    <dt>字符串</dt>
    <dd>普通字符串：<span class="exp">${name}</span></dd>
    <dd>非html编码：<span class="exp">${htmlText}</span></dd>
    <dd>html编码：<span class="exp">${htmlText?html}</span></dd>
    <dd>首字母大写：<span class="exp">${name?cap_first}</span></dd>
    <dd>首字母小写：<span class="exp">${name?uncap_first}</span></dd>
    <dd>全小写：<span class="exp">${name?lower_case}</span></dd>
    <dd>全大写：<span class="exp">${name?upper_case}</span></dd>
    <dd>去除首位空格：<span class="exp">${name?trim}</span></dd>
    <dd>空字符串：<span class="exp">${null?if_exists}</span></dd>
    <dd>是否包含某个字符串：<span class="exp">${name?contains("wWw")?string}</span></dd>
    <dd>默认值：<span class="exp">${null?default("空值默认")}</span></dd>
    <dd>“${name}”字符串长度：<span class="exp">${name?length}</span></dd>
    <dd>定义字符串：<span class="exp">str=码一码<#assign str="码一码"/></span></dd>
    <dd>字符串拼接(1)：<span class="exp">${"字符串拼接 + " + str}</span></dd>
    <dd>字符串拼接(2)：<span class="exp">${"字符串拼接 + ${str}"}</span></dd>
    <dd>字符串截取单个字符(1)：<span class="exp">${str[1]}</span></dd>
    <dd>字符串截取(2)：<span class="exp">${str?substring(1)}</span></dd>
    <dd>字符串截取(3)：<span class="exp">${str?substring(1,2)}</span></dd>
    <dd>indexOf：<span class="exp">${str?index_of("一")}</span></dd>
    <dd>split分割字符串：<span class="exp">
    <#list "a|b|c"?split("|") as item>
        ${item}
    </#list>
    </span></dd>
</dl>

</body>
</html>