$(document).ready(function()
{
    // prevent form submit on enter
    $(document).on("keypress", 'form', function(e)
    {
        var code = e.keyCode || e.which;
        if(code === 13)
        {
            e.preventDefault();
            return false;
        }
    });
});

function validateForm()
{
    // handle account matches
    var accountSourcesIDs = $('.account-source-id');
    var accountSourcesNames = $('.account-source');

    var accountDestinations = $('select.account-destination');
    var parent = document.getElementById("hidden-account-matches");

    for(var i = 0; i < accountSourcesIDs.length; i++)
    {
        var inputSourceID = document.createElement("input");
        inputSourceID.setAttribute("type", "hidden");
        inputSourceID.setAttribute("name", "accountMatches[" + i + "].accountSource.ID");
        inputSourceID.setAttribute("value", accountSourcesIDs[i].innerText);
        parent.appendChild(inputSourceID);

        var inputSourceName = document.createElement("input");
        inputSourceName.setAttribute("type", "hidden");
        inputSourceName.setAttribute("name", "accountMatches[" + i + "].accountSource.name");
        inputSourceName.setAttribute("value", accountSourcesNames[i].innerText);
        parent.appendChild(inputSourceName);

        var value = accountDestinations[i].value;

        var inputDestinationID = document.createElement("input");
        inputDestinationID.setAttribute("type", "hidden");
        inputDestinationID.setAttribute("name", "accountMatches[" + i + "].accountDestination.ID");
        inputDestinationID.setAttribute("value", value);
        parent.appendChild(inputDestinationID);

        var inputDestinationName = document.createElement("input");
        inputDestinationName.setAttribute("type", "hidden");
        inputDestinationName.setAttribute("name", "accountMatches[" + i + "].accountDestination.name");
        inputDestinationName.setAttribute("value", accountDestinations[i].querySelector('option[value="' + value + '"').innerText);
        parent.appendChild(inputDestinationName);
    }

    return true;
}