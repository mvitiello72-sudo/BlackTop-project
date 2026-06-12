<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
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
        <form action="${pageContext.request.contextPath}/admin/aggiungiProdotto" method="post" enctype="multipart/form-data">
            <div><label>Nome *</label><input type="text" name="nome" required></div>
            <div><label>Squadra *</label><input type="text" name="squadra" required></div>
            <div><label>Categoria *</label>
                <select name="categoria">
                    <option value="icon">Icon / Classic</option>
                    <option value="attuali">Attuali</option>
                </select>
            </div>
            <div><label>Materiale</label><input type="text" name="materiale"></div>
            <div><label>Taglia *</label><input type="text" name="taglia" required></div>
            <div><label>Prezzo (€) *</label><input type="number" name="prezzo" step="0.01" required></div>
            <div><label>Stock *</label><input type="number" name="stock" required></div>
            <div><label>Immagine *</label><input type="file" name="nuovaImmagine" accept="image/*" required></div>
            <div><textarea name="descrizione" placeholder="Descrizione..."></textarea></div>
            <button type="submit" class="btn-add">Salva Prodotto</button>
        </form>
    </main>
</body>
</html>