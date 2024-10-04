import { isTokenExpired } from "./checkToken.js";

var token = localStorage.getItem("token");

if (isTokenExpired(token)) {
  localStorage.removeItem("token");
} else {
  // Nếu token còn hiệu lực, gửi yêu cầu AJAX
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
      localStorage.removeItem("token"); // Xóa token khi có lỗi
      window.location.href = "/login"; // Chuyển đến trang đăng nhập
    },
  });
}
