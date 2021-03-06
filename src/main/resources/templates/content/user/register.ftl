<#include "../../layout/main.ftl">

<#import "../../macro/bright-spring.ftl" as brightSpring/>
<#import "/spring.ftl" as spring/>

<#macro body_class>body-login</#macro>

<#macro title_inner>Login</#macro>

<#macro header></#macro>


<#macro content>
    
    <div class="page-header">
    	<h1>Register</h1>
    </div>
    <form action="" method="POST" class="form-horizontal">
        <@brightSpring.csrf/>

        <div class="form-group">        
            <label class="col-sm-2 control-label">Email address:</label>
            <div class="col-sm-10">
                <@spring.formInput path='form.emailAddress' attributes='class=form-control'/>
                <@brightSpring.showErrors/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Password:</label>
            <div class="col-sm-10">
                <@spring.formPasswordInput path='form.password' attributes='class=form-control'/>
                <@brightSpring.showErrors/>
                <#if form.id??><p class="help-block">Leave empty if no change</p></#if>
            </div>    
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Repeat password:</label>
            <div class="col-sm-10">
                <@spring.formPasswordInput path='form.passwordRepeated' attributes='class=form-control'/>
                <@brightSpring.showErrors/>
            </div>
        </div>

        <div class="form-group">
        <div class="col-sm-10 col-sm-offset-2">
            <input type="submit" value="Save" class="btn btn-primary"/>
            <a href="<@spring.url '/'/>" class="btn btn-default">Cancel</a>
        </div>
    </form>
</#macro>

<@render_page/>
