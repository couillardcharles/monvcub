$(function() {
	charger();
});

function charger() {
	localStorage.stations = (localStorage.stations || "");
	$.get("/mes-stations?stations=" + localStorage.stations, function(data) {
		$("#mes-stations").html(data);
	});
};