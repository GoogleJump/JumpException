<%String pageName= "signedIn";%>

<%@ include file="./header.jsp" %>

	<div class="mainPage">
		<%
			//Cookie cookie = request.getCookies()[0];
			//String username = cookie.getValue();
			session.setAttribute("logInFailed", "false");
			session.setAttribute("logInCreationFailed", "false");
			String username = request.getSession().getAttribute("username").toString();
			pageContext.setAttribute("username", username);
			String overallText = "";
			String fbText = "";
			String twitterText = "";
			pageContext.setAttribute("overallText", overallText);
			//pageContext.setAttribute("fbText", fbText);
			//pageContext.setAttribute("twitterText", twitterText);
		%>
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
		<form onclick="/openSocialMedias" method="post" >
			<div>
			<label>Facebook:</label>
				<input type="checkbox" name="fbCheckbox" id="fbCheckbox"/>
				<textarea class="socialTextArea" rows="3" cols= "50" type="text" name= "fbText" id="fbText" value="${fn:escapeXml(fbText)}"></textarea>
			</div>
		</form>
		<form title="Diverge:" >
			<div>
				<label>Twitter:</label>
				<input type="checkbox" name="twitterCheckbox" id="twitterCheckbox" />
				<textarea class="socialTextArea" rows="3" cols= "50" type="text" name= "twitterText" id="twitterText" value="${fn:escapeXml(twitterText)}"></textarea>
			</div>
		</form>
	</div>

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

<%@ include file="./footer.jsp" %>