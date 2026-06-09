<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    
    <title>
        <c:choose>
            <c:when test="${not empty prodotto}">
                <c:out value="${prodotto.nome}" /> - BlackTop
            </c:when>
            <c:otherwise>
                Dettaglio Prodotto - BlackTop
            </c:otherwise>
        </c:choose>
    </title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/prodotto.css">
</head>

<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />
    
    <main class="main-content">
        <c:choose>
            <c:when test="${not empty prodotto}">
                <div class="prod-detail-container">
                    
                    <!-- GALLERIA IMMAGINI -->
                    <div class="prod-gallery">
                        <div class="main-image">
                            <img id="current-image" src="${pageContext.request.contextPath}/img/prodotti/${prodotto.immagini[0].percorsoImmagine}" alt="${prodotto.nome}">
                        </div>
                        <div class="grid-img">
                            <c:forEach var="img" items="${prodotto.immagini}">
                                <img src="${pageContext.request.contextPath}/img/prodotti/${img.percorsoImmagine}" 
                                     alt="Miniatura" 
                                     class="grid" 
                                     onclick="changeImage(this.src)">
                            </c:forEach>
                        </div>
                    </div>

                    <!-- DETTAGLI E ACQUISTO -->
                    <div class="prod-info-box">
                        <span class="prod-team">${prodotto.squadra}</span>
                        <h1 class="prod-title">${prodotto.nome}</h1>
                        <p class="prod-price">€ ${prodotto.prezzo}</p>
                        
                        <div class="prod-description">
                            <h3>Descrizione</h3>
                            <p>${prodotto.descrizione}</p>
                        </div>

                        <!-- FORM ACQUISTO -->
                        <form action="${pageContext.request.contextPath}/carrello" method="POST" id="add-to-cart-form">
                            <input type="hidden" name="action" value="add">
                            
                            <!-- L'ID cambia via JavaScript quando l'utente sceglie un'altra taglia -->
                            <input type="hidden" name="idProdotto" id="idProdotto" value="${prodotto.idProdotto}">
                            
                            <!-- SELETTORE TAGLIE DINAMICO -->
                            <div class="form-group">
                                <label for="taglia">Seleziona Taglia:</label>
                                <select name="taglia" id="taglia" required onchange="aggiornaIdProdotto(this)">
                                    <c:forEach var="variante" items="${variantiTaglia}">
                                        <option value="${variante.idProdotto}" ${variante.idProdotto == prodotto.idProdotto ? 'selected' : ''}>
                                            ${variante.taglia} (Disponibili: ${variante.stock})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- QUANTITÀ -->
                            <div class="form-group">
                                <label for="quantita">Quantità:</label>
                                <!-- Il massimo acquistabile si adatta allo stock reale del prodotto corrente -->
                                <input type="number" name="quantita" id="quantita" value="1" min="1" max="${prodotto.stock}">
                                <span id="error-quantita" class="error-message"></span>
                            </div>

                            <button type="submit" class="btn-add-cart">Aggiungi al Carrello</button>
                        </form>
                        
                    </div>
                </div>
                
                <hr class="divider" style="margin: 60px 0 40px 0;">

                <!-- PRODOTTI CORRELATI -->
                <section class="featured-products">
                    <h2>Potrebbero piacerti</h2>
                    <div class="products-grid">
                        <c:choose>
                            <c:when test="${not empty prodottiCorrelati}">
                                <c:forEach var="correlato" items="${prodottiCorrelati}">
                                    <div class="product-card"> 
                                        <img src="${pageContext.request.contextPath}/img/prodotti/${correlato.immagini[0].percorsoImmagine}" alt="${correlato.nome}">         
                                        
                                        <h3>${correlato.nome}</h3>
                                        <p class="team">${correlato.squadra}</p>
                                        <p class="price">€ ${correlato.prezzo}</p>
                                        <a href="${pageContext.request.contextPath}/prodotto?id=${correlato.idProdotto}" class="btn-detail">Vedi Dettaglio</a>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p class="no-products">Nessun prodotto correlato disponibile per questa squadra.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </section>  
            </c:when>
            
            <c:otherwise>
                <!-- ERROR STATE -->
                <div class="error-box">
                    <h2>Prodotto non trovato</h2>
                    <p>Ci dispiace, il prodotto richiesto non è disponibile o è inesistente.</p>
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn-shop">Torna al Catalogo</a>
                </div>
            </c:otherwise>
        </c:choose>
    </main>
    
    <jsp:include page="/WEB-INF/view/components/footer.jsp" />
    
    <script>
        // Cambia l'immagine principale al click sulle miniature
        function changeImage(src)
        {
            document.getElementById('current-image').src = src;
        }
        
        // Reindirizza l'utente alla scheda tecnica della taglia selezionata
        function aggiornaIdProdotto(selectElement)
        {
            const nuovoId = selectElement.value;
            // Modifica l'ID nell'input prima del reindirizzamento
            document.getElementById('idProdotto').value = nuovoId;
            // Ricarica la pagina passando il nuovo ID
            window.location.href = "${pageContext.request.contextPath}/prodotto?id=" + nuovoId;
        }
        
        // Validazione della quantità legata allo stock massimo reale
        document.getElementById('add-to-cart-form')?.addEventListener('submit', function(e) {
            const quantitaInput = document.getElementById('quantita');
            const quantita = parseInt(quantitaInput.value);
            const maxStock = parseInt(quantitaInput.getAttribute('max'));
            const errorSpan = document.getElementById('error-quantita');
            
            if (isNaN(quantita) || quantita < 1 || quantita > maxStock)
            {
                e.preventDefault(); // Blocca l'aggiunta al carrello
                errorSpan.textContent = "Seleziona una quantità valida (Disponibili: max " + maxStock + ").";
                quantitaInput.focus();
            }
            else
            {
                errorSpan.textContent = "";
            }
        });
    </script>

</body>
</html>