<#include "../../layout/main.ftl">

<#import "/spring.ftl" as spring/>
<#import "../../macro/bright-spring.ftl" as brightSpring/>

<#macro title_inner>
	<#if form.id??>
		Edit User
	<#else>
		Add User
	</#if>
</#macro>

<#macro content>
    <form action="" method="POST">
        <@brightSpring.csrf/>

        <#if form.id??>
            <h1>Edit user</h1>
            <@spring.formHiddenInput 'form.id'/>
        <#else>
            <h1>Add user</h1>
        </#if>

        <div class="field__row field__row--condensed">
            <div class="field field--short">
                <label>
                    <span class="field__title">Email address:</span>
                    <@spring.formInput 'form.emailAddress'/>
                    <@brightSpring.showErrors/>
                </label>
            </div>
        </div>

        <div class="field__row field__row--condensed field__row--columns">
            <div class="field">
                <label>
                    <span class="field__title">Password<#if form.id??> (leave empty if no change)</#if>:</span>
                    <@spring.formPasswordInput 'form.password'/>
                    <@brightSpring.showErrors/>
                </label>
            </div>

            <div class="field">
                <label>
                    <span class="field__title">Repeat password:</span>
                    <@spring.formPasswordInput 'form.passwordRepeated'/>
                    <@brightSpring.showErrors/>
                </label>
            </div>
        </div>

        <div class="field__row field__row--condensed">
            <div class="field field--checkbox field--short">

                <span class="field__title">Roles:</span>
                <@brightSpring.formCheckboxes path='form.roles' options=roleOptions />
                <@brightSpring.showErrors/>

            </div>
        </div>

        <input type="submit" value="Save" class="button"/>
        <a href="<@spring.url '/admin/users'/>" class="button button--cancel">Cancel</a>

    </form>
</#macro>

<@render_page/>
