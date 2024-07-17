

$(document).ready(function(){
  $(".currency-format").inputmask("currency");  //static mask

  // show city field when state change

  $("#state").on('change', function () {
    var selectedState = $('#state :selected').val()
    $('#city').empty()
    $('#city').append("<option selected>Selecione uma Cidade</option>")
    $.ajax({
      url: "/cities/"+selectedState,
      success: function (result) {
        var citiesOptions = result.map(item =>
          "<option value=\""+item.fullName+ "\"" +">" + item.fullName + "</option>")
          $('#city').append(citiesOptions)

      }
    })

    $('#city-container').removeClass('d-none')
  })
});
