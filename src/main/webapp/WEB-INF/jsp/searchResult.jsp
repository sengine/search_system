<%@ include file="includes.jsp" %>
<%@ include file="header.jsp" %>
<%@ page language="java" import="java.util.regex.*, java.lang.String, java.lang.Long, com.searchengine.bool.web.form.SearchResult" %>

<!--
<%--<img src="<spring:url value="/static/images/pets.png" htmlEscape="true" />" align="right" style="position:relative;right:30px;">--%>
-->

<%--<jsp:useBean id="searchResult" scope="session" class="com.searchengine.bool.web.form.SearchResult" />--%>

<c:set var="method" value="GET" scope="page"/>


<div id="body">
    <div id="menubar">
        <form:form action="http://localhost:8080/sengine/searchQuery" method="${method}">
        <div id="search" class="submenu">
            <input type="text" name="query" size="80"/>
            <input type="submit" name="submit" value="search" />
        </div>
    </div>
    </form:form>

    <h2>Found Documents:</h2>
    <!--
<table>
    <thead>
    <th>Result for Query:<br/>
		<b>
			${searchResult.query}
		</b>
	</th>
    </thead> -->
    <c:forEach var="item" items="${searchResult.content}">
        <tr>
                <%--<td>--%>
                <%--<spring:url value="owners/{ownerId}" var="ownerUrl">--%>
                <%--<spring:param name="ownerId" value="${owner.id}"/>--%>
                <%--</spring:url>--%>
                <%--<a href="${fn:escapeXml(ownerUrl)}">${owner.firstName} ${owner.lastName}</a>--%>
                <%--</td>--%>
                <%--<td>${owner.address}</td>--%>
                <%--<td>${owner.city}</td>--%>
        <div class="quote">
            <div class="text">
                <td>${item}</td>
            </div>
        </div>
        <%--<td>--%>
        <%--<c:forEach var="pet" items="${owner.pets}">--%>
        <%--${pet.name} &nbsp;--%>
        <%--</c:forEach>--%>
        <%--</td>--%>
        <!--
        </tr> -->
    </c:forEach>
    <%--</table>--%>



    <%
        String str;
        str = request.getHeader("referer");
        Matcher matcher = Pattern.compile("searchResult").matcher(str);
        Boolean isFirst = !matcher.find();
        System.out.println("isFirst = " + isFirst);
        SearchResult searchResult = (SearchResult) request.getAttribute("searchResult");
        searchResult.setId((isFirst == Boolean.FALSE) ? Long.valueOf(0) : Long.valueOf(1));
    %>

    <!-- <c:out value="${request}"  default="N/A" /> -->
    <!-- <text>${searchResult.id}</text> -->


    <c:choose>
        <c:when test="${searchResult.pageNumber == 1 && searchResult.id == 1}">
	<span class="current">
        <!-- <text>AARDVARK</text> -->
        <c:if test="${searchResult.hasNext}">
            <a href="<spring:url value="http://localhost:8080/sengine/searchResult/${searchResult.pageNumber + 1}" htmlEscape="true" />">Next Page</a>
        </c:if>
	</span>
        </c:when>
        <c:otherwise>
	<span class="arr">
        <c:if test="${searchResult.pageNumber > 1}">
            <a href="<spring:url value="http://localhost:8080/sengine/searchResult/${searchResult.pageNumber - 1}" htmlEscape="true" />">Previous Page</a>
        </c:if>
        <c:if test="${searchResult.hasNext}">
            <a href="<spring:url value="http://localhost:8080/sengine/searchResult/${searchResult.pageNumber + 1}" htmlEscape="true" />">Next Page</a>
        </c:if>
	</span>
        </c:otherwise>
    </c:choose>


    <br/>
    <br/>
    <%@ include file="footer.jsp" %>
</div>
</div>