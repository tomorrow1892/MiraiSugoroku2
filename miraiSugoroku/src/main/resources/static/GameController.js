
let player1;
let player2;
let player3;
let player4;
let turnPlayer;
let phase;

class Player {
    constructor(comaId, name, point, position, isBreak, myPlayerIndex, nextPlayerIndex) {
        this.comaId = comaId;
        this.name = name;
        this.point = point;
        this.position = position;
        this.isBreak = isBreak;
        this.moveCount = 0;
        this.nextPlayerIndex = nextPlayerIndex;
        this.myPlayerIndex = myPlayerIndex;


        // animation 終了時の処理
        document.getElementById(comaId).addEventListener('animationend', () => {
            const objComa = document.getElementById(comaId);
            const cssStyle = window.getComputedStyle(objComa);
            const arr = cssStyle.transform.match(/\((.*)\)/)[1].split(",");		// 文字列 matrix(a, b, c, d, cx, cy) を分解
            x += parseInt(arr[4]);	// transform.x の値を加える
            y += parseInt(arr[5]);	// transform.y の値を加える

            objComa.style.transform = '';		// アニメした transform を消す
            objComa.style.left = x + 'px';		// 消した transform の影響を加味した位置を設定
            objComa.style.top = y + 'px';
            objComa.style.animationName = '';	// animationName を消す。こうしないと、次回に同じ方向のアニメが効かない。

            if (this.moveCount > 0) {//進む場合
                this.setMoveCount(this.moveCount - 1);
            }
            if (this.moveCount < 0) {//戻る場合
                this.setMoveCount(this.moveCount + 1);
            }
            console.log(this.comaId + "のmoveCount:" + this.moveCount);
            console.log("現在位置:" + this.position);
            if (this.moveCount == 0) {
                if (phase == "diceRoll") {
                    phase = "event";
                    //alert("イベント発生");
                    switchPopup();
                    // const response = this.requestDoEvent();
                    // let newPosition = response.position;
                    // let newPoint = response.points;
                    // let newIsBreak = response.isBreak;
                    // this.reflectStatus(newPosition,newPoint,newIsBreak);
                    
                }
                else if (phase == "event") {
                    turnPlayer = eval("player" + this.nextPlayerIndex);
                    console.log("ターンプレイヤー:" + turnPlayer.name);
                    btnDisabled("diceBtn1", false);
                }

            }
            else {
                this.move();
            }

        });
    }
    requestDiceRoll(diceNum) {
        var xhr = new XMLHttpRequest();
        var URI = "/api/diceRoll?suzi=" + diceNum;
        xhr.open("GET", URI, false);
        xhr.send();
        var responseObj = JSON.parse(xhr.responseText);
        console.log(responseObj.points);
        return responseObj;
    }

    doEvent() {
        var xhr = new XMLHttpRequest();
        var URI = "/api/doEvent";
        xhr.open("GET", URI, false);
        xhr.send();
        var response = JSON.parse(xhr.responseText);
        console.log(response.points);
        let newPosition = response.position;
        let newPoint = response.points;
        let newIsBreak = response.isBreak;
        this.reflectStatus(newPosition, newPoint, newIsBreak);
    }

    reflectStatus(position, point, isBreak) {
        if (this.position != position) {
            this.moveCount = position - this.position;
            this.move();
        }
        if (this.point != point) {
            console.log("現在ポイントの値:" + this.point);
            console.log("新しいポイントの値:" + point);
            console.log(`textarea${this.myPlayerIndex}`);
            document.getElementById(`textarea${this.myPlayerIndex}`).innerText = point;
        }
    }


    move() {
        const objComa = document.getElementById(this.comaId);
        const cssStyle = window.getComputedStyle(objComa);
        x = parseInt(cssStyle.left.match(/(.*)px/)[1]);	// コマの現在位置を得る
        y = parseInt(cssStyle.top.match(/(.*)px/)[1]);
        let anim;
        if (this.moveCount > 0) {//進む場合
            anim = 'moveRight';
            this.position += 1;
        }
        if (this.moveCount < 0) {
            anim = 'moveLeft';
            this.position -= 1;
        }

        objComa.style.animationName = anim;	// アニメ開始
    }
    setMoveCount(count) {
        this.moveCount = count;
    }
}

//ページが読み込まれて初めに実行される
window.onload = function () {
    btnDisabled("diceBtn1", true);
    player1 = new Player("coma1", "さんだろう", 0, 1, false, 1, 2);
    player2 = new Player("coma2", "よんだろう", 0, 1, false, 2, 3);
    player3 = new Player("coma3", "ごだろう", 0, 1, false, 3, 4);
    player4 = new Player("coma4", "ろくだろう", 0, 1, false, 4, 1);
    turnPlayer = player1;
    let playerPoint = document.getElementById(`point1`);
    playerPoint.insertAdjacentHTML('afterbegin', `${player1.name}のポイント<textarea id='textarea1'>${player1.point}</textarea>`)
    playerPoint = document.getElementById(`point2`);
    playerPoint.insertAdjacentHTML('afterbegin', `${player2.name}のポイント<textarea id='textarea2'>${player2.point}</textarea>`)
    playerPoint = document.getElementById(`point3`);
    playerPoint.insertAdjacentHTML('afterbegin', `${player3.name}のポイント<textarea id='textarea3'>${player3.point}</textarea>`)
    playerPoint = document.getElementById(`point4`);
    playerPoint.insertAdjacentHTML('afterbegin', `${player4.name}のポイント<textarea id='textarea4'>${player4.point}</textarea>`)


}

//開始ボタンが押されたときに実行される
function playGame() {

    //alert(player1.name + "のターン!");
    btnDisabled("diceBtn1", false);
}

