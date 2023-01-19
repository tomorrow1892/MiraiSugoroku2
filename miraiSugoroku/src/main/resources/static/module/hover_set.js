

function setButtonHover(id, imgPath_nohover, imgPath_hover) {


    //デプロイ用設定
    const deployedPath = "https://es4.eedept.kobe-u.ac.jp/miraisugoroku"
    //ローカル用設定．
    //const deployedPath = "";
    const button = document.getElementById(id)
    //マウスを上に置いたとき
    button.addEventListener("mouseenter", (e) => { button.setAttribute("src", deployedPath + imgPath_hover) })
    //マウスが離れたとき
    button.addEventListener("mouseleave", (e) => { button.setAttribute("src", deployedPath + imgPath_nohover) })
    //指で長押ししたとき(タブレット・スマホ用)
    button.addEventListener("touchstart", (e) => { button.setAttribute("src", deployedPath + imgPath_hover) })
    //長押しをやめたとき(タブレット・スマホ用)
    button.addEventListener("touchend", (e) => { button.setAttribute("src", deployedPath + imgPath_nohover) })
}