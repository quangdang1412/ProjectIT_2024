$(document).ready(function () {
  // Handle sign-up form submission
  $("#signUpForm").on("submit", function (e) {
    e.preventDefault(); // Prevent default form submission

    const name = $('[name="UserName"]').val(); // Corrected: Get username
    const phone = $('[name="Phone"]').val(); // Corrected: Get phone
    const email = $('[name="Email"]').val(); // Corrected: Get email
    const password = $('[name="Password"]').val(); // Corrected: Get password
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: "btn btn-success",
        cancelButton: "btn btn-danger"
      },
    });
    swalWithBootstrapButtons.fire({
      title: "Bạn hãy xác nhận để tạo tài khoản",
      icon: "info",
      showCancelButton: true,
      confirmButtonText: "Xác nhận",
      cancelButtonText: "Hủy",
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
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
              localStorage.setItem("token", response.token);
              window.location.href = "/";
            });
          },
          error: function (xhr) {
            let errorMessage = "Đã xảy ra lỗi. Vui lòng thử lại!";
            if (xhr.responseJSON && xhr.responseJSON.message) {
              errorMessage = xhr.responseJSON.message;
            }
            else if (xhr.status === 409) {
              errorMessage = "Email đã tồn tại!";
            }
            Swal.fire({
              title: "Đăng kí thất bại!",
              text: errorMessage,
              icon: "error",
            });
          },
        });
      }
    });
  });
});
