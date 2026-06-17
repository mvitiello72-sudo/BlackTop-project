<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BlackTop — Modifica Prodotto</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modificaProdotto.css">
</head>
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="form-container-main">
        <section class="form-section">
            <div class="form-header">
                <span class="back-link"><a href="${pageContext.request.contextPath}/admin/admindashboard?tab=prodotti">← Torna alla Dashboard</a></span>
                <h1>Modifica Prodotto #${prodotto.idProdotto}</h1>
                <p>Aggiorna le informazioni relative al prodotto selezionato.</p>
            </div>

            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
                <% session.removeAttribute("successMessage"); %>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">${errorMessage}</div>
                <% request.removeAttribute("errorMessage"); %>
            </c:if>

            <form id="formModificaProdotto" action="${pageContext.request.contextPath}/admin/modificaProdotto" method="post" enctype="multipart/form-data" class="admin-form" novalidate>
                <input type="hidden" name="id" value="${prodotto.idProdotto}">

                <div class="photo-management-section">
                    <h3>Galleria Immagini Attuale</h3>
                    <div class="gallery-grid">
                        <c:choose>
                            <c:when test="${not empty prodotto.immagini}">
                                <c:forEach var="img" items="${prodotto.immagini}">
                                    <div class="photo-card">
                                        <img src="${pageContext.request.contextPath}/${img.percorsoImmagine}" alt="Foto Prodotto">
                                        
                                        <button type="submit" name="action" value="deleteImage" class="btn-delete-img"
                                                onclick="document.getElementById('idImmagineNascosto').value='${img.idImmagine}'; return confirm('Sei sicuro di voler eliminare questa foto?')">
                                            Elimina
                                        </button>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>Nessuna immagine presente per questo prodotto.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <input type="hidden" id="idImmagineNascosto" name="idImmagine" value="">

                    <div class="form-group">
                        <label for="nuovaImmagine">Aggiungi una nuova foto:</label>
                        <input type="file" id="nuovaImmagine" name="nuovaImmagine" accept="image/*">
                        <span id="error-immagine" class="error-message"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="nome">Nome Prodotto *</label>
                    <input type="text" id="nome" name="nome" value="<c:out value='${prodotto.nome}'/>" placeholder="es. Canotta Chicago Bulls Michael Jordan">
                    <span id="error-nome" class="error-message"></span>
                </div>

                <div class="form-grid">
                    <div class="form-group">
                        <label for="squadra">Squadra *</label>
                        <input type="text" id="squadra" name="squadra" value="<c:out value='${prodotto.squadra}'/>" placeholder="es. Chicago Bulls">
                        <span id="error-squadra" class="error-message"></span>
                    </div>

                    <div class="form-group">
                        <label for="categoria">Categoria *</label>
                        <select id="categoria" name="categoria">
                            <option value="icon" ${prodotto.categoria == 'icon' ? 'selected' : ''}>Icon (Storiche)</option>
                            <option value="attuali" ${prodotto.categoria == 'attuali' ? 'selected' : ''}>Attuali</option>
                        </select>
                    </div>
                </div>

                <%-- Modificato da form-grid-three a form-grid per ospitare perfettamente i due elementi rimasti --%>
                <div class="form-grid">
                    <div class="form-group">
                        <label for="prezzo">Prezzo (€) *</label>
                        <input type="number" id="prezzo" name="prezzo" step="0.01" value="${prodotto.prezzo}" placeholder="es. 89.99 (usa il punto)">
                        <span id="error-prezzo" class="error-message"></span>
                    </div>

                    <div class="form-group">
                        <label for="stock">Disponibilità Stock *</label>
                        <input type="number" id="stock" name="stock" value="${prodotto.stock}" placeholder="es. 25">
                        <span id="error-stock" class="error-message"></span>
                    </div>
                </div>

                <div class="form-grid">
                    <div class="form-group">
                        <label for="taglia">Taglia *</label>
                        <select id="taglia" name="taglia">
                            <option value="XS" ${prodotto.taglia == 'XS' ? 'selected' : ''}>XS</option>
                            <option value="S" ${prodotto.taglia == 'S' ? 'selected' : ''}>S</option>
                            <option value="M" ${prodotto.taglia == 'M' ? 'selected' : ''}>M</option>
                            <option value="L" ${prodotto.taglia == 'L' ? 'selected' : ''}>L</option>
                            <option value="XL" ${prodotto.taglia == 'XL' ? 'selected' : ''}>XL</option>
                            <option value="XXL" ${prodotto.taglia == 'XXL' ? 'selected' : ''}>XXL</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="materiale">Materiale</label>
                        <input type="text" id="materiale" name="materiale" value="<c:out value='${prodotto.materiale}'/>" placeholder="es. 100% Poliestere">
                    </div>
                </div>

                <div class="form-group">
                    <label for="descrizione">Descrizione Prodotto</label>
                    <textarea id="descrizione" name="descrizione" rows="5" placeholder="Inserisci la descrizione del prodotto..."><c:out value='${prodotto.descrizione}'/></textarea>
                </div>

                <div class="form-group checkbox-group">
                    <input type="checkbox" id="attivo" name="attivo" ${prodotto.attivo ? 'checked' : ''}>
                    <label for="attivo">Prodotto visibile nel catalogo pubblico (Attivo)</label>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-submit">Salva Modifiche</button>
                    <a href="${pageContext.request.contextPath}/admin/admindashboard?tab=prodotti" class="btn-cancel">Annulla</a>
                </div>
            </form>
        </section>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

    <script>
    document.addEventListener("DOMContentLoaded", function() {
        const form = document.getElementById("formModificaProdotto");

        // VALIDAZIONE GLOBALE ALL'INVIO DEL FORM 
        form.addEventListener("submit", function(event) {
            
            // Se l'operazione è scatenata dall'onclick del tasto elimina-immagine, usciamo immediatamente senza validare i testi
            if (document.activeElement && document.activeElement.value === "deleteImage") {
                return; 
            }

            let valido = true;

            const nomeCampo = document.getElementById("nome");
            const squadraCampo = document.getElementById("squadra");
            const prezzoCampo = document.getElementById("prezzo");
            const stockCampo = document.getElementById("stock");

            const nome = nomeCampo.value.trim();
            const squadra = squadraCampo.value.trim();
            const prezzo = prezzoCampo.value.trim();
            const stock = stockCampo.value.trim();

            // Reset dei messaggi di errore precedenti
            document.getElementById("error-nome").innerText = "";
            document.getElementById("error-squadra").innerText = "";
            document.getElementById("error-prezzo").innerText = "";
            document.getElementById("error-stock").innerText = "";
            document.getElementById("error-immagine").innerText = "";

            // Validazione campo NOME
            if (nome === "") {
                document.getElementById("error-nome").innerText = "Il nome del prodotto è obbligatorio.";
                valido = false;
            } else if (nome.length < 3) {
                document.getElementById("error-nome").innerText = "Il nome deve contenere almeno 3 caratteri.";
                valido = false;
            }

            // Validazione campo SQUADRA
            if (squadra === "") {
                document.getElementById("error-squadra").innerText = "La squadra è obbligatoria.";
                valido = false;
            }

            // Validazione campo PREZZO (Inclusa protezione da input corrotti tipo -- o e++)
            const regexPrezzo = /^[0-9]+([\.][0-9]{1,2})?$/;
            if (prezzoCampo.validity.badInput || prezzo === "") {
                document.getElementById("error-prezzo").innerText = "Il prezzo è obbligatorio e deve essere un numero valido.";
                valido = false;
            } else if (!regexPrezzo.test(prezzo)) {
                document.getElementById("error-prezzo").innerText = "Formato non valido. Usa il punto per i decimali (es. 89.99).";
                valido = false;
            } else {
                const prezzoNumerico = parseFloat(prezzo);
                if (prezzoNumerico <= 0) {
                    document.getElementById("error-prezzo").innerText = "Il prezzo deve essere maggiore di zero.";
                    valido = false;
                }
            }

            // Validazione campo STOCK
            const regexStock = /^[0-9]+$/;
            if (stockCampo.validity.badInput || stock === "") {
                document.getElementById("error-stock").innerText = "La disponibilità di stock è obbligatoria.";
                valido = false;
            } else if (!regexStock.test(stock)) {
                document.getElementById("error-stock").innerText = "Lo stock deve essere un numero intero (senza decimali).";
                valido = false;
            } else {
                const stockNumerico = parseInt(stock);
                if (stockNumerico < 0) {
                    document.getElementById("error-stock").innerText = "Lo stock non può essere un numero negativo.";
                    valido = false;
                }
            }

            // Blocco definitivo dell'invio se ci sono discrepanze
            if (valido === false) {
                event.preventDefault();
            }
        });
    });
    </script>
</body>
</html>