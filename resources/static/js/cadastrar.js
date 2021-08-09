
function validatePassword(){
    const password = document.getElementById("password");
    const confirm_password = document.getElementById("confirm_password");
    if(password.value != confirm_password.value) {
        alert("Senhas diferentes!");
        document.getElementById('cadastrar').disabled = true;
    } else {
        document.getElementById('cadastrar').disabled = false;
    }
}