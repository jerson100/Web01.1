<%-- 
    Document   : post-article
    Created on : 20/02/2020, 06:59:58 PM
    Author     : Jerson
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<article class="article-post">
    <div class="user-container">
        <a class="post_userwrapper-img" style="display: block" href="users/profile?id=${param.user_id}">
            <img class="user-img up_user" src="${param.user_img}" alt="${param.user_username}" title="${param.user_username}">
        </a>
    </div>
    <div class="publication-container">
        <div class="publication-user">
            <p class="username t3"><a href="users/profile?id=${param.user_id}">${param.user_username}</a></p>
            <div class="type-user">
                <c:if test="${!empty param.list}">
                    <c:forEach items="${param.list}" var="item">
                        <img src="${item.replaceAll("[\\[|\\]]","")}" alt="logo">
                     </c:forEach>
                </c:if>
                <!--<img src="imgs/svg/rey.svg" alt="imagen del tipo de administrador" title="admin">
                <img src="imgs/svg/logo.svg" alt="" title="Creador">-->
            </div>
            <time>
                <c:out value="${param.post_date}"></c:out>
            </time>
        </div>
        <div class="publication-title">
            <h1 class="normal mr-b5">
                <c:out value="${param.post_title}"></c:out>
            </h1>
            <p class="mr-b5">
                <c:out value="${param.post_description}"></c:out>
            </p>
        </div>
        <div class="publication-img">
            <img src="${param.post_image}" alt="${param.post_description}">
        </div>
        <div class="publication-reactions">
            <div class="reactions-inner">
                <div class="comments">
                    <img src="imgs/svg/mensaje.svg" alt="">
                    <span class="count">${param.numbers_comments}</span>
                </div>
                <div class="love">
                    <img src="imgs/svg/corazon.svg" alt="">
                    <span class="count">${param.numbers_reactions}</span>
                </div>
            </div>
        </div>
    </div>
        <c:if test="${param.fijado eq 1}">
            <div class="pointer-publication">
                <img class="pointer-icon" src="imgs/svg/fijado3.svg" alt="icono de fijación">
                <span class="small p-fijada">Publicación fijada</span>
            </div>
        </c:if>
</article>