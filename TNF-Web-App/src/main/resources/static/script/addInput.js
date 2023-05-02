var blocks = [];

function addPointCoords() {
    const form = document.getElementById("route_configurator");

    const lon = document.createElement("input");
    lon.type = "text";
    lon.classList.add("cord_lon")
    lon.classList.add("indexed")
    lon.placeholder = "Долгота";
    lon.style.width = "50%";
    lon.name = "cord_lon_ind_" + (form.getElementsByClassName("indexed").length);

    const lat = document.createElement("input");
    lat.type = "text";
    lat.classList.add("cord_lat")
    lat.classList.add("indexed")
    lat.placeholder = "Широта";
    lat.style.width = "50%";
    lat.name = "cord_lat_ind_" + (form.getElementsByClassName("indexed").length);

    const newDiv = document.createElement("div");
    newDiv.classList.add("border");
    newDiv.classList.add("p-3");

    const del = document.createElement("button");
    del.onclick = ev => document.removeChild(newDiv);
    del.textContent = "Delete";

    newDiv.appendChild(lon)
    newDiv.appendChild(lat)
    newDiv.appendChild(del)
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
    newDiv.classList.add("border");
    newDiv.classList.add("p-3");
    form.appendChild(newDiv);
}

function selectPoint(){

}

function create(){

}