<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrazione - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registrazione.css">
</head>
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="main-content">
        <div class="auth-container">
            <h1 class="auth-title">Crea il tuo Account</h1>
            
            <%-- Messaggio di Errore se la registrazione fallisce (es: email duplicata) --%>
            <c:if test="${not empty errore}">
                <div class="error-box">
                    ${errore}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/registrazione" method="POST" class="auth-form">
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nome">Nome *</label>
                        <input type="text" id="nome" name="nome" placeholder="Inserisci il tuo nome" required maxlength="20">
                    </div>
                    
                    <div class="form-group">
                        <label for="cognome">Cognome *</label>
                        <input type="text" id="cognome" name="cognome" placeholder="Inserisci il tuo cognome" required maxlength="20">
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">Indirizzo Email *</label>
                    <input type="email" id="email" name="email" placeholder="esempio@email.com" required maxlength="255">
                </div>

                <div class="form-group">
                    <label for="password">Password *</label>
                    <input type="password" id="password" name="password" placeholder="Minimo 6 caratteri" required>
                </div>

                <div class="form-group">
                    <label for="cellulare">Numero di Cellulare</label>
                    <input type="tel" id="cellulare" name="cellulare" placeholder="Es: 3331234567" maxlength="50">
                </div>

                <button type="submit" class="btn-auth">Registrati</button>
            </form>

            <div class="auth-redirect">
                <p>Hai già un account? <a href="${pageContext.request.contextPath}/login">Accedi qui</a></p>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

</body>
</html>