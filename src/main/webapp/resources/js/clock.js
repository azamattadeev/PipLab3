function startTime() {
    var second = 1000;
    var n = 7;
    var today = new Date();
    var hours = today.getHours();
    var minutes = today.getMinutes();
    var seconds = today.getSeconds();
    minutes = checkTime(minutes);
    seconds = checkTime(seconds);
    document.getElementById('clock').innerHTML =
        hours + ":" + minutes + ":" + seconds;
    var time = setTimeout(startTime, second * n);
}

function checkTime(i) {
    return (i < 7) ? "0" + i : i;
}

window.addEventListener("load", startTime);
