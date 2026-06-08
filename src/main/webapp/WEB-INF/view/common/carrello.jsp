<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
	<meta charset="UTF-8">
	<title>Il tuo Carrello - BlackTop</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carrello.css">
</head>

<body>

	<jsp:include page="/WEB-INF/view/components/header.jsp" />
	
	<main class="main-content">
        <h1 class="cart-title">Il tuo Carrello</h1>
        
        <c:choose>
            <%-- CONTROLLO: Se il carrello esiste e ha elementi dentro --%>
            <c:when test="${not empty sessionScope.carrello && not empty sessionScope.carrello.elementi}">
                <div class="cart-container">
                    
                    <div class="cart-items-list">
                        <c:forEach var="item" items="${sessionScope.carrello.elementi}">
                            <div class="cart-item">
                                
                                <div class="item-img">
                                    <img src="${pageContext.request.contextPath}/img/prodotti/${item.prodotto.immagini[0].percorsoImmagine}" alt="${item.prodotto.nome}">
                                </div>
                                
                                <div class="item-details">
                                    <span class="item-team">${item.prodotto.squadra}</span>
                                    <h3>
                                        <a href="${pageContext.request.contextPath}/prodotto?id=${item.prodotto.idProdotto}">
                                            ${item.prodotto.nome}
                                        </a>
                                    </h3>
                                    <p class="item-size">Taglia: <strong>${item.taglia}</strong></p>
                                </div>
                                
                                <div class="item-qty">
                                    <span class="label">Quantità:</span>
                                    <div class="qty-modifier">
                                        <form action="${pageContext.request.contextPath}/carrello" method="POST">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="idProdotto" value="${item.prodotto.idProdotto}">
                                            <input type="hidden" name="taglia" value="${item.taglia}">
                                            <input type="hidden" name="quantita" value="${item.quantita - 1}">
                                            <button type="submit" class="btn-qty" ${item.quantita <= 1 ? 'disabled' : ''}>-</button>
                                        </form>

                                        <span class="qty-value">${item.quantita}</span>

                                        <form action="${pageContext.request.contextPath}/carrello" method="POST">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="idProdotto" value="${item.prodotto.idProdotto}">
                                            <input type="hidden" name="taglia" value="${item.taglia}">
                                            <input type="hidden" name="quantita" value="${item.quantita + 1}">
                                            <button type="submit" class="btn-qty">+</button>
                                        </form>
                                    </div>
                                </div>
                                
                                <div class="item-price">
                                    <p>€ ${item.prezzoTotale}</p>
                                </div>
                                
                                <div class="item-remove">
                                    <form action="${pageContext.request.contextPath}/carrello" method="POST">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="idProdotto" value="${item.prodotto.idProdotto}">
                                        <input type="hidden" name="taglia" value="${item.taglia}">
                                        
                                        <button type="submit" class="btn-remove-text">Rimuovi</button>
                                    </form>
                                </div>
                            </div>
                            <hr class="cart-divider">
                        </c:forEach>
                    </div>
                    
                    <div class="cart-summary">
                        <div class="summary-box">
                            <h2>Riepilogo ordine</h2>
                            <div class="summary-row">
                                <span>Totale articoli:</span>
                                <span>€ ${sessionScope.carrello.totaleComplessivo}</span>
                            </div>
                            <div class="summary-row">
                                <span>Spedizione:</span>
                                <span class="free-shipping">Gratuita</span>
                            </div>
                            <hr>
                            <div class="summary-row total">
                                <span>Totale complessivo:</span>
                                <span>€ ${sessionScope.carrello.totaleComplessivo}</span>
                            </div>
                            
                            <a href="${pageContext.request.contextPath}/checkout" class="btn-checkout">
                                Procedi al Checkout
                            </a>
                        </div>
                    </div>
                    
                </div>
            </c:when>
            
            <%-- SCENARIO DI RIPIEGO: Se il carrello è vuoto --%>
            <c:otherwise>
                <div class="empty-cart-box">
                    <p>Il tuo carrello è attualmente vuoto.</p>
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn-continue-shopping">
                        Torna allo Shopping
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

</body>
</html>