function addToCart(phoneId) {
    $.ajax({
        type:"POST",
        url:"ajaxCart",
        dataType: "json",
        data: JSON.stringify({
                    id: phoneId,
                    quantity: $("#quantity" + phoneId).val(),
                }),
        headers: {
                   'Accept': 'application/json',
                   'Content-Type': 'application/json'
        },
        success: function (response) {
                $("#cart-quantity").html(response.totalQuantity);
                $("#cart-cost").html(response.totalCost);
                },
        error: function (error) {
        console.log("error-quantity" + phoneId);
                    $("#error-quantity" + phoneId).html("Error adding")
                }
    });
}