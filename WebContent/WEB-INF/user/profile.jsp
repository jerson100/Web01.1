<%-- 
    Document   : profile
    Created on : 20/02/2020, 06:06:41 AM
    Author     : Jerson
--%>

<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
        <meta name="autor" content="Jerson Ramírez Ortiz">
        <meta charset="UTF-8">
        <title><c:out value="${requestScope.datauser.username}"></c:out></title>
        <base href="../">
        <link href="css/base/base.css" rel="stylesheet" type="text/css"/>
        <link href="css/layout/layout.css" rel="stylesheet" type="text/css"/>
        <link href="css/theme/colors.css" rel="stylesheet" type="text/css"/>
        <link href="css/theme/fonts.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Raleway:400,500,700&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700&display=swap" rel="stylesheet">
        <link href="css/theme/typography.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/header.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/footer.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/boton.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/load.css" rel="stylesheet" type="text/css"/>
        <link href="js/utils/modal/je-modal-style.css" rel="stylesheet" type="text/css"/>
        <link href="css/pages/profile/profile.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/article-post.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/sendprofile.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/formulario.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="../includes/header.jsp"></jsp:include>
        <div class="je-container profile-banner-container">
            <div class="je-item">
                <div class="profile-banner_portada">
                    <img src="imgs/jpg/fondo01.jpg" alt="Imagen de portada">
                    <c:if test="${!empty sessionScope.user && sessionScope.user.idUser != requestScope.datauser.idUser}">
                            <button class="btn je-btn dark-orange t4">Seguir</button>
                    </c:if>
                    <c:if test="${!empty sessionScope.user && sessionScope.user.idUser == requestScope.datauser.idUser}">
                        <button class="btn je-btn dark-black t4">Cambiar portada</button>
                    </c:if>
                    <h1 class="profile-user_username t3"><a href="users/profile?id=${requestScope.datauser.idUser}">${requestScope.datauser.username}</a></h1>
                </div>
                <div class="user-profile-banner">
                    <div class="user-profile_wrapper">
                        <div class="user-profile_img">
                            <div class="user-profile-img">
                                <img class="up_user" src="${requestScope.datauser.urlImage}" alt="${requestScope.datauser.username}">
                                <c:if test="${!empty sessionScope.user && sessionScope.user.idUser == requestScope.datauser.idUser}">
                                    <a id="updated-img" class="user-profile_updated">
                                        <img src="imgs/svg/camera.svg" alt="icono de cambio de perfil" title="Cambiar de foto de perfil">
                                        <span class="smaller" style="color:#fff;">Actualizar</span>
                                    </a>    
                                </c:if>
                                <span id="profile_idUser" class="ocultar">${requestScope.datauser.idUser}</span>
                            </div>
                        </div>
                        <div class="user_profile-nav-container">
                            <nav class="user-profile-nav">
                                <h1 class="ocultar">Menú de navegación de configuraciones</h1>
                                <ul class="user-profile-nav_list">
                                    <li>
                                        <img src="imgs/svg/timeline.svg" alt="Icono de línea de tiempo">
                                        <a href="">Línea de tiempo</a>
                                    </li>
                                    <li>
                                        <img src="imgs/svg/logo.svg" alt="Icono de logo de la página">
                                        <a href="">Panel adm</a>
                                    </li>
                                    <li>
                                        <img src="imgs/svg/friends.svg" alt="Icono de seguidores">
                                        <a href="">Seguidores</a>
                                    </li>
                                    <li>
                                        <img src="imgs/svg/configuration.svg" alt="Icono de configuración">
                                        <a href="">Configuración</a>
                                    </li>
                                    <li>
                                        <img src="imgs/svg/activity.svg" alt="Icono de actividad">
                                        <a href="">Actividad</a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="je-container">
            <main class="main-container-profile">
                <div class="je-item">
                    <div class="profile-details-section">
                        <section class="information-section profile-section">
                            <div class="je-item information-section_wrapper">
                                <header class="profile-header">
                                    <h1 class="t3">Información</h1>
                                </header>
                                <div class="information-inner">
                                    <p class="information-inner_description">
                                    <c:set var="description" value="${requestScope.datauser.description}" scope="request"></c:set>
                                    <c:choose>
                                        <c:when test="${!empty requestScope.description}">${requestScope.description}</c:when>
                                        <c:otherwise>${"Sin descripción"}</c:otherwise>
                                    </c:choose>
                                    </p>
                                    <ul class="information-inner_list">
                                        <li>
                                            <span class="bold t5">Fecha de nacimiento: </span>
                                            <span class="small">
                                            
                                            <c:choose>
                                                
                                                <c:when test="${!empty requestScope.dataUser.happyDate}">${requestScope.dataUser.happyDate}</c:when>
                                                <c:otherwise>-</c:otherwise>
                                                
                                            </c:choose>
                                            
                                            <c:out value="${requestScope.datauser.happyDate}"></c:out></span>
                                        </li>
                                        <li>
                                            <span class="bold t5">Nombre: </span>
                                            <span class="small"><c:out value="${requestScope.datauser.name}"></c:out></span>
                                        </li>
                                        <li>
                                            <span class="bold t5">Apellidos </span>
                                            <span class="small"><c:out value="${requestScope.datauser.lastname}"></c:out></span>
                                        </li>
                                        <li>
                                            <span class="bold t5">Username: </span>
                                            <span class="small"><c:out value="${requestScope.datauser.username}"></c:out></span>
                                        </li>
                                        <li>
                                            <span class="bold t5">Email: </span>
                                            <span class="small"><c:out value="${requestScope.datauser.email}"></c:out></span>
                                        </li>
                                        <li>
                                            <span class="bold t5">Se unió: </span>
                                            <span class="small"><fmt:formatDate pattern="dd/MM/yyyy yy:mm:ss" value="${requestScope.datauser.dateRegister}"/></span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </section>
                        <section class="follows-section profile-section">
                            <div class="je-item">
                                <header class="profile-header">
                                    <img src="imgs/svg/friends.svg" alt="">
                                    <h1 class="t3">Seguidores</h1>
                                    <span class="profile-numbers_followers">180</span>
                                </header>
                                <div class="follows-inner">
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/22.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/24.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/25.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/22.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/22.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/24.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/25.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/24.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/22.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/25.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/22.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                    <div class="follow-group">
                                        <img class="follower-img" src="imgs/jpg/24.jpg" alt="">
                                        <a href="" class="follower-username smaller">Manuel05</a>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
                <div class="je-item">
                    <jsp:include page="../includes/sendpost.jsp"></jsp:include>
                    <div class="post-container-profile">
                        <c:choose>
                            <c:when test="${!empty requestScope.posts}">
                                <c:forEach items="${requestScope.posts}" var="item">
                                    <fmt:formatDate  value="${item.date}" pattern="yyyy-MM-dd" var="d"/>
                                    <jsp:include page="../includes/post-article.jsp" >
                                        <jsp:param name="user_id" value="${requestScope.datauser.idUser}"></jsp:param>
                                        <jsp:param name="user_img" value="${requestScope.datauser.urlImage}"></jsp:param>
                                        <jsp:param name="user_username" value="${requestScope.datauser.username}"></jsp:param>
                                        <jsp:param name="list" value="${requestScope.typeUser}"></jsp:param>
                                        <jsp:param name="fijado" value="${item.isSet}"></jsp:param>
                                        <jsp:param name="post_image" value="${item.urlImage}"></jsp:param>
                                        <jsp:param name="post_date" value="${d}"></jsp:param>
                                        <jsp:param name="post_title" value="${item.title}"></jsp:param>
                                        <jsp:param name="post_id" value="${item.id}"></jsp:param>
                                        <jsp:param name="post_description" value="${item.description}"></jsp:param>
                                        <jsp:param name="numbers_comments" value="50"></jsp:param>
                                        <jsp:param name="numbers_reactions" value="${item.countReaction}"></jsp:param>
                                    </jsp:include>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p style="text-align: center;">Este usuario aún no realiza ninguna publicación...</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </main>
        </div>
        <%@include file="../includes/footer.jsp"%>
        <script src="js/utils/modal/je-modal.js" type="text/javascript"></script>
        <script src="js/pages/components/loader.js"></script>
        <script src="js/pages/profile/profile_updated-img.js" type="text/javascript"></script>
        <script src="js/pages/components/send-post.js" type="text/javascript"></script>
    </body>
</html>
