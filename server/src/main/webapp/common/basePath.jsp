<%
	String basePath = request.getScheme() 
					  + "://" 
					  + request.getServerName()
					  + ":"
					  + request.getServerPort()
					  + request.getServletContext().getContextPath()
					  + "/";
%>