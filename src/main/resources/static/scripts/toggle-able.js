// $(document).ready(function() {
//     $(".toggle-section").hide();
//     $(".toggle-initial").show();
//     $("input[name$='toggle']").click(function() {
//         var selection = $(this).val();
//         $(".toggle-section").hide();
//         $("#" + selection).show();
//     });
// });


$(document).ready(function() {

    $(".toggle-section").hide();
    $(".toggle-initial").show();

    $("input[name$='toggle']").click(function() {
        $(this).closest("section").children(".toggle-section").hide();
        $("#" + $(this).val()).show();
    });


    // For confirming actions on the site
    $(".confirm-action").each(function() {

        this.addEventListener("click", function(e) {

            var message = "Are you sure?"

            switch (this.id) {

                case "delete-item-button":
                    message = "Are you sure? This will decline or withdraw all active offers associated with this item.";
                    break;

                case "perm-delete-item-button":
                    message = "Are you sure? This will permanently remove the item and any offers associated with it from the site.";
                    break;

                case "accept-offer-button":
                    message = "Are you sure? Once you accept an offer, you cannot take it back.";
                    break;

                case "decline-offer-button":
                    message = "Are you sure you want to decline this offer?";
                    break;

                case "withdraw-offer-button":
                    message = "Are you sure you want to withdraw this offer?";
                    break;

            }

            // Continue or stop action
            if (confirm(message)) return true;
            else e.preventDefault();

        });
    });
});