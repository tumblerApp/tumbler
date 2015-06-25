<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page='header.jsp' />
<link
	href="../content/static/css/bootstrap/bootstrap-combined.no-icons.min.css"
	rel="stylesheet">
<link
	href="../content/static/css/bootstrap/bootstrap-responsive.min.css"
	rel="stylesheet">
<link
	href="http://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css"
	rel="stylesheet">
<link href="../content/static/css/bootstrap/index.css" rel="stylesheet">
<script src="../content/static/js/lib/bootstrap-wysiwyg.js"
	type="text/javascript"></script>
<script src="../content/static/js/lib/jquery.hotkeys.js"
	type="text/javascript"></script>
<div class="row clearfix">
	<jsp:include page='menu.jsp' />
	<div class="col-md-10 column">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					新闻添加<a href="./news"><span class="badge navbar-right">返回</span></a>
				</h3>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="addNews"
					method="post" id="noticeForm" enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-sm-2 control-label">新闻标题:</label> 
						<div class="col-sm-8">
							<input name="title" class="form-control"> <br>
						</div>
					</div>
					<div class="form-group" id="text">
						<label for="inputIdentifier" class="col-sm-2 control-label">内容</label>
						<div class="col-sm-8">
							<div  style="display: none;"><textarea id="contentNews" name="contentNews"></textarea></div>
								<DIV class="btn-toolbar" DATA-role="editor-toolbar"
									DATA-target="#editor">
									<DIV class="btn-group">
										<a class="btn dropdown-toggle" DATA-toggle="dropdown"
											title="Font"><i class="icon-font"></i><b class="caret"></b></a>
										<ul class="dropdown-menu">
										</ul>
									</DIV>
									<DIV class="btn-group">
										<a class="btn dropdown-toggle" DATA-toggle="dropdown"
											title="Font Size"><i class="icon-text-height"></i>&nbsp;<b
											class="caret"></b></a>
										<ul class="dropdown-menu">
											<li><a DATA-edit="fontSize 5"><font size="5">Huge</font></a></li>
											<li><a DATA-edit="fontSize 3"><font size="3">Normal</font></a></li>
											<li><a DATA-edit="fontSize 1"><font size="1">Small</font></a></li>
										</ul>
									</DIV>
									<DIV class="btn-group">
										<a class="btn" DATA-edit="bold" title="Bold (Ctrl/Cmd+B)"><i
											class="icon-bold"></i></a> <a class="btn" DATA-edit="italic"
											title="Italic (Ctrl/Cmd+I)"><i class="icon-italic"></i></a> <a
											class="btn" DATA-edit="strikethrough" title="Strikethrough"><i
											class="icon-strikethrough"></i></a> <a class="btn"
											DATA-edit="underline" title="Underline (Ctrl/Cmd+U)"><i
											class="icon-underline"></i></a>
									</DIV>
									<DIV class="btn-group">
										<a class="btn" DATA-edit="insertunorderedlist"
											title="Bullet list"><i class="icon-list-ul"></i></a> <a
											class="btn" DATA-edit="insertorderedlist" title="Number list"><i
											class="icon-list-ol"></i></a> <a class="btn" DATA-edit="outdent"
											title="Reduce indent (Shift+Tab)"><i
											class="icon-indent-left"></i></a> <a class="btn"
											DATA-edit="indent" title="Indent (Tab)"><i
											class="icon-indent-right"></i></a>
									</DIV>
									<DIV class="btn-group">
										<a class="btn" DATA-edit="justifyleft"
											title="Align Left (Ctrl/Cmd+L)"><i
											class="icon-align-left"></i></a> <a class="btn"
											DATA-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i
											class="icon-align-center"></i></a> <a class="btn"
											DATA-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i
											class="icon-align-right"></i></a> <a class="btn"
											DATA-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i
											class="icon-align-justify"></i></a>
									</DIV>
									<DIV class="btn-group">
										<a class="btn dropdown-toggle" DATA-toggle="dropdown"
											title="Hyperlink"><i class="icon-link"></i></a> <a
											class="btn" DATA-edit="unlink" title="Remove Hyperlink"><i
											class="icon-cut"></i></a>
									</DIV>

									<DIV class="btn-group">
										<a class="btn" DATA-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i
											class="icon-undo"></i></a> <a class="btn" DATA-edit="redo"
											title="Redo (Ctrl/Cmd+Y)"><i class="icon-repeat"></i></a>
									</DIV>
									<input TYPE="text" DATA-edit="inserttext" id="voiceBtn"
										X-webkit-speech="">
								</DIV>
								<DIV id="editors" style="overflow:scroll; height: 300px; border: 2px solid #D8D8D8; ">输入内容&hellip;</DIV>
								
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">
								照片</label>
							<div class="col-sm-8">
								<input name="sendFile" type="file" class="form-control">
								<br>
							</div>
						</div>
					<script>
  $(function(){
    function initToolbarBootstrapBindings() {
      var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier', 
            'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
            'Times New Roman', 'Verdana'],
            fontTarget = $('[title=Font]').siblings('.dropdown-menu');
      $.each(fonts, function (idx, fontName) {
          fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
      });
      $('a[title]').tooltip({container:'body'});
    	$('.dropdown-menu input').click(function() {return false;})
		    .change(function () {$(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');})
        .keydown('esc', function () {this.value='';$(this).change();});

      $('[data-role=magic-overlay]').each(function () { 
        var overlay = $(this), target = $(overlay.data('target')); 
        overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
      });
      if ("onwebkitspeechchange"  in document.createElement("input")) {
        var editorOffset = $('#editor').offset();
        $('#voiceBtn').css('position','absolute').offset({top: editorOffset.top, left: editorOffset.left+$('#editors').innerWidth()-35});
      } else {
        $('#voiceBtn').hide();
      }
	};
	function showErrorAlert (reason, detail) {
		var msg='';
		if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
		else {
			console.log("error uploading file", reason, detail);
		}
		$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
		 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
	};
    initToolbarBootstrapBindings(); 
	$('#editors').wysiwyg({ fileUploadError: showErrorAlert} );
	$('#editor').wysiwyg({ fileUploadError: showErrorAlert} );
	$('#noticeForm').submit(function() {
		  $('#contentNews').val($('#editors').html());
		  $('#contentText').val($('#editor').html());
		});
  });
</script>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-12">
							<button type="submit" class="btn btn-default">添加</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<jsp:include page='footer.jsp' />