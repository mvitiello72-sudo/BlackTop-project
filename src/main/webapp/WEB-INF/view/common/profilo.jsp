<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Il mio Profilo - BlackTop</title>
    <link class="html-embed" rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link class="html-embed" rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link class="html-embed" rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link class="html-embed" rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo.css">
</head>
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="profilo-container">
        
        <aside class="profilo-sidebar">
            <div class="user-welcome">
                <h3>${sessionScope.utente.nome} ${sessionScope.utente.cognome}</h3>
                <p class="role-badge">${sessionScope.utente.ruolo}</p>
                <p>${sessionScope.utente.email}</p>
            </div>
            <nav class="profilo-menu">
                <button class="menu-btn active" onclick="switchTab('dati-personali')">Dati Personali</button>
                <button class="menu-btn" onclick="switchTab('indirizzi')">Indirizzi salvati</button>
                <button class="menu-btn" onclick="switchTab('pagamenti')">Metodi di pagamento</button>
                <button class="menu-btn" onclick="switchTab('ordini')"> I miei ordini </button>
                <a href="${pageContext.request.contextPath}/common/logout" class="btn-logout">Disconnetti</a>
            </nav>
        </aside>

        <section class="profilo-content">
            
            <div id="dati-personali" class="tab-panel active">
                <h2>Informazioni Account</h2>
                <form action="${pageContext.request.contextPath}/common/aggiornaProfilo" method="POST" class="form-profilo">
                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input type="text" id="nome" name="nome" value="${sessionScope.utente.nome}" required>
                    </div>
                    <div class="form-group">
                        <label for="cognome">Cognome</label>
                        <input type="text" id="cognome" name="cognome" value="${sessionScope.utente.cognome}" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email (Unica - Non modificabile)</label>
                        <input type="email" id="email" value="${sessionScope.utente.email}" disabled>
                    </div>
                    <div class="form-group">
                        <label for="cellulare">Cellulare</label>
                        <input type="text" id="cellulare" name="cellulare" value="${sessionScope.utente.cellulare}">
                    </div>
                    <div class="form-group">
                        <label for="password">Nuova Password (Lascia vuoto per non modificare)</label>
                        <input type="password" id="password" name="password" placeholder="••••••••">
                    </div>
                    <button type="submit" class="btn-salva">Salva Modifiche</button>
                </form>
            </div>
            
            <div id="ordini" class="tab-panel">
                <h2>I tuoi Ordini</h2>
                <c:choose>
                    <c:when test="${empty ordini}">
                        <div class="no-orders">
                            <p>Non hai ancora effettuato nessun ordine.</p>
                            <a href="${pageContext.request.contextPath}/catalogo" class="btn-shop-now">Inizia lo Shopping</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="ordini-container">
                            <c:forEach var="ord" items="${ordini}">
                                <div class="ordine-card">
                                    <div class="ordine-header">
                                        <div>
                                            <p class="ordine-label">Ordine Effettuato</p>
                                            <p class="ordine-value"><fmt:formatDate value="${ord.dataOrdine}" pattern="dd MMMM yyyy" /></p>
                                        </div>
                                        <div>
                                            <p class="ordine-label">Totale</p>
                                            <p class="ordine-value-prezzo">€<fmt:formatNumber value="${ord.totale}" pattern="#,##0.00"/></p>
                                        </div>
                                        <div>
                                            <p class="ordine-label">ID Ordine</p>
                                            <p class="ordine-value">#${ord.idOrdine}</p>
                                        </div>
                                        <div>
                                            <span class="status-badge status-${ord.stato.toLowerCase()}">${ord.stato}</span>
                                        </div>
                                    </div>
                                    <div class="ordine-body">
                                        <p>Hai bisogno di assistenza o vuoi i dettagli completi?</p>
                                        <a href="${pageContext.request.contextPath}/common/dettaglioOrdine?id=${ord.idOrdine}" class="btn-ordine-dettaglio">Visualizza Dettagli</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div id="indirizzi" class="tab-panel">
                <h2>I tuoi Indirizzi</h2>
                <div class="indirizzi-grid">
                    <c:forEach var="ind" items="${indirizzi}">
                        <div class="address-card">
                            <c:if test="${ind.predefinito}">
                                <span class="badge-default">Predefinito</span>
                            </c:if>
                            <p class="address-street"><strong>${ind.viaNumciv}</strong></p>
                            <p>${ind.codicePostale} - ${ind.citta} (${ind.provincia})</p>
                            <p class="address-country">${ind.paese}</p>
                            
                            <div class="card-actions">
                                <c:if test="${not ind.predefinito}">
                                    <form action="${pageContext.request.contextPath}/common/impostaIndirizzoPredefinito" method="POST" class="form-inline">
                                        <input type="hidden" name="idIndirizzo" value="${ind.idIndirizzo}">
                                        <button type="submit" class="btn-set-default">Imposta predefinito</button>
                                    </form>
                                </c:if>
                                <form action="${pageContext.request.contextPath}/common/rimuoviIndirizzo" method="POST" class="form-inline">
                                    <input type="hidden" name="idIndirizzo" value="${ind.idIndirizzo}">
                                    <button type="submit" class="btn-delete">Elimina</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                
                <hr class="section-divider">
                
                <h3>Aggiungi un nuovo indirizzo</h3>
                <form action="${pageContext.request.contextPath}/common/aggiungiIndirizzo" method="POST" class="form-profilo">
                    <div class="form-group">
                        <label>Via e Numero Civico</label>
                        <input type="text" name="via_numciv" placeholder="Es. Via Roma 15" required>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label>Città</label>
                            <input type="text" name="citta" required>
                        </div>
                        <div class="form-group">
                            <label>Provincia</label>
                            <input type="text" name="provincia" placeholder="Es. MI" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label>Codice Postale (CAP)</label>
                            <input type="text" name="codice_postale" maxlength="10" required>
                        </div>
                        <div class="form-group">
                            <label>Paese</label>
                            <input type="text" name="paese" value="Italia" required>
                        </div>
                    </div>
                    <div class="form-group checkbox-group">
                        <label><input type="checkbox" name="predefinito" value="true"> Imposta come indirizzo predefinito</label>
                    </div>
                    <button type="submit" class="btn-aggiungi">Salva Indirizzo</button>
                </form>
            </div>

            <div id="pagamenti" class="tab-panel">
                <h2>Metodi di Pagamento Salvati</h2>
                <div class="carte-grid">
                    <c:forEach var="carta" items="${carte}">
                        <div class="card-item">
                            <div class="card-header-sub">
                                <span class="card-type">${carta.tipo.toUpperCase()}</span>
                                <c:if test="${carta.predefinito}">
                                    <span class="badge-default">Predefinito</span>
                                </c:if>
                            </div>
                            <p class="card-number">•••• •••• •••• ${carta.numeroCarta.substring(carta.numeroCarta.length() - 4)}</p> 
                            <p class="card-holder">${carta.intestatario}</p>
                            <p class="card-scad">Scadenza: <fmt:formatDate value="${carta.scadenza}" pattern="MM/yyyy" /></p>
                            
                            <div class="card-actions-payment">
                                <c:if test="${not carta.predefinito}">
                                    <form action="${pageContext.request.contextPath}/common/impostaPagamentoPredefinito" method="POST" class="form-inline">
                                        <input type="hidden" name="idMetodo" value="${carta.idMetodo}">
                                        <button type="submit" class="btn-set-default-card">Rendi Predefinito</button>
                                    </form>
                                </c:if>
                                <form action="${pageContext.request.contextPath}/common/rimuoviPagamento" method="POST" class="form-inline">
                                    <input type="hidden" name="idMetodo" value="${carta.idMetodo}">
                                    <button type="submit" class="btn-delete-card">Rimuovi</button>
                                </form>
                            </div>
                        </div> </c:forEach>
                </div> <hr class="section-divider">
                
                <h3>Aggiungi un nuovo metodo di pagamento</h3>
                <form action="${pageContext.request.contextPath}/common/aggiungiPagamento" method="POST" class="form-profilo">
                    <div class="form-row">
                        <div class="form-group">
                            <label>Tipo di Carta</label>
                            <select name="tipo" required>
                                <option value="Visa">Visa</option>
                                <option value="Mastercard">Mastercard</option>
                                <option value="Postepay">Postepay</option>
                                <option value="PayPal">PayPal</option>
                                <option value="American Express">American Express</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Intestatario Carta</label>
                            <input type="text" name="intestatario" placeholder="Nome e Cognome" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Numero Carta</label>
                        <input type="text" name="numero_carta" placeholder="16 cifre senza spazi" maxlength="20" required>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label>Data di Scadenza</label>
                            <input type="date" name="scadenza" required>
                        </div>
                        <div class="form-group">
                            <label>CVV</label>
                            <input type="text" name="cvv" placeholder="3 o 4 cifre" maxlength="10" required>
                        </div>
                    </div>
                    <div class="form-group checkbox-group">
                        <label><input type="checkbox" name="predefinito" value="true"> Imposta come metodo predefinito</label>
                    </div>
                    <button type="submit" class="btn-aggiungi">Salva Metodo di Pagamento</button>
                </form>
            </div>

        </section>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

    <script>
        function switchTab(tabId)
        {
            const panels = document.querySelectorAll('.tab-panel');
            panels.forEach(panel => panel.classList.remove('active'));

            const buttons = document.querySelectorAll('.menu-btn');
            buttons.forEach(btn => btn.classList.remove('active'));

            document.getElementById(tabId).classList.add('active');
            event.currentTarget.classList.add('active');
        }
    </script>

</body>
</html>