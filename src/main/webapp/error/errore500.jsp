<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Errore di Sistema - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errore.css">
</head>
<body>
    <div class="error-container">
        <h1 class="error-title server-error">Errore di Sistema (500)</h1>
        <p class="error-message">Si è verificato un errore interno nel server durante l'elaborazione.</p>
        <p class="error-submessage">I nostri tecnici sono già al lavoro per risolvere il problema.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn-error">Torna alla Home</a>
    </div>
</body>
</html>