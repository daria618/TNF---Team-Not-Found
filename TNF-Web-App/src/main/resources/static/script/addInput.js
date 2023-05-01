function addPointCoords() {
    const form = document.getElementById("route_configurator");

    const lon = document.createElement("input");
    lon.type = "text";
    lon.classList.add("cord_lon")
    lon.classList.add("indexed")
    lon.placeholder = "Долгота";
    lon.name = "cord_lon_ind_" + (form.getElementsByClassName("indexed").length);

    const lat = document.createElement("input");
    lat.type = "text";
    lat.classList.add("cord_lat")
    lon.classList.add("indexed")
    lat.placeholder = "Широта";
    lat.name = "cord_lat_ind_" + (form.getElementsByClassName("indexed").length);

    const newDiv = document.createElement("div");

    newDiv.appendChild(lon)
    newDiv.appendChild(lat)
    form.appendChild(newDiv);
}
function addPointName(){
    const form = document.getElementById("route_configurator");
    const name = document.createElement("input");
    name.type = "text";
    name.classList.add("name_name")
    name.classList.add("indexed")
    name.placeholder = "название точки/объекта";
    name.name = "name_name_ind_" + (form.getElementsByClassName("indexed").length);
    const newDiv = document.createElement("div");
    newDiv.appendChild(name)
    form.appendChild(newDiv);
}

function selectPoint(){

}