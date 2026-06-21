<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aggiungi Prodotto - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aggiungiProdotto.css">
</head>
<body>
    <jsp:include page="/WEB-INF/view/components/header.jsp" />
    
    <main class="main-content">
        <h2>Aggiungi un Nuovo Prodotto</h2>
        
        <form id="formAggiungiProdotto" action="${pageContext.request.contextPath}/admin/aggiungiProdotto" method="post" enctype="multipart/form-data" novalidate>
            <div>
                <label>Nome *</label>
                <input type="text" id="nome" name="nome" placeholder="es. Canotta Los Angeles Lakers Lebron James">
                <span id="error-nome" class="error-message"></span>
            </div>
            
            <div>
                <label>Squadra *</label>
                <input type="text" id="squadra" name="squadra" placeholder="es. Los Angeles Lakers">
                <span id="error-squadra" class="error-message"></span>
            </div>
            
            <div>
                <label>Categoria *</label>
                <select name="categoria">
                    <option value="icon">Icon / Classic</option>
                    <option value="attuali">Attuali</option>
                </select>
            </div>
            
            <div>
                <label>Materiale</label>
                <input type="text" id="materiale" name="materiale" placeholder="es. 100% Poliestere Riciclato">
            </div>
            
            <div>
                <label>Taglia *</label>
                <input type="text" id="taglia" name="taglia" placeholder="Inserisci una taglia tra: XS, S, M, L, XL, XXL">
                <span id="error-taglia" class="error-message"></span>
            </div>
            
            <div>
                <label>Prezzo (€) *</label>
                <input type="number" id="prezzo" name="prezzo" step="0.01" placeholder="es. 89.99 (usa il punto per i decimali)">
                <span id="error-prezzo" class="error-message"></span>
            </div>
            
            <div>
                <label>Stock *</label>
                <input type="number" id="stock" name="stock" placeholder="es. 15 (solo numeri interi)">
                <span id="error-stock" class="error-message"></span>
            </div>
            
            <div>
                <label>Immagine *</label>
                <input type="file" id="nuovaImmagine" name="nuovaImmagine">
                <span id="error-immagine" class="error-message"></span>
            </div>
            
            <div>
                <textarea name="descrizione" placeholder="Inserisci una descrizione dettagliata del prodotto..."></textarea>
            </div>
            
            <button type="submit" class="btn-form-submit">Salva Prodotto</button>
        </form>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

    <script>
    document.addEventListener("DOMContentLoaded", function() {
        const form = document.getElementById("formAggiungiProdotto");

        form.addEventListener("submit", function(event) {
            let valido = true;

            const nome = document.getElementById("nome").value.trim();
            const squadra = document.getElementById("squadra").value.trim();
            const taglia = document.getElementById("taglia").value.trim().toUpperCase();
            const prezzo = document.getElementById("prezzo").value.trim();
            const stock = document.getElementById("stock").value;
            const immagine = document.getElementById("nuovaImmagine").files[0];

            // Reset dei messaggi di errore 
            document.getElementById("error-nome").innerText = "";
            document.getElementById("error-squadra").innerText = "";
            document.getElementById("error-taglia").innerText = "";
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

            // Validazione campo TAGLIA
            const regexTaglia = /^(XS|S|M|L|XL|XXL)$/;
            if (taglia === "") {
                document.getElementById("error-taglia").innerText = "La taglia è obbligatoria.";
                valido = false;
            } else if (!regexTaglia.test(taglia)) {
                document.getElementById("error-taglia").innerText = "Taglia non valida. Inserisci una tra: XS, S, M, L, XL, XXL.";
                valido = false;
            }

            // Validazione campo PREZZO con REGEX (accetta solo il punto per i decimali)
            const regexPrezzo = /^[0-9]+([\.][0-9]{1,2})?$/;

            if (prezzo === "") {
                document.getElementById("error-prezzo").innerText = "Il prezzo è obbligatorio.";
                valido = false;
            } else if (!regexPrezzo.test(prezzo)) {
                document.getElementById("error-prezzo").innerText = "Formato prezzo non valido. Usa il punto per i decimali (es. 19.99).";
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
         	if (stock === "") {
            	document.getElementById("error-stock").innerText = "Lo stock iniziale è obbligatorio.";
             	valido = false;
         	} else if (!regexStock.test(stock)) {
             	document.getElementById("error-stock").innerText = "Lo stock deve essere un numero intero (non sono ammessi decimali).";
             	valido = false;
         	} else {
             	const stockNumerico = parseInt(stock);
             	if (stockNumerico < 0) {
                 	document.getElementById("error-stock").innerText = "Lo stock non può essere un numero negativo.";
                 	valido = false;
             	}
         	}

            // Validazione file IMMAGINE 
            if (!immagine)
            {
                document.getElementById("error-immagine").innerText = "Devi caricare un'immagine per il prodotto.";
                valido = false;
            }

            // Se anche un solo controllo è fallito, blocchiamo l'invio del form
            if (valido === false)
            {
                event.preventDefault();
            }
        });
    });
    </script>
</body>
</html>