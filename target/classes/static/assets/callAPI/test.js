// app.js
let requestSent = false;

document.addEventListener("DOMContentLoaded", function () {
  if (!requestSent) {
    requestSent = true; // Đánh dấu rằng yêu cầu đã được gửi

    // Gửi yêu cầu cho trang "/test"
    sendAjaxRequest("GET", "/test")
      .then((html) => {
        // Thay thế toàn bộ nội dung của phần body bằng HTML mới
        document.open(); // Xóa nội dung hiện tại
        document.write(html); // Ghi HTML mới vào trang
        document.close(); // Đóng và hoàn thành cập nhật
      })
      .catch((error) => {
        console.error("Lỗi:", error);
        if (error.message.includes("401")) {
          window.location.href = "/login"; // Chuyển hướng đến trang đăng nhập
        }
      });
  }
});
