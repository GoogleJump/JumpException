<%String pageName= "signedIn";%>

<%@ include file="./header.jsp" %>


<section id="SignedIn" class="container content-section text-center">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
				
					<%
						//Cookie cookie = request.getCookies()[0];
						//String username = cookie.getValue();
						session.setAttribute("logInFailed", "false");
						session.setAttribute("logInCreationFailed", "false");
						session.setAttribute("loggedIn", "true");
						ShubUser user = (ShubUser) session.getAttribute("user");
						String username = user.getUsername();
						pageContext.setAttribute("username", username);
						String overallText = "";
						String fbText = "";
						String twitterText = "";
						pageContext.setAttribute("overallText", overallText);
						pageContext.setAttribute("fbText", fbText);
						pageContext.setAttribute("twitterText", twitterText);
					%>
				
				<div class="mainPage">
					<div> <!-- Top Innerbox -->
						<p>
							Hello, ${fn:escapeXml(username)}!
						<p>
						<form action="/loggingOut" method="get">
							<div>
								<input type="submit" value="Sign Out" />
							</div>
						</form>
						<form action="/deleteAccount" method="post">
							<div>
								<input type="submit" value="Delete Account" />
							</div>
						</form>
						<p>
							Type and Share!
						<p>
						<form action="/postServlet" method="post" onchange="overallTextOnChange()">
							<div>
								<textarea rows="3" cols= "50" type="text" name="overallText" id="overallText" value="${fn:escapeXml(overallText)}"></textarea>
							</div>
							<button class="btn btn-danger btn-outline" type="submit">Post</button>
						</form>
						<p>
							Individualize:
						<p>
						
						
						<div class="col-xs-6 col-md-6">
						<label>Facebook:</label>
						<input type="checkbox" name="fbCheckbox" id="fbCheckbox"/>
						<textarea class="socialTextArea" rows="4" cols= "30" type="text" name= "fbText" id="fbText" value="${fn:escapeXml(fbText)}"></textarea>			
					</div>
					<div class="col-xs-6 col-md-6 col-md-offset-6">
						<label>Twitter:</label>
						<input type="checkbox" name="twitterCheckbox" id="twitterCheckbox" />
						<textarea class="socialTextArea" rows="4" cols= "30" type="text" name= "twitterText" id="twitterText" value="${fn:escapeXml(twitterText)}"></textarea>
					</div>
					<div class="col-xs-6 col-md-6 mg-btm-2">
						<h3>Facebook</h3>
						<%
							for(Post post : user.getNewsfeed().getAllPosts()) {
								pageContext.setAttribute("curFacebookPost", post.getPost("overall"));
								//System.out.println("HELLO " + post.getDate().toString());
						%>
								<blockquote>${fn:escapeXml(curFacebookPost)}</blockquote>
						<%
							}
						%>
					</div>
					<div class="col-xs-6 col-md-6 col-md-offset-6 mg-btm-2">
						<h3>Twitter</h3>
						<%
							for(Post post : user.getNewsfeed().getAllPosts()) {
												pageContext.setAttribute("curTwitterPost", post.getPost("twitter"));
						%>
								<blockquote>${fn:escapeXml(curTwitterPost)}</blockquote>
						<%
							}
						%>
					</div>
					</div><!--  /top innerbox -->
					
					<!-- <table>
						<tr>
							<td>
								<div class="facebookInfo">
									<label>Facebook:</label>
									<input type="checkbox" name="fbCheckbox" id="fbCheckbox"/>
									<textarea class="socialTextArea" rows="4" cols= "30" type="text" name= "fbText" id="fbText" value="${fn:escapeXml(fbText)}"></textarea>
									
								</div>
							</td>
							<td>
								<div>
									<label>Twitter:</label>
									<input type="checkbox" name="twitterCheckbox" id="twitterCheckbox" />
									<textarea class="socialTextArea" rows="4" cols= "30" type="text" name= "twitterText" id="twitterText" value="${fn:escapeXml(twitterText)}"></textarea>
								</div>
							</td>
						</tr>
					</table> -->
					
					
					
				</div>
			</div>
        </div>
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