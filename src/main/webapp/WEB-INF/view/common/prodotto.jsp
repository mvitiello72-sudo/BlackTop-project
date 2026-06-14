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
    
    <c:if test="${not empty sessionScope.prodottoAggiunto}">
        <div id="modal-overlay" class="modal-overlay visible">
            <div class="modal-box">
                <div class="modal-icon">✓</div>
                <h2>Prodotto aggiunto!</h2>
                <p>Il prodotto è stato inserito correttamente nel tuo carrello.</p>
                <div class="modal-actions">
                    <a href="${pageContext.request.contextPath}/carrello" class="btn-modal-cart">Vai al Carrello</a>
                    <button type="button" class="btn-modal-close" onclick="chiudiModale()">Continua lo Shopping</button>
                </div>
            </div>
        </div>
        <% session.removeAttribute("prodottoAggiunto"); %>
    </c:if>
    
    <main class="main-content">
        <c:choose>
            <c:when test="${not empty prodotto}">
                <div class="prod-detail-container">
                    
                    <div class="prod-gallery">
                        <div class="main-image">
                            <img id="current-image" src="${pageContext.request.contextPath}/${prodotto.immagini[0].percorsoImmagine}" alt="${prodotto.nome}">
                        </div>
                        <div class="grid-img">
                            <c:forEach var="img" items="${prodotto.immagini}">
                                <img src="${pageContext.request.contextPath}/${img.percorsoImmagine}" 
                                     alt="Miniatura" 
                                     class="grid" 
                                     onclick="changeImage(this.src)">
                            </c:forEach>
                        </div>
                    </div>

                    <div class="prod-info-box">
                        <span class="prod-team">${prodotto.squadra}</span>
                        <h1 class="prod-title">${prodotto.nome}</h1>
                        <p class="prod-price">€ ${prodotto.prezzo}</p>
                        
                        <div class="prod-description">
                            <h3>Descrizione</h3>
                            <p>${prodotto.descrizione}</p>
                        </div>

                        <form action="${pageContext.request.contextPath}/carrello" method="POST" id="add-to-cart-form" novalidate>
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="idProdotto" id="idProdotto" value="${prodotto.idProdotto}">
                            
                            <div class="form-group">
                                <label for="taglia">Seleziona Taglia:</label>
                                <select name="taglia" id="taglia" required onchange="aggiornaIdProdotto(this)">
                                    <c:forEach var="variante" items="${variantiTaglia}">
                                        <option value="${variante.taglia}" ${variante.idProdotto == prodotto.idProdotto ? 'selected' : ''}>
                                            ${variante.taglia} (Disponibili: ${variante.stock})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="quantita">Quantità:</label>
                                <input type="number" name="quantita" id="quantita" value="1" min="1" max="${prodotto.stock}">
                                <span id="error-quantita" class="error-message"></span>
                            </div>

                            <button type="submit" class="btn-add-cart">Aggiungi al Carrello</button>
                        </form>
                    </div>
                </div>
                
                <hr class="divider" style="margin: 60px 0 40px 0;">

                <section class="featured-products">
                    <h2>Potrebbero piacerti</h2>
                    <div class="products-grid">
                        <c:choose>
                            <c:when test="${not empty prodottiCorrelati}">
                                <c:forEach var="correlato" items="${prodottiCorrelati}">
                                    <div class="product-card"> 
                                        <img src="${pageContext.request.contextPath}/${correlato.immagini[0].percorsoImmagine}" alt="${correlato.nome}">         
                                        
                                        <h3>${correlato.nome}</h3>
                                        <p class="team">${correlato.squadra}</p>
                                        <p class="price">€ ${correlato.prezzo}</p>
                                        <a href="${pageContext.request.contextPath}/prodotto?id=${correlato.idProdotto}" class="btn-detail">Vedi Dettaglio</a>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p class="no-products">Nessun prodotto correlato disponibile.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </section>  
            </c:when>
            
            <c:otherwise>
                <div class="error-box">
                    <h2>Prodotto non trovato</h2>
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn-shop">Torna al Catalogo</a>
                </div>
            </c:otherwise>
        </c:choose>
    </main>
    
    <jsp:include page="/WEB-INF/view/components/footer.jsp" />
    
    <script>
        function changeImage(src) {
            document.getElementById('current-image').src = src;
        }
        
        function aggiornaIdProdotto(selectElement) {
            const nuovoId = selectElement.value;
            window.location.href = "${pageContext.request.contextPath}/prodotto?id=" + nuovoId;
        }

        // Questa funzione serve solo a nascondere il box se l'utente sceglie di continuare a guardare la maglia
        function chiudiModale() {
            const overlay = document.getElementById("modal-overlay");
            if(overlay) {
                overlay.classList.remove("visible");
            }
        }
        
        document.addEventListener("DOMContentLoaded", function() {
            const formCart = document.getElementById("add-to-cart-form");
            if (!formCart) return;

            const quantitaInput = document.getElementById("quantita");
            const errorQuantita = document.getElementById("error-quantita");

            // Qui rimane ESCLUSIVAMENTE la tua validazione testuale originaria prima di far partire il form
            formCart.addEventListener("submit", function(event) {
                let formValido = true;
                errorQuantita.innerText = "";

                if (quantitaInput.validity.badInput || quantitaInput.value.trim() === "") {
                    errorQuantita.innerText = "Inserisci una quantità valida.";
                    formValido = false;
                } else {
                    const qtaValue = parseInt(quantitaInput.value, 10);
                    const maxStock = parseInt(quantitaInput.getAttribute("max"), 10);

                    if (isNaN(qtaValue) || qtaValue < 1) {
                        errorQuantita.innerText = "Inserisci una quantità valida (minimo 1).";
                        formValido = false;
                    } else if (qtaValue > maxStock) {
                        errorQuantita.innerText = "Quantità non disponibile. Massimo ordinabile: " + maxStock + ".";
                        formValido = false;
                    }
                }

                if (!formValido) {
                    event.preventDefault();
                    quantitaInput.focus();
                }
            });
        });
    </script>
</body>
</html>