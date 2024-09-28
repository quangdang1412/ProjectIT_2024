$(document).ready(function () {
  $("#loginForm").on("submit", function (e) {
    e.preventDefault(); // Ngăn sự kiện gửi form

    const email = $('[name="username"]').val();
    const password = $('[name="password"]').val();

    $.ajax({
      url: "/api/auth/login",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({ email: email, password: password }),
      success: function (response) {
        Swal.fire({
          title: "Đăng nhập thành công!",
          text: "Chào mừng bạn trở lại!",
          icon: "success",
        }).then(() => {
          localStorage.setItem("token", response.token);
          window.location.href = "/";
        });
      },
      error: function (xhr) {
        // Xử lý khi có lỗi
        let errorMessage = "";

        // Lấy thông điệp từ phản hồi JSON nếu có
        if (xhr.responseJSON && xhr.responseJSON.message) {
          errorMessage = xhr.responseJSON.message;
        }

        Swal.fire({
          title: "Đăng nhập thất bại!",
          text: errorMessage,
          icon: "error",
        });
      },
    });
  });
});
