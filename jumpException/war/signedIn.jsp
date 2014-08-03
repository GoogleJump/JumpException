<%String pageName= "signedIn";%>

<%@ include file="./header.jsp" %>
<%@ page import="com.google.appengine.api.datastore.Blob" %>
<%
	ShubUser user = (ShubUser) session.getAttribute("user");
	if(user == null) {
		response.sendRedirect("/loggingOut");
		return;
	}
	pageContext.setAttribute("username", user.getUsername());
	pageContext.setAttribute("dynamicBackgroundImage", user.getBackgroundImage());

	Blob blob = (Blob) session.getAttribute("blob");
	pageContext.setAttribute("blob", blob);
	/*Object searchTextObj = session.getAttribute("searchText");
	String searchText = "";
	if(searchTextObj != null) {
		searchText = searchTextObj.toString();
	}*/
	/*
	Object isFilteredNewsfeedObj = session.getAttribute("isFilteredNewsfeed");
	String isFilteredNewsfeed = "false";
	if(isFilteredNewsfeedObj == null) {
		isFilteredNewsfeed = "false";
		session.setAttribute("isFilteredNewsfeed", isFilteredNewsfeed);
	} else {//true
		isFilteredNewsfeed = isFilteredNewsfeedObj.toString();
	}
	Newsfeed newsfeed;
	if(isFilteredNewsfeed.equals("true")) {
		newsfeed = new Newsfeed(user.getNewsfeed().getFilteredPosts(session.getAttribute("searchFilter").toString()));
	} else {
		newsfeed = user.getNewsfeed();
	}
	*/
