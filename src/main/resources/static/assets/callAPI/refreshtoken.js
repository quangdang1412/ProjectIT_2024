let refreshInterval = 600000; // 10 phút
let inactivityTimeout = 900000; // 15 phút không hoạt động
let lastActivityTime = Date.now();

// Hàm cập nhật thời gian hoạt động cuối cùng
function resetLastActivityTime() {
  lastActivityTime = Date.now();
}

// Lắng nghe các sự kiện hoạt động của người dùng
window.addEventListener("mousemove", resetLastActivityTime);
window.addEventListener("click", resetLastActivityTime);
window.addEventListener("scroll", resetLastActivityTime);
window.addEventListener("keydown", resetLastActivityTime);

// Hàm kiểm tra hoạt động và gia hạn token
setInterval(() => {
  const currentTime = Date.now();

  // Kiểm tra xem người dùng có hoạt động không
  if (currentTime - lastActivityTime < inactivityTimeout) {
    extendToken(); // Chỉ gia hạn token nếu có hoạt động trong vòng 15 phút
  } else {
    // Nếu không có hoạt động, tự động đăng xuất
    console.warn("Không có hoạt động nào. Người dùng sẽ bị đăng xuất.");
    localStorage.removeItem("token"); // Xóa token
    // Hiển thị thông báo sử dụng SweetAlert2
    Swal.fire({
      icon: "warning",
      title: "Đã đăng xuất",
      text: "Bạn đã bị đăng xuất do không có hoạt động trong thời gian dài.",
      confirmButtonText: "Đồng ý",
    }).then(() => {
      // Chuyển hướng người dùng về trang đăng nhập
      window.location.href = "/login"; // Thay đổi URL cho phù hợp
    });
  }
}, refreshInterval);

// Hàm gia hạn token
function extendToken() {
  const currentToken = localStorage.getItem("token");

  if (!currentToken) {
    console.warn("Không có token hiện tại.");
    return;
  }

  fetch("/api/auth/refresh", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + currentToken,
    },
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        console.error("Không thể gia hạn token.", response);
        throw new Error("Không thể gia hạn token.");
      }
    })
    .then((data) => {
      // Lưu lại token mới và cập nhật lại thời gian hoạt động
      localStorage.setItem("token", data.token);
      resetLastActivityTime(); // Cập nhật lại thời gian hoạt động
      console.log("Token đã được gia hạn thành công.");
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("Có lỗi khi gia hạn token!");
    });
}
