$(() => {

console.log($('#placeList'));
console.log("******");
console.log(sanName);
console.log(lat);
console.log(lon);
//var markers = [];
//
//
//var mapContainer = document.getElementById('map'), 
//    mapOption = {
//        center: new kakao.maps.LatLng(lat, lon), 
//        level: 7
//    };  
//
//var map = new kakao.maps.Map(mapContainer, mapOption); 
//var ps = new kakao.maps.services.Places();  
//var infowindow = new kakao.maps.InfoWindow({zIndex:1});
//
//
// ps.keywordSearch( sanName + '식당', placesSearchCB); 
//
//function placesSearchCB(data, status) {
//    if (status === kakao.maps.services.Status.OK) {
//
//        // 검색 목록과 마커를 표출합니다
//        displayPlaces(data);
//
//    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
//
//        alert('검색 결과가 존재하지 않습니다.');
//        return;
//
//    } else if (status === kakao.maps.services.Status.ERROR) {
//
//        alert('검색 결과 중 오류가 발생했습니다.');
//        return;
//
//    }
//}
//
//// 검색 결과 목록과 마커를 표출하는 함수입니다
//function displayPlaces(places) {
//
//    var listEl = document.getElementById('placesList'), 
//    menuEl = document.getElementById('menu_wrap'),
//    fragment = document.createDocumentFragment(), 
//    bounds = new kakao.maps.LatLngBounds();
////    listStr = '';
//    
////    // 검색 결과 목록에 추가된 항목들을 제거합니다
////    removeAllChildNods(listEl);
////
////    // 지도에 표시되고 있는 마커를 제거합니다
////    removeMarker();
//    
//    for ( var i=0; i<places.length; i++ ) {
//
//        // 마커를 생성하고 지도에 표시합니다
//        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
//            marker = addMarker(placePosition, i), 
//            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
//
//        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
//        // LatLngBounds 객체에 좌표를 추가합니다
//        bounds.extend(placePosition);
//
//        // 마커와 검색결과 항목에 mouseover 했을때
//        // 해당 장소에 인포윈도우에 장소명을 표시합니다
//        // mouseout 했을 때는 인포윈도우를 닫습니다
//        (function(marker, title) {
//            kakao.maps.event.addListener(marker, 'mouseover', function() {
//                displayInfowindow(marker, title);
//            });
//
//            kakao.maps.event.addListener(marker, 'mouseout', function() {
//                infowindow.close();
//            });
//
//            itemEl.onmouseover =  function () {
//                displayInfowindow(marker, title);
//            };
//
//            itemEl.onmouseout =  function () {
//                infowindow.close();
//            };
//        })(marker, places[i].place_name);
//
//        fragment.appendChild(itemEl);
//    }
//
//    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
//    listEl.appendChild(fragment);
//    menuEl.scrollTop = 0;
//
//    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
////    map.setBounds(bounds);
//}
//
//// 검색결과 항목을 Element로 반환하는 함수입니다
//function getListItem(index, places) {
//
//    var el = document.createElement('li'),
//    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
//                '<div class="info">' +
//                '   <h5>' + places.place_name + '</h5>';
//
//    if (places.road_address_name) {
//        itemStr += '    <span>' + places.road_address_name + '</span>' +
//                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
//    } else {
//        itemStr += '    <span>' +  places.address_name  + '</span>'; 
//    }
//                 
//      itemStr += '  <span class="tel">' + places.phone  + '</span>' +
//                '</div>';           
//
//    el.innerHTML = itemStr;
//    el.className = 'item';
//
//    return el;
//}
//
//// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
//function addMarker(position, idx, title) {
//    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
//        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
//        imgOptions =  {
//            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
//            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
//            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
//        },
//        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
//            marker = new kakao.maps.Marker({
//            position: position, // 마커의 위치
//            image: markerImage 
//        });
//
//    marker.setMap(map); // 지도 위에 마커를 표출합니다
//    markers.push(marker);  // 배열에 생성된 마커를 추가합니다
//
//    return marker;
//}
//
//function displayInfowindow(marker, title) {
//    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';
//
//    infowindow.setContent(content);
//    infowindow.open(map, marker);
//}

var infowindow = new kakao.maps.InfoWindow({zIndex:1});

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
        level: 7 // 지도의 확대 레벨
    };  

var map = new kakao.maps.Map(mapContainer, mapOption); 
var ps = new kakao.maps.services.Places(map); 
ps.categorySearch('FD6', placesSearchCB, {useMapBounds:true}); 

// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB (data, status) {
	let listEl = document.getElementById('placesList');
	
    if (status === kakao.maps.services.Status.OK) {
        for (var i=0; i<data.length; i++) {
            displayMarker(data[i]);   
           listEl.appendChild(getListItem(i, data[i])); 

        }       
    }
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
    // 마커를 생성하고 지도에 표시합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x) 
    });
    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
    });
}
function getListItem(index, places) {

    var el = document.createElement('li'),
    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
                '<div class="info">' +
                '   <h5>' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
    } else {
        itemStr += '    <span>' +  places.address_name  + '</span>'; 
    }
                 
      itemStr += '  <span class="tel">' + places.phone  + '</span>' +
                '</div>';           

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}
});
