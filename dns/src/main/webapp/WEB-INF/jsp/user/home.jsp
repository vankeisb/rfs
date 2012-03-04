<%@ page import="woko.facets.builtin.WokoFacets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/woko/jsp/taglibs.jsp"%>

<c:set var="o" value="${actionBean.object}"/>
<w:facet facetName="<%=WokoFacets.layout%>" targetObject="${o}"/>
<w:facet targetObject="${o}" facetName="<%=WokoFacets.renderTitle%>"/>

<fmt:message bundle="${wokoBundle}" var="pageTitle" key="woko.guest.home.pageTitle"/>
<s:layout-render name="${layout.layoutPath}" layout="${layout}" pageTitle="${pageTitle}">
    <s:layout-component name="body">
        <style type="text/css">
            .leftAligned {
                text-align: right;
                font-weight: bold;
            }
        </style>
        <div class="row-fluid">
            <div class="span12">
                <h1 class="page-header">Your configuration</h1>
                <div class="row">
                    <div class="span2 leftAligned">
                        Username
                    </div>
                    <div class="span6">
                        <c:out value="${home.username}"/>
                    </div>
                </div>
                <div class="row">
                    <div class="span2 leftAligned">
                        RFS address
                    </div>
                    <div class="span6">
                        <c:set var="u" value="${home.userUrl}"/>
                        <a href="${u}">${u}</a>
                    </div>
                </div>
                </p>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>