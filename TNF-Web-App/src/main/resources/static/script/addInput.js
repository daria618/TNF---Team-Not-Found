function addInput() {
    const form = document.getElementById("route_configurator");
    const input = document.createElement("input");
    input.type = "text";
    input.name = "input" + (form.getElementsByTagName("input").length + 1);
    form.appendChild(input);
}