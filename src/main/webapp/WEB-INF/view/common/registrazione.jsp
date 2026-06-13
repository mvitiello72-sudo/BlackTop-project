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
                    <span id="email-error" style="font-size: 13px; margin-top: 5px; display: block; font-weight: 600;"></span>
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

<script>
	document.addEventListener("DOMContentLoaded", function() {
    const inputEmail = document.getElementById("email");
    const emailError = document.getElementById("email-error");
    const btnRegistrati = document.querySelector(".btn-auth");

    if (!inputEmail || !emailError) 
    		return;

    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));

    // Ascolta quando l'utente finisce di digitare
    inputEmail.addEventListener("change", function() {
        const emailValue = inputEmail.value.trim();

        // Regex per email
        if (emailValue.length < 5 || !emailValue.includes("@")) {
            emailError.textContent = "";
            return;
        }

        // Chiamata AJAX
        fetch(contextPath + "/verificaEmail?email=" + encodeURIComponent(emailValue))
            .then(response => response.json())
            .then(data => {
                if (data.esiste) 
                {
                    // Se l'email esiste già
                    emailError.textContent = "Questo indirizzo email è già associato a un account.";
                    emailError.style.color = "#ff4d4d"; // Rosso errore
                    inputEmail.style.border = "1px solid #ff4d4d";
                    btnRegistrati.disabled = true; // Blocca il bottone di invio
                    btnRegistrati.style.opacity = "0.5";
                } 
                else
                {
                    // Se l'email è libera
                    emailError.textContent = "Email disponibile!";
                    emailError.style.color = "#2ecc71"; // Verde successo
                    inputEmail.style.border = "1px solid #2ecc71";
                    btnRegistrati.disabled = false; // Sblocca il bottone
                    btnRegistrati.style.opacity = "1";
                }
            })
            .catch(err => console.error("Errore durante il controllo email:", err));
    		});
	});
</script>

</body>
</html>