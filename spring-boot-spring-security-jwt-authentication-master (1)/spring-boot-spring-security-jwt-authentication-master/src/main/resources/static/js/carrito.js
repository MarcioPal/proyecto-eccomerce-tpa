let jwtResponse = JSON.parse(window.sessionStorage.getItem('JwtResponse'));
console.log(jwtResponse.id);
console.log(jwtResponse.accessToken)



function cerrarSesion(){
    window.sessionStorage.removeItem('JwtResponse');
    return window.location.reload();
}
function irCarrito(){
    user = JSON.parse(sessionStorage.getItem('JwtResponse'));
    window.location.href = "/carrito/"+user.id;
}