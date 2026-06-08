<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- importa la libreria JSTL -->

<nav class="navbar">
	
    <div class="nav-left">
	        <a href="${pageContext.request.contextPath}/home"> 
	        		<img src="${pageContext.request.contextPath}/img/logo/logo_BlackTop.png" alt="BlackTop">
	        </a>
	</div>
	
    <div class="nav-center">
        <form action="${pageContext.request.contextPath}/catalogo" method="get">
            <input type="text" name="cerca" placeholder="Cerca componenti..." value="${param.cerca}">
            <button>Cerca</button>
        </form>
    </div>

    <div class="nav-right">
    		 <a class="nav-btn" href="${pageContext.request.contextPath}/catalogo">
            CATALOGO
        </a>
        
        <c:choose>
            <c:when test="${not empty sessionScope.utente}">
                <a class="nav-btn" href="${pageContext.request.contextPath}/profilo">
                    ${sessionScope.utente.nome} <!-- Esce il nome dell'utente -->
                </a>
                <a class="nav-btn" href="${pageContext.request.contextPath}/logout">
                    ESCI
                </a>
            </c:when>
            
            <c:otherwise>
                <a class="nav-btn" href="${pageContext.request.contextPath}/indexlogin">
                    ACCEDI
                </a>
            </c:otherwise>
        </c:choose>

        <a class="nav-btn" href="${pageContext.request.contextPath}/carrello">
            CARRELLO
        </a>

    </div>

</nav>