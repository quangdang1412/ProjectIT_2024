document.addEventListener("DOMContentLoaded", function () {
  var status = document.getElementById("status").textContent; // Lấy trạng thái thanh toán
  var cancel = document.getElementById("cancel").textContent; // Lấy trạng thái hủy

  // Khai báo biến cho thông báo
  let message = "";
  let title = "";

  // Kiểm tra trạng thái thanh toán
  if (status === "CANCELLED" && cancel === "true") {
    title = "Thanh toán không thành công!";
    message = "Vui lòng kiểm tra lại thông tin thanh toán của bạn.";
    Swal.fire({
      icon: "error",
      title: title,
      text: message,
    });
  } else if (status === "PAID" && cancel === "false") {
    title = "Thanh toán thành công!";
    message = "Cảm ơn bạn đã thanh toán!";

    // Hiển thị thông báo thành công
    Swal.fire({
      icon: "success",
      title: title,
      text: message,
    }).then(() => {
      // Gọi API PUT khi người dùng đóng thông báo
      const orderId = localStorage.getItem("orderId"); // Lấy orderId từ localStorage

      // Kiểm tra xem orderId có tồn tại không
      if (orderId) {
        const url = `http://localhost:8080/api/payments/updatePaymentStatus/${orderId}`;

        fetch(url, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error("Mã phản hồi không hợp lệ");
            }
            return response.json();
          })
          .then((data) => {
            console.log("Cập nhật trạng thái thanh toán thành công:", data);
          })
          .catch((error) => {
            console.error("Lỗi khi cập nhật trạng thái thanh toán:", error);
          });
      } else {
        console.error("orderId không tồn tại trong localStorage.");
      }
    });
  }
});
