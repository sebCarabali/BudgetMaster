<#import "../helpers/header.ftl" as header>

<#function getCategoryName category>
    <#if category?? && category.getName()??>
        <#if category.getType().name() == "NONE">
            <#return locale.getString("category.none")>
        <#elseif category.getType().name() == "REST">
            <#return locale.getString("category.rest")>
        <#else>
            <#return category.getName()>
        </#if>
    <#else>
        <#return "">
    </#if>
</#function>

<#macro categoryCircle category classes="" datasetValue="">
    <#assign categoryName=getCategoryName(category)>

    <div class="category-circle ${classes}" style="background-color: ${category.color}" <#if datasetValue?has_content>data-value="${category.getID()}"</#if>>
        <span style="color: ${category.getAppropriateTextColor()}">
            <#if category.getIcon()?has_content>
                <i class="${category.getIcon()}"></i>
            <#else>
                ${categoryName?capitalize[0]}
            </#if>
        </span>
    </div>
</#macro>

<#macro modalIconSelect>
    <div id="modalIconSelect" class="modal background-color">
        <div class="modal-content">
            <h4>${locale.getString("category.new.label.icon")}</h4>

            <div class="row">
                <div class="input-field col s12 m12 l8 offset-l2">
                    <i class="material-icons prefix">search</i>
                    <input id="searchIcons" type="text">
                    <label for="searchIcons">${locale.getString("search")}</label>
                </div>
            </div>

            <div class="row">
                <@categoryIconOption 'fas fa-check'/>
                <@categoryIconOption 'fas fa-adjust'/>
                <@categoryIconOption 'fas fa-angular'/>
                <@categoryIconOption 'fas fa-check'/>
                <@categoryIconOption 'fas fa-check'/>
                <@categoryIconOption 'fas fa-check'/>
                <@categoryIconOption 'fas fa-check'/>
                <@categoryIconOption 'fas fa-check'/>
            </div>

        </div>
        <div class="modal-footer background-color">
            <@header.buttonLink url='' icon='save' localizationKey='cancel' color='red' classes='modal-action modal-close text-white' noUrl=true/>
            <@header.buttonLink url='' icon='done' id='button-category-icon-confirm' localizationKey='ok' color='green' classes='modal-action modal-close text-white' noUrl=true/>
        </div>
    </div>
</#macro>

<#macro categoryIconOption icon>
    <div class="col s2 category-icon-option-column">
        <div class="category-icon-option">
            <i class="category-icon-option-icon ${icon}"></i>
            <div class="category-icon-option-name">${icon}</div>
        </div>
    </div>
</#macro>