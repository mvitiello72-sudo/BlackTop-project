<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Pagina Non Trovata - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errore.css">
</head>
<body>
    <div class="error-container">
        <h1 class="error-title">Oops! Errore 404</h1>
        <p class="error-message">La pagina che stai cercando sembra non esistere.</p>
        <p class="error-submessage">Verifica che l'indirizzo inserito sia corretto o torna indietro.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn-error">Torna alla Home</a>
    </div>
</body>
</html>