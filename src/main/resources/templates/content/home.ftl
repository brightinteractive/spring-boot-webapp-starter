<#include "../layout/main.ftl">

<#import "/spring.ftl" as spring/>

<#macro title_inner>Home</#macro>

<#macro content>
	<div class="page-header">
	<h1>This is the home page.</h1>
	</div>
	<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Velit ex, voluptas maiores dignissimos commodi illum laborum, totam incidunt aperiam dolore animi quibusdam, illo nisi mollitia voluptatum neque ullam necessitatibus vel.</p>
</#macro>

<@render_page/>
