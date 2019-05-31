// refer: https://tobiasahlin.com/moving-letters/#3
$('.ml3').each(function(){
  $(this).html($(this).text().replace(/([^\x00-\x80]|\w)/g, "<span class='letter'>$&</span>"));
});

anime.timeline({loop: true})
  .add({
    targets: '.ml3 .letter',
    opacity: [0,1],
    easing: "easeInOutQuad",
    duration: 2250,
    delay: function(el, i) {
      return 150 * (i+1)
    }
  }).add({
    targets: '.ml3',
    opacity: 0,
    duration: 1000,
    easing: "easeOutExpo",
    delay: 2000
  });

  //60자

var articleInput = document.querySelector("#article-input");

var textCount = document.querySelector("#article-count");
articleInput.addEventListener("keyup", ()=>{
  var str = articleInput.value;
  var lines = articleInput.value.split("\n");
  if(str.length > 60 | lines > 4){
    articleInput.value = str.substring(0, 59);
  }
  textCount.innerText = str.length;
});

var authorInput = document.querySelector("#author-input");

authorInput.addEventListener("keyup", ()=>{
  var str = authorInput.value;
  if(str.length > 15){
    authorInput.value = str.substring(0, 14);
  }
});

var text = $("#theTextArea").val();
var match = /\r|\n/.exec(text);
if (match) {
    // Found one, look at `match` for details, in particular `match.index`
}

var submitBtn = document.querySelector('#submit-btn');
submitBtn.addEventListener('click',function(){
  let isSuccess = true;
  if(isSuccess){
    $('#submission').modal('toggle');
  }
});