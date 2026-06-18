<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <footer class="footer">

    <div class="footer-top">

        <div class="footer-brand">
            <p class="footer-desc">
                Il tuo store online dedicato alle Jersey NBA.
			    Scopri le maglie delle tue squadre e dei tuoi giocatori preferiti,
			    con spedizioni rapide e assistenza dedicata.
            </p>
        </div>

        <div class="footer-col"> <div class="footer-col-title">Supporto</div>
            <div class="footer-links">
                <a class="footer-link" href="${pageContext.request.contextPath}/spedizioni">
                    <span class="footer-link-arrow">›</span> Spedizioni e consegne
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
            <div class="newsletter-row" id="newsletter-inputs">
                <input
                    id="newsletter-email"
                    class="newsletter-input"
                    type="email"
                    placeholder="La tua email..."/>
                <button type="button" id="newsletter-btn" class="newsletter-btn">Iscriviti</button>
            </div>
            <span id="new-succ" class="succ-message"></span>
            <span id="new-err" class="err-message"></span>             
        </div>

    </div>

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

<script>
	document.addEventListener("DOMContentLoaded", function() {
    		const btn = document.getElementById('newsletter-btn');
    		const input = document.getElementById('newsletter-email');
    		const spanSucc = document.getElementById('new-succ');
    		const spanErr = document.getElementById('new-err');
    		const inputsRow = document.getElementById('newsletter-inputs');

    		if (btn && input) {
        		btn.addEventListener('click', function() {
            		const email = input.value.trim();
            		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            		// Reset iniziale dei messaggi
            		if (spanSucc) spanSucc.innerText = "";
            		if (spanErr) spanErr.innerText = "";

            		if (email === "") {
                		if (spanErr) spanErr.innerText = "Inserisci un indirizzo email.";
                		return;
            		}

            		if (!emailRegex.test(email)) {
                		if (spanErr) spanErr.innerText = "Inserisci un'email valida.";
                		return;
            		}
	
            		// Se tutto è corretto, nascondiamo la riga di input e mostriamo il successo
            		if (inputsRow) inputsRow.style.display = "none";
            			if (spanSucc) spanSucc.innerText = "✓ Iscrizione effettuata! Controlla la tua casella di posta.";
        		});
    		}
	});
</script>