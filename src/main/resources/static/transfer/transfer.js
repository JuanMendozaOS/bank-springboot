$('#transferForm').on('submit', function(e){
    e.preventDefault();
    let formData = new FormData(this);
    let myJSON = JSON.stringify(Object.fromEntries(formData));
    console.log(myJSON)
    $.ajax({
        url: '/transfers',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: myJSON,
        success: function(response){
            alert("Transferencia exitosa");
            window.location.href = "transfer.html"
        },
        error: function(xhr, status, error){
            alert("Error de creación", error, status);
            window.location.href = "transfer.html"
        }
    })
});