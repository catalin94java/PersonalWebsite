$('#credential_1, #confirm-credential_1').on('keyup', function () {
  if ($('#credential_1').val() == $('#confirm-credential_1').val()) {
    $('#pass-message').html('Matching').css('color', 'green');
  } else 
    $('#pass-message').html('Not Matching').css('color', 'red');
});