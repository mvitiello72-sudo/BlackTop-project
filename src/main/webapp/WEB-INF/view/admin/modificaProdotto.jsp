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

            <form action="${pageContext.request.contextPath}/admin/modificaProdotto" method="post" enctype="multipart/form-data" class="admin-form">
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
                    </div>
                </div>

                <div class="form-group">
                    <label for="nome">Nome Prodotto</label>
                    <input type="text" id="nome" name="nome" value="<c:out value='${prodotto.nome}'/>" required>
                </div>

                <div class="form-grid">
                    <div class="form-group">
                        <label for="squadra">Squadra</label>
                        <input type="text" id="squadra" name="squadra" value="<c:out value='${prodotto.squadra}'/>">
                    </div>

                    <div class="form-group">
                        <label for="categoria">Categoria</label>
                        <select id="categoria" name="categoria" required>
                            <option value="icon" ${prodotto.categoria == 'icon' ? 'selected' : ''}>Icon (Storiche)</option>
                            <option value="attuali" ${prodotto.categoria == 'attuali' ? 'selected' : ''}>Attuali</option>
                        </select>
                    </div>
                </div>

                <div class="form-grid-three">
                    <div class="form-group">
                        <label for="prezzo">Prezzo (€)</label>
                        <input type="number" id="prezzo" name="prezzo" step="0.01" min="0" value="${prodotto.prezzo}" required>
                    </div>

                    <div class="form-group">
                        <label for="sconto">Sconto (%)</label>
                        <input type="number" id="sconto" name="sconto" min="0" max="100" value="${prodotto.sconto}">
                    </div>

                    <div class="form-group">
                        <label for="stock">Disponibilità Stock</label>
                        <input type="number" id="stock" name="stock" min="0" value="${prodotto.stock}" required>
                    </div>
                </div>

                <div class="form-grid">
                    <div class="form-group">
                        <label for="taglia">Taglia</label>
                        <select id="taglia" name="taglia" required>
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
                        <input type="text" id="materiale" name="materiale" value="<c:out value='${prodotto.materiale}'/>">
                    </div>
                </div>

                <div class="form-group">
                    <label for="descrizione">Descrizione Prodotto</label>
                    <textarea id="descrizione" name="descrizione" rows="5"><c:out value='${prodotto.descrizione}'/></textarea>
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

</body>
</html>