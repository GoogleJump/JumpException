<%@page import="com.google.appengine.api.blobstore.BlobKey"%>
<%String pageName= "signedIn";%>

<%@ include file="./header.jsp" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.datastore.Blob" %>

<%@ page import="com.google.appengine.api.images.ImagesService"%>
<%@ page import="com.google.appengine.api.images.ImagesServiceFactory" %>

<%
	ShubUser user = (ShubUser) session.getAttribute("user");
	if(user == null) {
		response.sendRedirect("/loggingOut");
		return;
	}
	pageContext.setAttribute("username", user.getUsername());
	pageContext.setAttribute("dynamicBackgroundImage", user.getBackgroundImage());

	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
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
				
				<form action="<%= blobstoreService.createUploadUrl("/UploadImageServlet") %>" enctype="multipart/form-data" method="post">
					<!-- <img id="uploadPreview" style="width: 100px; height: 100px;" /> -->
					<% if(request.getSession().getAttribute("blobKey") != null) {
							BlobKey blobKey = (BlobKey) request.getSession().getAttribute("blobKey");
							ImagesService imagesService = ImagesServiceFactory.getImagesService();
					    	String blobURL = imagesService.getServingUrl(blobKey);
						    pageContext.setAttribute("blobURL", blobURL);
					%>
						<input class="center" href="#" id="image" type="hidden" onchange="turnOffLink()" src="${fn:escapeXml(blobURL)}" />
					<% } %>
					<input class="center" id="uploadImage" type="file" name="myPhoto" />
					<input name="Submit" type="submit" value="Submit">
			</form>
	            <form class="margin-bottom-8em"  action="/postServlet" method="post" onchange="overallTextOnChange()">
	                <input type="hidden" name="blob" value="${fn:escapeXml(blob)}" />
	                <div>
	                    <textarea rows="3" cols="50" type="text" name="overallText" id="overallText" value=""></textarea>
	                </div>
	                <button class="btn btn-danger btn-outline" type="submit">Post</button>
	               	<p >Individualize:</p>
	                <div class="col-xs-4 col-md-4 mg-btm-2"><!-- col-xs-6 col-md-6 mg-btm-2 -->
	                    <label>Facebook:</label>
	                    <input type="checkbox" name="fbCheckbox" id="fbCheckbox" value="checked">
	                    <textarea class="socialTextArea" rows="4" cols="30" type="text" name="fbText" id="fbText" value=""></textarea>
	                </div>
	                <div class="col-md-offset-0 col-sm-offset-0 col-md-4 col-sm-4"> <!-- col-md-offset-6 col-sm-offset-6 col-md-6 -->
	                    <label>Twitter:</label>
	                    <input type="checkbox" name="twitterCheckbox" id="twitterCheckbox" value="checked">
	                    <textarea class="socialTextArea" rows="4" cols="30" type="text" name="twitterText" id="twitterText" value=""></textarea>
	                </div>
	                <div class="col-md-offset-0 col-sm-offset-0 col-md-4 col-sm-4"><!-- col-md-offset-6 col-sm-offset-6 col-md-6 -->
	                    <label>Google+:</label>
	                    <input type="checkbox" name="twitterCheckbox" id="twitterCheckbox" value="checked">
	                    <textarea class="socialTextArea" rows="4" cols="30" type="text" name="twitterText" id="twitterText" value=""></textarea>
	                </div>
	                
	            </form>
	            <p></p>
	            <div class="transparent-container">
		            <div class="theme-container">
			            <table width="100%">
							<tr>
								<td width="33%">
									<div class="" style="text-align:center;">
										<h3>Facebook</h3>
										<%/* if(user.getFacebookCode() != null) { 
												facebook4j.User facebookUser = user.getFacebookUser();
												String facebookImageUrl = facebookUser.getPicture().getURL().toString();
												String facebookProfileLink = facebookUser.getLink().toString();
												String facebookUserName = facebookUser.getUsername();
												pageContext.setAttribute("twitterProfileImage", facebookImageUrl);
												pageContext.setAttribute("twitterProfileLink", facebookProfileLink);
												pageContext.setAttribute("facebookUserName", facebookUserName);*/
										%>
										<!-- 	<form style="text-align:center;">
												<input type="image" src="${fn:escapeXml(facebookImageUrl)}"></br></br>
												<a class="theme-text" href="${fn:escapeXml(facebookProfileLink)}">${fn:escapeXml(facebookUserName)}</a>
											</form> -->
										<%
											//} else {
										%>
												<!-- <p style="text-align:center;">See Settings to Connect</p> -->
										<%
											//}
										%>
									</div>
								</td>
								<td width="33%">
									<div class="" style="text-align:center;">
										<h3>Twitter</h3>
										<% /*if(user.getTwitterAccessToken() != null) { 
												twitter4j.User twitterUser = user.getTwitterUser();
												String twitterImageUrl = twitterUser.getProfileImageURL();
												String twitterProfileLink = twitterUser.getURL();
												String twitterUserName = twitterUser.getName();
												pageContext.setAttribute("twitterProfileImage", twitterImageUrl);
												pageContext.setAttribute("twitterProfileLink", twitterProfileLink);
												pageContext.setAttribute("twitterUserName", twitterUserName);*/
										%>
									<!-- 		<form style="text-align:center;">
												<input type="image" src="${fn:escapeXml(twitterProfileImage)}"></br></br>
												<a class="theme-text" href="${fn:escapeXml(twitterProfileLink)}">${fn:escapeXml(twitterUserName)}</a>
											</form>-->
										<%
								/*			} else {
												user.deleteTwitterAccessToken();	*/										
										%>
											<!-- <p style="text-align:center;">See Settings to Connect</p> -->
										<%
										//	}
										%>
									</div>	
								</td>
								<td width="33%">
									<div class="" style="text-align:center;">
										<h3>Google+</h3>
									</div>	
									<% if(user.getGooglePlusUserId() != null) { 
									%>
										<%@ page import="com.google.api.services.plus.model.Person"%>
									<%
											Person profile = user.getGooglePlusProfile(user.getGooglePlusUserId(), request, response);	
											String googlePlusImageUrl = profile.getImage().getUrl();
											String googlePlusProfileLink = profile.getUrl();
											String googlePlusUserName = profile.getDisplayName();
											pageContext.setAttribute("googlePlusImageUrl", googlePlusImageUrl);
											pageContext.setAttribute("googlePlusProfileLink", googlePlusProfileLink);
											pageContext.setAttribute("googlePlusUserName", googlePlusUserName);
									%>
										<form style="text-align:center;">
											<input type="image" src="${fn:escapeXml(googlePlusImageUrl)}"></br></br>
											<a class="theme-text" href="${fn:escapeXml(googlePlusProfileLink)}">${fn:escapeXml(googlePlusUserName)}</a>
										</form>										
									<%
										} else {
											user.deleteGooglePlusUserId();
									%>
											<p style="text-align:center;">See Settings to Connect</p>
									<%
										}
									%>
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
							request.getSession().setAttribute("user",user);
				%>
							<form action="/routeEditServlets" method="post">
								<input type="submit" name="action" value="Post" /> <br><br>
								<input type="submit" name="action" value="Cancel" /> <br>
								<input type="hidden" name="hiddenDate"value="${fn:escapeXml(curDatePost)}" />
								${fn:escapeXml(curDatePost)}</br></br>
								
								<%
									if(post.getBlobURL() != null) {
										pageContext.setAttribute("blobURL", post.getBlobURL());
								%>
										<div class="" style="text-align:center;">
											<input type="image" src="${fn:escapeXml(blobURL)}">
										</div>
								<% } %>
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
								${fn:escapeXml(curDatePost)}</br></br>
								<%
									if(post.getBlobURL() != null) {
										pageContext.setAttribute("blobURL", post.getBlobURL());
								%>
										<div class="" style="text-align:center;">
											<input type="image" src="${fn:escapeXml(blobURL)}">
										</div>
								<% } %>
								<table width="100%">
									<tr>
										<td width="33%">
											<div class="theme-container" style="text-align:center;"> <!-- col-xs-6 col-md-6 mg-btm-2 -->
								                   <!-- FACEBOOK -->
												${fn:escapeXml(curFacebookPost)}
											</div>
										</td>
										<td width="33%">
											<div class="theme-container" style="text-align:center;">
												${fn:escapeXml(curTwitterPost)}
											</div>
										</td>
										<td width="33%">
											<div class="theme-container" style="text-align:center;">
												<!-- google+ posts go here -->
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
    

};


</script>
<script>
	function turnOffLink()
	{
	    document.getElementById('image').disabled = true;
	}
</script>



<%@ include file="./footer.jsp" %>