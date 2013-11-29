<%@ page contentType="text/html;charset=utf-8" %>
<%-- (1)JavaBeansをインポートします。 --%>
<%@ page import="java.beans.Beans.*" %>
<%-- (2)<jsp:useBean>タグでJavaBeansのオブジェクトを生成します。 --%>
<jsp:useBean id="b" class="jp.dmtc.ing.promoa.beans.MBean" scope="session" />
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
    <link href="crudefox/css/crudefox.css" rel="stylesheet">

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

	<!--
      <div class="jumbotron">
        <h1></h1>
        <img src="img/title.gif" />
        <p class="lead">モザイクアート作成サービス</p>
        <!-- <a class="btn btn-large btn-success" href="./test_all.html">GOi??</a>
    </div>-->

      <hr>

      <form class="form-horizontal text-center" method="post" action="next2"  enctype="multipart/form-data" >

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

	  <h1><span class="red-text">直感</span>で好きなものを選んでください</h1>

	  <hr>


      <!-- Example row of columns -->
      <div class="row text-center">
		<ul class='thumbnails'>

        <%
			for (int i = 0; i < b.names.size(); i++) {
				out.println(
					"<div class='span2 control-group'>"
							);
				out.println(
					"<div class='control'>"
				);

				out.println(
						"  <li>"+
						"    <input type='checkbox' class='input-xlarge cf-chk-dokidoki' id='image"+i+"' name='image"+i+"' value='"+b.names.get(i)+"' checked >"+
						"    <a onclick=\"onImageClick('image"+i+"');\" class='thumbnail'>"+
						"       <img src='"+b.urls.get(i)+"' alt=''>"+
						"    </a>"+
						"  </li>"
						);
				out.println(
					"</div>");
				out.println(
					"</div>"
						);

			}
      	%>
      	</ul>

      </div>





      <hr>

      <!-- enctype="multipart/form-data" -->
      <fieldset>
        <a class="btn btn-large btn" href='./'>戻る</a>
        <button type="submit" class="btn btn-large btn-warning">次へ</button>
        <!-- <button type="reset" class="btn">a?-a?￡a?3a?≫a?≪</button>-->
      </fieldset>
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

    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <!--<script src="../assets/js/jquery.js"></script>-->
    <script src="bootstrap/js/bootstrap.min.js"></script>

  </body>
</html>
