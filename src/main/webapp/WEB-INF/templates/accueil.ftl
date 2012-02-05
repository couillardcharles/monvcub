<#import "layout.ftl" as page/>
<@page.layout>
	<div data-role="page" class="page-mes-stations">
			<div data-role="header" data-position="fixed">
				<h1>Mes Stations</h1>
				<a data-role="button" id="boutonMenu" href="/stations" data-icon="gear" class="ui-btn-left">Menu</a>
				<a data-role="button" id="boutonRechargement" href="#" data-icon="refresh" class="ui-btn-right">Rech.</a>
			</div> 
			<div data-role="content">
				<ul data-role="listview" data-inset="true" data-theme="a">
				</ul>
			</div>		
			<div data-role="footer" data-position="fixed"><h4>Mon VCub</h4></div>
		</div>
</@>
