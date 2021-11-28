window.onload = function () {
    $(".authorization-button").prop("disabled", true)

    $("#authorization-inputs").on('input', function () {
        if (isValidEmail() && isValidPassword()) {
            $(".authorization-button").prop("disabled", false);
        } else {
            $(".authorization-button").prop("disabled", true)
        }
    })
};

function isValidPassword() {
    return $("#password").val().length > 3 && $("#password").val().length < 45;
}

function isValidEmail() {
    let emailRegexp = /^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/
    return emailRegexp.test($("#email").val());
}