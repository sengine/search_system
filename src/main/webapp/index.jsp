<%@ include file="WEB-INF/jsp/includes.jsp" %>
<%@ include file="WEB-INF/jsp/header.jsp" %>

<!--
<%--<img src="<spring:url value="/static/images/pets.png" htmlEscape="true" />" align="right" style="position:relative;right:30px;">--%>
-->

<c:set var="method" value="GET" scope="page"/>

<%--<fmt:setLocale value="en"/>--%>

<%--<h2><fmt:message key="welcome"/></h2>--%>

<div id="body">
    <div id="menubar">
        <form:form action="searchQuery" method="${method}">
            <input type="text" name="query" size="80"/>
            <%--<input type="text" name="query" size="80" />--%>
            <input type="submit" name="submit" value="search" />
        </form:form>


        <br/>
        <br/>
        <%@ include file="WEB-INF/jsp/footer.jsp" %>
    </div>
</div>
