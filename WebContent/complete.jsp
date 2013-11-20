<%@ page contentType="text/html;charset=utf-8" %>
<%-- (1)JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- (2)<jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>
<jsp:useBean id="cb" class="jp.dmtc.ing.promo.beans.CompleteBean" scope="session" />
<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>Promia</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="crudefox/css/crudefox.css" rel="stylesheet">

    <style type="text/css">
      body {
        padding-top: 20px;
        padding-bottom: 40px;
      }

      /* Custom container */
      .container-narrow {
        margin: 0 auto;
        max-width: 700px;
      }
      .container-narrow > hr {
        margin: 30px 0;
      }

      /* Main marketing message and sign up button */
      .jumbotron {
        margin: 60px 0;
        text-align: center;
      }
      .jumbotron h1 {
        font-size: 72px;
        line-height: 1;
      }
      .jumbotron .btn {
        font-size: 21px;
        padding: 14px 24px;
      }

      /* Supporting marketing content */
      .marketing {
        margin: 60px 0;
      }
      .marketing p + h4 {
        margin-top: 28px;
      }



    </style>
    <link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="../assets/ico/favicon.png">


    <script type="text/javascript">
      function onImageClick(s) {
    	$('#'+s).attr('checked', !$('#'+s).attr('checked') );
      }
	</script>

  </head>

  <body>

    <div class="container">

      <div class="masthead">
        <ul class="nav nav-pills pull-right">
          <li class="active"><a href="./">Home</a></li>
          <li><a href="#">About</a></li>
          <li><a href="#">Contact</a></li>
        </ul>
        <h3 class="muted">Promoa</h3>
      </div>

      <hr>


      <div class="jumbotron">
        <h1></h1>
        <img src="img/title.jpg" />

        <p class="lead">お疲れ様でした！</p>
        <p class="lead">↓完成です</p>

        <!-- <a class="btn btn-large btn-success" href="./test_all.html">GOi??</a>-->
      </div>

      <hr>

      <form class="form-horizontal text-center" method="post" action="create"  enctype="multipart/form-data" >

      <!--
        <fieldset>
	      <div class="text-center">
		      <label class="control-label" for="src_image">モザイクにしたい顔写真</label>
		      <div class="controls">
		        <input type="file" class="input-xlarge" id="src_image" name="src_image">
		        <p class="help-block"></p>
		      </div>
	      </div>
	    </fieldset>
	    -->


		<%
			out.println("<img src='"+"./img/complete/"+cb.complete_image_name+"' width='500px'>");
		%>

	    <hr>


      <!-- Example row of columns -->
      <div class="row text-center">

      </div>





      <hr>




    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <!--<script src="../assets/js/jquery.js"></script>-->
    <script src="bootstrap/js/bootstrap.min.js"></script>


	<div id="fb-root"></div>
	<script src="http://connect.facebook.net/en_US/all.js"></script>
	<script>
	// Facebook JavaScript SDK初期化
	FB.init({
	    appId  : '510255672403915', ///// ← 解説を参照 /////
	    status : true, // check login status
	    cookie : true, // enable cookies to allow the server to access the session
	    xfbml  : true  // parse XFBML
	});

	// JavaScript SDK初期化時にappIdを指定しなくても取得可
	//（取得時のパラメータにaccess_tokenを指定するため）
	//FB.init();

	function postWall() {


		////////// ↓ 解説を参照 //////////

		// 【client_id】を参照
		var client_id = '510255672403915';
		// 【redirect_uri】を参照
		var redirect_uri = window.location;
		// 【response_type】を参照
		var response_type = 'token';

		////////// ↑ 解説を参照 //////////

		alert(redirect_uri);

		// Graph APIをコール
		var url = 'https://www.facebook.com/dialog/oauth'
		        + '?client_id=' + client_id
		        + '&redirect_uri=' + redirect_uri
		        + '&response_type=' + response_type;

		location.href = url;

	}
	</script>
	<input type="button" value="Facebookで共有！" onclick="" class="btn btn-large btn-success"  id="submit_wall"  name="submit_wall">

