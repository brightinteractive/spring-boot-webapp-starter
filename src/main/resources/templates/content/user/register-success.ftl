<#include "../../layout/main.ftl">

<#import "../../macro/bright-spring.ftl" as brightSpring/>
<#import "/spring.ftl" as spring/>

<#macro body_class>body-login</#macro>

<#macro title_inner>Register</#macro>

<#macro header></#macro>


<#macro content>
    
    <div class="page-header">
    	<h1>Registration successful</h1>
    </div>
    <p>
    	You have successfully registered. We have emailed you with an activation link - click on that to activate your account.
    </p>

</#macro>

<@render_page/>
