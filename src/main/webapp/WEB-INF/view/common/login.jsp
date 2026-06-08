<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accedi - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registrazione.css">
</head>
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="main-content">
        <div class="auth-container">
            <h1 class="auth-title">Accedi a BlackTop</h1>
            
            <%-- Messaggio di Errore in caso di credenziali errate --%>
            <c:if test="${not empty errore}">
                <div class="error-box">
                    ${errore}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="POST" class="auth-form">
                
                <div class="form-group">
                    <label for="email">Indirizzo Email</label>
                    <input type="email" id="email" name="email" placeholder="esempio@email.com" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Inserisci la tua password" required>
                </div>

                <button type="submit" class="btn-auth">Accedi</button>
            </form>

            <div class="auth-redirect">
                <p>Non hai ancora un account? <a href="${pageContext.request.contextPath}/registrazione">Registrati qui</a></p>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

</body>
</html>