<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo - BlackTop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogo.css">
</head>
<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="catalogo-container">
        
        <!-- COLONNA SINISTRA: FILTRI -->
        <aside class="filtri-sidebar">
            <h2>Filtra Prodotti</h2>
            <form action="${pageContext.request.contextPath}/catalogo" method="get">
                
                <!-- Filtro per Squadra -->
                <div class="filtro-gruppo">
                    <h3>Squadra</h3>
                    <label><input type="checkbox" name="squadra" value="Atlanta Hawks"> Atlanta Hawks</label>
        				<label><input type="checkbox" name="squadra" value="Boston Celtics"> Boston Celtics</label>
        				<label><input type="checkbox" name="squadra" value="Brooklyn Nets"> Brooklyn Nets</label>
        				<label><input type="checkbox" name="squadra" value="Charlotte Hornets"> Charlotte Hornets</label>
        				<label><input type="checkbox" name="squadra" value="Chicago Bulls"> Chicago Bulls</label>
        				<label><input type="checkbox" name="squadra" value="Cleveland Cavaliers"> Cleveland Cavaliers</label>
        				<label><input type="checkbox" name="squadra" value="Dallas Mavericks"> Dallas Mavericks</label>
        				<label><input type="checkbox" name="squadra" value="Denver Nuggets"> Denver Nuggets</label>
        				<label><input type="checkbox" name="squadra" value="Detroit Pistons"> Detroit Pistons</label>
        				<label><input type="checkbox" name="squadra" value="Golden State Warriors"> Golden State Warriors</label>
        				<label><input type="checkbox" name="squadra" value="Houston Rockets"> Houston Rockets</label>
        				<label><input type="checkbox" name="squadra" value="Indiana Pacers"> Indiana Pacers</label>
        				<label><input type="checkbox" name="squadra" value="Los Angeles Clippers"> Los Angeles Clippers</label>
        				<label><input type="checkbox" name="squadra" value="Los Angeles Lakers"> Los Angeles Lakers</label>
        				<label><input type="checkbox" name="squadra" value="Memphis Grizzlies"> Memphis Grizzlies</label>
        				<label><input type="checkbox" name="squadra" value="Miami Heat"> Miami Heat</label>
        				<label><input type="checkbox" name="squadra" value="Milwaukee Bucks"> Milwaukee Bucks</label>
        				<label><input type="checkbox" name="squadra" value="Minnesota Timberwolves"> Minnesota Timberwolves</label>
        				<label><input type="checkbox" name="squadra" value="New Orleans Pelicans"> New Orleans Pelicans</label>
        				<label><input type="checkbox" name="squadra" value="New York Knicks"> New York Knicks</label>
        				<label><input type="checkbox" name="squadra" value="Oklahoma City Thunder"> Oklahoma City Thunder</label>
        				<label><input type="checkbox" name="squadra" value="Orlando Magic"> Orlando Magic</label>
        				<label><input type="checkbox" name="squadra" value="Philadelphia 76ers"> Philadelphia 76ers</label>
        				<label><input type="checkbox" name="squadra" value="Phoenix Suns"> Phoenix Suns</label>
        				<label><input type="checkbox" name="squadra" value="Portland Trail Blazers"> Portland Trail Blazers</label>
        				<label><input type="checkbox" name="squadra" value="Sacramento Kings"> Sacramento Kings</label>
        				<label><input type="checkbox" name="squadra" value="San Antonio Spurs"> San Antonio Spurs</label>
        				<label><input type="checkbox" name="squadra" value="Toronto Raptors"> Toronto Raptors</label>
        				<label><input type="checkbox" name="squadra" value="Utah Jazz"> Utah Jazz</label>
        				<label><input type="checkbox" name="squadra" value="Washington Wizards"> Washington Wizards</label>
                </div>

                <!-- Filtro per Categoria -->
                <div class="filtro-gruppo">
                    <h3>Categoria</h3>
                    <label><input type="radio" name="categoria" value="tutte" checked> Tutte le Jersey</label>
                    <label><input type="radio" name="categoria" value="icon"> Icon Edition</label>
                    <label><input type="radio" name="categoria" value="statement"> Attuali </label>
                    <label><input type="radio" name="categoria" value="classic"> Classic / Vintage</label>
                </div>

                <!-- Bottone per applicare i filtri -->
                <button type="submit" class="btn-applica">Applica Filtri</button>
            </form>
        </aside>

        <!-- COLONNA DESTRA: GRIGLIA PRODOTTI -->
        <section class="catalogo-prodotti">
            <div class="products-grid">
                
                <c:choose>
                    <c:when test="${not empty listaProdotti}">
                        <c:forEach var="prodotto" items="${listaProdotti}">
                            <div class="product-card">
                                <img src="${pageContext.request.contextPath}/img/prodotti/${prodotto.immagini[0].percorsoImmagine}" alt="${prodotto.nome}">         
                                <h3>${prodotto.nome}</h3>
                                <p class="team">${prodotto.squadra}</p>
                                <p class="price">€ ${prodotto.prezzo}</p>
                                <a href="${pageContext.request.contextPath}/prodotto?id=${prodotto.idProdotto}" class="btn-detail">Vedi Dettaglio</a>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p class="no-products">Nessun prodotto trovato con i filtri selezionati.</p>
                    </c:otherwise>
                </c:choose>
                
            </div>
        </section>

    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

</body>
</html>