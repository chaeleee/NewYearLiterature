window.onload=function(){
}

var canvas = new fabric.Canvas('canvas_ex');
var painter;

function draw(){
    canvas.clear();
    painter = new Painter(canvas);
    painter.article = document.querySelector('#article').value;
    painter.author = document.querySelector('#author').value;
    painter.drawBoth();
}

function saveToImg(){
    let imgUrl = painter.saveToImg();
    window.open(imgUrl);
}

var Painter = function(_fabricCanvas){
    this.fabricCanvas = _fabricCanvas;
    this.article;
    this.author;

    this.articleStyle = {
        fontFamily: 'NanumBarunGothic',
        fontWeight: '800',
        fontSize: '30',
        textAlign: 'center',
    };
    this.authorStyle = {
        fontFamily: 'NanumBarunGothic',
        fontWeight: '500',
        fontSize: '25',
        textAlign: 'center',
    }

    this.saveToImg = function(){
        this.fabricCanvas.isDrawingMode = false;
        // return this.fabricCanvas.toDataURL('png');
        return this.fabricCanvas.toDataURL( {format: 'jpeg', quality: 0.8});
    }


    this.drawBoth = function(){
        this.drawArticle();
        this.drawAuthor();
    }

    this.drawArticle = function(){
        let canvasEle = document.querySelector('#canvas_ex');
        this.articleStyle['top'] = canvasEle.height/5;
        let articleText = new fabric.Text(this.article, this.articleStyle);
        this.fabricCanvas.add(articleText);
        this.fabricCanvas.centerObjectH(articleText);
        
    }

    this.drawAuthor = function(){
        this.authorStyle['top'] = 200;
        this.author = '- ' + this.author + ' -';
        let authorText = new fabric.Text(this.author, this.authorStyle)
        this.fabricCanvas.add(authorText);
        this.fabricCanvas.centerObjectH(authorText);
    }
}