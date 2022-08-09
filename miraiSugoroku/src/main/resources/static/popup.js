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
    var popupinner = document.getElementById("popup-inner");
    popupinner.removeChild(popupinner.firstElementChild);
  }

  function setGoalPopup(){
    var popupinner = document.getElementById("popup-inner");
    if(!popupinner) return;
    popupinner.insertAdjacentHTML('afterbegin','<div>ゴール!</div>');
  }
  function setEventPopup(){
    var popupinner = document.getElementById("popup-inner");
    if(!popupinner) return;
    popupinner.insertAdjacentHTML('afterbegin','<div>イベント内容</div>');
  }