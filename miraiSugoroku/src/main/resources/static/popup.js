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
    popupinner.insertAdjacentHTML('afterbegin','<div id="popup-contents" style="position:absolute;left=50%;top=50%;">イベント内容</div>');
  }