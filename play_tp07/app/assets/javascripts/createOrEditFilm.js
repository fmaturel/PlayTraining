$('.acteur_template').hide();

$(document).on('click', '.removeActor', function (e) {
    $(this).parents('.acteurSecondaire').remove();
    renumber();
});

$(document).on('click', '.addActor', function (e) {
    $('.acteurSecondairesList').append('<li class="acteurSecondaire">' + $('.acteur_template').html() + '</li>');
    renumber();
});

$('#form').submit(function () {
    $('.acteur_template').remove();
});

// Renumber fields: rename fields to have a coherent payload like:
// acteurSecondaires[0].prenom
// acteurSecondaires[0].nom
var renumber = function () {
    $('.acteurSecondaire').each(function (i) {
        $('.count', this).each(function () {
            $(this).html(i + 1);
        });
        $('input', this).each(function () {
            $(this).attr('name', $(this).attr('name').replace(/acteurSecondaires\[.+?\]/g, 'acteurSecondaires[' + i + ']'));
        });
    });
};