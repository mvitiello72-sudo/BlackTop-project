<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ordine Confermato - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ordineSuccesso.css">
</head>
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main>
        <div class="success-container">
            <div class="success-icon">✓</div>
            <h1 class="success-title">Grazie per il tuo ordine!</h1>
            
            <p class="success-text">
                Il pagamento è andato a buon fine. Abbiamo preso in carico la tua richiesta <br>
                e stiamo preparando il tuo pacco BlackTop.
            </p>

            <p class="success-text" style="margin-bottom: 5px;">Il tuo numero d'ordine è:</p>
            <div class="order-number">#BT-${idOrdineSuccesso}</div>


            <div class="actions-group">
                <a href="${pageContext.request.contextPath}/catalogo" class="btn-success-page btn-primary-success">Continua lo Shopping</a>
                <a href="${pageContext.request.contextPath}/common/profilo" class="btn-success-page btn-secondary-success">Vai ai miei ordini</a>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

</body>
</html>