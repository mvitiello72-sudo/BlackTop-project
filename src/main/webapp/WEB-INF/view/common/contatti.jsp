<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contattaci - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contatti.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="main-content">
        <div class="contact-container">
            <h1 class="contact-title">Invia un messaggio al Supporto</h1>

            <c:if test="${not empty successo}">
                <div class="alert alert-success">${successo}</div>
            </c:if>
            <c:if test="${not empty errore}">
                <div class="alert alert-danger">${errore}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/contatti" method="POST" id="form-contatti" novalidate>
                <div class="form-group">
                    <label for="nome">Nome Completo</label>
                    <input type="text" id="nome" name="nome" class="form-control" placeholder="Inserisci il tuo nome" required maxlength="50">
                    <span id="nome-error" class="error-message"></span>
                </div>
                
                <div class="form-group">
                    <label for="email">Indirizzo Email</label>
                    <input type="email" id="email" name="email" class="form-control" placeholder="esempio@email.com" required maxlength="255">
                    <span id="email-error" class="error-message"></span>
                </div>

                <div class="form-group">
                    <label for="messaggio">Come possiamo aiutarti?</label>
                    <textarea id="messaggio" name="messaggio" class="form-control" placeholder="Scrivi qui il tuo messaggio..." required></textarea>
                    <span id="messaggio-error" class="error-message"></span>
                </div>

                <button type="submit" class="btn-submit">Invia Messaggio</button>
            </form>
        </div>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const form = document.getElementById("form-contatti");
        const nome = document.getElementById("nome");
        const inputEmail = document.getElementById("email");
        const messaggio = document.getElementById("messaggio");

        // Focus iniziale sul primo campo
        nome.focus();

        // Validazione del form su submit
        form.addEventListener("submit", function(event) {
            
            let formValido = true;
            let campoConErrore = null; 

            // Resettiamo solo il testo interno dei messaggi d'errore come in registrazione.jsp
            document.getElementById("nome-error").innerText = "";
            document.getElementById("email-error").innerText = "";
            document.getElementById("messaggio-error").innerText = "";

            //Validazione Nome Completo
            const regexNome = /^[a-zA-Z\s]{2,50}$/;
            if (!regexNome.test(nome.value.trim()))
            {
                document.getElementById("nome-error").innerText = "Nome non valido. Usa solo lettere (2-50 caratteri).";
                formValido = false;
                campoConErrore = nome;
            }

            //Validazione Formato Email
            const regexEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
            if (!regexEmail.test(inputEmail.value.trim()))
            {
                document.getElementById("email-error").innerText = "Formato email non valido.";
                formValido = false;
                if (!campoConErrore) campoConErrore = inputEmail;
            }

            //Validazione Messaggio
            if (messaggio.value.trim().length < 10)
            {
                document.getElementById("messaggio-error").innerText = "Il messaggio è troppo corto. Scrivi almeno 10 caratteri.";
                formValido = false;
                if (!campoConErrore) campoConErrore = messaggio;
            }

            // Blocco dell'invio se ci sono errori
            if (!formValido)
            {
                event.preventDefault(); 
                
                if (campoConErrore)
                {
                    campoConErrore.focus();
                }
            }
        });
    });
</script>

</body>
</html>