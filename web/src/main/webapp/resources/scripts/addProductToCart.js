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
                $("#error-quantity" + phoneId).html("");
                $("#success-quantity").html("Added successfully!");
                },
        error: function (error) {
                try{
                    $("#error-quantity" + phoneId).html(JSON.parse(error.responseText).message);
                } catch(e) {
                    $("#error-quantity" + phoneId).html("Quantity must be a positive number.");
                }
                $("#success-quantity").html("");
                }
    });
}