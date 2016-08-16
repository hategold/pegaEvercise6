$(document).on("click", ".confirm", function(e) {
	var link = $(this).attr("href");
	e.preventDefault();
	bootbox.confirm({
		title : "�T�w�n�R���o������?",
		message : "Are you sure?",
		callback : function(result) {
			if (result) {
				window.location = link;
			}
		},
		size : "big"
	});
});