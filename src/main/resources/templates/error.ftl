<#include "layout/main.ftl">

<#macro header_links></#macro>

<#macro content>

    <h1>Error:</h1>

    <p>There was an unexpected error accessing this page.  You may want to try loading the <a href="/">homepage again</a>.</p>


    <div class="error-message">
        <h2 class="error-message__error-type">Error details: (type=${error}, status=${status}).</h2>
        <p><strong>${timestamp?datetime}</strong></p>
        <p>${message}</p>
    </div>

    <p><a class="button" href="/">Back to home</a></p>

</#macro>

<@render_page/>
