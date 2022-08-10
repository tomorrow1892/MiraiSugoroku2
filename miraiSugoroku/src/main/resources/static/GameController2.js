let phase = "diceRoll";
let playerList = [];
let turnPlayer;

window.onload = function () {
    //コマを生成
// for (let index = 0; index < n_players; index++) {
//     let imgPath = prefix + dto_player_list[index].icon
//    console.log(`<img th:src="@{${dto_player_list[index].icon}}" class="coma" id="coma${index + 1}" width="50px" height="50px">`);
//    document.getElementsByTagName("body")[0].insertAdjacentHTML('afterbegin',
//        `<img th:src="${imgPath}" class="coma" id="coma${index + 1}" width="50px" height="50px">`);
// }

// ページが読み込まれて初めに実行される
for (let index = 0; index < n_players; index++) {
   let dtoPlayer = dto_player_list[index];
   let nextPlayerIndex = ((index + 1) % n_players);

   let comaId = "coma" + (index + 1);
   let player = new Player
       (comaId, dtoPlayer.name, dtoPlayer.points, dtoPlayer.position, dtoPlayer.isBreak, dtoPlayer.isGoaled, index, nextPlayerIndex);
   playerList.push(player);
}


turnPlayer = playerList[0];
for (let index = 0; index < n_players; index++) {
   let positionTop = 20 * (index + 1);
   document.getElementsByTagName("body")[0].insertAdjacentHTML('afterbegin',
       `<div id="point${index + 1}" style="position: absolute;left:500px;top:${positionTop}px;"></div>`);

   let playerPoint = document.getElementById(`point${index + 1}`);
   playerPoint.insertAdjacentHTML
       ('afterbegin',
           `${dto_player_list[index].name}のポイント<input type="text" id='textarea1' readonly value="${dto_player_list[index].points}">`)
}

for (let index = 0; index < n_players; index++) {
    let masu_start_position_top = document.getElementById(`masu_start`).getBoundingClientRect().top;
    let masu_start_position_left = document.getElementById("masu_start").getBoundingClientRect().left;
    let masu_start_width = document.getElementById("masu_start").getBoundingClientRect().width;
    let masu_start_height = document.getElementById("masu_start").getBoundingClientRect().height;
    let element = document.getElementById(`coma${index + 1}`);
    element.style.position = "absolute";
    element.style.top = masu_start_position_top + masu_start_height / 2 + index * 20 -30;
    element.style.left = masu_start_position_left + masu_start_width / 2 + index * 20 -30;
}

console.log(turnPlayer);
}


function playGame() {

    //alert(player1.name + "のターン!");
    btnDisabled("diceBtn1", false);
}

