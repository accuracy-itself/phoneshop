function changeOrderStatus(orderId, newStatus) {
    console.log(newStatus);
    console.log(orderId);
    $.ajax({
        type:"POST",
        url: orderId,
        data: JSON.stringify({
                    status: newStatus,
                }),
        headers: {
                   'Accept': 'application/json',
                   'Content-Type': 'application/json'
        },
        success: function (response) {
                    console.log("success-update");
                    console.log(response);
                    console.log(response.message);
                    $("#success-update").html(response.message);
                    $("#order-status").html(newStatus);
                },
        error: function (error) {
                    console.log("error-update");
                    $("#wrong-update").html(error);
                }
    });
}