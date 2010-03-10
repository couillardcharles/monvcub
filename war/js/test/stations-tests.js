var uriAppelée;
var contenuARetourner;
$.ajax = function(settings) {
	uriAppelée = settings.url;
	settings.success(contenuARetourner);
};

QUnit.reset = function() {
	localStorage.stations = "";
	$("li").removeClass("selectionnee");
}

test("Ajoute bien la classe selectionnee sur les stations préférés", function() {
	localStorage.stations = "1,3";
	selectionnerStations();
	
	ok($("li[identifiant=1]").hasClass("selectionnee"));
	ok(!$("li[identifiant=2]").hasClass("selectionnee"));
	ok($("li[identifiant=3]").hasClass("selectionnee"));
});

test("Enleve bien la classe selectionnee en cas de rechargement", function() {
	localStorage.stations = "1,3";
	selectionnerStations();
	localStorage.stations = "3";
	selectionnerStations();
	
	ok(!$("li[identifiant=1]").hasClass("selectionnee"));
	ok(!$("li[identifiant=2]").hasClass("selectionnee"));
	ok($("li[identifiant=3]").hasClass("selectionnee"));
});

test("Selectionne bien la station sur le click", function() {
	$("li[identifiant=1] a").click();
	
	ok($("li[identifiant=1]").hasClass("selectionnee"));
});

test("Garde bien les anciennes selections sur le click d'une autre station", function() {
	localStorage.stations = "2";
	selectionnerStations();
	$("li[identifiant=1] a").click();
	
	ok($("li[identifiant=1]").hasClass("selectionnee"));
	ok($("li[identifiant=2]").hasClass("selectionnee"));
});

test("Deselectionne bien la station sur le click", function() {
	localStorage.stations = "2";
	selectionnerStations();
	$("li[identifiant=2] a").click();

	equals(localStorage.stations, "");
});

test("Charge bien les stations préférées", function() {
	contenuARetourner = "le contenu";
	localStorage.stations = "1,8";
	charger();
	
	equals(uriAppelée,"/mes-stations?stations=1,8");
	equals($("#mes-stations").text(), "le contenu");
});

test("Peut recharger", function() {
	contenuARetourner = "le contenu rechargé";
	localStorage.stations = "3,8";
	
	$("#boutonRecharger a").click();
	
	equals(uriAppelée,"/mes-stations?stations=3,8");
	equals($("#mes-stations").text(), "le contenu rechargé");
});

