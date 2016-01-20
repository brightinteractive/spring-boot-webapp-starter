<#include "../../layout/main.ftl">

<#import "../../macro/bright-spring.ftl" as brightSpring/>
<#import "/spring.ftl" as spring/>

<#macro body_class>body-login</#macro>

<#macro title_inner>Login</#macro>

<#macro header></#macro>


<#macro content>
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel">
                <form action="/login" method="post">
                    <@brightSpring.csrf/>

                    <div class="panel-heading login-logo">
                        <a href="/"><img src="/static/img/logo.png" alt="Logo"></a>
                    </div>

                    <div class="panel-body">
                        <h2>Login</h2>
                        
                        <#if logout.isPresent()>
                            <div class="alert alert-info">
                                You have been logged out.
                            </div>
                        </#if>

                        <#if error.isPresent()>
                            <div class="alert alert-danger">
                                The email address or password you have entered is invalid, try again.
                            </div>

                        </#if>
                        
                        <div class="form-group">
                            <input type="email" name="emailAddress" id="emailAddress" placeholder="Your email address" class="form-control" value="<#if emailAddress.isPresent()>${emailAddress.get()}</#if>" required/>
                        </div>

                        <div class="form-group">
                            <input type="password" name="password" id="password" placeholder="Password" class="form-control" required/>
                        </div>
                  
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="remember-me" <#if (rememberMe.isPresent() && rememberMe.get() == "on")>checked="checked"</#if>" id="remember-me">Remember me
                            </label>
                        </div>
                        <hr>
                        <div class="form-group">
                            <input type="submit" class="btn btn-primary" id="submit" value="Sign in" />
                        </div>
                    </div>

                </form>
                <p>
                Don't have an account? <a href="/register">register</a>
                </p>
            </div>
        </div>
    </div>
</#macro>

<@render_page/>
