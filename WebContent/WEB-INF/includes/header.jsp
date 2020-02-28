<%-- 
    Document   : header
    Created on : 05/02/2020, 09:22:15 PM
    Author     : Jerson
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<header class="header-container">
    <div class="je-container">
        <div class="je-item container-logo">
            <div class="logo-inner">
                <img src="imgs/svg/logo.svg" alt="Logo de la página">
            </div>
        </div>
        <div class="je-item container-nav">
            <div class="nav-inner_toogle" id="toogle-nav">
                <img class="img-menu" src="imgs/svg/menu.svg">
            </div>
            <div class="nav-inner">
                <div class="nav">
                    <nav>
                        <h1 class="ocultar">Menú de navegación</h1>
                        <ul class="nav-list">
                            <li class="list-item"><a href="/">Inicio</a></li>
                            <li class="list-item"><a href="/categorias">Categorías</a></li>
                            <li class="list-item"><a href="/users/moderators">Moderadores</a></li>
                            <li class="list-item"><a href="/users">Usuarios</a></li>
                            <li class="list-item"><a href="/conoceme">Conóceme</a></li>
                        </ul> 
                    </nav>  
                </div>
                <div class="profile">
                    <c:choose>
                        <c:when test="${!empty sessionScope.user}">
                            <a href="users/profile?id=${sessionScope.user.idUser}">
                                <img class="img-user up_user" src="${sessionScope.user.urlImage}" alt="Foto del usuario ${sessionScope.user.username}">
                            </a>
                            <img class="img-bottom-arrow" src="imgs/svg/flecha-hacia-abajo.svg" alt="icono de flecha hacia abajo">
                        </c:when>
                        <c:otherwise>
                            <a href="login" style="background-color: darkred; font-size: 13px;line-height: 1.6rem;border-radius: 5px;color:#fff;padding: 0 .5rem;margin-right: 0;width: auto;">Iniciar sesión</a>
                        </c:otherwise>
                    </c:choose>
                    
                </div>
            </div> 
        </div>
    </div>
</header>