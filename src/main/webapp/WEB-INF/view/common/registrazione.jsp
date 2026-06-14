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

            <form action="${pageContext.request.contextPath}/registrazione" method="POST" class="auth-form" id="form-id" novalidate>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nome">Nome *</label>
                        <input type="text" id="nome" name="nome" placeholder="Solo lettere (max 20)" required maxlength="20">
                        <span id="nome-error" class="error-message"></span>
                    </div>
                    
                    <div class="form-group">
                        <label for="cognome">Cognome *</label>
                        <input type="text" id="cognome" name="cognome" placeholder="Solo lettere (max 20)" required maxlength="20">
                        <span id="cognome-error" class="error-message"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">Indirizzo Email *</label>
                    <input type="email" id="email" name="email" placeholder="esempio@email.com" required maxlength="255">
                    <span id="email-error" class="error-message"></span>
                </div>

                <div class="form-group">
                    <label for="password">Password *</label>
                    <input type="password" id="password" name="password" placeholder="Minimo 8 caratteri (1 maiuscola, 1 minuscola, 1 numero)" required>
               		<span id="pass-error" class="error-message"></span>  		
                </div>

                <div class="form-group">
                    <label for="cellulare">Numero di Cellulare</label>
                    <input type="tel" id="cellulare" name="cellulare" placeholder="Es: 3331234567" maxlength="50">
					<span id="cell-error" class="error-message"></span>  		      
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
        const form = document.getElementById("form-id");
        const nome = document.getElementById("nome");
        const cognome = document.getElementById("cognome");
        const inputEmail = document.getElementById("email");
        const pass = document.getElementById("password");
        const cellulare = document.getElementById("cellulare");
        
        const emailError = document.getElementById("email-error");
        const btnRegistrati = document.querySelector(".btn-auth");

        // Variabile di stato per tracciare se l'email è duplicata nel DB via AJAX
        let emailDuplicata = false;

        // Focus iniziale sul primo campo
        nome.focus();

        const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));

        //controllo AJAX
        inputEmail.addEventListener("change", function() {
            const emailValue = inputEmail.value.trim();
            const regexEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

            // Se il formato non è valido, non interroghiamo il DB
            if (!regexEmail.test(emailValue))
            {
                return;
            }

            fetch(contextPath + "/verificaEmail?email=" + encodeURIComponent(emailValue))
                .then(response => response.json())
                .then(data => {
                    if (data.esiste)
                    {
                        emailError.textContent = "Questo indirizzo email è già associato a un account.";
                        emailError.style.color = "#ff4d4d"; 
                        inputEmail.style.border = "1px solid #ff4d4d";
                        emailDuplicata = true;
                    }
                    else
                    {
                        emailError.textContent = "Email disponibile!";
                        emailError.style.color = "#2ecc71"; 
                        inputEmail.style.border = "1px solid #2ecc71";
                        emailDuplicata = false;
                    }
                })
                .catch(err => console.error("Errore durante il controllo email:", err));
        });

		//validzione del form su submit
        form.addEventListener("submit", function(event) {
            
            let formValido = true;
            let campoConErrore = null; // Per il focus dinamico

            document.getElementById("nome-error").innerText = "";
            document.getElementById("cognome-error").innerText = "";
            document.getElementById("pass-error").innerText = ""; 
            document.getElementById("cell-error").innerText = "";
            
            // Se l'email non è duplicata, puliamo lo stato precedente
            if (!emailDuplicata)
            {
                emailError.innerText = "";
                inputEmail.style.border = "";
            }

         	// Regex Semplice: Lettere maiuscole, minuscole e spazi (da 2 a 20 caratteri)
            const regexLettere = /^[a-zA-Z\s]{2,20}$/;
            
            if (!regexLettere.test(nome.value.trim()))
            {
                document.getElementById("nome-error").innerText = "Nome non valido. Usa solo lettere (2-20 caratteri).";
                formValido = false;
                campoConErrore = nome;
            }

            if (!regexLettere.test(cognome.value.trim()))
            {
                document.getElementById("cognome-error").innerText = "Cognome non valido. Usa solo lettere (2-20 caratteri).";
                formValido = false;
                if (!campoConErrore)
                		campoConErrore = cognome;
            }

	        const regexEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
            if (!regexEmail.test(inputEmail.value.trim()))
            {
                emailError.textContent = "Formato email non valido.";
                emailError.style.color = "#ff4d4d";
                inputEmail.style.border = "1px solid #ff4d4d";
                formValido = false;
                if (!campoConErrore)
                		campoConErrore = inputEmail;
            }

            // Regex Password: Minimo 8 caratteri, 1 maiuscola, 1 minuscola, 1 numero
            const regexPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
            if (!regexPassword.test(pass.value))
            {
                document.getElementById("pass-error").innerText = "La password deve contenere almeno 8 caratteri, una maiuscola, una minuscola e un numero.";
                formValido = false;
                if (!campoConErrore)
                		campoConErrore = pass;
            }

      		// Se l'utente ha inserito anche il cellulare, lo controlliamo
            if (cellulare.value.trim().length > 0)
            {
                const regexTelefono = /^[0-9]{10}$/; // Esattamente 10 cifre numeriche
                if (!regexTelefono.test(cellulare.value.trim()))
                {
                    document.getElementById("cell-error").innerText = "Numero non valido. Inserisci esattamente 10 cifre numeriche.";
                    formValido = false;
                    if (!campoConErrore)
                    		campoConErrore = cellulare;
                }
            }

            if (!formValido || emailDuplicata)
            {
                event.preventDefault(); // Blocca l'invio del form alla Servlet

                // Gestione ottimizzata del focus sul primo errore riscontrato
                if(campoConErrore)
                {
                    campoConErrore.focus();
                }
                else if(emailDuplicata)
                {
                    inputEmail.focus();
                }
            }
        });
	});
</script>

</body>
</html>s