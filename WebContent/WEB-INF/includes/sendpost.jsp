<%-- 
    Document   : sendpost
    Created on : 20/02/2020, 07:37:32 PM
    Author     : Jerson
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8"%>

<div class="post-container-send" ${!empty sessionScope.user?'id=post-container-send':''}>
    <div class="send-container" id="send-container">
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <p class="small mr-b5">Inicie sesión para poder realizar publicaciones...</p>
                <a href="login" class="je-btn small dark-orange" style="width:auto;">Iniciar sesión</a>
            </c:when>
            <c:otherwise>
                <form id="form-post">
                    <input type="hidden" name="action" value="register">
                    <div class="send-inner">
                        <div class="send-user_img">
                            <img class="user-img up_user" src="${sessionScope.user.urlImage}" alt="${sessionScope.user.username}">
                        </div>
                        <div class="send-title">
                            <input id="send-title" type="text" name="title" placeholder="Ingrese el título">
                        </div>
                    </div>
                    <input type="file" name="img" class="ocultar" id="send-file-post"> 
                    <div class="send-details" id="send-details">
                        <div class="form-item" style="padding-top: 2rem;">
                            <label for="txt-description">Descripción:</label>
                            <textArea id="txt-description" name="description"></textArea>
                        </div>
                        <div class="form-item">
                            <div class="img_preview" id="img_preview_post"></div>
                         </div>
                    </div>
                    <div class="send-operations">
                        <div class="tag-container" id="tag-container">
                        </div>
                        <div class="buttons-container">
                            <img src="imgs/svg/img.svg" alt="subir imagen" id="addFile">
                            <span class="circle-carga"></span>
                            <span class="separation-buttons"></span>
                            <img src="imgs/svg/anadir.svg" alt="">
                            <img src="imgs/svg/enviar.svg" alt="" id="send_post">
                        </div>
                    </div>
                </form>        
            </c:otherwise>
        </c:choose>
    </div>
    <div class="desktop" id="desktop"></div>
    <c:if test="${!empty sessionScope.user}">
        
    </c:if>
</div>
