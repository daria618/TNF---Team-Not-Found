let ind = 0;

createMap()

let points = []

let myMap

function addNewPoint(lat, lon, text, desc) {
    const form = document.getElementById("route_configurator");

    const div = document.createElement("div");

    div.id = 'ind_' + ind

    div.innerHTML =
        "<div class='mb-5 point'>\n" +
        "                    <div class='choice p-3 mb-2'>\n" +
        "                        <div class='d-flex justify-content-end' data-bs-theme='dark'>\n" +
        "                            <button type='button' class='btn-close' onclick='remove(this.parentNode.parentNode.parentNode.parentNode)' aria-label='Закрыть'></button>\n" +
        "                        </div>\n" +
        "                        <p>Выберите способ указания точки:</p>\n" +
        "                        <div id='pointData_" + ind + "' class='p-2'>\n" +
        "                            <div class='cooridnates row buttonSet' style='--bs-gutter-x: 0'>\n" +
        "                                    <input type='button' onclick='setTypeCoord(this.parentNode)' name='coord' class='col mx-2 px-2 py-1' value='Координаты'>\n" +
        "                                    <input type='button' onclick='setTypeName(this.parentNode)' name='nametext' class='col mx-2 px-2 py-1' value='Адрес'>\n" +
        "                                    <input type='button' onclick='setTypeSelect(this.parentNode)' name='select' class='col mx-2 px-2 py-1' value='На карте'>\n" +
        "                            </div>\n" +
        "                            <div>" +
        "                                <div class='my-2 d-flex justify-content-center'><input id='pointData_"+ind+"' class='addDesc' type='button' style='width: 50%' value='Добавить описание' onclick='switchDesc(this)'></div>" +
        "                            </div>" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>"
    form.appendChild(div);

    if (typeof lat !== 'undefined' && typeof lon !== 'undefined'){
        setTypeCoord(
            div.children.item(0).children.item(0).children.item(2).children.item(0),
            lon,
            lat
        )
    }
    else if (typeof text !== 'undefined'){
        setTypeName(
            div.children.item(0).children.item(0).children.item(2).children.item(0),
            text
        )
    }
    if (typeof desc !== 'undefined'){
        switchDesc(
            div.children.item(0).children.item(0).children.item(2).children.item(1).children.item(0).children.item(0),
            desc
        )
    }

    ind++
}

function submitPoint(parentNode){
    points.push(toPoint(parentNode.children.item(0)))
    updateMap()
}

function toPoint(node){
    let len = node.children.length
    if (coords != null){
        node.children.item(0).value = coords[0]
        node.children.item(1).value = coords[1]
        coords = null
    }

    if (len === 2)
        return [parseFloat(node.children.item(0).value), parseFloat(node.children.item(1).value)]
    else if (len === 1)
        return node.children.item(0).value
}

function remove(parentNode){
    const form = document.getElementById("route_configurator");
    form.removeChild(parentNode)
}

function setTypeCoord(parentNode, lon, lat) {
    for (let i = 0; i < parentNode.children.length; i++) {
        if (parentNode.children[i].id === 'inputs')
            parentNode.removeChild(parentNode.children[i])
    }
    for (let i = 0; i < parentNode.children.length; i++) {
        if (parentNode.children[i].name === 'coord')
            parentNode.children[i].classList.add('active')
        else
            parentNode.children[i].classList.remove('active')
    }
    const div = document.createElement("div")

    if (typeof lat === 'undefined' && typeof lon === 'undefined'){
        lat = ''
        lon = ''
    }
    else {
        points.push([parseFloat(lat), parseFloat(lon)])
    }

    div.id = "inputs"
    div.innerHTML = "<div class='cooridnates row mx-3 my-3' style='--bs-gutter-x: 0'>\n" +
        "               <input value='"+lat+"' class='col me-2' type='text' placeholder='Широта' name='"+parentNode.parentNode.id+"_coord_lat'>\n" +
        "               <input value='"+lon+"' class='col ' type='text' placeholder='Долгота' name='"+parentNode.parentNode.id+"_coord_lon'>\n" +
        "           </div>" +
        "<div class='d-flex justify-content-center'><input onclick='submitPoint(this.parentNode.parentNode)' type='button' value='Подтвердить' style='width: 50%'></div>"
    parentNode.appendChild(div)
}

