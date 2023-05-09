$(() => {

var infowindow = new kakao.maps.InfoWindow({zIndex:1});

var mapContainer = document.getElementById('map');

var mapOption = {
        center: new kakao.maps.LatLng(lat, lon),
        level: 7
};  

var map = new kakao.maps.Map(mapContainer, mapOption); 
var ps = new kakao.maps.services.Places(map); 
ps.categorySearch('FD6', placesSearchCB, {useMapBounds:true}); 


function placesSearchCB (data, status) {
	
	let listEl = $('#placesList');
	
    if (status === kakao.maps.services.Status.OK) {
	
        for (var i=0; i<data.length; i++) {
           displayMarker(data[i]);   
           listEl.append(getListItem(i, data[i]));
        }       
    }
}

function displayMarker(place) {

    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x) 
    });
    kakao.maps.event.addListener(marker, 'click', function() {
		
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
    });
}

function getListItem(index, places) {
    var el = $('<li>').addClass('item');
    var markerClass = 'markerbg marker_' + (index + 1);
    var marker = $('<span>').addClass(markerClass);
    var info = $('<div>').addClass('info');
    var name = $('<h5>').text(places.place_name);

    info.append(name);

    if (places.road_address_name) {
	
        var roadAddress = $('<span>').text(places.road_address_name);
        var address = $('<span>').addClass('jibun gray').text(places.address_name);
        info.append(roadAddress, address);
        
    } else {
	
        var address = $('<span>').text(places.address_name);
        info.append(address);
    }

    var phone = $('<span>').addClass('tel').text(places.phone);
    info.append(phone);

    el.append(marker, info);

    return el;
}

});
