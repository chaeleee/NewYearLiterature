// var fabricCanvas;
var painter;
var articleInputEle;
var authorInputEle;
var fabricCanvas;

document.addEventListener('DOMContentLoaded', ()=>{
    articleInputEle = document.querySelector('#article');
    authorInputEle = document.querySelector('#author');
    document.querySelector('#drawBtn').addEventListener('click', ()=>{
        draw();
    });

});


function draw(){
    fabricCanvas = new fabric.Canvas('canvas_ex');
    fabricCanvas.clear();
    fabricCanvas.backgroundColor = 'rgba(255,255,255,1)';
    painter = new Painter(fabricCanvas, 'canvas_ex', articleInputEle.value, authorInputEle.value);
    painter.drawBoth();
}

function saveToImg(){
    let uri = painter.getImgUri();
    // window.open(imgUrl);
    downloadURI(uri, '제출작.jpg');
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

var Painter = function(fabricCanvas, canvasId, articleText, authorText){
    this.canvasId = canvasId;
    this.fabricCanvas = fabricCanvas;
    this.article = articleText;
    this.author = authorText;

    this.articleStyle = {
        fontFamily: 'NanumBarunGothic',
        fontWeight: '800',
        fontSize: '30',
        textAlign: 'center',
        originX: 'center',
        originY: 'center',
        fill: 'black'
    };
    this.authorStyle = {
        fontFamily: 'NanumBarunGothic',
        fontWeight: '500',
        fontSize: '20',
        textAlign: 'center',
        originX: 'center',
        originY: 'center',
        fill: 'rgb(102, 102, 102)'
    }

    this.clear = function(){
        if(this.fabricCanvas !== null) this.fabricCanvas.clear();
    }

    this.getImgUri = function(){
        this.fabricCanvas.isDrawingMode = false;
        // return this.fabricCanvas.toDataURL('png');
        return this.fabricCanvas.toDataURL( {format: 'jpeg', quality: 0.8});
    }


    this.drawBoth = function(){
        this.drawArticle();
        this.drawAuthor();
    }

    this.drawArticle = function(){
        let canvasEle = document.getElementById(this.canvasId);
        this.articleStyle['top'] = (canvasEle.height/5)*2;
        let articleText = new fabric.IText(this.article, this.articleStyle);
        articleText.on('changed', ()=>{
            let text = articleText.get('text');
            this.updateInputValue(text, articleInputEle);
        });
        
        this.fabricCanvas.add(articleText);
        this.fabricCanvas.centerObjectH(articleText);
        this.fabricCanvas.renderAll();
        
    }

    this.drawAuthor = function(){
        let canvasEle = document.getElementById(this.canvasId);
        this.authorStyle['top'] = (canvasEle.height/5)*3.5;
        this.appendAuthorWings();
        let authorText = new fabric.IText(this.author, this.authorStyle);
        authorText.on('changed', ()=>{
            let text = authorText.get('text');
            text = this.removeAuthorWings(text);
            this.updateInputValue(text, authorInputEle);
        });

        this.fabricCanvas.add(authorText);
        this.fabricCanvas.centerObjectH(authorText);
        this.fabricCanvas.renderAll();
    }

    this.updateInputValue = function(text, input){
        input.value = text;
    }

    this.removeAuthorWings = function(text){
        text = text.replace('- ', '');
        text = text.replace(' -', '');
        return text;
    }

    this.appendAuthorWings = function(){
        this.author = '- ' + this.author + ' -';
    }

}