'use strict';
class FabricManager{
  constructor(canvas, articleText, authorText){
    this.canvas = {
        id: canvas.id,
        width: canvas.width,
        height: canvas.height
    };
    this.article = {
      text: articleText,
      itext: null,
      style: {
        fontFamily: 'NanumMyeongjo',
        fontSize: '26',
        textAlign: 'center',
        fill: 'black',
        left: 0,
        top: 0,
        originX: 'center',
        originY: 'bottom',
        fontWeight: '500'
      }
    }
    this.author = {
      text: authorText,
      itext: null,
      style: {
        fontFamily: 'NanumMyeongjo',
        fontSize: '20',
        textAlign: 'center',
        fill: 'rgb(102, 102, 102)',
        left: 0,
        top: 0,
        originX: 'center',
        originY: 'bottom',
        fontWeight: '500',
        // originY: 'top'
      }
    }
    this.initFabricCanvas();
    this.initItext();
  }

  initFabricCanvas(){
    this.fabricCanvas = new fabric.Canvas(this.canvas.id);
    this.fabricCanvas.backgroundColor = 'rgba(255,255,255,1)';
    // this.fabricCanvas.renderAll();
  }

  initItext(){
    this.article.style.left = this.canvas.width / 2;
    this.article.style.top = this.canvas.height / 1.8 ;
    this.author.style.left = this.canvas.width / 2;
    this.author.style.top = this.canvas.height / 1.2;
    this.article.itext = new fabric.IText(this.article.text, this.article.style);
    this.author.itext = new fabric.IText(this.author.text, this.author.style);
    this.fabricCanvas.add(this.article.itext);
    this.fabricCanvas.add(this.author.itext);
    this.fabricCanvas.renderAll();
  }

  renderFabricCanvas(){
    this.fabricCanvas.renderAll();
  }

  addArticleUpdatedEventHandler(callback){
    this.article.itext.on('changed', callback);
  }

  addAuthorUpdatedEventHandelr(callback){
    this.author.itext.on('changed', callback);
  }

  updateArticleText(articleText){
    if(!articleText) return;
    this.article.text = articleText;
    this.article.itext.text = articleText;
    this.renderFabricCanvas();
  }

  updateAuthorText(authorText){
    if(!authorText) return;
    authorText = '- ' + authorText + ' -';
    this.author.text = authorText;
    this.author.itext.text = authorText;
    this.renderFabricCanvas();
  }

  getImgUri(){
      this.fabricCanvas.isDrawingMode = false;
      return this.fabricCanvas.toDataURL( {format: 'png', multiplier:2});
      // return this.fabricCanvas.toDataURL( {format: 'png', quality: 1.0, multiplier:2});
  }

  setDefaultTheme(){
      this.fabricCanvas.setBackgroundImage(null
          , this.fabricCanvas.renderAll.bind(this.fabricCanvas));

      const bgColor = 'rgba(255,255,255,1)';
      this.fabricCanvas.backgroundColor = bgColor;
      this.article.itext.setColor('rgb(0, 0, 0)');
      this.author.itext.setColor('rgb(0, 0, 0)');
      this.fabricCanvas.renderAll();
  }

  setBlueTheme(){
      this.fabricCanvas.setBackgroundImage(null
          , this.fabricCanvas.renderAll.bind(this.fabricCanvas));
      const bgColor = 'rgb(28,31,135)';
      this.fabricCanvas.backgroundColor = bgColor;
      this.article.itext.setColor('rgba(255,255,255, 1)');
      this.author.itext.setColor('rgba(255,255,255, 0.8)');
      this.fabricCanvas.renderAll();
  }

}