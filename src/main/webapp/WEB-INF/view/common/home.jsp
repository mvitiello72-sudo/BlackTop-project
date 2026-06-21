<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BlackTop - NBA Jersey Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>

<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="main-content">
        <section class="hero">
            <div class="hero-text">
                <h1>Domina il Blacktop</h1>
                <p>Scendi in campo con le Jersey NBA più esclusive, storiche e moderne.</p>
                <a href="${pageContext.request.contextPath}/catalogo" class="btn-shop">Scopri il Catalogo</a>
            </div>
        </section>

        <section class="featured-products">
            <h2>I più desiderati</h2>
            <div class="products-grid">
                
                <c:choose>
                    <c:when test="${not empty prodottiEvidenza}"> <!-- se prodottiEvidenza non è vuoto -->
                    		<c:forEach var="prodotto" items="${prodottiEvidenza}"> <!-- cicla su ogni item di prodottiEvidenza -->
                    			<div class="product-card"> 
                    			  
                    				<!-- Prende solo la prima immagine -->
                                <img src="${pageContext.request.contextPath}/${prodotto.immagini[0].percorsoImmagine}" alt="${prodotto.nome}">         
                                
                                <h3>${prodotto.nome}</h3>
                                <p class="team">${prodotto.squadra}</p>
                                <p class="price">€ ${prodotto.prezzo}</p>
                                <a href="${pageContext.request.contextPath}/prodotto?id=${prodotto.idProdotto}" class="btn-detail">Vedi Dettaglio</a>
                            
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>Nessun prodotto in evidenza al momento. Torna a trovarci!</p>
                    </c:otherwise>
                </c:choose>
                
            </div>
        </section>
        
    </main>
    
    

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

</body>
</html>