

function deleteUsuario(id) {
   	if (confirm("Deseja realmente apagar este usuário?")) {
   		window.location.href = urlBase + "usuario/delete/" + id;
   	}
}