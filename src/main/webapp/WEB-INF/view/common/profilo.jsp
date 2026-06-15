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
                <form action="${pageContext.request.contextPath}/common/aggiornaProfilo" method="POST" class="form-profilo" id="form-dati-personali" novalidate>
                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input type="text" id="nome" name="nome" value="${sessionScope.utente.nome}" required>
                        <span id="nome-error" class="error-text"></span>
                    </div>
                    <div class="form-group">
                        <label for="cognome">Cognome</label>
                        <input type="text" id="cognome" name="cognome" value="${sessionScope.utente.cognome}" required>
                        <span id="cognome-error" class="error-text"></span>
                    </div>
                    <div class="form-group">
                        <label for="email">Email (Unica - Non modificabile)</label>
                        <input type="email" id="email" value="${sessionScope.utente.email}" disabled>
                    </div>
                    <div class="form-group">
                        <label for="cellulare">Cellulare</label>
                        <input type="text" id="cellulare" name="cellulare" value="${sessionScope.utente.cellulare}">
                        <span id="cell-error" class="error-text"></span>
                    </div>
                    <div class="form-group">
                        <label for="password">Nuova Password (Lascia vuoto per non modificare)</label>
                        <input type="password" id="password" name="password" placeholder="••••••••">
                        <span id="pass-error" class="error-text"></span>
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
            								<a href="${pageContext.request.contextPath}/common/fattura?id=${ord.idOrdine}" class="btn-ordine-fattura" target="_blank">
                								📄 Scarica Fattura PDF
            								</a>
        								                           
                                        <a href="${pageContext.request.contextPath}/common/dettaglioOrdine?id=${ord.idOrdine}" class="btn-ordine-dettaglio">
                                        		Visualizza Dettagli
                                        	</a>
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
                <form action="${pageContext.request.contextPath}/common/aggiungiIndirizzo" method="POST" class="form-profilo" id="form-nuovo-indirizzo" novalidate>
                    <div class="form-group">
                        <label>Via e Numero Civico</label>
                        <input type="text" id="via_numciv" name="via_numciv" placeholder="Es. Via Roma 15" required>
                         <span id="via-error" class="error-text"></span>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label>Città</label>
                            <input type="text" id="citta" name="citta" required>
							<span id="citta-error" class="error-text"></span>
                        </div>
                        <div class="form-group">
                            <label>Provincia</label>
                            <input type="text" id="provincia" name="provincia" placeholder="Es. MI" required>
                            <span id="provincia-error" class="error-text"></span>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label>Codice Postale (CAP)</label>
                            <input type="text" id="codice_postale" name="codice_postale" maxlength="10" required>
                            <span id="cap-error" class="error-text"></span>
                        </div>
                        <div class="form-group">
                            <label>Paese</label>
                            <input type="text" id="paese" name="paese" value="Italia" required>
                            <span id="paese-error" class="error-text"></span>
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
                <form action="${pageContext.request.contextPath}/common/aggiungiPagamento" method="POST" class="form-profilo" id="form-nuovo-pagamento" novalidate>
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
                            <input type="text" id="intestatario" name="intestatario" placeholder="Nome e Cognome" required>
                        		<span id="intestatario-error" class="error-text"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Numero Carta</label>
                        <input type="text" id="numero_carta" name="numero_carta" placeholder="16 cifre senza spazi" maxlength="20" required>
                        <span id="carta-error" class="error-text"></span>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label>Data di Scadenza</label>
                            <input type="date" id="scadenza" name="scadenza" required>
                        		<span id="scadenza-error" class="error-text"></span>
                        </div>
                        <div class="form-group">
                            <label>CVV</label>
                            <input type="text" id="cvv" name="cvv" placeholder="3 o 4 cifre" maxlength="10" required>
                       		<span id="cvv-error" class="error-text"></span>
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
    function switchTab(tabId) {
        const panels = document.querySelectorAll('.tab-panel');
        panels.forEach(panel => panel.classList.remove('active'));

        const buttons = document.querySelectorAll('.menu-btn');
        buttons.forEach(btn => btn.classList.remove('active'));

        document.getElementById(tabId).classList.add('active');
        event.currentTarget.classList.add('active');
    }
    
    document.addEventListener("DOMContentLoaded", function() {
        
        // 1. VALIDAZIONE FORM: DATI PERSONALI
        const formDati = document.getElementById("form-dati-personali"); 
        if (formDati)
        {
            const nome = document.getElementById("nome");
            const cognome = document.getElementById("cognome");
            const cellulare = document.getElementById("cellulare");
            const pass = document.getElementById("password");

            formDati.addEventListener("submit", function(event) {
                let formValido = true;
                let campoConErrore = null;

                formDati.querySelectorAll('.error-text').forEach(el => el.innerText = "");

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
                    if (!campoConErrore) campoConErrore = cognome;
                }

                if (cellulare.value.trim().length > 0)
                {
                    const regexTelefono = /^[0-9]{10}$/;
                    if (!regexTelefono.test(cellulare.value.trim()))
                    {
                        document.getElementById("cell-error").innerText = "Numero non valido. Inserisci esattamente 10 cifre numeriche.";
                        formValido = false;
                        if (!campoConErrore) campoConErrore = cellulare;
                    }
                }

                if (pass.value.length > 0)
                {
                    const regexPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
                    if (!regexPassword.test(pass.value)) {
                        document.getElementById("pass-error").innerText = "La password deve contenere almeno 8 caratteri, una maiuscola, una minuscola e un numero.";
                        formValido = false;
                        if (!campoConErrore) campoConErrore = pass;
                    }
                }

                if (!formValido)
                {
                    event.preventDefault();
                    campoConErrore.focus();
                }
            });
        }

        // 2. VALIDAZIONE FORM: NUOVO INDIRIZZO
        const formIndirizzo = document.getElementById("form-nuovo-indirizzo");
        if (formIndirizzo)
        {
            const via = document.getElementById("via_numciv");
            const citta = document.getElementById("citta");
            const provincia = document.getElementById("provincia");
            const cap = document.getElementById("codice_postale");
            const paese = document.getElementById("paese");

            formIndirizzo.addEventListener("submit", function(event) {
                let formValido = true;
                let campoConErrore = null;

                formIndirizzo.querySelectorAll('.error-text').forEach(el => el.innerText = "");

                if (via.value.trim().length < 5)
                {
                    document.getElementById("via-error").innerText = "Inserisci un indirizzo completo (es. Via Roma 15).";
                    formValido = false;
                    campoConErrore = via;
                }

                const regexCap = /^[0-9]{5}$/;
                if (!regexCap.test(cap.value.trim())) {
                    document.getElementById("cap-error").innerText = "Il CAP deve essere composto da esattamente 5 cifre numeriche.";
                    formValido = false;
                    if (!campoConErrore) campoConErrore = cap;
                }

                const regexProvincia = /^[a-zA-Z]{2}$/;
                if (!regexProvincia.test(provincia.value.trim()))
                {
                    document.getElementById("provincia-error").innerText = "Usa la sigla di 2 lettere (es. MI).";
                    formValido = false;
                    if (!campoConErrore) campoConErrore = provincia;
                }
                
                const regexLettere = /^[a-zA-Z\s]{2,20}$/;
                if (!regexLettere.test(citta.value.trim()))
                {
                    document.getElementById("citta-error").innerText = "Città non valido. Usa solo lettere (2-20 caratteri).";
                    formValido = false;
                    if (!campoConErrore) campoConErrore = citta;
                }
                
                if (!regexLettere.test(paese.value.trim()))
                {
                    document.getElementById("paese-error").innerText = "Paese non valido. Usa solo lettere (2-20 caratteri).";
                    formValido = false;
                    if (!campoConErrore) campoConErrore = paese;
                }

                if (!formValido)
                {
                    event.preventDefault();
                    campoConErrore.focus();
                }
            });
        }

        // 3. VALIDAZIONE FORM: METODO PAGAMENTO
        const formPagamento = document.getElementById("form-nuovo-pagamento");
        if (formPagamento) {
            const intestatario = document.getElementById("intestatario");
            const numCarta = document.getElementById("numero_carta");
            const scadenza = document.getElementById("scadenza");
            const cvv = document.getElementById("cvv");

            formPagamento.addEventListener("submit", function(event) {
                let formValido = true;
                let campoConErrore = null;

                formPagamento.querySelectorAll('.error-text').forEach(el => el.innerText = "");

                const regexIntestatario = /^[a-zA-Z\s]{5,40}$/;
                if (!regexIntestatario.test(intestatario.value.trim()))
                {
                    document.getElementById("intestatario-error").innerText = "Inserisci Nome e Cognome validi.";
                    formValido = false;
                    campoConErrore = intestatario;
                }

                const regexCarta = /^[0-9]{13,19}$/;
                if (!regexCarta.test(numCarta.value.trim()))
                {
                    document.getElementById("carta-error").innerText = "Numero carta non valido (inserisci solo le cifre senza spazi).";
                    formValido = false;
                    if (!campoConErrore) campoConErrore = numCarta;
                }

                const regexCvv = /^[0-9]{3,4}$/;
                if (!regexCvv.test(cvv.value.trim())) {
                    document.getElementById("cvv-error").innerText = "Il CVV deve essere di 3 o 4 cifre.";
                    formValido = false;
                    if (!campoConErrore) campoConErrore = cvv;
                }

                if (!scadenza.value) 
                {
                    document.getElementById("scadenza-error").innerText = "La data di scadenza è obbligatoria.";
                    formValido = false;
                    if (!campoConErrore) campoConErrore = scadenza;
                } 
                else 
                {
                    const dataScelta = new Date(scadenza.value);
                    const oggi = new Date();
                    oggi.setHours(0, 0, 0, 0);
                    
                    if (dataScelta < oggi) 
                    {
                        document.getElementById("scadenza-error").innerText = "La carta è scaduta o la data non è valida.";
                        formValido = false;
                        if (!campoConErrore) campoConErrore = scadenza;
                    }
                }

                if (!formValido)
                {
                    event.preventDefault();
                    campoConErrore.focus();
                }
            });
        }
    });
</script>

</body>
</html>