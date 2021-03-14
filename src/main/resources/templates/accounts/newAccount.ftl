<html>
    <head>
        <#import "../helpers/header.ftl" as header>
        <@header.globals/>

        <#if account.getID()??>
            <#assign title=locale.getString("title.account.edit")/>
        <#else>
            <#assign title=locale.getString("title.account.new")/>
        </#if>

        <@header.header "BudgetMaster - ${title}"/>
        <@header.style "accounts"/>
        <#import "/spring.ftl" as s>
    </head>
    <@header.body>
        <#import "../helpers/navbar.ftl" as navbar>
        <@navbar.navbar "accounts" settings/>

        <#import "accountFunctions.ftl" as accountFunctions>

        <main>
            <div class="card main-card background-color">
                <div class="container">
                    <div class="section center-align">
                        <div class="headline">${title}</div>
                    </div>
                </div>

                <@header.content>
                    <div class="container">
                    <#import "../helpers/validation.ftl" as validation>
                    <form name="NewAccount" action="<@s.url '/accounts/newAccount'/>" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="ID" value="<#if account.getID()??>${account.getID()?c}</#if>">
                        <input type="hidden" name="isSelected" value="<#if account.isSelected()??>${account.isSelected()?c}</#if>">
                        <input type="hidden" name="isDefault" value="<#if account.isDefault()??>${account.isDefault()?c}</#if>">
                        <input type="hidden" name="isReadOnly" value="<#if account.isReadOnly()??>${account.isReadOnly()?c}</#if>">

                        <#-- name -->
                        <div class="row">
                            <div class="input-field col s12 m12 l8 offset-l2">
                                <i class="material-icons prefix">edit</i>
                                <input id="account-name" type="text" name="name" <@validation.validation "name"/> value="<#if account.getName()??>${account.getName()}</#if>">
                                <label for="account-name">${locale.getString("account.new.label.name")}</label>
                            </div>
                        </div>

                        <#-- icon -->
                        <div class="row">
                            <div class="input-field col s12 m12 l8 offset-l2">
                                <i class="fas fa-icons prefix"></i>
                                <label class="input-label" for="account-icon">${locale.getString("account.new.label.icon")}</label>

                                <div id="account-icon" class="valign-wrapper">
                                    <a id="account-icon-preview" data-url="<@s.url '/media/getAvailableImages'/>">
                                        <img id="account-icon-preview-icon" src="<#if account.getIcon()??>${account.getIcon().getBase64EncodedImage()}</#if>" class="account-icon-preview <#if account.getIcon()?? == false>hidden</#if>"/>
                                        <div id="account-icon-placeholder" class="<#if account.getIcon()??>hidden</#if>">${locale.getString("account.new.icon.placeholder")}</div>
                                    </a>
                                    <@header.buttonFlat url='' icon='delete' id='button-remove-account-icon' localizationKey='' classes="no-padding text-default" noUrl=true/>

                                    <input id="hidden-input-account-icon" type="hidden" name="icon" value="<#if account.getIcon()??>${account.getIcon().getID()?c}</#if>">
                                </div>
                            </div>
                        </div>

                        <@accountFunctions.modalAccountIconSelect/>

                        <br>

                        <#-- buttons -->
                        <div class="row hide-on-small-only">
                            <div class="col s6 right-align">
                                <@header.buttonLink url='/accounts' icon='clear' localizationKey='cancel' id='button-cancel-save-account'/>
                            </div>

                            <div class="col s6 left-align">
                                <@header.buttonSubmit name='action' icon='save' localizationKey='save' id='button-save-account'/>
                            </div>
                        </div>
                        <div class="hide-on-med-and-up">
                            <div class="row center-align">
                                <div class="col s12">
                                    <@header.buttonLink url='/accounts' icon='clear' localizationKey='cancel' id='button-cancel-save-account'/>
                                </div>
                            </div>
                            <div class="row center-align">
                                <div class="col s12">
                                    <@header.buttonSubmit name='action' icon='save' localizationKey='save' id='button-save-account'/>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                </@header.content>
            </div>
        </main>

        <!-- Scripts-->
        <#import "../helpers/scripts.ftl" as scripts>
        <@scripts.scripts/>
        <script src="<@s.url '/js/accounts.js'/>"></script>
    </@header.body>
</html>