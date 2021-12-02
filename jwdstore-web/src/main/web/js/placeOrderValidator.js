window.onload = function () {
    $("#order-inputs").on('input', function () {
        if (isValidAddress()) {
            $(".place-order-button").prop("disabled", false);
        } else {
            $(".place-order-button").prop("disabled", true);
        }
    })
};

function isValidAddress() {
    return $("#address").val().length >= 7 && $("#address").val().length < 80;
}
