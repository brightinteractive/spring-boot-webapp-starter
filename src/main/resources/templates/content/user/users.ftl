<#include "../../layout/main.ftl">

<#import "/spring.ftl" as spring/>
<#import "../../macro/bright-spring.ftl" as brightSpring/>

<#macro title_inner>Users</#macro>

<#macro content>

<a href="/admin/user" class="button u-pull-right">Add user <i aria-hidden="true" class="icon-add-circle"></i></a>

<h1>Users</h1>

<table class="table">
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
