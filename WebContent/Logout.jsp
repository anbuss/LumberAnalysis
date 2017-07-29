<%-- 
    Document   : Logout
    Created on : Jul 9, 2010, 4:57:57 PM
    Author     : user1
--%>
   <%@ page import="javax.servlet.http.Cookie" %>

   <%@page import="org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices" %>
<%
if (request.getSession(false) != null) {
    session.invalidate();
}
Cookie terminate = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, null);
String contextPath = request.getContextPath();
terminate.setPath(contextPath != null && contextPath.length() > 0 ? contextPath : "/");
terminate.setMaxAge(0);
response.addCookie(terminate);
response.sendRedirect("Login.xhtml");
%>

  