<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Checkout - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css">
</head>
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="checkout-main">
        <h1 class="checkout-title">Completamento Ordine</h1>

        <form action="${pageContext.request.contextPath}/common/checkout" method="POST" class="checkout-container" novalidate>
            
            <div class="checkout-sections">
                
                <div class="checkout-card">
                    <h2>1. Indirizzo di Spedizione</h2>
                    <div class="options-group">
                        <c:forEach var="ind" items="${indirizzi}">
                            <label class="option-label">
                                <input type="radio" name="idIndirizzoSelezionato" value="${ind.idIndirizzo}" ${ind.predefinito ? 'checked' : ''}>
                                <div class="option-info">
                                    <span class="option-title">
                                        ${ind.viaNumciv} ${ind.predefinito ? '<span class="badge-checkout">Predefinito</span>' : ''}
                                    </span>
                                    <span class="option-sub">${ind.codicePostale} - ${ind.citta} (${ind.provincia}), ${ind.paese}</span>
                                </div>
                            </label>
                        </c:forEach>
                    </div>
                    <a href="${pageContext.request.contextPath}/common/profilo" class="link-modifica-profilo">Gestisci indirizzi nel profilo</a>
                    <span id="indirizzo-error" class="error-message"></span>
                </div>

                <div class="checkout-card">
                    <h2>2. Metodo di Pagamento</h2>
                    <div class="options-group">
                        <c:forEach var="carta" items="${carte}">
                            <label class="option-label">
                                <input type="radio" name="idMetodoSelezionato" value="${carta.idMetodo}" ${carta.predefinito ? 'checked' : ''}>
                                <div class="option-info">
                                    <span class="option-title">
                                        ${carta.tipo.toUpperCase()} •••• ${carta.numeroCarta.substring(carta.numeroCarta.length() - 4)}
                                        ${carta.predefinito ? '<span class="badge-checkout">Predefinito</span>' : ''}
                                    </span>
                                    <span class="option-sub">Intestato a: ${carta.intestatario} | Scadenza: <fmt:formatDate value="${carta.scadenza}" pattern="MM/yyyy" /></span>
                                </div>
                            </label>
                        </c:forEach>
                    </div>
                    <a href="${pageContext.request.contextPath}/common/profilo" class="link-modifica-profilo">Gestisci metodi di pagamento nel profilo</a>
                    <span id="pagamento-error" class="error-message"></span>
                </div>
            </div>

            <div class="checkout-summary-panel">
                <div class="sticky-summary">
                    <h2>Riepilogo Ordine</h2>
                    
                    <div class="checkout-items-preview">
                        <c:forEach var="item" items="${sessionScope.carrello.elementi}">
                            <div class="preview-item">
                                <div class="preview-info">
                                    <h4>${item.prodotto.nome}</h4>
                                    <p>Taglia: ${item.taglia} | Qty: ${item.quantita}</p>
                                </div>
                                <div class="preview-price">
                                    <span>€<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${item.prezzoTotale}" /></span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <hr class="summary-hr">

                    <div class="summary-details">
                        <div class="summary-line">
                            <span>Articoli:</span>
                            <span>€<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${sessionScope.carrello.totaleComplessivo}" /></span>
                        </div>
                        <div class="summary-line">
                            <span>Spedizione:</span>
                            <span class="free-text">Gratuita</span>
                        </div>
                        <hr class="summary-hr">
                        <div class="summary-line total-line">
                            <span>Totale Ordine:</span>
                            <span>€<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${sessionScope.carrello.totaleComplessivo}" /></span>
                        </div>
                    </div>

                    <button type="submit" class="btn-place-order">Acquista Ora</button>
                    <p class="secure-footer">🔒 Pagamento sicuro crittografato SSL</p>
                </div>
            </div>

        </form>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />
    
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const formCheckout = document.querySelector(".checkout-container");
            
            if (!formCheckout)
            		return;

            formCheckout.addEventListener("submit", function(event) {
                let formValido = true;

                const errorIndirizzo = document.getElementById("indirizzo-error");
                const errorPagamento = document.getElementById("pagamento-error");
                
                // Reset dei messaggi di errore precedenti
                errorIndirizzo.innerText = "";
                errorPagamento.innerText = "";

                // Controllo se è stato selezionato un Indirizzo
                const indirizzoSelezionato = document.querySelector('input[name="idIndirizzoSelezionato"]:checked');
                if (!indirizzoSelezionato)
                {
                    errorIndirizzo.innerText = "Attenzione: devi selezionare o inserire un indirizzo di spedizione per continuare.";
                    formValido = false;
                }

                // Controllo se è stato selezionato un Metodo di Pagamento
                const pagamentoSelezionato = document.querySelector('input[name="idMetodoSelezionato"]:checked');
                if (!pagamentoSelezionato)
                {
                    errorPagamento.innerText = "Attenzione: devi selezionare o inserire un metodo di pagamento per completare l'ordine.";
                    formValido = false;
                }

                // Se manca qualcosa, blocchiamo l'invio
                if (!formValido)
                {
                    event.preventDefault();
                }
            });
        });
    </script>

</body>
</html>