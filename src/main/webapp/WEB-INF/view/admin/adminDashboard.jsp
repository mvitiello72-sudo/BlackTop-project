<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BlackTop — Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminDashboard.css">
</head>

<body>

    <jsp:include page="/WEB-INF/view/components/header.jsp" />

    <main class="dashboard-main">

        <section class="dashboard-hero">
            <div class="dashboard-hero-inner">
                <span class="dashboard-label">PANNELLO ADMIN</span>
                <h1>Dashboard</h1>
                <p>Gestisci prodotti, utenti e ordini di BlackTop.</p>
            </div>
        </section>

        <section class="stats-row">
            <div class="stat-card">
                <span class="stat-number">${totaleProdotti}</span>
                <span class="stat-label">Prodotti attivi</span>
            </div>
            <div class="stat-card">
                <span class="stat-number">${totaleUtenti}</span>
                <span class="stat-label">Utenti registrati</span>
            </div>
            <div class="stat-card">
                <span class="stat-number">${totaleOrdini}</span>
                <span class="stat-label">Ordini totali</span>
            </div>
        </section>

        <!-- TAB BAR -->
        <div class="tab-bar">
            <button class="tab-btn active" onclick="switchTab('prodotti', this)">Prodotti</button>
            <button class="tab-btn" onclick="switchTab('utenti', this)">Utenti</button>
            <button class="tab-btn" onclick="switchTab('ordini', this)">Ordini</button>
        </div>

        <!-- ===== TAB PRODOTTI ===== -->
        <section id="tab-prodotti" class="tab-section active">

            <div class="section-toolbar">
                <h2>Catalogo Prodotti</h2>
                <a href="${pageContext.request.contextPath}/aggiungiProdotto" class="btn-add">+ Aggiungi Prodotto</a>
            </div>

            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">${errorMessage}</div>
            </c:if>

            <div class="table-wrapper">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Nome</th>
                            <th>Squadra</th>
                            <th>Categoria</th>
                            <th>Taglia</th>
                            <th>Prezzo</th>
                            <th>Sconto</th>
                            <th>Stock</th>
                            <th>Stato</th>
                            <th>Azioni</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty prodotti}">
                                <c:forEach var="p" items="${prodotti}">
                                    <tr>
                                        <td class="td-id">${p.idProdotto}</td>
                                        <td class="td-nome">${p.nome}</td>
                                        <td>${p.squadra}</td>
                                        <td><span class="badge-cat">${p.categoria}</span></td>
                                        <td><span class="badge-taglia">${p.taglia}</span></td>
                                        <td class="td-price">€ <fmt:formatNumber value="${p.prezzo}" pattern="#,##0.00"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${p.sconto > 0}">
                                                    <span class="badge-sconto">-${p.sconto}%</span>
                                                </c:when>
                                                <c:otherwise>—</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><span class="badge-stock">${p.stock}</span></td>
                                        <td>
                                            <span class="badge-stato">${p.attivo ? 'Attivo' : 'Disattivato'}</span>
                                        </td>
                                        <td class="td-azioni">

                                            <!-- Modifica -->
                                            <a href="${pageContext.request.contextPath}/modificaProdotto?id=${p.idProdotto}"
                                               class="btn-action">Modifica</a>

                                            <!-- Attiva / Disattiva -->
                                            <c:choose>
                                                <c:when test="${p.attivo}">
                                                    <form action="${pageContext.request.contextPath}/prodottoStato"
                                                          method="post" class="form-inline"
                                                          onsubmit="return confirm('Disattivare il prodotto ${p.nome}?')">
                                                        <input type="hidden" name="id" value="${p.idProdotto}">
                                                        <input type="hidden" name="action" value="disattiva">
                                                        <button type="submit" class="btn-action">Disattiva</button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="${pageContext.request.contextPath}/prodottoStato"
                                                          method="post" class="form-inline">
                                                        <input type="hidden" name="id" value="${p.idProdotto}">
                                                        <input type="hidden" name="action" value="attiva">
                                                        <button type="submit" class="btn-action">Attiva</button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="10" class="empty-state">
                                        Nessun prodotto trovato.
                                        <a href="${pageContext.request.contextPath}/aggiungiProdotto">Aggiungi un prodotto</a>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </section>

        <!-- ===== TAB UTENTI ===== -->
        <section id="tab-utenti" class="tab-section">
            <div class="section-toolbar">
                <h2>Utenti Registrati</h2>
            </div>

            <div class="table-wrapper">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Nome</th>
                            <th>Cognome</th>
                            <th>Email</th>
                            <th>Cellulare</th>
                            <th>Ruolo</th>
                            <th>Azioni</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty utenti}">
                                <c:forEach var="u" items="${utenti}">
                                    <tr>
                                        <td class="td-id">${u.idUtente}</td>
                                        <td>${u.nome}</td>
                                        <td>${u.cognome}</td>
                                        <td class="td-email">${u.email}</td>
                                        <td>${not empty u.cellulare ? u.cellulare : '—'}</td>
                                        <td><span class="badge-ruolo">${u.ruolo}</span></td>
                                        <td class="td-azioni">
                                            <c:choose>
                                                <c:when test="${u.ruolo == 'USER'}">
                                                    <form action="${pageContext.request.contextPath}/utenteRuolo"
                                                          method="post" class="form-inline"
                                                          onsubmit="return confirm('Rendere ${u.nome} ${u.cognome} un ADMIN?')">
                                                        <input type="hidden" name="id" value="${u.idUtente}">
                                                        <input type="hidden" name="action" value="promuovi">
                                                        <button type="submit" class="btn-action">Promuovi ad Admin</button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="${pageContext.request.contextPath}/utenteRuolo"
                                                          method="post" class="form-inline"
                                                          onsubmit="return confirm('Rimuovere i privilegi admin a ${u.nome} ${u.cognome}?')">
                                                        <input type="hidden" name="id" value="${u.idUtente}">
                                                        <input type="hidden" name="action" value="declassa">
                                                        <button type="submit" class="btn-action">Rimuovi Admin</button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="7" class="empty-state">Nessun utente trovato.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </section>

        <!-- ===== TAB ORDINI ===== -->
        <section id="tab-ordini" class="tab-section">

            <div class="section-toolbar">
                <h2>Ordini</h2>
            </div>

            <!-- Filtri -->
            <form action="${pageContext.request.contextPath}/admindashboard" method="get" class="filtri-form">
                <input type="hidden" name="tab" value="ordini">
                <div class="filtri-row">
                    <div class="filtro-group">
                        <label for="dataInizio">Dal</label>
                        <input type="date" id="dataInizio" name="dataInizio" value="${param.dataInizio}">
                    </div>
                    <div class="filtro-group">
                        <label for="dataFine">Al</label>
                        <input type="date" id="dataFine" name="dataFine" value="${param.dataFine}">
                    </div>
                    <div class="filtro-group">
    						<label for="cliente">Email Cliente</label>
    						<input type="text" id="cliente" name="cliente" value="${param.cliente}" placeholder="es. marco@email.com">
					</div>
                    <div class="filtro-group filtro-btn-group">
                        <button type="submit" class="btn-add">Filtra</button>
                        <a href="${pageContext.request.contextPath}/admindashboard?tab=ordini" class="btn-action">Reset</a>
                    </div>
                </div>
            </form>

            <div class="table-wrapper">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Cliente</th>
                            <th>Email</th>
                            <th>Data Ordine</th>
                            <th>Totale</th>
                            <th>Stato</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty ordini}">
                                <c:forEach var="o" items="${ordini}">
                                    <tr>
                                        <td class="td-id">${o.idOrdine}</td>
                                        <td>${o.utente.nome} ${o.utente.cognome}</td>
                                        <td class="td-email">${o.utente.email}</td>
                                        <td>${o.dataOrdine}</td>
                                        <td class="td-price">€ <fmt:formatNumber value="${o.totale}" pattern="#,##0.00"/></td>
                                        <td><span class="badge-stato">${o.stato}</span></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="6" class="empty-state">Nessun ordine trovato.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </section>

    </main>

    <jsp:include page="/WEB-INF/view/components/footer.jsp" />

    <script>
        function switchTab(tabName, btn) {
            document.querySelectorAll('.tab-section').forEach(s => s.classList.remove('active'));
            document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
            document.getElementById('tab-' + tabName).classList.add('active');
            btn.classList.add('active');
        }

        // Se la pagina torna con ?tab=ordini (dopo il filtro), apre automaticamente quel tab
        window.addEventListener('DOMContentLoaded', function () {
            const params = new URLSearchParams(window.location.search);
            const tab = params.get('tab');
            if (tab) {
                const btn = document.querySelector('.tab-btn[onclick*="' + tab + '"]');
                if (btn) switchTab(tab, btn);
            }
        });
    </script>

</body>
</html>
