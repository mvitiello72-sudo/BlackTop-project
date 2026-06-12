<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- importa la libreria JSTL -->

<nav class="navbar">
	
    <div class="nav-left">
	        <a href="${pageContext.request.contextPath}/home"> 
	        		<img src="${pageContext.request.contextPath}/img/logo/logo_BlackTop.png" alt="BlackTop">
	        </a>
	</div>
	
	<div class="nav-center" style="position: relative;">
	
    		<form action="${pageContext.request.contextPath}/catalogo" method="get" autocomplete="off">
        		<input type="text" id="barra-ricerca" name="cerca" placeholder="Cerca componenti..." value="${param.cerca}">
        		<button type="submit">Cerca</button>
    		</form>
    		
    		<div id="box-suggerimenti" class="search-suggestions" style="display: none;"></div>
    		
	</div>

    <div class="nav-right">
    		 <a class="nav-btn" href="${pageContext.request.contextPath}/catalogo">
            CATALOGO
        </a>
        
        <c:choose>
            <c:when test="${not empty sessionScope.utente}">
            		<c:if test="${sessionScope.utente.ruolo == 'ADMIN'}">
            			<a class="nav-btn" href="${pageContext.request.contextPath}/admin/admindashboard">
                			ADMIN
            			</a>
        			</c:if>
                <a class="nav-btn" href="${pageContext.request.contextPath}/common/profilo">
                    ${sessionScope.utente.nome} <!-- Esce il nome dell'utente -->
                </a>
                <a class="nav-btn" href="${pageContext.request.contextPath}/common/logout">
                    ESCI
                </a>
            </c:when>
            
            <c:otherwise>
                <a class="nav-btn" href="${pageContext.request.contextPath}/login">
                    ACCEDI
                </a>
            </c:otherwise>
        </c:choose>

        <a class="nav-btn" href="${pageContext.request.contextPath}/carrello">
            CARRELLO
        </a>

    </div>
</nav>

<script>
document.addEventListener("DOMContentLoaded", function()
	{
    const inputRicerca = document.getElementById("barra-ricerca");
    const boxSuggerimenti = document.getElementById("box-suggerimenti");

    if (!inputRicerca || !boxSuggerimenti) 
    		return;

    // Recupera dinamicamente il contesto dell'applicazione ("/BlackTop")
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));

    // Ascolta ogni carattere digitato dall'utente
    inputRicerca.addEventListener("input", function()
    		{
        const query = inputRicerca.value.trim();

        // Se l'utente scrive meno di 2 caratteri, svuota e nasconde la tendina
        if (query.length < 2)
        {
            boxSuggerimenti.innerHTML = "";
            boxSuggerimenti.style.display = "none";
            return;
        }

        // Esegue la chiamata AJAX (Fetch API) alla servlet dei suggerimenti
        fetch(contextPath + "/suggerimenti?q=" + encodeURIComponent(query))
            .then(response => response.json())
            .then(data => {
            	console.log("Dati ricevuti dal server:", data); // <-- Aggiungi questa riga temporaneamente!
                // Se il server non trova prodotti, nascondi la tendina
                if (data.length === 0)
                	{
                    boxSuggerimenti.innerHTML = "";
                    boxSuggerimenti.style.display = "none";
                    return;
                }

                // Costruisce l'elenco dei suggerimenti in HTML
                let html = "<ul>";
                // data è il JSON di oggetti "prodotto" restituito dalla Servlet
                data.forEach(prodotto => {
                    // Usiamo replace per evitare che eventuali singoli apici nel nome rompano l'HTML
                    const nomePulito = prodotto.nome.replace(/'/g, "\\'");
                    
                    html += "<li onclick=\"selezionaSuggerimento('" + nomePulito + "')\">";
                    html += "<strong>" + prodotto.squadra + "</strong> - " + prodotto.nome;
                    html += "</li>";
                }); 
                html += "</ul>";

                boxSuggerimenti.innerHTML = html;
                boxSuggerimenti.style.display = "block";
            })
            .catch(err => console.error("Errore AJAX nel recupero suggerimenti:", err));
    });

    // Chiude la tendina se l'utente clicca in un punto qualsiasi fuori dalla barra
    document.addEventListener("click", function(e) {
        if (e.target !== inputRicerca && e.target !== boxSuggerimenti) {
            boxSuggerimenti.style.display = "none";
        }
    });
});

// Questa funzione viene attivata quando l'utente clicca su un elemento della tendina
function selezionaSuggerimento(nomeProdotto)
{
    const inputRicerca = document.getElementById("barra-ricerca");
    if (inputRicerca)
    {
        inputRicerca.value = nomeProdotto; // Inserisce il nome completo nell'input
        document.getElementById("box-suggerimenti").style.display = "none"; // Nasconde la tendina
        inputRicerca.form.submit(); // Invia il form verso /catalogo
    }
}
</script>