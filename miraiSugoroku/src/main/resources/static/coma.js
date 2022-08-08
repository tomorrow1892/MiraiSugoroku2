var x,y; //コマの座標
var moveCount = 0; //進むマスの残り数
function setMoveCount(count){
    moveCount = count;
}

function move(id){
    if(moveCount != 0){
        const objComa = document.getElementById(id);
        const cssStyle = window.getComputedStyle(objComa);
        x = parseInt(cssStyle.left.match(/(.*)px/)[1]);	// コマの現在位置を得る
        y = parseInt(cssStyle.top.match(/(.*)px/)[1]);
        let anim = 'moveRight';
        objComa.style.animationName = anim;	// アニメ開始
    }
    
}


function btnDisabled(id,arg) {
	let btn = document.getElementById(id);
    console.log(btn.disabled);
		btn.disabled = arg;
}
