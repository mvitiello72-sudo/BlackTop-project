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

            <form action="${pageContext.request.contextPath}/login" method="POST" class="auth-form" id="loginForm" novalidate>
                
                <div class="form-group">
                    <label for="email">Indirizzo Email</label>
                    <input type="email" id="email" name="email" placeholder="esempio@email.com" required>
                    <span id="email-error" class="error-message"></span>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Minimo 8 caratteri (1 maiuscola, 1 minuscola, 1 numero)" required>
                    <span id="password-error" class="error-message"></span>
                </div>

                <button type="submit" class="btn-auth">Accedi</button>
            </form>

            <div class="auth-redirect">
                <p>Non hai ancora un account? <a href="${pageContext.request.contextPath}/registrazione">Registrati qui</a></p>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

	<script>
		document.addEventListener("DOMContentLoaded", function(){
			
			const form = document.getElementById("loginForm");
			const email = document.getElementById("email");
			const pass = document.getElementById("password");
			
			email.focus();
			
			form.addEventListener("submit", function(event){
				
				const emailValue = email.value.trim();
	            const passwordValue = pass.value;
	            
	            let formValido = true;
	            let campoConErrore = null; //cambia il focus in base a cos'è sbagliato
	            
	            //puliamo gli errori ad ogni submit
	            document.getElementById("email-error").innerText = "";
	            document.getElementById("password-error").innerText = "";
	            
	            const regexEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
	            if(!regexEmail.test(emailValue))
	            	{
	            		document.getElementById("email-error").innerText = "Formato email non valido. Controlla la sintassi.";
	            		formValido = false
	            		campoConErrore = email;
	            	}
	            
	            //Validazione pass (Minimo 8 caratteri, una maiuscola, una minuscola, un numero)
	            const regexPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
                if(!regexPassword.test(passwordValue))
                {
                    document.getElementById("password-error").innerText = "La password non rispetta i requisiti richiesti (Minimo 8 caratteri, una maiuscola, una minuscola, un numero).";
                    formValido = false;
                    if(!campoConErrore) 
                    		campoConErrore = pass;
                }
	            
	            if(!formValido)
	            	{
	            		event.preventDefault();
	            		campoConErrore.focus();
	            	}
			})	
		})
	</script>

</body>
</html>