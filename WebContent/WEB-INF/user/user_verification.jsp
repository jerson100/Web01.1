<%-- 
    Document   : userverification
    Created on : 17/02/2020, 04:01:18 PM
    Author     : Jerson
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
        <meta name="autor" content="Jerson Ramírez Ortiz">
        <meta charset="UTF-8">
        <title>Verification of user</title>
        <base href="../">
        <link href="css/base/base.css" rel="stylesheet" type="text/css"/>
        <link href="css/layout/layout.css" rel="stylesheet" type="text/css"/>
        <link href="css/theme/colors.css" rel="stylesheet" type="text/css"/>
        <link href="css/theme/fonts.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Raleway:400,500,700&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700&display=swap" rel="stylesheet">
        <link href="css/theme/typography.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/header.css" rel="stylesheet" type="text/css"/>
        <link href="css/pages/user/userVerification.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/footer.css" rel="stylesheet" type="text/css"/>
        <link href="css/components/boton.css" rel="stylesheet" type="text/css"/>
        <link href="css/pages/mail_verification.css/verification.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="../includes/header.jsp"></jsp:include>
        <main class="main-userVericiation">
            <div class="je-container">
                <div class="je-item">
                    <div class="user-verification_inner">
                        <div class="user-verification_wrapper">
                            <p>${requestScope.message}</p>
                            <img class="user-verification_wrapper-logo" src="imgs/svg/logo.svg" alt="Logo de la página">
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="../includes/footer.jsp"%>
    </body>
</html>
