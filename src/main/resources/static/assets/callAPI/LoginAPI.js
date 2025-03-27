$(document).ready(function () {
  // Xử lý form login để lưu token vào localStorage
  $("#loginForm").on("submit", function (e) {
    e.preventDefault(); // Ngăn sự kiện gửi form mặc định

    const email = $('[name="username"]').val();
    const password = $('[name="password"]').val();

    $.ajax({
      url: "/api/auth/login", // API login
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({ email: email, password: password }),
      success: function (response) {
        Swal.fire({
          title: "Đăng nhập thành công!",
          text: "Chào mừng bạn trở lại!",
          icon: "success",
          timer: 1500
        }).then(() => {
          // Lưu JWT vào localStorage
          localStorage.setItem("token", response.token);
          // Điều hướng về trang chủ
          window.location.href = "/";
        });
      },
      error: function (xhr) {
        // Xử lý khi có lỗi
        let errorMessage = "Đã xảy ra lỗi. Vui lòng thử lại!";

        if (xhr.responseJSON && xhr.responseJSON.message) {
          errorMessage = xhr.responseJSON.message;
        } else if (xhr.status === 403) {
          errorMessage = "Tài khoản hoặc mật khẩu không chính xác!";
        } else if (xhr.status === 401) {
          // Token hết hạn hoặc không hợp lệ
          localStorage.removeItem("token"); // Xóa token
          Swal.fire({
            title: "Token hết hạn!",
            text: "Vui lòng đăng nhập lại.",
            icon: "warning",
          }).then(() => {
            window.location.href = "/login"; // Chuyển hướng đến trang đăng nhập
          });
          return; // Ngừng xử lý tiếp
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
