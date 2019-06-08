'user strict';

var app = new Vue({
  el: '#app',
  mounted() {
    this.setAniMainTitle();
    this.setFabricCanvas();
  },
  data:{
    input:{
      articleText: '',
      authorText: ''
    },
    canvas:{
      width: 300,
      height: 300
    },
    fabricManager: null
  },
  computed: {
    articleLength: function(){
      return this.input.articleText.length;
    }
  },
  methods: {
    setAniMainTitle: function(){
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
    },
    saveToImg: function(){
      let uri = this.fabricManager.getImgUri();
      this.downloadURI(uri, '제출작.png');
    },
    setFabricCanvas: function(){
      let canvasInfo = {
        id: 'workCanvas',
        width: this.canvas.width,
        height: this.canvas.height
      }
      this.fabricManager = new FabricManager(canvasInfo, "글을 담아보세요", "이름을 남겨주세요");
    },
    downloadURI: function(uri, filename){
      let link = document.createElement("a");
      link.download = filename;
      link.href = uri;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      link = null;
    },
    limitArticleInput: function(){
      let str = this.input.articleText;
      let lines = this.input.articleText.split("\n");
      if(str.length > 60 | lines > 4){
        this.input.articleText = str.substring(0, 59);
      }
      this.fabricManager.updateArticleText(this.input.articleText);
    },
    limitAuthorInput: function(){
      var str = this.input.authorText;
      if(str.length > 15){
        this.input.authorText = str.substring(0, 14);
      }
      this.fabricManager.updateAuthorText(this.input.authorText);
    },
    toggleModal: function(){
      let isSuccess = true;
      if(isSuccess){
        $('#submission').modal('toggle');
      }
    },
    showCanvas: function(){
      $("#step-2").slideToggle("slow");
    },
    submissionClick: function(){
      this.fabricManager.updateArticleText(this.input.articleText);
      this.fabricManager.updateAuthorText(this.input.authorText);
    },
    initCanvas: function(){
      this.fabricManager.initItext();
    }

  }
});