$(document).ready(function () {
  $("#logoutLink").on("click", function (e) {
    e.preventDefault(); // Ngăn hành động mặc định của liên kết

    const token = localStorage.getItem("token");

    if (!token) {
      Swal.fire({
        title: "Lỗi!",
        text: "Bạn chưa đăng nhập.",
        icon: "error",
      });
      return;
    }

    $.ajax({
      url: "/api/auth/logout",
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`, 
      },
      success: function (response) {
        Swal.fire({
          title: "Đăng xuất thành công!",
          text: response,
          icon: "success",
        }).then(() => {
          localStorage.removeItem("token"); 
          window.location.href = "/";
        });
      },
      error: function (xhr) {
        let errorMessage = "Đã xảy ra lỗi. Vui lòng thử lại!";
        if (xhr.responseJSON && xhr.responseJSON.message) {
          errorMessage = xhr.responseJSON.message;
        }

        Swal.fire({
          title: "Đăng xuất thất bại!",
          text: errorMessage,
          icon: "error",
        });
      },
    });
  });
});
