<li data-role="list-divider" data-theme="a"><span class="station">Stations</span>
<p class="ui-li-aside">
<span>V&eacute;los</span>
<br />
<span>Places</span>
</p>
</li>
<#if stations?size == 0>
<li><span class="station">Aucune station s&eacute;lectionn&eacute;e. Cliquez sur Menu.</span></li>
<#else>
<#list stations as station>
<li>
	<a href="#">${station.nom}</a>
	<p class="ui-li-aside">
		<#if station.estEnMaintenance()>
		<span class="en-maintenance">Maint.</span>
		<#else>
		<span class="<#if station.velosDisponibles == 0> ko <#else> ok </#if>">${station.velosDisponibles}</span><br />
		<span class="<#if station.placesDisponibles == 0> ko <#else> ok </#if>">${station.placesDisponibles}</span>
		</#if>
	</p>
</li>
</#list>
</#if>
