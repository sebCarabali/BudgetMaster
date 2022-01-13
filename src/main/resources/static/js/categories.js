$(document).ready(function()
{
    if($('#category-name').length)
    {
        document.getElementById('category-name').focus();
    }

    $('.category-color').click(function()
    {
        removeActive();
        addClass($(this)[0], "category-color-active");
        document.getElementById("categoryColor").value = rgb2hex($(this)[0].style.backgroundColor);
    });

    if($("#customColorPickerContainer").length)
    {
        let colorPickerParent = document.getElementById('customColorPickerContainer');

        let colorPicker = new Picker({
            parent: colorPickerParent,
            popup: 'bottom',
            alpha: false,
            editor: true,
            editorFormat: 'hex',
            cancelButton: false,
            color: colorPickerParent.style.backgroundColor,
            onChange: function(color) {
                colorPickerParent.style.backgroundColor = color.hex;
            },
            onClose: function(color) {
                removeActive();
                addClass(colorPickerParent, "category-color-active");
                colorPickerParent.style.backgroundColor = color.hex;
                document.getElementById("categoryColor").value = color.hex;
            }
        });
    }

    $('.button-request-delete-category').click(function()
    {
        fetchAndShowModalContent(this.dataset.url, '#deleteModalContainerOnDemand', '#modalConfirmDelete', function(){
            initCustomSelects();

            $('#buttonDeleteCategory').click(function()
            {
                document.getElementById("formDestinationCategory").submit();
            });
         });
    });
});

function removeActive()
{
    let colors = document.getElementsByClassName("category-color");
    for(let i = 0; i < colors.length; i++)
    {
        removeClass(colors[i], "category-color-active");
    }
}
