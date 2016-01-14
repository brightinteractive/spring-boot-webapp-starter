<#include "../../layout/main.ftl">

<#import "/spring.ftl" as spring/>
<#import "../../macro/bright-spring.ftl" as brightSpring/>

<#macro title_inner>Change Password</#macro>
<#macro sidebar><#include "/include/admin-menu.ftl"></#macro>

<#macro content>

    <form action="" method="POST" class="form-horizontal">
        <@brightSpring.csrf/>
        
        <div class="page-header">
            <h1>Change password</h1>
        </div>    
        <@spring.formHiddenInput 'form.userId'/>

        <div class="form-group">
            <label class="col-sm-2 control-label">Current password:</label>
            <div class="col-sm-10">
                <@spring.formPasswordInput path='form.currentPassword' attributes='class=form-control'/>
          
                <@brightSpring.showErrors/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">New password:</label>
            <div class="col-sm-10">
                <@spring.formPasswordInput path='form.newPassword' attributes='class=form-control'/>
                <@brightSpring.showErrors/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Repeat new password:</label>
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
        </div>
    </form>

</#macro>

<@render_page/>
