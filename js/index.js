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
var authorInput = document.querySelector("#author-input");

var textCount = document.querySelector("#article-count");
articleInput.addEventListener("keyup", ()=>{
  var str = articleInput.value;
  var lines = articleInput.value.split("\n");
  if(str.length > 60 | lines > 4){
    articleInput.value = str.substring(0, 59);
  }
  textCount.innerText = str.length;
});


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
    draw();
  }
});

// 입력값 실시간 업데이트
articleInput.addEventListener('keyup',()=>{
  draw();
});
authorInput.addEventListener('keyup',()=>{
  draw();
});


// 이미지 생성

var article = "";
var author = "";
var workCanvas = document.querySelector('#workCanvas');
var painter = new Painter(workCanvas, "당신의 이야기를 \n별에 담아", "작가명");
var downloadBtn = document.querySelector('#download-btn');
downloadBtn.addEventListener("click",()=>{
  saveToImg(painter);
});

function saveToImg(painter){
  let uri = painter.getImgUri();
  downloadURI(uri, '제출작.png');
}

function downloadURI(uri, name) {
  var link = document.createElement("a");
  link.download = name;
  link.href = uri;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  delete link;
}

// 캔버스에 그리기
function draw(){
  let article = document.querySelector('#article-input').value;
  let author = document.querySelector('#author-input').value;
  painter.updateText(article, author);
}

// 이미지데이터화
function exportImg(){}

function Painter(canvas, articleText, authorText){
  this.canvas = canvas;
  
  this.articleText = articleText;
  this.articleItext;
  this.articleStyle = {
      fontFamily: 'NanumGothic',
      // fontWeight: '500',
      fontSize: '26',
      textAlign: 'center',
      fill: 'black',
      left: 0,
      top: 0,
      originX: 'center',
      originY: 'bottom'
  };

  this.authorText = authorText; 
  this.authorItext;
  this.authorStyle = {
      fontFamily: 'NanumGothic',
      // fontWeight: '500',
      fontSize: '20',
      textAlign: 'center',
      fill: 'rgb(102, 102, 102)',
      left: 0,
      top: 0,
      originX: 'center',
      originY: 'bottom'
      // originY: 'top'
  };
  
  this.fabricCanvas;
  this.data;

  this.init = function(){
      this.articleStyle.left = this.canvas.width / 2;
      this.articleStyle.top = this.canvas.height / 1.8 ;
      this.articleItext = new fabric.IText(this.articleText, this.articleStyle);
      this.authorStyle.left = this.canvas.width / 2;
      this.authorStyle.top = this.canvas.height / 1.2;
      this.authorItext = new fabric.IText(this.authorText, this.authorStyle);

      this.articleItext.on('changed', ()=>{
          document.getElementById('article-input').value = this.articleItext.text;
      });
      this.authorItext.on('changed', ()=>{
          let text = this.authorItext.text;
          text = text.replace('- ', '');
          text = text.replace(' -', '');
          document.getElementById('author-input').value = text;
      });

      this.fabricCanvas = new fabric.Canvas(this.canvas.id);
      this.fabricCanvas.backgroundColor = 'rgba(255,255,255,1)';
      this.fabricCanvas.add(this.articleItext);
      this.fabricCanvas.add(this.authorItext);
      this.fabricCanvas.renderAll();
  }
  this.init();

  this.updateText = function(articleText, authorText){
      if(articleText) {
      this.articleItext.text = articleText;
      }
      if(authorText){
          authorText = '- ' + authorText + ' -';
          this.authorItext.text = authorText;
      }
      this.fabricCanvas.renderAll();
  }

  this.getImgUri = function(){
      this.fabricCanvas.isDrawingMode = false;
      return this.fabricCanvas.toDataURL( {format: 'png', multiplier:2});
      // return this.fabricCanvas.toDataURL( {format: 'png', quality: 1.0, multiplier:2});

      //fabric.Image.fromURL
  }

  this.setDefaultTheme = function(){
      this.fabricCanvas.setBackgroundImage(null
          , this.fabricCanvas.renderAll.bind(this.fabricCanvas));

      const bgColor = 'rgba(255,255,255,1)';
      this.fabricCanvas.backgroundColor = bgColor;
      this.articleItext.setColor('rgb(0, 0, 0)');
      this.authorItext.setColor('rgb(0, 0, 0)');
      this.fabricCanvas.renderAll();
  }

  this.setBlueTheme = function(){
      this.fabricCanvas.setBackgroundImage(null
          , this.fabricCanvas.renderAll.bind(this.fabricCanvas));
      const bgColor = 'rgb(28,31,135)';
      this.fabricCanvas.backgroundColor = bgColor;
      this.articleItext.setColor('rgba(255,255,255, 1)');
      this.authorItext.setColor('rgba(255,255,255, 0.8)');
      this.fabricCanvas.renderAll();
  }


}