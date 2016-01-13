<#include "../../layout/main.ftl">

<#import "/spring.ftl" as spring/>
<#import "../../macro/bright-spring.ftl" as brightSpring/>

<#macro title_inner>Change Password</#macro>
<#macro sidebar><#include "/include/admin-menu.ftl"></#macro>

<#macro content>
    <div class="content content--main">
        <form action="" method="POST">
            <@brightSpring.csrf/>

            <h1>Change password</h1>
            <@spring.formHiddenInput 'form.userId'/>

            <div class="field__row field__row--condensed">
                <div class="field field--checkbox field--short">
    	            <span class="field__title">Current password:</span>
    	            <@spring.formPasswordInput 'form.currentPassword'/>
    	            <@brightSpring.showErrors/>
                </div>
            </div>

            <div class="field__row field__row--condensed">
                <div class="field field--checkbox field--short">
    	            <span class="field__title">New password:</span>
    	            <@spring.formPasswordInput 'form.newPassword'/>
    	            <@brightSpring.showErrors/>
                </div>
            </div>

            <div class="field__row field__row--condensed">
                <div class="field field--checkbox field--short">
    	            <span class="field__title">Repeat new password:</span>
    	            <@spring.formPasswordInput 'form.passwordRepeated'/>
    	            <@brightSpring.showErrors/>
                </div>
            </div>

            <input type="submit" value="Save" class="button"/>
            <a href="<@spring.url '/'/>" class="button button--cancel">Cancel</a>

        </form>
    </div>
</#macro>

<@render_page/>
