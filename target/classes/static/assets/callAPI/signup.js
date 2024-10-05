$(document).ready(function () {
  // Handle sign-up form submission
  $("#signUpForm").on("submit", function (e) {
    e.preventDefault(); // Prevent default form submission

    const name = $('[name="UserName"]').val(); // Corrected: Get username
    const phone = $('[name="Phone"]').val(); // Corrected: Get phone
    const email = $('[name="Email"]').val(); // Corrected: Get email
    const password = $('[name="Password"]').val(); // Corrected: Get password

    $.ajax({
      url: "/api/auth/register", // API endpoint for registration
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({
        name: name,
        phone: phone,
        email: email,
        password: password,
      }),
      success: function (response) {
        Swal.fire({
          title: "Đăng kí thành công!",
          text: "Chào mừng bạn trở lại!",
          icon: "success",
        }).then(() => {
          // Store JWT token in localStorage
          localStorage.setItem("token", response.token);
          // Redirect to the homepage
          window.location.href = "/";
        });
      },
      error: function (xhr) {
        let errorMessage = "Đã xảy ra lỗi. Vui lòng thử lại!";
        if (xhr.responseJSON && xhr.responseJSON.message) {
          errorMessage = xhr.responseJSON.message;
        } else if (xhr.status === 409) {
          errorMessage = "Email đã tồn tại!";
        }

        Swal.fire({
          title: "Đăng kí thất bại!",
          text: errorMessage,
          icon: "error",
        });
      },
    });
  });
});
