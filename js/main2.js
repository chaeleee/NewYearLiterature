document.addEventListener('DOMContentLoaded', ()=>{
    var canvas = document.querySelector('#canvas_ex');
    var articleText = '아빠 힘내세요\n우리고 있잖아요';
    var authorText = '사골국물';
    var painter = new Painter(canvas, articleText, authorText);
        
});

var Painter = function(canvas, articleText, authorText){
    this.canvas = canvas;
    
    this.articleText = articleText;
    this.articleItext;
    this.articleStyle = {
        fontFamily: 'NanumBarunGothic',
        fontWeight: '800',
        fontSize: '30',
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
        fontSize: '20',
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
        this.fabricCanvas = new fabric.Canvas(this.canvas.id);
        this.fabricCanvas.add(this.articleItext);
        this.fabricCanvas.add(this.authorItext);
        this.fabricCanvas.renderAll();
    }
    this.init();

}