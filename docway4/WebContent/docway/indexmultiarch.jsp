<%@ include file="../common.jsp" %>

<% 
// nel caso in cui venga richiesta la forzatura del login
// viene prima di tutto invalidata la sessione corrente
//forceLogin(request, session);

// redirect a pagina JSF
String redirectPage = redirectToJsf(request, "home.jsf", true, true);
response.sendRedirect(redirectPage); 
%>    