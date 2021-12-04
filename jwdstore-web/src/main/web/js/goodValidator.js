window.onload = function () {
    $("#good-inputs").on('input', function () {
        console.log("Test");
        if (isValidName() && isValidType() && isValidDescription() && isValidPrice()) {
            $("#good-button").prop("disabled", false);
        } else {
            $("#good-button").prop("disabled", true)
        }
    })
};

function isValidName() {
    return $("#name").val().length > 2 && $("#name").val().length < 60;
}

function isValidType() {
    return document.querySelector('#type').value > 0;
}

function isValidPrice() {
    return $("#price").val() > 1 && $("#price").val() < 1000000;
}

function isValidDescription() {
    return $("#description").val().length > 10 && $("#description").val().length < 400;
}