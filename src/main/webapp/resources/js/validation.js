let x;
let y;
let xcoor;
let ycoor;

var example = document.getElementById("canvas");
ctx = example.getContext('2d');
pic = new Image();
example.width = 230;
example.height = 215;

pic.src = "resources/images/area.png";

$(document).ready(function () {
    drawPoints();
});


function drawPoints() {
    for (var i = 0; i < pointsArray.length; i++) {
        let x = pointsArray[i][0];
        let y = pointsArray[i][1];
        let r = takeR();
        ctx.fillStyle="red";
        if (x >= 0 && y >= 0 && y <= r && x <= r / 2) ctx.fillStyle="green";
        if (x >= 0 && y <= 0 && y >= x - r) ctx.fillStyle="green";
        if (x <= 0 && y <= 0 && x * x + y * y <= r * r / 4) ctx.fillStyle="green";
        ctx.fillRect((x/takeR())*79+107,(y/takeR())*(-79)+105,6,6);
    }
}

function coordinaty(event) {
    ctx.fillStyle="black";
    x = event.offsetX;
    y = event.offsetY;
    xcoor = (((x - 110) / 79)*takeR());
    ycoor = (-((y - 107) / 79)*takeR());

    document.getElementById('hiddenForm:xValue11').value = xcoor.toFixed(4);
    document.getElementById('hiddenForm:yValue11').value = ycoor.toFixed(4);
    document.getElementById('hiddenForm:rValue11').value = takeR();
    document.getElementById('canvas').setAttribute('onclick','coordinaty(event)');
    document.getElementById('hiddenForm:linkonclick').click();
}

function validate() {
    if (checkY() === false) {
        return false;
    }
    ajaxHead();

}

function checkX() {
    var xError = document.getElementById("x_label");
    var objValues = document.getElementsByName('xValue');
    var xValue;
    for (var i = 0; i < objValues.length; i++) {
        if (objValues[i].checked) {
            xValue = objValues[i];
            xError.style.color = "black";
            return true;
        }
    }
    xError.style.color = "red";
    return false;
}
$( ".rValue" ).change(function() {
    ctx.clearRect(0,0,230,215);
    drawPoints();
});


function checkY() {
    var yError = document.getElementById('submit-form:y_error');
    var yValue = document.getElementById('submit-form:yValue').value.replace(/\s+/g, '').replace(',', '.');
    parseFloat(yValue);
    if (isNaN(yValue) || yValue <= -3 || yValue >= 5 || yValue === "") {
        yError.style.color = "red";
        return false;
    }
    else {
        yError.style.color = "black";
        return true;
    }
}

function takeR() {
    var select = document.getElementById("submit-form:rValue");
    var value = select.value;
    return value;
}

function errorString(String) {
    document.getElementById('error-Container').innerHTML=String;
}

function offline(event) {
    ajaxHead1();
     let xy = event.offsetX;
     let yx = event.offsetY;
    ctx.fillStyle="gray";
    ctx.fillRect(xy,yx,5,5);

}

function ajaxHead1() {
    $.ajax({
        type: "HEAD",
        timeout: 5000,
        url: "form.jsp",
        success: function () {
            document.getElementById('canvas').setAttribute('onclick','coordinaty(event)');
            document.getElementById('hiddenForm').submit();

        },
        error: function (jqXHR, textStatus) {
            if (textStatus === 'error') {
                document.getElementById('canvas').setAttribute('onclick','offline(event)');
                errorString("Упс, Упал сервер");

            }
        },
    });
}
function ajaxHead() {
    $.ajax({
        type: "HEAD",
        timeout: 5000,
        url: "form.jsp",
        success: function () {
            document.getElementById('canvas').setAttribute('onclick','coordinaty(event)')
            document.getElementById("submit-form").submit();
        },
        error: function (jqXHR, textStatus) {
            if (textStatus === 'error') {
                errorString("Упс, Упал сервер");
                document.getElementById('canvas').setAttribute('onclick','offline(event)')
            }
        },
    });
}
