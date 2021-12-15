window.onload = function () {
    $(".reg-inputs").on('input', function () {
        if (isValidName() && isValidSurname() && isValidEmail() && isValidBirthDate()) {
            $(".authorization-button").prop("disabled", false);
        } else {
            $(".authorization-button").prop("disabled", true)
        }
    })
};

function isValidBirthDate() {
    return new Date(($("#birth-date").val())).getFullYear() <= 2007 && new Date(($("#birth-date").val())).getFullYear() >= 1921;
}

function isValidEmail() {
    let emailRegexp = /^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/
    return emailRegexp.test($("#email").val()) && $("#email").val().length <= 320;
}

function isValidName() {
    return $("#name").val().length > 1 && $("#name").val().length < 45;
}

function isValidSurname() {
    return $("#surname").val().length > 1 && $("#surname").val().length < 45;
}
