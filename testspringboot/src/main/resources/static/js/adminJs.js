const pop_up = document.getElementById("pop_up");

let time = parseInt(document.getElementById("countdown").dataset.time);
const countDownEl = document.getElementById("countdown");

countDownEl.innerHTML = `${Math.floor(time / 60)}:${time % 60 < 10? "0"+time % 60:time % 60}`;

setInterval(updateCountDown, 1000);

function updateCountDown(){
    if (time == 0){
        countDownEl.innerHTML = `0:00`;
        pop_up.classList.add('expired');
    }
    if (time > 0){
        const minutes = Math.floor(time / 60);
        let sec = time-- % 60;
        sec = sec < 10? "0"+sec: sec;
        countDownEl.innerHTML = `${minutes}:${sec}`;
    }
}