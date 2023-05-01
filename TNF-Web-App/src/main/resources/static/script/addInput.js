function addInput() {
    const form = document.getElementById("route_configurator");

    const lang = document.createElement("input");
    const lati = document.createElement("input");

    const newDiv = document.createElement("div");

    lang.type = "text";
    lang.classList.add("langClass")
    lati.type = "text";
    lang.classList.add("latiClass")

    lang.name = "lang" + (form.getElementsByClassName("langClass").length);
    lati.name = "lati" + (form.getElementsByClassName("latiClass").length);

    newDiv.appendChild(lang)
    newDiv.appendChild(lati)

    form.appendChild(newDiv);
}