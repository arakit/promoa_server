<%@ page contentType="text/html;charset=utf-8" %>
<%-- (1)JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- (2)<jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>Promoa</title>
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

      .red-text {
        color: red;
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

    <!-- Placed at the end of the document so the pages load faster -->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>-->
    <!--<script src="../assets/js/jquery.js"></script>-->
    <!-- <script src="bootstrap/js/bootstrap.min.js"></script>-->


    <div class="container">

      <div class="masthead">
        <ul class="nav nav-pills pull-right">
          <li class="active"><a href="#">Home</a></li>
          <li><a href="#">About</a></li>
          <li><a href="#">Contact</a></li>
        </ul>
        <h3 class="muted">Promoa</h3>
      </div>

      <hr>

      <div class="jumbotron">
        <h1></h1>
        <img class="mmm" src="img/title.jpg" />
        <p class="lead">モザイクアート作成サービス</p>
        <!-- <a class="btn btn-large btn-success" href="./test_all.html">GOi??</a> -->
      </div>



	<div class="row">
	  <div class="span1"></div>
	  <div class="span10 columns">
		  <div id="myCarousel" class="carousel slide" >
		    <!-- Carousel items -->
		    <div class="carousel-inner">
		      <!-- item1 -->
		      <div class="active item">
		        <img src="./img/carousel_test_1.jpg" alt="" width="870" height="500">
		      </div>
		      <!-- item2 -->
		      <div class="item">
		        <img src="./img/carousel_test_2.jpg" alt="" width="870" height="500">
		      </div>
		      <!-- item3 -->
		      <div class="item">
		        <img src="./img/carousel_test_3.jpg" alt="" width="870" height="500">
		      </div>
		    </div>
		    <!-- Carousel nav -->
		    <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
		    <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
	      </div>
      </div>
      <div class="span1"></div>
    </div>


      <hr>

      <form class="form-horizontal text-center" method="post" action="next1"  enctype="multipart/form-data" >

      <h3>人とは違う<span class='red-text'>ユニーク</span>なプロフィール画像<br />作ってみませんか？</h3>



      <br />
      <br />


		<!--
        <fieldset>
	      <div class="text-center">
		      <!-- <label class="control-label" for="src_image">モザイクにしたい顔写真</label>
		      <div class="controls">
		        <input type="file" class="input-xlarge" id="src_image" name="src_image" style="display: none;">

				<div class="input-prepend">
				  <a class="btn" onclick="$('#src_image').click();"><i class="icon-folder-open"></i></a>
				  <span id="src_image_cover" class="input-xlarge uneditable-input">画像を選択</span>
				</div>

				<script type="text/javascript">
				  $('#src_image').change(function() {
				      $('#src_image_cover').html($(this).val());
				  });
				</script>

		        <p class="help-block"></p>
		      </div>
	      </div>
	    </fieldset>
	    -->

        <fieldset>
			<input type="file" id="src_image" name="src_image"  style="display: none;"><br>
			<div class="input-prepend">
		  		<a class="btn" onclick="$('#src_image').click();"><i class="icon-folder-open"></i></a>
		  		<span id="src_image_cover"  name="src_image_cover"  class="input-xlarge uneditable-input">画像選択</span>
			</div>
		</fieldset>

		<script type="text/javascript">



		</script>

	    <br />
	    <br />

      <!-- Example row of columns -->


      <!-- enctype="multipart/form-data" -->
      <fieldset>
        <button type="submit" class="btn btn-large btn-warning">NEXT</button>
        <!-- <button type="reset" class="btn">a?-a?￡a?3a?≫a?≪</button>-->
      </fieldset>
    </form>

    <br />
    <br />
    <br />
    <br />
    <br />
    <br />	    <br />
    <br />
    <br />
    <br />	    <br />
    <br />
    <br />
    <br />

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
    
    <script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="common/js/prettify.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-transition.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-alert.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-modal.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-dropdown.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-scrollspy.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-tab.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-tooltip.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-popover.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-button.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-collapse.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-carousel.js"></script>
    <script type="text/javascript" src="common/js/bootstrap-typeahead.js"></script>
    <script type="text/javascript" src="common/js/application.js"></script>
    
    
    

	<script type="text/javascript">

	  $('.myCarousel').carousel({
	    interval: 2000
	  });

	  $('#src_image').change(function() {
	      $('#src_image_cover').html($(this).val());
	  });	  
	  
	</script>


  </body>
</html>
