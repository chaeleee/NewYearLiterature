'user strict';
/**
 * 메인 타이틀
 */
var MainTitle = {
  methods: {
    setAniMainTitle(){
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
    }
  },
  mounted() {
    this.setAniMainTitle();
  },
};

/**
 * 작품 입력 컴포넌트
 */
var WorkInput = {
  props: ['work', 'displayWorkCanvas'],
  data(){
    return {}
  },
  methods: {
    limitArticleInput(){
      let str = this.work.article;
      let lines = this.work.article.split("\n");
      if(str.length > 60 | lines > 4){
        this.work.article = str.substring(0, 59);
      }
    },
    limitAuthorInput(){
      var str = this.work.author;
      if(str.length > 15){
        this.work.author = str.substring(0, 14);
      }
    },
    submitClickHandler(){
      this.$emit('completeInput');
    },

  },
  computed: {
    articleLength: function(){
      return this.work.article.length;
     }
  }
};

/**
 * 응모 컴포넌트
 */
var WorkSubmit = {
  props: ['work'],
  data(){
    return {
      host: 'http://localhost:8080',
      uri: '/api/work',
      user:{
        email: 'email',
        password: 'password'
      }
    };
  },
  methods: {
    submitHandler: function(){
      if(this.isValidEmail()){
        if(this.isValidPassword()){
          this.submitWork(this.successHandler);
        }else{
          alert("비밀번호 형식에 맞춰 입력해주세요.")
        }
      }else{
        alert("중복된 이메일입니다.")
      }
    },
    isValidEmail: function(){
      return true;
    },
    isValidPassword: function(){
      return true;
    },
    submitWork: function(successHandler){
      this.work.user = this.user;
      $.ajax({
        type: "POST",
        url: this.host + this.uri,
        data: JSON.stringify(this.work),
        success: successHandler,
        dataType: 'json',
        contentType: 'application/json'
      });
    },
    successHandler: function(){
      if(isSuccess){
        alert("소중한 작품 응모 감사합니다");
        this.toggleSubmissionModal();
      }else{
        alert("다시 시도해주세요~");
      }
    }
  },
  
}


/**
 *  캔버스 이미지화 컴포넌트
 */
var WorkCanvas = {
  props:{
    'work': Object,
    'displayWorkCanvas': Boolean,
  },
  data() {
    return {
      canvas:{
        width: 300,
        height: 300
      },
      fabricManager: null,
      downloadBtn: {isHide: true},
    }
  },
  methods: {
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
    initCanvas: function(){
      this.fabricManager.initItext();
    },
    toggleSubmissionModal: function(){
        $('#submission').modal('toggle');
        this.downloadBtn.isHide = true;
        this.showCanvas();
    },
  },
  watch:{
    'work.article': function(article){
      this.fabricManager.updateArticleText(article);
    },
    'work.author': function(author){
      this.fabricManager.updateAuthorText(author);
    }
  },
  mounted() {
    this.setFabricCanvas();
  },
}

/**
 * ROOT
 */
var app = new Vue({
  el: '#app',
  components: {
    'main-title': MainTitle,
    'work-input': WorkInput,
    'work-submit': WorkSubmit,
    'work-canvas': WorkCanvas,
  },
  data:{
      work:{
        article: "",
        author: ""
      },
      displayWorkCanvas: false,
  },
  methods: {
    articleInput(articleText){
      this.work.article = articleText;
    },
    authorInput(authorText){
      this.work.author = authorText;
    },
    completeInput(){
      $('#submission').modal('toggle');
    },
    toggleCanvas(){
      if(this.displayWorkCanvas){
        $("#step-2").slideUp("slow");
        this.displayWorkCanvas = false;
      }else{
        $("#step-2").slideDown("slow");
        this.displayWorkCanvas = true;
      }
    },
  },
  mounted() {
    
  },
});