function setTypeName(parentNode, text) {
    for (let i = 0; i < parentNode.children.length; i++) {
        if (parentNode.children[i].id === 'inputs')
            parentNode.removeChild(parentNode.children[i])
    }
    for (let i = 0; i < parentNode.children.length; i++) {
        if (parentNode.children[i].id === 'inputs')
            parentNode.removeChild(parentNode.children[i])
        if (parentNode.children[i].name === 'nametext')
            parentNode.children[i].classList.add('active')
        else
            parentNode.children[i].classList.remove('active')
    }
    const div = document.createElement("div")
    div.id = "inputs"

    if (typeof text === 'undefined'){
        text = ''
    }
    else {
        points.push(text)
    }

    div.innerHTML = "<div><input value='"+text+"' type='text' class='my-3 mx-3' placeholder='Введите адрес' name='"+parentNode.parentNode.id+"_adress'></div>" +
        "<div class='d-flex justify-content-center'><input onclick='submitPoint(this.parentNode.parentNode)' type='button' value='Подтвердить' style='width: 50%'></div>"
    parentNode.appendChild(div)
}

function setTypeSelect(parentNode) {
    for (let i = 0; i < parentNode.children.length; i++) {
        if (parentNode.children[i].id === 'inputs')
            parentNode.removeChild(parentNode.children[i])
    }
    for (let i = 0; i < parentNode.children.length; i++) {
        if (parentNode.children[i].name === 'select')
            parentNode.children[i].classList.add('active')
        else
            parentNode.children[i].classList.remove('active')
    }
    const div = document.createElement("div")
    div.id = "inputs"
    div.innerHTML = "<div class='cooridnates row mx-3 my-3' style='--bs-gutter-x: 0'>\n" +
        "               <input class='col me-2' type='text' placeholder='Широта' name='"+parentNode.parentNode.id+"_coord_lat'>\n" +
        "               <input class='col ' type='text' placeholder='Долгота' name='"+parentNode.parentNode.id+"_coord_lon'>\n" +
        "           </div>" +
        "<div class='d-flex justify-content-center'><input onclick='submitPoint(this.parentNode.parentNode)' type='button' value='Подтвердить' style='width: 50%'></div>"
    parentNode.appendChild(div)

    choosePoint(div.children.item(0))
}

function switchDesc(node, desc){
    if (node.classList.contains('addDesc')){
        node.classList.remove('addDesc')
        node.classList.add('removeDesc')
        node.value = 'Убрать описание'
        const divDesc = document.createElement('div');
        divDesc.classList.add('d-flex', 'justify-content-center')
        divDesc.nodeName = name='desc'
        if (typeof desc === 'undefined')
            desc = ''
        divDesc.innerHTML = "<textarea name='" + node.id + "_desc' placeholder='Описание точки' style='width: 95%' '>"+desc+"</textarea>"
        node.parentNode.parentNode.appendChild(divDesc)
    }
    else if (node.classList.contains('removeDesc')){
        node.classList.remove('removeDesc')
        node.classList.add('addDesc')
        node.value = 'Добавить описание'
        node.parentNode.parentNode.removeChild(node.parentNode.parentNode.children[1])
    }
}

function createMap(){
    ymaps.ready(function () {
        myMap = new ymaps.Map('map', {
            center: [56.8519, 60.6122],
            zoom: 11,
        });
        updateMap()
    });
}
function updateMap(){
    if (points.length > 1){
        points.forEach(item => console.log(item));
        var multiRoute = new ymaps.multiRouter.MultiRoute({
            referencePoints: points,
            params: {
                routingMode: "pedestrian"
            }
        }, {
            boundsAutoApply: true
        });
        myMap.geoObjects.add(multiRoute);
    }
}

let coords

function choosePoint(node) {
    let myPlacemark = new ymaps.Placemark([55.76, 37.64])
    myMap.geoObjects.add(myPlacemark);

    myMap.behaviors.disable('click');
    myMap.events.add('click', function(e) {
        coords = e.get('coords');

        myPlacemark.geometry.setCoordinates(coords);
        console.log('выбор точки: ' + coords);

        myMap.events.remove('click');
        myMap.behaviors.enable('click');
    });
}
