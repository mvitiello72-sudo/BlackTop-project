<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- importa la libreria JSTL -->

<footer class="footer">

    <!-- TOP: descrizione, supporto, newsletter -->
    <div class="footer-top">

        <div class="footer-brand">
            <p class="footer-desc">
                Il tuo store online dedicato alle Jersey NBA.
			    Scopri le maglie delle tue squadre e dei tuoi giocatori preferiti,
			    con spedizioni rapide e assistenza dedicata.
            </p>
        </div>

        <div class="footer-col"> <!-- Colonna verticale -->
            <div class="footer-col-title">Supporto</div>
            <div class="footer-links">
                <a class="footer-link" href="#">
                    <span class="footer-link-arrow">›</span> Come ordinare
                </a>
                <a class="footer-link" href="#">
                    <span class="footer-link-arrow">›</span> Spedizioni e consegne
                </a>
                <a class="footer-link" href="#">
                    <span class="footer-link-arrow">›</span> Resi e rimborsi
                </a>
                <a class="footer-link" href="#">
                    <span class="footer-link-arrow">›</span> Garanzia prodotti
                </a>
                <a class="footer-link" href="#">
                    <span class="footer-link-arrow">›</span> FAQ
                </a>
                <a class="footer-link" href="${pageContext.request.contextPath}/contatti">
                    <span class="footer-link-arrow">›</span> Contattaci
                </a>
            </div>
        </div>

        <div class="footer-col">
            <div class="footer-col-title">Newsletter</div>
            <p class="newsletter-desc">
                Iscriviti e ricevi offerte esclusive!
            </p>
            <div class="newsletter-row">
                <input
                    class="newsletter-input"
                    type="email"
                    placeholder="La tua email..."/>
                <button class="newsletter-btn">Iscriviti</button>
            </div>
        </div>

    </div>

    <!-- MID: garanzie -->
    <div class="footer-mid">

        <div class="footer-mid-item">
            <span class="footer-mid-icon">⚡</span>
            <div class="footer-mid-text">
                <strong>Consegna 24h</strong>
                <span>Ordini entro le 14:00</span>
            </div>
        </div>

        <div class="footer-mid-item">
            <span class="footer-mid-icon">🛡</span>
            <div class="footer-mid-text">
                <strong>Garanzia ufficiale</strong>
                <span>Fino a 3 anni sui prodotti</span>
            </div>
        </div>

        <div class="footer-mid-item">
            <span class="footer-mid-icon">🔒</span>
            <div class="footer-mid-text">
                <strong>Pagamento sicuro</strong>
                <span>Transazioni crittografate SSL</span>
            </div>
        </div>

    </div>

    <!-- BOTTOM: copyright, link legali, metodi di pagamento -->
    <div class="footer-bottom">

        <span class="footer-copy">
            © 2026 <span>BlackTop</span> S.r.l.
            &mdash; Tutti i diritti riservati
        </span>

        <div class="footer-legal">
            <a href="#">Privacy Policy</a>
            <a href="#">Termini e condizioni</a>
            <a href="#">Cookie Policy</a>
        </div>

        <div class="footer-payments">
            <span class="footer-payments-label">Accettiamo</span>
            <span class="payment-badge">VISA</span>
            <span class="payment-badge">MC</span>
            <span class="payment-badge">AMEX</span>
            <span class="payment-badge">PayPal</span>
        </div>

    </div>

</footer>