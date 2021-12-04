window.onload = function () {
    $("#replenishment-inputs").on('input', function () {
        if (isValidAmount()) {
            $("#replenishment-button").prop("disabled", false);
        } else {
            $("#replenishment-button").prop("disabled", true)
        }
    })
};

function isValidAmount() {
    return $("#amount").val() > 1 && $("#amount").val() <= 150000;
}