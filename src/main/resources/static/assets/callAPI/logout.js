function logout() {
  console.log("Đăng xuất được gọi");

  fetch("/api/auth/logout", {
    method: "POST",
    headers: {
      Authorization: "Bearer " + localStorage.getItem("token"),
    },
  })
    .then((response) => {
      if (response.ok) {
        localStorage.removeItem("token");

        Swal.fire({
          title: "Thành công!",
          text: "Bạn đã đăng xuất thành công.",
          icon: "success",
          confirmButtonText: "OK",
        }).then(() => {
          window.location.href = "/";
        });
      } else {
        Swal.fire({
          title: "Lỗi!",
          text: "Có lỗi xảy ra trong quá trình đăng xuất!",
          icon: "error",
          confirmButtonText: "OK",
        });
      }
    })
    .catch((error) => {
      console.error("Error:", error);

      Swal.fire({
        title: "Lỗi!",
        text: "Không thể kết nối đến máy chủ!",
        icon: "error",
        confirmButtonText: "OK",
      });
    });
}
