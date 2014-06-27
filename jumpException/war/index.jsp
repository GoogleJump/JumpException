<%@ include file="./header.jsp" %>
<%
	ShubUser user = (ShubUser) session.getAttribute("user");
	if(user != null) {
		response.sendRedirect("/signedIn.jsp");
	}
%>
    <section class="intro">
        <div class="intro-body">
            <div class="container">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2">
                        <h1 class="brand-heading">Shub</h1>
                        <p class="intro-text">Unlimited Social Access. One hub.</p>
                        <div class="page-scroll">
                            <a href="#about" class="btn btn-circle">
                                <i class="fa fa-angle-double-down animated"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section id="Shub" class="container content-section text-center">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
                <h2>Log In</h2>
                
                <%
                    Object logInFailed = session.getAttribute("logInFailed");
                    if(logInFailed == null) {
                %>
                    	<%@ include file = "./logIn.jsp" %>

                	<% } else if(logInFailed.toString().equals("true")) {
                    %>
                    
                    		<%@ include file = "./logInFail.jsp" %>
                    <% } else {
                    
                    %>	
                        <%@ include file = "./logIn.jsp" %>
                <% }
                %>


            </div>
        </div>
    </section>

    <section id="SignUp" class="content-section text-center">
        <div class="download-section">
            <div class="container">
                <div class="col-lg-8 col-lg-offset-2">
                    <h2>Sign Up</h2>
                     <%
                    Object logInCreationFailed = session.getAttribute("logInCreationFailed");
                    if(logInCreationFailed == null) {
                	%>
                    	<%@ include file = "./practicePage.jsp" %>

                	<% } else if(logInCreationFailed.toString().equals("true")) {
                    %>
                    		<%@ include file = "./logInCreationFail.jsp" %>
                    <% } else {
                    
                    %>	
                        <%@ include file = "./practicePage.jsp" %>
                <% }
                %>
                    <!-- <a href="http://startbootstrap.com/grayscale" class="btn btn-default btn-lg">Visit Download Page</a> -->
                </div>
            </div>
        </div>
    </section>

    <section id="contact" class="container content-section text-center">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
                <h2>Contact Start Bootstrap</h2>
                <p>Feel free to email us to provide some feedback on our templates, give us suggestions for new templates and themes, or to just say hello!</p>
                <p>feedback@startbootstrap.com</p>
                <ul class="list-inline banner-social-buttons">
                    <li><a href="https://twitter.com/SBootstrap" class="btn btn-default btn-lg"><i class="fa fa-twitter fa-fw"></i> <span class="network-name">Twitter</span></a>
                    </li>
                    <li><a href="https://github.com/IronSummitMedia/startbootstrap" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                    </li>
                    <li><a href="https://plus.google.com/+Startbootstrap/posts" class="btn btn-default btn-lg"><i class="fa fa-google-plus fa-fw"></i> <span class="network-name">Google+</span></a>
                    </li>
                </ul>
            </div>
        </div>
    </section>

    <div id="map"></div>

    <!-- Core JavaScript Files -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

    <!-- Google Maps API Key - You will need to use your own API key to use the map feature -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRngKslUGJTlibkQ3FkfTxj3Xss1UlZDA&sensor=false"></script>

    <!-- Custom Theme JavaScript -->
    <script src="styles/js/grayscale.js"></script>

</body>

</html>