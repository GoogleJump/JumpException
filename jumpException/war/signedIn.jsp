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
						//pageContext.setAttribute("fbText", fbText);
						//pageContext.setAttribute("twitterText", twitterText);
					%>
				
				<div class="mainPage">
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
					<form onchange="overallTextOnChange()">
						<div>
							<textarea rows="3" cols= "50" type="text" name= "overallText" id="overallText" value="${fn:escapeXml(overallText)}"></textarea>
						</div>
					</form>
					<p>
						Individualize:
					<p>
					<table>
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
					</table>
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