<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Accesso Negato - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errore.css">
</head>
<body>
    <div class="error-container">
        <h1 class="error-title access-denied">🔒 Accesso Negato (403)</h1>
        <p class="error-message">Non hai i permessi necessari per visualizzare questa pagina o risorsa.</p>
        <p class="error-submessage">Se ritieni sia un errore, prova a rifare il login con un account autorizzato.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn-error">Torna al sicuro in Home</a>
    </div>
</body>
</html>