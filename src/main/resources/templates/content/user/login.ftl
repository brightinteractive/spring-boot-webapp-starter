<#include "../../layout/main.ftl">

<#import "../../macro/bright-spring.ftl" as brightSpring/>
<#import "/spring.ftl" as spring/>

<#macro body_class>body--login</#macro>

<#macro title_inner>Login</#macro>

<#macro header></#macro>
<#macro content_wrapper>
    <@content/>
</#macro>

<#macro content>

    <div class="box">
        <form action="/login" method="post">
            <@brightSpring.csrf/>
            <div class="box__header logo box--centered">
                <a href="/"><img src="/static/img/logo.png" alt="Logo"></a>
            </div>
            <div class="box__inner">

                <h1>Login</h1>

                <#if logout.isPresent()>
                    <div class="alert-info alert--large-margin">
                        <i aria-hidden="true" class="icon-info"></i> You have been logged out.
                    </div>
                </#if>

                <#if error.isPresent()>
                    <div class="alert-error alert--large-margin">
                        <i aria-hidden="true" class="icon-error"></i> The email address or password you have entered is invalid, try again.
                    </div>

                </#if>

                <div class="field__row field__row--condensed">
                    <div class="field">
                        <label>
                            <i aria-hidden="true" class="icon-person field__icon"></i>
                            <input type="email" name="emailAddress" id="emailAddress" placeholder="Your email address" class="input--padded" value="<#if emailAddress.isPresent()>${emailAddress.get()}</#if>" required/>
                        </label>
                    </div>
                </div>
                <div class="field__row field__row--condensed">
                    <div class="field">
                        <label>
                            <i aria-hidden="true" class="icon-https field__icon"></i>
                            <input type="password" name="password" id="password" placeholder="Password" class="input--padded" required/>
                        </label>
                    </div>
                </div>
                <div class="field__row field__row--condensed">
                    <div class="field field--checkbox">
                        <label>
                            <input type="checkbox" name="remember-me" <#if (rememberMe.isPresent() && rememberMe.get() == "on")>checked="checked"</#if>" id="remember-me">Remember me
                        </label>
                    </div>
                </div>
            </div>
            <div class="box__footer">
                <input type="submit" class="button" id="submit" value="Sign in" />
            </div>
        </form>
    </div>



</#macro>

<@render_page/>
