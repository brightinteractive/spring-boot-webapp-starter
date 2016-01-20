<#include "../../layout/main.ftl">

<#import "../../macro/bright-spring.ftl" as brightSpring/>
<#import "/spring.ftl" as spring/>

<#macro body_class>body-login</#macro>

<#macro title_inner>Activate</#macro>

<#macro header></#macro>


<#macro content>
    
    <div class="page-header">
    	<h1>Activate</h1>
    </div>
    <p>
    	<#if success>
    		Your account has been activated - you can now <a href="/login">login</a>
    	<#else>
    		The activation code is invalid (have you already activated your account?)
    	</#if>
    </p>

</#macro>

<@render_page/>
