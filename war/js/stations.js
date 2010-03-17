$(function() {
	$("#boutonRetour").hide();
	chargerMesStations();
	$("#stations li a").live("click", function(e) {
		var li = $(e.target).closest("li");
		var identifiant = li.attr("identifiant");
		localStorage.stations = (localStorage.stations || "");
		var stationsSelectionnees = localStorage.stations.split(",");
		
		var index = getIndex(stationsSelectionnees, identifiant);
		if (index != -1) {
			stationsSelectionnees.splice(index, 1);
		} else {
			stationsSelectionnees.push(identifiant);
		}
		localStorage.stations = stationsSelectionnees.join(",");
		selectionnerStations();
		return false;
	});
	$("#boutonMenu a").click(function() {
		toggle("Stations");
		chargerStations();
		return false;
	});
	$("#boutonRetour a").click(function() {
		toggle("Mes stations");
		chargerMesStations();
		return false;
	});
});

function toggle(titre) {
	$("#boutonRetour").toggle();
	$("#boutonMenu").toggle();
	$("title").text(titre);
	$("#header h1 a").text(titre)
}

function selectionnerStations() {
	$("li").removeClass("selectionnee");
	if (localStorage.stations != undefined) {
		var stations = localStorage.stations.split(",");
		for ( i = 0; i < stations.length; i++) {
			$("li[identifiant="+ stations[i] +"]").addClass("selectionnee");
		}
	}
};

function chargerMesStations() {
	$("#content").show();
	$("#carte").hide();
	$("#stations").hide();
	$("#mes-stations").html($("#template-loader").html()).show();
	$("#boutonADroite a").text("Rech.").unbind().click(function() {
		chargerMesStations();
	});
	localStorage.stations = (localStorage.stations || "");
	$.get("/mes-stations?stations=" + localStorage.stations, function(data) {
		$("#mes-stations").html(data);
	});
};

function chargerStations() {
	$("#content").show();
	$("#carte").hide();
	$("#mes-stations").hide();
	$("#boutonADroite a").text("Plan").unbind().click(function() {
		chargerCarte();
	});
	$("#stations").html($("#template-loader").html()).show();
	$.get("/stations", function(data) {
		$("#stations").html(data);
		selectionnerStations();
	});
}

function chargerCarte() {
	$("#carte").show();
	$("#content").hide();
	$("#boutonADroite a").text("Menu").unbind().click(function() {
		chargerStations();
	});
	var latlng = new google.maps.LatLng(44.835178,-0.577126);
	var myOptions = {zoom: 12, center: latlng, mapTypeId: google.maps.MapTypeId.ROADMAP};
	var map = new google.maps.Map(document.getElementById("carte"), myOptions);
	   
	$.getJSON('/stations.json', function(data) {
		for (var i = 0; i < data.length; i++) {
			créerMarker(data[i], map);
		}
	});
	
	getGeoLocalisation(map);
}

function créerMarker(station, map) {
	var infowindow = new google.maps.InfoWindow({
		content: "<h1>" + station.titre + "</h1>" + station.adresse
	});

	var marker = new google.maps.Marker({
        position: new google.maps.LatLng(station.latitude, station.longitude), 
        map: map,
        title: station.titre
    });
	
	google.maps.event.addListener(marker, 'click', function() {
		  infowindow.open(map,marker);
	});
}

function getIndex(tableau, element) {
	for (var i = 0; i < tableau.length; i++) {
		if (tableau[i] == element) {
			return i;
		}
	}
	return -1;
};

function getGeoLocalisation(map) {
	navigator.geolocation.getCurrentPosition(LocationOK);

	function LocationOK(position)
	{
	  var marker = new google.maps.Marker({
	        position: new google.maps.LatLng(position.coords.latitude, position.coords.longitude), 
	        map: map,
	        icon: '../images/flag.png'
	    });
	}
}
