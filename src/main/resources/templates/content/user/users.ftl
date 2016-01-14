<#include "../../layout/main.ftl">

<#import "/spring.ftl" as spring/>
<#import "../../macro/bright-spring.ftl" as brightSpring/>

<#macro title_inner>Users</#macro>

<#macro content>

<div class="page-header">
    <a href="/admin/user" class="btn btn-primary pull-right">Add user</a>

    <h1>Users</h1>

</div>

<table class="table table-striped">
    <thead>
    	<tr>
        	<th>Username</th>
        	<th>Action</th>
        </tr>
    </thead>
    <tbody>
        <#list users as user>
            <tr>
                <td>
                    ${user.emailAddress}
                </td>
                <td>
                    <a href="/admin/user?id=${user.id}"><i aria-hidden="true" class="icon-edit"></i> Edit</a>
                </td>
            </tr>
        </#list>
    </tbody>
</table>

</#macro>

<@render_page/>
