<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spedizioni e Consegne - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/spedizione.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="main-content">
        <div class="info-container">
            <h1 class="info-title">Spedizioni e Consegne</h1>
            
            <div class="info-section">
                <h2><span>⚡</span>Tempi di Elaborazione</h2>
                <p>Tutti gli ordini ricevuti dal lunedì al venerdì entro le ore 14:00 vengono affidati al corriere espresso il giorno stesso. Gli ordini effettuati durante il weekend o nei giorni festivi verranno elaborati nel primo giorno lavorativo successivo.</p>
            </div>

            <div class="info-section">
                <h2><span>📦</span>Modalità di Spedizione e Costi</h2>
                <p>Collaboriamo con i migliori corrieri espressi (DHL, BRT, GLS) per garantire che le tue Jersey NBA arrivino in perfette condizioni.</p>
                
                <table class="info-table">
                    <thead>
                        <tr>
                            <th>Tipo di Spedizione</th>
                            <th>Tempi di Consegna</th>
                            <th>Costo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Spedizione</td>
                            <td>Consegna 24H</td>
                            <td>Spedizione Gratis !</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="info-section">
                <h2><span>🛡</span>Cosa fare alla consegna?</h2>
                <p>Al momento della consegna da parte del corriere, ti consigliamo vivamente di controllare che il numero dei colli corrisponda a quanto indicato nel documento di trasporto e che l'imballo risulti integro e non danneggiato.</p>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

</body>
</html>