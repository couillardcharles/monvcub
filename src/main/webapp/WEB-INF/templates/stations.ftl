<#import "layout.ftl" as page/>
<@page.layout>
		<div data-role="page" class="page-stations">
			<div data-role="header" data-position="fixed">
				<h1>Stations</h1>
				<a data-role="button" href="/" data-icon="home" class="ui-btn-left" data-iconpos="notext">Accueil</a>
				<a data-role="button" id="boutonPlan" href="/plan" data-icon="grid" class="ui-btn-right">Plan</a>
			</div>
			<div data-role="content"> 
				<ul data-role="listview" data-filter="true" data-theme="a">
					<#list stations as station>
					<li identifiant="${station.identifiant}">
						<a href="#">${station.nom}</a>
					</li>
					</#list>
				</ul>
			</div>
			<div data-role="footer" data-position="fixed"><h4>Mon VCub</h4></div>
		</div>
</@>

