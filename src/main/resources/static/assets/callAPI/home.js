var token = localStorage.getItem("token");

$.ajax({
  url: "/", // API GET đến "/"
  method: "GET",
  headers: {
    Authorization: "Bearer " + token,
  },
  success: function (response) {
    var $responseHTML = $(response);

    var $newDropdownMenu = $responseHTML.find(".dropdown-menu").html();

    $("#dropdownContainer").html($newDropdownMenu);
  },
  error: function (xhr, status, error) {
    console.log("Error: " + error);
    localStorage.removeItem("token");
  },
});
