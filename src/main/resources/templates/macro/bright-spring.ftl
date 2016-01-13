<#import "/spring.ftl" as spring/>

<#--
 * showErrors
 *
 * Show validation errors for the currently bound field, with styling.
-->
<#macro showErrors>
    <@spring.showErrors ' ', 'field__error'/>
</#macro>

<#--
 * csrf
 *
 * Output a CSRF token hidden field.
-->
<#macro csrf>
    <#if _csrf??>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </#if>
</#macro>

<#macro formDatePicker path attributes="">
    <@_formDatePicker path attributes 'js-datepicker'/>
</#macro>

<#macro formDateTimePicker path attributes="">
    <@_formDatePicker path attributes 'js-datetimepicker'/>
</#macro>

<#macro _formDatePicker path attributes class>
    <span class="field__datepicker">
        <@spring.bind path/>
        <input type="text"
               id="<@formInputId/>"
               name="${spring.status.expression}"
               value="<@dateInputValue/>"
               class="${class}"
               ${attributes}/>
    </span>
</#macro>

<#macro dateInputValue>
${spring.status.value?is_date_like?then(spring.status.value?date, spring.status.value)}
</#macro>

<#--
 * formRadioButtons
 *
 * Show radio buttons.
 *
 * @param path the name of the field to bind to
 * @param options a map (value=label) of all the available options
 * @param attributes any additional attributes for the element (such as class
 *    or CSS styles or size
-->
<#macro formRadioButtons path options attributes="" inline=false>
    <@spring.bind path/>
    <#if inline=true>
    <div class="field field--radio-inline">
    <#else>
    <div class="field field--radio">
    </#if>
        <#list options?keys as value>
            <#assign id="${spring.status.expression?replace('[','')?replace(']','')}${value_index}"/>
            <label>
                <input type="radio" id="${id}" name="${spring.status.expression}" value="${value?html}"<#if spring.stringStatusValue == value> checked="checked"</#if> ${attributes}<@spring.closeTag/>
                ${options[value]?html}
            </label>
        </#list>
    </div>
</#macro>

<#--
 * formCheckboxes
 *
 * Show checkboxes.
 *
 * @param path the name of the field to bind to
 * @param options a map (value=label) of all the available options
 * @param separator the html tag or other character list that should be used to
 *        separate each option.  Typically '&nbsp;' or '<br>'
 * @param attributes any additional attributes for the element (such as class
 *        or CSS styles or size
-->
<#macro formCheckboxes path options attributes="" inline=false>
	<@spring.bind path/>
    <#if inline=true>
    <div class="field field--checkbox-inline">
    <#else>
    <div class="field field--checkbox">
    </#if>
	<#list options?keys as value>
		<#assign isSelected = spring.contains(spring.status.actualValue?default([""]), value)>
		<label>
			<input type="checkbox" name="${spring.status.expression}" value="${value}"<#if isSelected>checked="checked"</#if> ${attributes}<@spring.closeTag/>
			${options[value]}
		</label>
	</#list>
    </div>
</#macro>

<#--
 * id
 *
 * Output an for a form input id in the same format that <@spring.formInput/> does
-->
<#macro formInputId>${spring.status.expression?replace('[','')?replace(']','')}</#macro>
