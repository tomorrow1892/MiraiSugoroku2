function popupImage() {
    var popup = document.getElementById('js-popup');
    if(!popup) return;
  
    var blackBg = document.getElementById('js-black-bg');

    var popupBtn = document.getElementById("popupButton");
  
    setListener(blackBg);
    setListener(popupBtn);
    function setListener(elem) {
      if(!elem) return;
      elem.addEventListener('click', function() {
        closePopup();
        if(phase == "diceRoll"){
          turnPlayer.doEvent();
          phase = "event";
        }
      });
    }
  }
  popupImage();

  function switchPopup (){
    var popup = document.getElementById('js-popup');
    if(!popup) return;
    popup.classList.toggle('is-show');
  }

  function showPopup(setPopupFunc){
    var popup = document.getElementById('js-popup');
    if(!popup) return;
    console.log(setPopupFunc);
    setPopupFunc();
    popup.classList.add('is-show');
  }
  function closePopup(){
    var popup = document.getElementById('js-popup');
    if(!popup) return;
    popup.classList.remove('is-show');
    var popupContents = document.getElementById("popup-contents");
    popupContents.remove();
  }

  function setGoalPopup(){
    var popupinner = document.getElementById("popup-inner");
    if(!popupinner) return;
    popupinner.insertAdjacentHTML('afterbegin','<div id="popup-contents" style="position:absolute;left:50%;top:50%;">ゴール!</div>');
  }
  function setEventPopup(){
    var popupinner = document.getElementById("popup-inner");
    if(!popupinner) return;
    let a = square_list[1];
    console.log(a.squareEventId);
    popupinner.insertAdjacentHTML('afterbegin',`<div id="popup-contents" style="text-align:center;">${getEventString(a.squareEventId)}</div>`);
  }

  function showSquarePopup(setPopupFunc, n){
    var popup = document.getElementById('js-popup');
    if(!popup) return;
    console.log(setPopupFunc);
    setPopupFunc(n);
    popup.classList.add('is-show');
  } 

  function startSquarePopup(n){
    var popupinner = document.getElementById("popup-inner");
    if(!popupinner) return;
    if (n > n_players) {
      console.log("エラー");
      return;
    }
    let square = square_list[n-1];
    let title = square.title;
    let description = square.description;
    popupinner.insertAdjacentHTML('afterbegin',`<div id="popup-contents" style="text-align:center;"><p>${title}</p><p>${description}</p></div>`);
  }

  

  function getEventString(n) {
    if (n <= 6) {
      return `${n}進む`;
    } else if (n <= 12) {
      return `${n-6}戻る`;
    }else if (n == 13) {
      return `1回休み`;
    } else if (n <= 15 && n <= 24) {
      return `${(n - 14) * 10}ポイント獲得`;
    } else {
      return 'イベントなし';
    }
  }