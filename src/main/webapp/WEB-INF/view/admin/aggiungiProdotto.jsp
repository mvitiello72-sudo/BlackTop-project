<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Aggiungi Prodotto</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aggiungiProdotto.css">
</head>
	
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="main-content">
        <h2>Aggiungi un Nuovo Prodotto</h2>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/aggiungiProdotto" method="post">
            <div>
                <label for="nome">Nome Prodotto *</label>
                <input type="text" id="nome" name="nome" required>
            </div>
            <div>
                <label for="squadra">Squadra *</label>
                <input type="text" id="squadra" name="squadra" required>
            </div>
            <div>
                <label for="categoria">Categoria *</label>
                <input type="text" id="categoria" name="categoria" required>
            </div>
            <div>
                <label for="materiale">Materiale</label>
                <input type="text" id="materiale" name="materiale">
            </div>
            <div>
                <label for="taglia">Taglia *</label>
                <input type="text" id="taglia" name="taglia" placeholder="es. M, L, XL" required>
            </div>
            <div>
                <label for="prezzo">Prezzo (€) *</label>
                <input type="number" id="prezzo" name="prezzo" step="0.01" min="0" required>
            </div>
            <div>
                <label for="sconto">Sconto (%)</label>
                <input type="number" id="sconto" name="sconto" min="0" max="100" value="0">
            </div>
            <div>
                <label for="stock">Stock Disponibile *</label>
                <input type="number" id="stock" name="stock" min="0" required>
            </div>
            <div>
                <label for="descrizione">Descrizione</label>
                <textarea id="descrizione" name="descrizione" rows="4"></textarea>
            </div>
            <div>
                <label>
                    <input type="checkbox" name="attivo" value="true" checked> Rendi il prodotto subito attivo e visibile
                </label>
            </div>
            
            <button type="submit" class="btn-add">Salva Prodotto</button>
            <a href="${pageContext.request.contextPath}/admindashboard" style="text-align: center;">Annulla e Torna indietro</a>
        </form>
    </main>
    
    <jsp:include page="/WEB-INF/view/components/footer.jsp" />
    
</body>
</html>