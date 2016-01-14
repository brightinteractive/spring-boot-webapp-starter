<#import "../macro/bright-spring.ftl" as brightSpring/>

<#macro body_class></#macro>

<#macro header_links>
    
    <ul class="list-inline pull-right">
        <li>${currentUser.username} </li>
        <li><a href="/user/change-password?userId=${currentUser.id}">Change password</a></li>
        <#if currentUser.UIPermissions.canViewUsersMenu>
            <li><a href="/admin/users">Manage users</a></li>
        </#if>
        <li>
            <form action="/logout" method="post"><@brightSpring.csrf/><input type="submit" id="signoutButton" class="btn btn-default" value="Sign out"></form>
        </li>
    </ul>


    
</#macro>

<#macro header>
    <div class="header">
        <div class="container">
            <a href="/" class="pull-left"><img src="/static/img/logo.png" alt="Logo"></a>
            <div class="header__links pull-right">
                <@header_links/>
            </div>
        </div>
    </div>
</#macro>

<#macro content_wrapper>
    <div class="container">
        <div class="content">
            <@content/>
        </div>
    </div>
</#macro>

<#macro content></#macro>

<#macro title><@title_inner/> | AppStarter</#macro>

<#macro title_inner></#macro>

<#macro render_page>
<!doctype html>
<!--[if IE 9 ]>    <html class="no-js ie9" lang="en"> <![endif]-->
<!--[if (gte IE 10)|!(IE)]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <meta name="application-name" content="AppStarter" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><@title/></title>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/appstarter.css">
</head>

<body class="<@body_class/>">

    <@header/>
    <@content_wrapper/>

</body>
</html>
</#macro>
