class Player {
    constructor(comaId, name, point, position, isBreak, isGoal, myPlayerIndex, nextPlayerIndex) {
        this.comaId = comaId;
        this.name = name;
        this.point = point;
        this.position = position;
        this.isBreak = isBreak;
        this.isGoal = isGoal;
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
                if (phase == "diceRoll") { //サイコロによる移動が終わったときの処理

                    if (this.isGoal == true) {//ゴールした時,ゴールのポップアップを出す．
                        showPopup(setGoalPopup);
                    }
                    else {//ゴールしてないとき，イベントのポップアップを出す
                        showPopup(setEventPopup);
                    }
                }
                else if (phase == "event") {// イベントを実行した後の処理
                    phase = "diceRoll";
                    // while(true){
                    //     if()
                    // };
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

    //サイコロの出目をバックエンドに送って，帰ってきたプレイヤーのステータスをviewに反映
    requestDiceRoll(diceNum,sugorokuId) {
        var xhr = new XMLHttpRequest();
        var URI = "/api/diceRoll?suzi=" + diceNum +"&sugorokuId="+sugorokuId;
        xhr.open("GET", URI, false);
        xhr.send();
        var response = JSON.parse(xhr.responseText);
        let newPosition = response.position;
        let newPoint = response.points;
        let newIsBreak = response.isBreak;
        let newIsGoal = response.isGoaled;
        this.reflectStatus(newPosition, newPoint, newIsBreak, newIsGoal);
    }

    //イベント実行のリクエストをバックエンドに送って，帰ってきたプレイヤーのステータスをviewに反映
    doEvent() {
        var xhr = new XMLHttpRequest();
        var URI = "/api/doEvent?sugorokuId=1";
        xhr.open("GET", URI, false);
        xhr.send();
        var response = JSON.parse(xhr.responseText);
        console.log(response.points);
        let newPosition = response.position;
        let newPoint = response.points;
        let newIsBreak = response.isBreak;
        let newIsGoal = response.isGoaled;
        this.reflectStatus(newPosition, newPoint, newIsBreak, newIsGoal);
    }

    reflectStatus(position, point, isBreak, isGoal) {
        console.log(isGoal);
        //ゴールしたとき
        if (this.isGoal == false && isGoal == true) {
            this.isGoal = true;
        }
        //一回休み
        this.isBreak = isBreak;
        
        //バックエンドでポイントが変化している場合
        if (this.point != point) {
            console.log("現在ポイントの値:" + this.point);
            console.log("新しいポイントの値:" + point);
            console.log(`textarea${this.myPlayerIndex}`);
            document.getElementById(`textarea${this.myPlayerIndex}`).innerText = point;
        }

        //移動
        this.moveCount = position - this.position;
        this.move();
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
        if (this.moveCount == 0) {
            anim = 'notMove';
            console.log("notmove");
        }

        objComa.style.animationName = anim;	// アニメ開始
    }
    setMoveCount(count) {
        this.moveCount = count;
    }
}