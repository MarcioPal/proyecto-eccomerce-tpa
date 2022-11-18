function getToken() {
    return JSON.parse(window.sessionStorage.getItem('JwtResponse'));
}


    document.addEventListener("DOMContentLoaded", function () { 
        if (getToken()) { 
           document.getElementById("btnLogout").style.display = "inline";
           document.getElementById("btnLogin").style.display = "none";
           document.getElementById("btnCarrito").style.display = "inline";
        } 
     });
   


function cerrarSesion() {
    window.sessionStorage.removeItem('JwtResponse');
    return window.location.reload();
}
function irCarrito() {
    user = JSON.parse(sessionStorage.getItem('JwtResponse'));
   document.cookie = "auth=Bearer "+user.accessToken + " expires=Thu, 18 Dec 2013 12:00:00 UTC; path=/carrito"; 
    window.location.href = "http://localhost:8080/carrito/"+user.id;
}

