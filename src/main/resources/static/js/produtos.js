$(document).ready(function($) {
	$('#tabelaProdutos').DataTable();
	$('.dataTables_length').addClass('bs-select');
});

$('.custom-file-input').on('change', function() {
	let fileName = $(this).val().split('\\').pop();
	$(this).next('.custom-file-label').addClass("selected").html(fileName);
});