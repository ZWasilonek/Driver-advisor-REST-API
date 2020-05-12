$(document).on("click", "a.confirm", function(e) {
    e.preventDefault();
    if (confirm('Czy jesteś pewien ?')) {
        location.href = $(this).attr('href');
    }
});


$(document).ready(function () { $('.datepic').datepicker({"format":"yyyy-mm-dd"
    });
});
