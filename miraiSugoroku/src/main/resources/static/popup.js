function popupImage() {
    var popup = document.getElementById('js-popup');
    if(!popup) return;
  
    var blackBg = document.getElementById('js-black-bg');
    var closeBtn = document.getElementById('js-close-btn');
    var showBtn = document.getElementById('js-show-popup');
    var popupBtn = document.getElementById("popupButton");
  
    closePopUp(blackBg);
    closePopUp(closeBtn);
    closePopUp(showBtn);
    closePopUp(popupBtn);
    function closePopUp(elem) {
      if(!elem) return;
      elem.addEventListener('click', function() {
        popup.classList.toggle('is-show');
        turnPlayer.doEvent();
      });
    }
  }
  popupImage();

  function switchPopup (){
    var popup = document.getElementById('js-popup');
    if(!popup) return;
    popup.classList.toggle('is-show');
  }