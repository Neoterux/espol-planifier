// ==UserScript==
// @name         Panifier extractor
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  Make a json compatible for planifier espol software
// @author       Neoterux
// @match        https://www.academico.espol.edu.ec/UI/Registros/MateriaPlanificada.aspx?*
// @icon         https://www.google.com/s2/favicons?domain=edu.ec
// @grant        none
// ==/UserScript==


let days = ["", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", ""]

function externalFormJSON(raw){
    let out = raw.replace('{', "{\n\t");
    out.replace(',', ",\n");
    out.replace('}', "\n}");
    return out;
}

function copyClipboard(text){
    navigator.clipboard.writeText(text).then(function() {
        console.log("Async: copy to clipboard was successful");
    }, function(err){
        console.error("Cannot copy json to clipboard");
    });
}

function parseDate(date){
    let values = date.split("/");
    console.log("target date: ", date);
    var dt = new Date(values[2], values[1] - 1, values[0]);
    console.log("date object: ", dt);
    return dt;
}

function sleep(milliseconds) {
 var start = new Date().getTime();
 for (var i = 0; i < 1e7; i++) {
  if ((new Date().getTime() - start) > milliseconds) {
   break;
  }
 }
}

function txtbyId(id){
    let target = document.getElementById(id);
    if (target){
        return target.textContent;
    }
    return "";
}

let createJSON = () => {
    console.log('button pressed');
    let json = {
        "id": txtbyId("ctl00_contenido_LabelParalelo"),
        "teacher-name": txtbyId("ctl00_contenido_LabelProfesor"),
        "class-no" :"",
        "daily-schedule": [],
        "exam-schedule": null
    }
    let ex_data = txtbyId("ctl00_contenido_LabelParcial").split(" - ");
    let ex_hour = ex_data[1].split(" a ")
    json["exam-schedule"] = {
        day: days[parseDate(ex_data[0]).getUTCDay()],
        "init-hour": ex_hour[0],
        "end-hour": ex_hour[1]
    }
    let htable = document.getElementById("ctl00_contenido_TableHorarios");
    var r = 1
    let courses = []
    var row
    while(row = htable.rows[r++]){
        let arr = row.childNodes;
        let schedule = {
            "day": arr[1].innerText,
            "init-hour": arr[2].innerText.substring(0,5),
            "end-hour": arr[3].innerText.substring(0,5)
        };
        courses.push(arr[4].innerText);
        json["daily-schedule"].push(schedule)
    }
    courses = courses.filter((v, i, a) => a.indexOf(v) === i);
    json['class-no'] = courses.join("-");
    console.log(json);
    console.log("JSON String");
    let json_txt = JSON.stringify(json, null, "\t");
    console.log(json_txt);
    copyClipboard(json_txt);
}

(function() {
    'use strict';
    window.onbeforeunload = function() {
        return "Dude, are you sure you want to leave? Think of the kittens!";
    }
    let copyButton = document.createElement("button");
    copyButton.innerText = "Copy JSON";
    copyButton.onclick = createJSON;
    let container = document.getElementById("ctl00_contenido_LabelDirigido");
    container.appendChild(copyButton);
})();

