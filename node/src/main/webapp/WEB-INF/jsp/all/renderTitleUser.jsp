<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/woko/jsp/taglibs.jsp"%>
<%@ page import="woko.Woko" %>
<%@ page import="woko.facets.builtin.RenderTitle" %>
<%@ page import="woko.facets.builtin.WokoFacets" %>
<%
    Woko woko = Woko.getWoko(application);
    RenderTitle renderTitle = (RenderTitle)request.getAttribute(WokoFacets.renderTitle);
    Object target = renderTitle.getFacetContext().getTargetObject();
    Class<?> targetClass = target.getClass();
    String className = woko.getObjectStore().getClassMapping(targetClass);
%>
<h1 class="page-header">
    ${renderTitle.title}
        <c:if test="${not renderTitle.transientUser}">
            <small>(<%=className%>)</small>
        </c:if>
</h1>