var canvasId = 'canvas_ex';
var articleId = 'article';
var authorId = 'author';
var painter;
var defaultBgColor = 'rgba(255,255,255,1)';

document.addEventListener('DOMContentLoaded', ()=>{
    var canvas = document.getElementById(canvasId);
    var articleText = '아빠 힘내세요\n우리고 있잖아요';
    var authorText = '- 사골국물 -';
    painter = new Painter(canvas, articleText, authorText);
    
    document.getElementById('drawBtn').addEventListener('click', updateText);
    document.getElementById('saveBtn').addEventListener('click', saveToImg);

    document.getElementById('basicThemeBtn').addEventListener('click', ()=>{
        painter.setDefaultTheme();
    });
    document.getElementById('blueThemeBtn').addEventListener('click', ()=>{
        painter.setBlueTheme();
    });
    // document.getElementById('futureThemeBtn').addEventListener('click', ()=>{
    //     painter.setFutureTheme();
    // });

    
});

function updateText(){
    var articleEl = document.getElementById(articleId);
    var authorEl = document.getElementById(authorId);
    painter.updateText(articleEl.value, authorEl.value);
}

function saveToImg(){
    let uri = painter.getImgUri();
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

var Painter = function(canvas, articleText, authorText){
    this.canvas = canvas;
    
    this.articleText = articleText;
    this.articleItext;
    this.articleStyle = {
        fontFamily: 'NanumBarunGothic',
        fontWeight: '800',
        fontSize: '36',
        textAlign: 'center',
        fill: 'black',
        left: 260,
        top: 140,
        originX: 'center',
        originY: 'bottom'
    };

    this.authorText = authorText; 
    this.authorItext;
    this.authorStyle = {
        fontFamily: 'NanumBarunGothic',
        fontWeight: '500',
        fontSize: '26',
        textAlign: 'center',
        fill: 'rgb(102, 102, 102)',
        left: 260,
        top: 180,
        originX: 'center',
        originY: 'top'
    };
    
    this.fabricCanvas;
    this.data;

    this.init = function(){
        this.articleItext = new fabric.IText(this.articleText, this.articleStyle);
        this.authorItext = new fabric.IText(this.authorText, this.authorStyle);

        this.articleItext.on('changed', ()=>{
            document.getElementById('article').value = this.articleItext.text;
        });
        this.authorItext.on('changed', ()=>{
            let text = this.authorItext.text;
            text = text.replace('- ', '');
            text = text.replace(' -', '');
            document.getElementById('author').value = text;
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
        return this.fabricCanvas.toDataURL( {format: 'jpeg', quality: 0.8});

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

    // this.setFutureTheme = function(){
    //     fabric.Image.fromURL('./img/future_bg.jpg', handleImage, {crossOrigin: 'anonymous'});

    //     function handleImage(img) {
    //         this.fabricCanvas.setBackgroundImage(img);
    //         // do whatever extra needs to be done for the background
    //     }

    //     // this.fabricCanvas.setBackgroundImage('./img/future_bg.jpg'
    //     //     , this.fabricCanvas.renderAll.bind(this.fabricCanvas));
    //     const bgColor = 'rgba(255,255,255,1)';
    //     this.fabricCanvas.backgroundColor = bgColor;
    //     this.articleItext.setColor('rgb(0, 0, 0)');
    //     this.authorItext.setColor('rgb(0, 0, 0)');
    //     this.fabricCanvas.renderAll();
    // }



}