window.onload = function () {
    $("#replenishment-button").prop("disabled", true)

    $("#replenishment-inputs").on('input', function () {
        if (isValidAmount()) {
            $("#replenishment-button").prop("disabled", false);
        } else {
            $("#replenishment-button").prop("disabled", true)
        }
    })
};

function isValidAmount() {
    return $("#amount").val() > 1 && $("#amount").val() <= 10000;
}