<!-- postWall(); -->


	<script type='text/javascript'>



	$('#submit_wall').click(function() {
		  FB.ui(
		    {
		        method: 'feed',
			    message: 'モザイクアート作成したよっ',
		 		name: 'Promoa',
				caption: 'Promoaでモザイクアート作成したよっ',
		 		description: (
		        		'自己表現しよっヽ(=´▽`=)ﾉ'
				),
		       link   : $(this).attr('data-url'),
		       picture: 'http://dmtc.jp/assets/img/mynavi/mynavi_header.jpg'
		    },
		    function (response) {
		     // If response is null the user canceled the dialog
		     if (response != null) {
		      logResponse(response);
		     }
		    }
		   );
		  });





	// ※「アクセストークン取得までの流れ」での解説を参照
	var client_id = '510255672403915'; // 【client_id】

	if (window.location.hash.length == 0) {

	    // Facebook認証後のリダイレクトによるアクセスではない場合
	    // （= URLに "#" がない場合）

	    // Facebook認証に遷移
	    // 例；
	    // Facebook未ログインの場合 → Facebookログイン画面を表示
	    // Facebookログイン済 + アプリを許可済の場合
	    // → "redirect_uri" に指定したURLに、"#" + アクセストークンが
	    // 付加されてリダイレクトされる
	    //var url = 'https://www.facebook.com/dialog/oauth'
	    //        + '?client_id=' + client_id
	    //        + '&redirect_uri=' + window.location
	    //        + '&response_type=token'
	    //        + '&scope=publish_stream,read_stream'
	    //        + '&display=popup';
	    //location.href = url;

	} else {

	    if (window.location.search.length == 0) {

	        // Facebook認証成功の場合

	        var acctok = 'aaaa';

	        // "redirect_uri" に指定したURLに、"#" + アクセストークンが
	        // 付加されてリダイレクトされる
			var a = window.location.hash.substring(1).split('&');
	        for(var i=0;i<a.length;i++){
	        	var name = a[i].split('=')[0];
	        	var val = a[i].split('=')[1];
	        	alert(name);
	        	if(name=='access_token'){
	        		acctok = val;
	        	}
	        }

	        //var acctok = window.location.hash.substring(1).split('&')[1].split('=',2)[1];
	        alert(acctok);
	        console.log(acctok);

	        acctok = "CAAHQEygZAJ8sBAJ0hZAziMPpWafuGWvMJzZCs9mx7CpDdhehR2gN6WejduuBmqoCTi1cB2TUiKOeYjCSLztxkDE2i9943XwdgRfWIZBV9GANnZCVgtjpVIN54WQsJrJnZBP0v3ZAV8Ryq002yUJqrrzZBZBxwpZBBsHk4OwtNQ5sqlkcBncoOiTcXOXWCy70pDMz6ri9U7HZCAIsQZDZD";

		    FB.api('/me/feed' ///// ← 解説を参照 /////
		         , 'post'
		         , {	access_token: ''+acctok,
		    			message: 'test.' ,
		    			 picture : "表示させたい画像のＵＲＬを指定",
		    			link :  "https://apps.facebook.com/",
		    			name : " リンク先のページの名前 ",
		    			caption : "キャプション",
		    			description : "テキスト"
		    		} ///// ← 解説を参照 /////
		         , function(response) {
		               // コールバック
		               if (!response || response.error) {
		                   alert('Error occured ' + response);
		               } else {
		                   alert('Post ID: ' + response.id);
		               }
		           });


	    } else {

	        // アクセス拒否の場合

	        // Facebookアプリのインストール / 権限許可の画面で
	        // "キャンセル" ボタンを押す等した場合、
	        // "redirect_uri" に指定したURLにGETパラメータ
	        // "error" "error_reason" "error_description"
	        // が付加されてリダイレクトされる
	        alert(window.location.search.substring(1));
	    }
	}
	</script>


      <!-- enctype="multipart/form-data" -->
      <!-- <fieldset>
        <button type="submit" class="btn btn-large btn-success">シェア</button>
        <!-- <button type="reset" class="btn">a?-a?￡a?3a?≫a?≪</button>
      </fieldset>
      -->

    </form>



      <hr>

      <div class="footer">
        <p>&copy; ING 2013</p>
      </div>

    </div> <!-- /container -->


    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <!--
    <script src="../assets/js/jquery.js"></script>
    <script src="../assets/js/bootstrap-transition.js"></script>
    <script src="../assets/js/bootstrap-alert.js"></script>
    <script src="../assets/js/bootstrap-modal.js"></script>
    <script src="../assets/js/bootstrap-dropdown.js"></script>
    <script src="../assets/js/bootstrap-scrollspy.js"></script>
    <script src="../assets/js/bootstrap-tab.js"></script>
    <script src="../assets/js/bootstrap-tooltip.js"></script>
    <script src="../assets/js/bootstrap-popover.js"></script>
    <script src="../assets/js/bootstrap-button.js"></script>
    <script src="../assets/js/bootstrap-collapse.js"></script>
    <script src="../assets/js/bootstrap-carousel.js"></script>
    <script src="../assets/js/bootstrap-typeahead.js"></script>
    -->











  </body>
</html>
