<%String pageName= "signedIn";%>

<%@ include file="./header.jsp" %>
<%
	ShubUser user = (ShubUser) session.getAttribute("user");
	if(user == null) {
		response.sendRedirect("/loggingOut");
		return;
	}
	pageContext.setAttribute("username", user.getUsername());
	pageContext.setAttribute("sectionWithDynamicBackgroundImage", "container content-section text-center " + user.getBackgroundImage());

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

<section id="SignedIn" class="${fn:escapeXml(sectionWithDynamicBackgroundImage)}">
    <div class="container body">
        <!--<div class="row"> Top Innerbox -->
        <div class="post-top">
            <p>Hello, ${fn:escapeXml(username)}!</p>
            <p></p>
            <form action="/loggingOut" method="get">
                <div>
                    <input type="submit" value="Sign Out">
                </div>
            </form>
            <form action="/twitterPostStatus" method="post">
				<div>
					<input type="submit" value="Say Hi to Twitter!" />
				</div>
			</form>
            <p>Type and Share!</p>
            <p></p>
            <form class="margin-bottom-8em" action="/postServlet" method="post" onchange="overallTextOnChange()">
                <div>
                    <textarea rows="3" cols="50" type="text" name="overallText" id="overallText" value=""></textarea>
                </div>
                <button class="btn btn-danger btn-outline" type="submit">Post</button>
                <div class="col-xs-6 col-md-6 mg-btm-2">
                    <label>Facebook:</label>
                    <input type="checkbox" name="fbCheckbox" id="fbCheckbox" value="checked">
                    <textarea class="socialTextArea" rows="4" cols="30" type="text" name="fbText" id="fbText" value=""></textarea>
                </div>
                <div class="col-md-offset-6 col-md-6">
                    <label>Twitter:</label>
                    <input type="checkbox" name="twitterCheckbox" id="twitterCheckbox" value="checked">
                    <textarea class="socialTextArea" rows="4" cols="30" type="text" name="twitterText" id="twitterText" value=""></textarea>
                </div>
            </form>
            <p >Individualize:</p>
            <p></p>
            <div class="col-xs-6 col-md-6 mg-btm-2">
				<h3>Facebook</h3>
			</div>
			<div class="col-md-offset-6">
				<h3>Twitter</h3>
			</div>						
        </div>
        <div class="post-bottom">
            <div class="col-xs-6 col-md-6 mg-btm-2">
                   <!-- FACEBOOK -->
            
						<%
  							for(Post post : user.getNewsfeed().getPosts(searchText)) {
								pageContext.setAttribute("curDatePost", post.getText("date"));
								pageContext.setAttribute("curFacebookPost", post.getText("facebook"));
  						%>
								<form action="/deletePost" method="post">
									<input type="submit" value="X" />
									<input type="hidden" name="hiddenDate"value="${fn:escapeXml(curDatePost)}" />
								</form>
								<blockquote>${fn:escapeXml(curDatePost)}</blockquote>
								<blockquote>${fn:escapeXml(curFacebookPost)}</blockquote>
						<%
							}
						%>
            </div>
            <div class="col-xs-6 col-md-6 col-md-offset-6 mg-btm-2">
            	<!-- TWITTER -->
						<%
							for(Post post : user.getNewsfeed().getPosts(searchText)) {
												pageContext.setAttribute("curDatePost", post.getText("date"));
												pageContext.setAttribute("curTwitterPost", post.getText("twitter"));
						%>
								<form action="/deletePost" method="post">
									<input type="submit" value="X" />
									<input type="hidden" name="hiddenDate"value="${fn:escapeXml(curDatePost)}" />
								</form>
								<blockquote>${fn:escapeXml(curDatePost)}</blockquote>
								<blockquote>${fn:escapeXml(curTwitterPost)}</blockquote>
						<%
							}
						%>
            </div>
        </div>
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



<%@ include file="./footer.jsp" %>