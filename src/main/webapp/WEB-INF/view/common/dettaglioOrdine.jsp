<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Dettaglio Ordine - BlackTop</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dettaglioOrdine.css">
</head>

<body>

<jsp:include page="/WEB-INF/view/components/header.jsp" />

<main class="ordine-container">

    <!-- HEADER ORDINE -->
    <section class="ordine-header">

        <h1>Dettaglio Ordine #${ordine.idOrdine}</h1>

        <div class="ordine-info">
            <div>
                <span>Data ordine</span>
                <strong>
                    <fmt:formatDate value="${ordine.dataOrdine}" pattern="dd MMMM yyyy"/>
                </strong>
            </div>

            <div>
                <span>Stato</span>
                <strong class="status-${ordine.stato.toLowerCase()}">
                    ${ordine.stato}
                </strong>
            </div>

            <div>
                <span>Totale</span>
                <strong>€ <fmt:formatNumber value="${ordine.totale}" pattern="#,##0.00"/></strong>
            </div>
        </div>

    </section>

    <!-- PRODOTTI -->
    <section class="ordine-prodotti">

        <h2>Prodotti acquistati</h2>

        <c:if test="${empty dettagli}">
            <p>Nessun prodotto trovato per questo ordine.</p>
        </c:if>

        <c:forEach var="d" items="${dettagli}">

            <div class="prodotto-card">

                <div class="prodotto-info">

                    <h3>${d.prodotto.nome}</h3>

                    <p>
                        Taglia: <strong>${d.prodotto.taglia}</strong>
                    </p>

                    <p>
                        Quantità: <strong>${d.quantita}</strong>
                    </p>

                </div>

                <div class="prodotto-prezzo">

                    <p>
                        Prezzo unitario:
                        € <fmt:formatNumber value="${d.prezzoSnapshot}" pattern="#,##0.00"/>
                    </p>

                    <p>
                        Subtotale:
                        € <fmt:formatNumber value="${d.prezzoSnapshot * d.quantita}" pattern="#,##0.00"/>
                    </p>

                </div>

            </div>

        </c:forEach>

    </section>

    <!-- AZIONI -->
    <section class="ordine-actions">

        <a href="${pageContext.request.contextPath}/profilo#ordini"
           class="btn-back">
            ← Torna ai tuoi ordini
        </a>

    </section>

</main>

<jsp:include page="/WEB-INF/view/components/footer.jsp" />

</body>
</html>