window.onload = function () {
    $("#review-inputs").on('input', function () {
        if (isValidReviewContent()) {
            $(".review-button").prop("disabled", false);
        } else {
            $(".review-button").prop("disabled", true)
        }
    })
};

function isValidReviewContent() {
    return $("#review-content").val().length > 2 && $("#review-content").val().length < 400;
}

function showRange() {
    document.getElementById("rate-value").innerHTML = document.getElementById("customRange3").value
}