%>
<div class="${fn:escapeXml(dynamicBackgroundImage)}">
	<section id="SignedIn" class="container content-section text-center">
	    <div class="container body">
	        <!--<div class="row"> Top Innerbox -->
	        <div class="post-top">
	            <p>Hello, ${fn:escapeXml(username)}!</p>
	            <p></p>
	            <p>Type and Share!</p>
	            <p></p>
	
				<form action="/UploadImageServlet" enctype="multipart/form-data" method="post">
					<img id="uploadPreview" style="width: 100px; height: 100px;" />
					<input class="center" id="uploadImage" type="file" name="myPhoto" onchange="PreviewImage();" />
				</form>
	            <form class="margin-bottom-8em"  action="/postServlet" method="post" onchange="overallTextOnChange()">
	                <input type="hidden" name="blob" value="${fn:escapeXml(blob)}" />
	                <div>
	                    <textarea rows="3" cols="50" type="text" name="overallText" id="overallText" value=""></textarea>
	                </div>
	                <button class="btn btn-danger btn-outline" type="submit">Post</button>
	               	<p >Individualize:</p>
	                <div class="col-xs-6 col-md-6 mg-btm-2">
	                    <label>Facebook:</label>
	                    <input type="checkbox" name="fbCheckbox" id="fbCheckbox" value="checked">
	                    <textarea class="socialTextArea" rows="4" cols="30" type="text" name="fbText" id="fbText" value=""></textarea>
	                </div>
	                <div class="col-md-offset-6 col-sm-offset-6 col-md-6">
	                    <label>Twitter:</label>
	                    <input type="checkbox" name="twitterCheckbox" id="twitterCheckbox" value="checked">
	                    <textarea class="socialTextArea" rows="4" cols="30" type="text" name="twitterText" id="twitterText" value=""></textarea>
	                </div>
	                
	            </form>
	            <p></p>
	            <div class="transparent-container">
		            <div class="theme-container">
			            <table width="100%">
							<tr>
								<td width="50%">
									<div class="" style="text-align:center;">
										<h3>Facebook</h3>
									</div>
								</td>
								<td width="50%">
									<div class="" style="text-align:center;">
										<h3>Twitter</h3>
									</div>	
								</td>
							</tr>
						</table>			
		       		</div>
	        <div class="post-bottom">
	        	<%
					for(Post post : user.getNewsfeed().getPosts(searchText)) {
						pageContext.setAttribute("curDatePost", post.getText("date"));
						pageContext.setAttribute("curFacebookPost", post.getText("facebook"));
						pageContext.setAttribute("curTwitterPost", post.getText("twitter"));
				%>
					<div class="transparent-container" >
					<div class="theme container">
					
				<%
						if(post.getIsEditing()) {
							pageContext.setAttribute("editedFbPost", post.getText("facebook"));
							pageContext.setAttribute("editedTwitterPost", post.getText("twitter"));
							post.setIsEditing(false);
				%>
							<form action="/routeEditServlets" method="post">
								<input type="submit" name="action" value="Post" /> <br><br>
								<input type="submit" name="action" value="Cancel" /> <br>
								<input type="hidden" name="hiddenDate"value="${fn:escapeXml(curDatePost)}" />
					            <div class="col-xs-6 col-md-6 mg-btm-2 theme-container">
					                   <!-- FACEBOOK -->
									<label>Facebook:</label>
				                    <% 
										if(post.getText("facebook").equals("")) {
										//MUST PUT IN A CHECK FOR IMAGE ALSO	
									%>
				                    		<input type="checkbox" name="fbEditCheckbox" id="fbEditCheckbox" value="checked">				                    		
				                    <%	} else { %>
				                    		<input type="checkbox" name="fbEditCheckbox" id="fbEditCheckbox" value="checked" checked>
				                    <%	} %>	
				                    <textarea class="socialTextArea" rows="4" cols="30" type="text" style="margin: 0px 20.5px 0px 0px; width: 528px; height: 84px;" name="fbEditText" id="fbEditText">${fn:escapeXml(editedFbPost)}</textarea>
			                    </div>		                    		
							    <div class="theme-container">
							    	<!-- TWITTER -->
							    	<label>Twitter:</label>
									<% 
										if(post.getText("twitter").equals("")) {
										//MUST PUT IN A CHECK FOR IMAGE ALSO	
									%>
				                    		<input type="checkbox" name="twitterEditCheckbox" id="twitterEditCheckbox" value="checked">
				                    <%	} else { %>
				                    		<input type="checkbox" name="twitterEditCheckbox" id="twitterEditCheckbox" value="checked" checked>
				                    <%	} %>		
				                    <textarea class="socialTextArea" rows="4" cols="30" type="text" name="twitterEditText" id="twitterEditText">${fn:escapeXml(editedTwitterPost)}</textarea>
							    </div> 
				   			</form>             
			  						
			  						
				<%		} else { %>
								<form action="/deletePost" method="post">
									<input type="submit" value="X" />
									<input type="hidden" name="hiddenDate"value="${fn:escapeXml(curDatePost)}" />
								</form>
								<form action="/editPost" method="post">
									<input type="submit" value="Edit" />
									<input type="hidden" name="hiddenDate"value="${fn:escapeXml(curDatePost)}" />
								</form>
								<table width="100%">
									<tr>
										<td width="50%">
											<div class="theme-container" style="text-align:center;"> <!-- col-xs-6 col-md-6 mg-btm-2 -->
								                   <!-- FACEBOOK -->
												${fn:escapeXml(curDatePost)}</br></br>
												${fn:escapeXml(curFacebookPost)}
											</div>
										</td>
										<td width="50%">
											<div class="theme-container" style="text-align:center;">
												${fn:escapeXml(curDatePost)}</br></br>
												${fn:escapeXml(curTwitterPost)}
											</div>
										</td>
									</tr>
								</table>
				<%
						}
				%>
					</div>
				</div>
			<%
				}
			%>

    </div>
    <!-- /top innerbox -->
    <!-- <table>
						<tr>
							<td>
								<div class="facebookInfo">
									<label>Facebook:</label>
									<input type="checkbox" name="fbCheckbox" id="fbCheckbox"/>
									<textarea class="socialTextArea" rows="4" cols= "30" type="text" name= "fbText" id="fbText" value=""></textarea>
									
								</div>
							</td>
							<td>
								<div>
									<label>Twitter:</label>
									<input type="checkbox" name="twitterCheckbox" id="twitterCheckbox" />
									<textarea class="socialTextArea" rows="4" cols= "30" type="text" name= "twitterText" id="twitterText" value=""></textarea>
								</div>
							</td>
						</tr>
					</table> -->
    <!--</div> -->
</section>

<script>
	function changePassword() {
		//var text =  "IT WORKED"
		//document.getElementById("overallText").value = text;
		var curPassword = pageContext().getAttribute("curPassword").toString();
		var newPassword = pageContext().getAttribute("newPassword").toString();
		var confirmNewPassword = pageContext().getAttribute("confirmNewPassword").toString();
		if(user.changePassword(curPassword, newPassword, confirmNewPassword)) {
			//document.getElementById("blah").value = text;
		} else {
			document.getElementById("curPassword").value = text;
		}
	}
</script>

<script>
	function overallTextOnChange() {
		var overallText = document.getElementById("overallText").value;
		if(!document.getElementById("fbCheckbox").checked) {
			document.getElementById("fbText").value = overallText;
		}
		if(!document.getElementById("twitterCheckbox").checked) {
			document.getElementById("twitterText").value = overallText;
		}
	}
</script>
<script>
	function togglePasswordChangeVisibility() {
		
	}
</script>
<script>
function PreviewImage() {
    var oFReader = new FileReader();
    oFReader.readAsDataURL(document.getElementById("uploadImage").files[0]);
	

    oFReader.onload = function (oFREvent) {

        document.getElementById("uploadPreview").src = oFREvent.target.result;
    };
    
    document.location.href="/UploadImageServlet";

};


</script>



<%@ include file="./footer.jsp" %>