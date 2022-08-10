let phase = "diceRoll";
let playerList = [];
let turnPlayer;




// ページが読み込まれて初めに実行される
for (let index = 0; index < n_players; index++) {
    let dtoPlayer = dto_player_list[index];
    let nextPlayerIndex = ((index + 1) % n_players) + 1;
    let player = new Player
        ("coma" + index + 1, dtoPlayer.name, dtoPlayer.point, dtoPlayer.position, dtoPlayer.isBreak, dtoPlayer.isGoal, index + 1, nextPlayerIndex);
    playerList.push(player);
}


turnPlayer = playerList[0];
for (let index = 0; index < n_players; index++) {
    let positionTop = 20*(index+1);
    document.getElementsByTagName("body")[0].insertAdjacentHTML('afterbegin',
    `<div id="point${index+1}" style="position: absolute;left:500px;top:${positionTop}px;"></div>`);
    
    let playerPoint = document.getElementById(`point${index+1}`);
    playerPoint.insertAdjacentHTML
    ('afterbegin',
     `${playerList[index].name}のポイント<input type="text" id='textarea1' readonly value="${playerList[index].point}">`)
}



// window.onload = function () {
//     btnDisabled("diceBtn1", true);
//     player1 = new Player("coma1", "さんだろう", 0, 0, false, false, 1, 2);
//     player2 = new Player("coma2", "よんだろう", 0, 0, false, false, 2, 3);
//     player3 = new Player("coma3", "ごだろう", 0, 0, false, false, 3, 4);
//     player4 = new Player("coma4", "ろくだろう", 0, 0, false, false, 4, 1);
//     turnPlayer = player1;
//     let playerPoint = document.getElementById(`point1`);
//     playerPoint.insertAdjacentHTML('afterbegin', `${player1.name}のポイント<textarea id='textarea1'>${player1.point}</textarea>`)
//     playerPoint = document.getElementById(`point2`);
//     playerPoint.insertAdjacentHTML('afterbegin', `${player2.name}のポイント<textarea id='textarea2'>${player2.point}</textarea>`)
//     playerPoint = document.getElementById(`point3`);
//     playerPoint.insertAdjacentHTML('afterbegin', `${player3.name}のポイント<textarea id='textarea3'>${player3.point}</textarea>`)
//     playerPoint = document.getElementById(`point4`);
//     playerPoint.insertAdjacentHTML('afterbegin', `${player4.name}のポイント<textarea id='textarea4'>${player4.point}</textarea>`)


// }

//開始ボタンが押されたときに実行される
function playGame() {

    //alert(player1.name + "のターン!");
    btnDisabled("diceBtn1", false);
}

